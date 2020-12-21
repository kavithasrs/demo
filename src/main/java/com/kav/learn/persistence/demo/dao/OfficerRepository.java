package com.kav.learn.persistence.demo.dao;

import com.kav.learn.persistence.demo.entities.Officer;
import com.kav.learn.persistence.demo.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    List<Officer> findByLastName(String lastName);
    List<Officer> findAllByRankAndLastNameLike(Rank rank, String lastName);
}
