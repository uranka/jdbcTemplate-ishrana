package com.jelena.ishrana.repository.jdbc;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.repository.NamirnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

// po ugledu na
// https://gitlab.levi9.com/d.gajic/code9/blob/master/tutorial/workshop3/lab11-end/src/main/java/rs/code9/taster/repository/jdbc/JdbcCategoryRepository.java


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
    public List<Namirnica> findByCategory(String category) {
        return null;
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

        //testiram sta se desava kada imam dve namirnice u listi
        //na jednu nadjenu dodajem jos i ovu
        //Namirnica testNamirnica = new Namirnica();
        //listaNamirnica.add(testNamirnica);

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
        jdbcTemplate.update("replace into namirnice (namirnica_id, naziv, kcal, p, m, uh, kategorija) values(?, ?, ?, ?, ?, ?, ?)",
                namirnica.getNamirnica_id(), namirnica.getNaziv(), namirnica.getKcal(), namirnica.getP(), namirnica.getM(), namirnica.getUh(), namirnica.getKategorija());
        return namirnica;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {

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
