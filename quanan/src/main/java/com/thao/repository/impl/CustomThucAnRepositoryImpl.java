/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.ThucAn;
import com.thao.repository.CustomThucAnRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
public class CustomThucAnRepositoryImpl implements CustomThucAnRepository{
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ThucAn> getThucAnByChiNhanh(int cnId) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class);
        Root root = q.from(ThucAn.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("idChiNhanh").get("id"), cnId));
        q.where(predicates.toArray(Predicate[]::new));

        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
