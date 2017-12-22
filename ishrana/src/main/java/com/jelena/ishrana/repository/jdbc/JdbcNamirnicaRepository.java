package com.jelena.ishrana.repository.jdbc;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.repository.NamirnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;



@Repository
public class JdbcNamirnicaRepository implements NamirnicaRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Namirnica> findAll() {
        return jdbcTemplate.query("select * from namirnice",
                new NamirnicaRowMapper());
    }

    @Override
    public List<Namirnica> findAll(int firstRow, int rowCount) {
        return jdbcTemplate.query("SELECT * FROM namirnice\n" +
                        "ORDER BY naziv\n" +
                        "LIMIT ? OFFSET ?",
                new Object[] { rowCount, firstRow },
                new NamirnicaRowMapper());
    }

    @Override
    public List<Namirnica> findByCategory(String category) {
        return jdbcTemplate.query("select * from namirnice where kategorija = ?",
                new Object[] { category },
                new NamirnicaRowMapper());
    }

    //neproverena metoda, potrebna za paginaciju kategorije nam.
    @Override
    public List<Namirnica> findByCategory(String category, int firstRow, int rowCount) {
        return jdbcTemplate.query("SELECT * \n" +
                        "FROM namirnice\n" +
                        "WHERE kategorija = ?\n" +
                        "ORDER BY naziv\n" +
                        "LIMIT ? OFFSET ?",
                new Object[] { category, rowCount, firstRow },
                new NamirnicaRowMapper());
    }

    // vraca null ako nema namirnice sa datim id u bazi
    // ako pronadje vise od jedne namirnice sa datim id baca izuzetak IncorrectResultSizeDataAccessException
    // hvatam ga u gde
    // ako nadje tacno jednu namirnicu sa datim id vraca objekat Namirnica koji predstavlja tu namirnicu
    @Override
    public Namirnica findOne(Long id) /*throws IncorrectResultSizeDataAccessException */ {
        System.out.println("inside jdbc findOne");

        List<Namirnica> listaNamirnica = jdbcTemplate.query("select * from namirnice where namirnica_id = ?",
                new Object[] { id },
                new NamirnicaRowMapper());

        if (listaNamirnica.size() == 1) {
            return listaNamirnica.get(0);
        }
        //IncorrectResultSizeDataAccessException(int expectedSize, int actualSize)
        if (listaNamirnica.size() > 1){
            throw new IncorrectResultSizeDataAccessException(1, listaNamirnica.size());
        }
        return null;
    }


    @Override
    public Namirnica save(Namirnica namirnica) {
        if (namirnica.getNamirnica_id() != null) {
            // update old namirnica
            PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                    "UPDATE namirnice \n" +
                            "SET naziv = ?, kcal = ?, p = ?, m = ?, uh = ?, kategorija = ?\n" +
                            "WHERE namirnica_id = ?",
                    new int[] {Types.VARCHAR, Types.FLOAT, Types.FLOAT, Types.FLOAT, Types.FLOAT,
                            Types.VARCHAR, Types.BIGINT });

            PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                    new Object[] {namirnica.getNaziv(), namirnica.getKcal(), namirnica.getP(), namirnica.getM(), namirnica.getUh(),
                    namirnica.getKategorija(), namirnica.getNamirnica_id()});

            jdbcTemplate.update(psc);
        }
        else {
            // save new namirnica
            PreparedStatementCreatorFactory pscFactory2 = new PreparedStatementCreatorFactory(
                    "INSERT INTO namirnice (naziv, kcal, p, m, uh, kategorija) VALUES\n" +
                            "(?, ?, ?, ?, ?, ?)\n",
                    new int[] {Types.VARCHAR, Types.FLOAT, Types.FLOAT, Types.FLOAT, Types.FLOAT, Types.VARCHAR});
            pscFactory2.setReturnGeneratedKeys(true);

            KeyHolder holder = new GeneratedKeyHolder();
            PreparedStatementCreator psc2 = pscFactory2.newPreparedStatementCreator(
                    new Object[] {namirnica.getNaziv(), namirnica.getKcal(), namirnica.getP(), namirnica.getM(), namirnica.getUh(),
                            namirnica.getKategorija()});

            jdbcTemplate.update(psc2, holder);

            // snimi id namirnice
            namirnica.setNamirnica_id(holder.getKey().longValue());
        }
        System.out.println("namirnica iz metode save u bazu " + namirnica);
        return namirnica;
    }



    @Override
    public void remove(Long id) {
        System.out.println("inside remove namirnica klase JdbcNamirnicaRepository");
        jdbcTemplate
                .update("delete from  namirnice where namirnica_id = ?", id); // moze da baci DataIntegrityViolationException

    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM namirnice", Integer.class);
    }

    @Override
    public int count(String category) {
        return jdbcTemplate.queryForObject("SELECT count(*)\n" +
                "FROM namirnice\n" +
                "WHERE kategorija = ?",
                new Object[] { category },
                Integer.class);
    }


    private static final class NamirnicaRowMapper implements RowMapper<Namirnica> {
        @Override
        public Namirnica mapRow(ResultSet rs, int rowNum)throws SQLException {
            Namirnica namirnica = new Namirnica();
            namirnica.setNamirnica_id(rs.getLong("namirnica_id"));
            namirnica.setNaziv(rs.getString("naziv"));
            namirnica.setKcal(rs.getDouble("kcal"));
            namirnica.setP(rs.getDouble("p"));
            namirnica.setM(rs.getDouble("m"));
            namirnica.setUh(rs.getDouble("uh"));
            namirnica.setKategorija(rs.getString("kategorija"));
            return namirnica;
        }
    }
}
