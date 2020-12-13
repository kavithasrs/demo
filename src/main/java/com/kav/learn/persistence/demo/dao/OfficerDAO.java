package com.kav.learn.persistence.demo.dao;

import com.kav.learn.persistence.demo.entities.Officer;

import java.util.List;
import java.util.Optional;

/** Interface that defines the contract for the DAO object.
 * The most common functions expected out of a database */

public interface OfficerDAO {
    Officer save(Officer officer);
    Optional<Officer> findById(Integer id);
    List<Officer> findAll();
    Long count();
    void delete(Officer officer);
    boolean existById(Integer id);
}
