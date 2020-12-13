package com.kav.learn.persistence.demo.dao;

import com.kav.learn.persistence.demo.entities.Officer;
import com.kav.learn.persistence.demo.entities.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class JdbcOfficerDAOTest {
    @Autowired
    private OfficerDAO officerDAO;


    @Test
    public void save() {
        Officer officer = new Officer(Rank.ADMIRAL, "Sahitya", "Subramanian");
        officer = officerDAO.save(officer);
        assertNotNull(officer.getId());
    }

    @Test
    public void findByIdThatExists() {
        Optional<Officer> officer = officerDAO.findById(1);
        assertTrue(officer.isPresent());
        assertEquals(1, officer.get().getId().intValue());
    }

    @Test
    public void findByIdThatDoesNotExist() {
        Optional<Officer> officer = officerDAO.findById(999);
        assertFalse(officer.isPresent());

    }

    @Test
    public void findAll() {
        List<String> namesInDB = officerDAO.findAll()
                .stream()
                .map(Officer::getLastName)
                .collect(Collectors.toList());
        assertThat(namesInDB, containsInAnyOrder(
                "Kirk", "Picard", "Sisko", "Janeway", "Archer"));

    }

    @Test
    void count() {
        assertEquals(5,officerDAO.count());
    }

    @Test
    void delete() {
        IntStream.rangeClosed(1,5).forEach(id -> {
            Optional<Officer> officer = officerDAO.findById(id);
            assertTrue(officer.isPresent());
            officerDAO.delete(officer.get());
        });
        assertEquals(0, officerDAO.count());

    }

    @Test
    void existById() {
        IntStream.rangeClosed(1,5)
                .forEach(id -> assertTrue(officerDAO.existById(id)));
    }
}