package com.kav.learn.persistence.demo.dao;

import com.kav.learn.persistence.demo.entities.Officer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQLInspection")
@Repository
public class JpaOfficerDAO implements OfficerDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Officer save(Officer officer) {
        entityManager.persist(officer);
        return officer;
    }

    @Override
    public Optional<Officer> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Officer.class, id));
    }

    @Override
    public List<Officer> findAll() {
        return entityManager.createQuery("select o from Officer o", Officer.class).getResultList();
    }

    @Override
    public Long count() {
        return entityManager.createQuery("select count (o.id) from Officer o", Long.class).getSingleResult();
    }

    @Override
    public void delete(Officer officer) {
        entityManager.remove(officer);

    }

    @Override
    public boolean existById(Integer id) {
        Long count = entityManager.createQuery("select count (o.id) from Officer o where o.id=:id", Long.class)
                                  .setParameter("id", id)
                                  .getSingleResult();
        return count > 0;
    }
}
