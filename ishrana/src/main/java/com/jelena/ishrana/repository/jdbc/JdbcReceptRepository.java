package com.jelena.ishrana.repository.jdbc;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
        jdbcTemplate.query("SELECT r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, rn.namirnica_id, rn.kolicina_namirnice,\n" +
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
        jdbcTemplate.query("SELECT r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, rn.namirnica_id, rn.kolicina_namirnice,\n" +
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
        if (recept.getRecept_id() != null) {
            // Update polja naziv, vreme pripreme, vreme kuvanja (tabela recepti):
            PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                    "update recepti \n" +
                            "set naziv = ?, vreme_pripreme = ?, vreme_kuvanja = ?\n" +
                            "where recept_id = ?",
                    new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BIGINT});

            //KeyHolder holder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                    new Object[] {recept.getNaziv(), recept.getVremePripreme(),
                    recept.getVremeKuvanja(), recept.getRecept_id()});

            jdbcTemplate.update(psc/*, holder*/);

            //Obrisati sve postojece namirnice vezane za recept, pa insertovati nove iz argumenta recept
            updateNamirnice(recept);
        }
        else {
            // snimanje novog recepta
            // snimanje naziva, vremena pripreme, vremena kuvanja
            PreparedStatementCreatorFactory pscFactory2 = new PreparedStatementCreatorFactory(
                    "INSERT INTO recepti (naziv, vreme_pripreme, vreme_kuvanja)\n" +
                            "VALUES (?, ?, ?)",
                    new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER});
            pscFactory2.setReturnGeneratedKeys(true);

            KeyHolder holder = new GeneratedKeyHolder();
            PreparedStatementCreator psc2 = pscFactory2.newPreparedStatementCreator(
                    new Object[] {recept.getNaziv(), recept.getVremePripreme(), recept.getVremeKuvanja()});

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
}
