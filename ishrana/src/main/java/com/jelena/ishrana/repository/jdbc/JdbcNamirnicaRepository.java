package com.jelena.ishrana.repository.jdbc;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.repository.NamirnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Namirnica findOne(Long id) {
        return null;
    }

    @Override
    public Namirnica save(Namirnica namirnica) {
        return null;
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
