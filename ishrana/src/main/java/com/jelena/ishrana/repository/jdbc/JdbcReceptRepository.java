package com.jelena.ishrana.repository.jdbc;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcReceptRepository implements ReceptRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Recept> findAll() {
        ReceptRowCallbackHandler receptRowCallbackHandler =
                new ReceptRowCallbackHandler();
        jdbcTemplate.query("SELECT r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, rn.namirnica_id, rn.kolicina_namirnice,\n" +
                        "n.naziv, n.kcal, n.p, n.m, n.uh, n.kategorija\n" +
                        "FROM recepti r JOIN recepti_namirnice rn\n" +
                        "ON r.recept_id = rn.recept_id\n" +
                        "JOIN namirnice n\n" +
                        "ON n.namirnica_id = rn.namirnica_id\n",
                receptRowCallbackHandler);
        return receptRowCallbackHandler.getRecepti();
    }

    @Override
    public Recept findOne(Long id) {
        ReceptRowCallbackHandler receptRowCallbackHandler =
                new ReceptRowCallbackHandler();
        jdbcTemplate.query("select r.naziv, r.recept_id, r.vreme_pripreme, r.vreme_kuvanja, rn.namirnica_id, rn.kolicina_namirnice,\n" +
                "n.naziv, n.kcal, n.p, n.m, n.uh, n.kategorija\n" +
                "from recepti r join recepti_namirnice rn\n" +
                "on r.recept_id = rn.recept_id\n" +
                "join namirnice n\n" +
                "on n.namirnica_id = rn.namirnica_id\n" +
                "where r.recept_id = ?",
                new Object[] { id },
                receptRowCallbackHandler);
        return receptRowCallbackHandler.getRecept();
    }

    @Override
    public Recept save(Recept recept) {
        return null;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {

    }

    @Override
    public void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException {

    }

    @Override
    public void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina) {

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
            namirnica.setNaziv(rs.getString("n.naziv"));
            namirnica.setKcal(rs.getDouble("n.kcal"));
            namirnica.setP(rs.getDouble("n.p"));
            namirnica.setM(rs.getDouble("n.m"));
            namirnica.setUh(rs.getDouble("n.uh"));
            namirnica.setKategorija(rs.getString("n.kategorija"));
            List<Namirnica> listaNamirnica = currentRecept.getListaNamirnica();
            listaNamirnica.add(namirnica);
            currentRecept.setListaNamirnica(listaNamirnica);

            Integer kolicina = rs.getInt("rn.kolicina_namirnice");
            List<Integer> listaKolicina = currentRecept.getListaKolicina();
            listaKolicina.add(kolicina);
            currentRecept.setListaKolicina(listaKolicina);

            System.out.println("currentRecept = " + currentRecept);
            System.out.println("brojacRecepta = " + brojacRecepta);
            // zamenjuje recept na poziciji brojacRecepta sa trenutnim receptom (update recepta)
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
