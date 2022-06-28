package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MPADBStorageImpl implements MPADBStorage {

    private final Logger log = LoggerFactory.getLogger(FilmDBStorageImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public MPADBStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public MPA getMPAById(int id) {
        final String sqlQuery = "select * from MPA where MPA_ID = ?";
        final List<MPA> films = jdbcTemplate.query(sqlQuery, MPADBStorageImpl::makeMpa, id);
        if (films.size() != 1) {
            throw new ObjectNotFoundException("map id=" + id);
        }
        return films.get(0);
    }

    @Override
    public List<MPA> getAllMPA() {
        return jdbcTemplate.query("select * from MPA", MPADBStorageImpl::makeMpa);
    }

    static MPA makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new MPA(
                rs.getInt("mpa_id"),
                rs.getString("mpa_name"));
    }
}