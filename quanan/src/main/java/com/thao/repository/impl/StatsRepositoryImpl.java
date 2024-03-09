/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.HoaDonChiTiet;
import com.thao.repository.StatsRepository;
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
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
public class StatsRepositoryImpl implements StatsRepository{
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> getTongTienMoiThucAn(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<HoaDonChiTiet> root = q.from(HoaDonChiTiet.class);
        q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            
            String y = params.get("y");
            if (y != null && !y.isEmpty()) {
                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.parseInt(y)));
            }
            String m = params.get("m");
            if (m != null && !m.isEmpty()) {
                predicates.add(b.equal(b.function("MONTH", Integer.class, root.get("createdDate")), Integer.parseInt(m)));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }
    
    
}
