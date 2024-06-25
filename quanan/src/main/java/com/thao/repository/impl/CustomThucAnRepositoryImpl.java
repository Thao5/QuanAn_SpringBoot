/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.ThucAn;
import com.thao.repository.CustomThucAnRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
public class CustomThucAnRepositoryImpl implements CustomThucAnRepository{
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Environment env;

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

    @Override
    public List<ThucAn> getThucAns(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class);
        Root root = q.from(ThucAn.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.greaterThan(root.<Integer>get("soLuong"), 0));
        predicates.add(b.isTrue(root.<Boolean>get("active")));
        if (params != null) {
            
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }
            String idLoai = params.get("idLoai");
            if (idLoai != null && !idLoai.isEmpty()) {
                predicates.add(b.equal(root.get("idLoai").get("id"), idLoai));
            }
            String from = params.get("from");
            if (from != null && !from.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.<Integer>get("price"), Integer.parseInt(from)));
            }
            String to = params.get("to");
            if (to != null && !to.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.<Integer>get("price"), Integer.parseInt(to)));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }
    
    @Override
    public List<ThucAn> getThucAnsAll(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class);
        Root root = q.from(ThucAn.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }
            String idLoai = params.get("idLoai");
            if (idLoai != null && !idLoai.isEmpty()) {
                predicates.add(b.equal(root.get("idLoai").get("id"), idLoai));
            }
            String from = params.get("from");
            if (from != null && !from.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.<Integer>get("price"), Integer.parseInt(from)));
            }
            String to = params.get("to");
            if (to != null && !to.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.<Integer>get("price"), Integer.parseInt(to)));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = entityManager.createQuery(q);
        
        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public Boolean isAlreadyHaveFoodCN(ThucAn ta) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class);
        Root root = q.from(ThucAn.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("idChiNhanh").get("id"), ta.getIdChiNhanh().getId()));
        predicates.add(b.equal(root.get("name"), ta.getName()));
        predicates.add(b.isTrue(root.<Boolean>get("active")));
        q.where(predicates.toArray(Predicate[]::new));

        Query query = entityManager.createQuery(q);

        try{
            ThucAn t = (ThucAn) query.getSingleResult();
            return true;
        } catch(NoResultException ex){
            return false;
        }
    }
}
