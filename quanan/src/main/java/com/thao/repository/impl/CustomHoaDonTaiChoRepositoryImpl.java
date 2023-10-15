/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.HoaDonTaiCho;
import com.thao.repository.CustomHoaDonTaiChoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
@PropertySource("classpath:configs.properties")
public class CustomHoaDonTaiChoRepositoryImpl implements CustomHoaDonTaiChoRepository{
    
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Environment env;

    @Override
    public List<HoaDonTaiCho> getHDs(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<HoaDonTaiCho> q = b.createQuery(HoaDonTaiCho.class);
        Root root = q.from(HoaDonTaiCho.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("idBan").get("moTa"), String.format("%%%s%%", kw)));
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
    
    
    
}
