package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
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
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from mpa where mpa_id = ?", id);
        if(mpaRows.next()){
            return new MPA(mpaRows.getInt("mpa_id"),mpaRows.getString("mpa_name"));
        }
        return null;
    }

    @Override
    public List<MPA> getAllMPA() {
        List<MPA> mpaList = new ArrayList<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from mpa");
        while(mpaRows.next()){
            MPA mpa = new MPA(mpaRows.getInt("mpa_id"),mpaRows.getString("mpa_name"));
            mpaList.add(mpa);
        }
        return mpaList;
    }
}