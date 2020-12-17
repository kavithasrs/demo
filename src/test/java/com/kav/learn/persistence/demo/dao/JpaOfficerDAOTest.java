package com.kav.learn.persistence.demo.dao;

import com.kav.learn.persistence.demo.entities.Officer;
import com.kav.learn.persistence.demo.entities.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class JpaOfficerDAOTest {

    @Autowired @Qualifier("jpaOfficerDAO")
    private OfficerDAO dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Integer> idMapper = (rs, rowNum) -> rs.getInt("id");

    @Test
    public void testSave() {
        Officer officer = new Officer(Rank.COMMANDER, "Nyoto", "Uhuru");
        officer = dao.save(officer);
        assertNotNull(officer.getId());
    }

    @Test
    public void findOneThatExists() {
        jdbcTemplate.query("select id from officers", idMapper)
                    .forEach(id -> {
                        Optional<Officer> officer = dao.findById(id);
                        assertTrue(officer.isPresent());
                        assertEquals(id, officer.get().getId());
                    });

    }

    @Test
    public void findOneThatDoesNotExist() {
        Optional<Officer> officer = dao.findById(999);
        assertFalse(officer.isPresent());
    }

    @Test
    public void testFindAll() {
        List<String> dBnames = dao.findAll()
                                  .stream()
                                  .map(Officer::getLastName)
                                  .collect(Collectors.toList());
        assertThat(dBnames,containsInAnyOrder("Archer", "Janeway", "Kirk", "Picard", "Sisko"));
    }

    @Test
    void testCount() {
        assertEquals(5,dao.count());
    }

    @Test
    void testDelete() {
        jdbcTemplate.query("select id from officers", idMapper)
                    .forEach(id -> {
                                Optional<Officer> officer = dao.findById(id);
                                assertTrue(officer.isPresent());
                                dao.delete(officer.get());
                            });
                            assertEquals(0, dao.count());
    }

    @Test
    void testExistById() {
        jdbcTemplate.query("select id from officers", idMapper)
                    .forEach(id -> assertTrue(dao.existById(id)));
    }

    @Test
    public void doesNotExist(){
        List<Integer> ids = jdbcTemplate.query("select id from officers", idMapper);
        assertThat(ids, not(contains(999)));
        assertFalse(dao.existById(999));
    }
}