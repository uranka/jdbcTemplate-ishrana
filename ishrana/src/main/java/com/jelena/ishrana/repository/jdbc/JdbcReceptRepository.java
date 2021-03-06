package com.jelena.ishrana.repository.jdbc;


import com.jelena.ishrana.exceptions.NoImageReaderException;
import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Repository
public class JdbcReceptRepository implements ReceptRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

// ova varijanta moze da prikaze i recepte koji nemaju svoje namirnice
    @Override
    public List<Recept> findAll() {
        ReceptRowCallbackHandler receptRowCallbackHandler =
                new ReceptRowCallbackHandler();
        jdbcTemplate.query("SELECT r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, r.slika, rn.namirnica_id, rn.kolicina_namirnice,\n" +
                        "n.naziv, n.kcal, n.p, n.m, n.uh, n.kategorija\n" +
                        "FROM recepti r LEFT OUTER JOIN recepti_namirnice rn\n" +
                        "ON r.recept_id = rn.recept_id\n" +
                        "LEFT OUTER JOIN namirnice n\n" +
                        "ON n.namirnica_id = rn.namirnica_id\n" +
                        "ORDER BY r.recept_id\n",
                receptRowCallbackHandler);
        return receptRowCallbackHandler.getRecepti();
    }

    // varijanta koja pronalazi recept iako recept nema nijednu namirnicu
    @Override
    public Recept findOne(Long id) {
        ReceptRowCallbackHandler receptRowCallbackHandler =
                new ReceptRowCallbackHandler();
        jdbcTemplate.query("SELECT r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, r.slika, rn.namirnica_id, rn.kolicina_namirnice,\n" +
                        "n.naziv, n.kcal, n.p, n.m, n.uh, n.kategorija\n" +
                        "FROM recepti r LEFT OUTER JOIN recepti_namirnice rn\n" +
                        "ON r.recept_id = rn.recept_id\n" +
                        "LEFT OUTER JOIN namirnice n\n" +
                        "ON n.namirnica_id = rn.namirnica_id\n" +
                        "WHERE r.recept_id = ?",
                new Object[] { id },
                receptRowCallbackHandler);
        return receptRowCallbackHandler.getRecept();
    }

    @Override
    public Recept save(Recept recept) {
        System.out.println("inside save method JdbcReceptRepository class");
/*
        Object s = null;
        if (isJpeg(recept.getSlika())) {
            LobHandler lobHandler = new DefaultLobHandler();
            s = new SqlLobValue(recept.getSlika(), lobHandler);
        }
*/
        LobHandler lobHandler = new DefaultLobHandler();
        Object s = new SqlLobValue(recept.getSlika(), lobHandler);

        if (recept.getRecept_id() != null) {

            System.out.println("updejtujem recept s=" + s);

            // Update polja naziv, vreme pripreme, vreme kuvanja i slike(tabela recepti):
            PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                    "UPDATE recepti \n" +
                            "SET naziv = ?, vreme_pripreme = ?, vreme_kuvanja = ?, slika = ? \n" +
                            "WHERE recept_id = ?",
                    new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BLOB, Types.BIGINT});


            PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                    new Object[]{recept.getNaziv(), recept.getVremePripreme(),
                            recept.getVremeKuvanja(), s,
                            recept.getRecept_id()});

            jdbcTemplate.update(psc);
/*

                if (s == null) {
                    // Update polja naziv, vreme pripreme, vreme kuvanja , ali ne i update slike -BUDZENJE
                    // jer ja ne mogu iz forme da bajndujem sliku koja nije dosla preko upload-a
                    // i to sto nema bajndovane slike ne mora da znaci da nema slike,
                    // nego da je ima i da tu staru treba ostaviti
                    PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                            "UPDATE recepti \n" +
                                    "SET naziv = ?, vreme_pripreme = ?, vreme_kuvanja = ? \n" +
                                    "WHERE recept_id = ?",
                            new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BIGINT});


                    PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                            new Object[]{recept.getNaziv(), recept.getVremePripreme(),
                                    recept.getVremeKuvanja(),
                                    recept.getRecept_id()});

                    jdbcTemplate.update(psc);
                }
                else {

                    // Update polja naziv, vreme pripreme, vreme kuvanja i slike(tabela recepti):
                    PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                            "UPDATE recepti \n" +
                                    "SET naziv = ?, vreme_pripreme = ?, vreme_kuvanja = ?, slika = ? \n" +
                                    "WHERE recept_id = ?",
                            new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BLOB, Types.BIGINT});


                    PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                            new Object[]{recept.getNaziv(), recept.getVremePripreme(),
                                    recept.getVremeKuvanja(), s,
                                    recept.getRecept_id()});

                    jdbcTemplate.update(psc);
                }
*/

                //Obrisati sve postojece namirnice vezane za recept, pa insertovati nove iz argumenta recept
                updateNamirnice(recept);
        }
        else {
                // snimanje novog recepta
                System.out.println("snimam novi recept");

                // snimanje naziva, vremena pripreme, vremena kuvanja
                PreparedStatementCreatorFactory pscFactory2 = new PreparedStatementCreatorFactory(
                        "INSERT INTO recepti (naziv, vreme_pripreme, vreme_kuvanja, slika)\n" +
                                "VALUES (?, ?, ?, ?)",
                        new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BLOB});
                pscFactory2.setReturnGeneratedKeys(true);

                KeyHolder holder = new GeneratedKeyHolder();
                PreparedStatementCreator psc2 = pscFactory2.newPreparedStatementCreator(
                        new Object[]{recept.getNaziv(), recept.getVremePripreme(), recept.getVremeKuvanja(), s});

                jdbcTemplate.update(psc2, holder);

                // snimi recept_id u recept
                recept.setRecept_id(holder.getKey().longValue());

                // snimanje lista namirnica i njihovih kolicina
                // pozvacu metodu updateNamirnice, jest da ce dzabe da brise
                updateNamirnice(recept);
        }

        // metoda save treba da vrati snimljeni recept
        System.out.println("recept iz metode save u bazu " + recept);
        return recept;
    }

    private void updateNamirnice(Recept recept) {
        // brise listu svih namirnica i njihovih kolicina
        jdbcTemplate.update("DELETE FROM recepti_namirnice\n" +
                "WHERE recept_id = ?",
                recept.getRecept_id());

        // postavlja novu listu namirnica i kolicina
        PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO recepti_namirnice (recept_id, namirnica_id, kolicina_namirnice)\n" +
                        "VALUES (?, ?, ?)",
                new int[] {Types.BIGINT, Types.BIGINT, Types.INTEGER});
        pscFactory.setReturnGeneratedKeys(true);

        for (int i = 0; i < recept.getListaNamirnica().size(); i++) {
            KeyHolder holder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                new Object[]{recept.getRecept_id(), recept.getListaNamirnica().get(i).getNamirnica_id(),
                        recept.getListaKolicina().get(i)});
            jdbcTemplate.update(psc, holder);
        }
    }

/*
    private boolean isImageFormatJpeg(InputStream is) throws IOException, NoImageReaderException  {
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        // get all currently registered readers that recognize the image format
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            throw new NoImageReaderException ("No readers found says isImageFormatJpeg function!");
        }

        System.out.println("Readers found");
        // get the first reader
        ImageReader reader = iter.next();
        String format = reader.getFormatName();
        //iis.close();
        return format.equalsIgnoreCase("JPEG")? true : false;
    }

    private boolean isJpeg(byte[] slika) {
        boolean isJpeg = false;
        if (slika != null) { // imam neki niz bajtova za sliku

            // utvrdjivanje da li je fajl slika tipa jpg
            try {
                InputStream fileContent = new ByteArrayInputStream(slika);
                isJpeg = isImageFormatJpeg(fileContent); // isImageFormatJpeg pokvari input stream
                //fileContent = new ByteArrayInputStream(recept.getSlika());
                System.out.println("ending try jpeg");
            } catch (NoImageReaderException e) {
                System.out.println(e.getMessage());
                System.out.println("ending no image reader catch jpeg");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isJpeg;
    }
*/

    @Override
    public void removeSlika(Long recept_id){
        System.out.println("uklanjam sliku iz recepta");

        jdbcTemplate.update("UPDATE recepti \n " +
                            "SET slika = NULL \n" +
                            "WHERE recept_id = ?",
                            recept_id);

    }

    // ako imamo jpg sliku snimiti je u bazu, a ako nemamo nista ne diraj
    @Override
    public void addSlika(Long recept_id, byte[] slika) {
        System.out.println("snimam sliku u recept");
/*
        Object s = null;
        if (isJpeg(slika)) {
            LobHandler lobHandler = new DefaultLobHandler();
            s = new SqlLobValue(slika, lobHandler);
        }
*/
        LobHandler lobHandler = new DefaultLobHandler();
        Object s = new SqlLobValue(slika, lobHandler);

        PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                "UPDATE recepti \n" +
                        "SET slika = ? \n" +
                        "WHERE recept_id = ?",
                new int[]{Types.BLOB, Types.BIGINT});

        PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                new Object[]{s, recept_id});

        jdbcTemplate.update(psc);
    }


    @Override
    public void remove(Long id) throws IllegalArgumentException {
        //brise listu namirnica i njihovih kolicina iz tabele recepti_namirnice
        jdbcTemplate.update("DELETE FROM recepti_namirnice\n" +
                        "WHERE recept_id = ?",
                        id);

        // brise recept_id, naziv, vreme pripreme, vreme kuvanja iz tabele recepti
        jdbcTemplate.update("DELETE FROM recepti\n" +
                "WHERE recept_id = ?",
                id);
    }

    @Override
    public void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException {
        if (namirnica_id == null || namirnica_id <= 0) {
            throw new IllegalArgumentException("non existing namirnica_id: " + namirnica_id);
        }
        List<Namirnica> listaNamirnica = recept.getListaNamirnica();

        //System.out.println(listaNamirnica);
        for(int i = 0; i < listaNamirnica.size(); i++) {
            if (listaNamirnica.get(i).getNamirnica_id().equals(namirnica_id) ) {
                recept.getListaNamirnica().remove(i);
                recept.getListaKolicina().remove(i);
                break;
            }
        }
    }

    @Override
    public void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina) {
        recept.getListaNamirnica().add(namirnica);
        if (kolicina == null) {
            kolicina = 0;
        }
        recept.getListaKolicina().add(kolicina);
    }

    @Override
    public Map<String, Double> analizaRecepta(Long id){
        ReceptAnalizaRowCallbackHandler receptAnalizaRowCallbackHandler =
                new ReceptAnalizaRowCallbackHandler();
        jdbcTemplate.query("SELECT n.kcal*rn.kolicina_namirnice/100 AS kcal," +
                        "n.p*rn.kolicina_namirnice/100 AS p," +
                        "n.m*rn.kolicina_namirnice/100 AS m," +
                        "n.uh*rn.kolicina_namirnice/100 AS uh\n" +
                        "FROM namirnice n JOIN recepti_namirnice rn\n" +
                        "ON n.namirnica_id = rn.namirnica_id\n" +
                        "WHERE recept_id = ?",
                new Object[] {id},
                receptAnalizaRowCallbackHandler);
        return receptAnalizaRowCallbackHandler.getMap();
    }


    private static final class ReceptRowCallbackHandler implements RowCallbackHandler {
        private List<Recept> result = new ArrayList<>();
        private Recept currentRecept = null;
        private int brojacRecepta = 0;

        public void processRow(ResultSet rs) throws SQLException {

            final Long receptId = rs.getLong("r.recept_id");
            if ( currentRecept == null ||
                     !receptId.equals(currentRecept.getRecept_id()))  {

                currentRecept = new Recept();
                //postavi id recepta, naziv recepta, vreme pripreme i vreme kuvanja
                currentRecept.setRecept_id(receptId);
                currentRecept.setNaziv(rs.getString("r.naziv"));
                currentRecept.setVremePripreme(rs.getInt("r.vreme_pripreme"));
                currentRecept.setVremeKuvanja(rs.getInt("r.vreme_kuvanja"));

                Blob blob = rs.getBlob("r.slika");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    byte[] blobAsBytes = blob.getBytes(1, blobLength);
                    currentRecept.setSlika(blobAsBytes);
                }


                // dodaj recept u listu recepata
                result.add(currentRecept);
                brojacRecepta++;
            }

            // postavlja jednu namirnicu u listu namirnica i kolicinu te nam. u listu kolicina
            // jer processRow obradjuje samo jedan red rezultata upita

            Namirnica namirnica = new Namirnica();
            namirnica.setNamirnica_id(rs.getLong("rn.namirnica_id"));
            System.out.println("postavljeni id namirnice je " + namirnica.getNamirnica_id()); // ovde stavlja 0, a u bazi imam null!!!!
            // getLong returns the column value; if the value is SQL NULL, the value returned is 0 !!!

            namirnica.setNaziv(rs.getString("n.naziv"));
            namirnica.setKcal(rs.getDouble("n.kcal"));
            namirnica.setP(rs.getDouble("n.p"));
            namirnica.setM(rs.getDouble("n.m"));
            namirnica.setUh(rs.getDouble("n.uh"));
            namirnica.setKategorija(rs.getString("n.kategorija"));
            List<Namirnica> listaNamirnica = currentRecept.getListaNamirnica();
            // ako je namirnica u bazi bila null ne dodavaj je u listu, isto i za njenu kolicinu
            if (namirnica.getNamirnica_id() != 0) {
                listaNamirnica.add(namirnica);
            }
            currentRecept.setListaNamirnica(listaNamirnica);

            Integer kolicina = rs.getInt("rn.kolicina_namirnice");
            List<Integer> listaKolicina = currentRecept.getListaKolicina();
            if (namirnica.getNamirnica_id() != 0) {
                listaKolicina.add(kolicina);
            }
            currentRecept.setListaKolicina(listaKolicina);

            System.out.println("currentRecept = " + currentRecept);
            System.out.println("brojacRecepta = " + brojacRecepta);
            // zamenjuje recept na poziciji brojacRecepta - 1 sa trenutnim receptom (update recepta)
            result.set(brojacRecepta - 1, currentRecept);
        }

        public Recept getRecept() {
            if (result.isEmpty()) {
                System.out.println("result set is empty");
                return null;
            }
            if (result.size() > 1) {
                throw new IllegalStateException("More than one recept in a result.");
            }
            return result.get(0);
        }

        public List<Recept> getRecepti() {
            return result;
        }
    }


    private static final class ReceptAnalizaRowCallbackHandler implements RowCallbackHandler {
        private Map<String, Double> map = new HashMap<>();
        private Double kcalUkupno = 0.0;
        private Double pUkupno = 0.0;
        private Double mUkupno = 0.0;
        private Double uhUkupno = 0.0;

        private Double kcalUkupnoKorigovano;

        public void processRow(ResultSet rs) throws SQLException {
            kcalUkupno += rs.getDouble("kcal"); // kcal prema kolicini namirnice
            pUkupno += rs.getDouble("p"); // proteini za datu kolicinu namirnice
            mUkupno += rs.getDouble("m");
            uhUkupno += rs.getDouble("uh");
        }

        public Map<String, Double> getMap() {
            // postavljam podatke u mapu
            map.put("kcalUkupno", kcalUkupno);
            map.put("pUkupno", pUkupno);
            map.put("mUkupno", mUkupno);
            map.put("uhUkupno", uhUkupno);
            kcalUkupnoKorigovano = pUkupno*4.1 + mUkupno*9.3 + uhUkupno*4.1;
            map.put("pProcenti", pUkupno*4.1*100/kcalUkupnoKorigovano);
            map.put("mProcenti", mUkupno*9.3*100/kcalUkupnoKorigovano);
            map.put("uhProcenti", uhUkupno*4.1*100/kcalUkupnoKorigovano);
            return map;
        }
    }
}
