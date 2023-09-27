/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.ChiNhanh;
import com.thao.pojo.ThucAn;
import com.thao.repository.ChiNhanhRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thao
 */
@Repository
@Transactional
public class ChiNhanhRepositoryImpl implements ChiNhanhRepository{

//    @Autowired
//    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public List<ChiNhanh> getChiNhanhs(Map<String, String> params) {
        Session s = entityManager.unwrap(Session.class);
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChiNhanh> q = b.createQuery(ChiNhanh.class);
        Root root = q.from(ChiNhanh.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

//            String fromPrice = params.get("fromPrice");
//            if (fromPrice != null && !fromPrice.isEmpty()) {
//                predicates.add(b.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice)));
//            }
//
//            String toPrice = params.get("toPrice");
//            if (toPrice != null && !toPrice.isEmpty()) {
//                predicates.add(b.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice)));
//            }

            String catestoreId = params.get("catestoreId");
            if (catestoreId != null && !catestoreId.isEmpty()) {
                predicates.add(b.equal(root.get("idLoaiChiNhanh"), Integer.parseInt(catestoreId)));
            }

            q.where(predicates.stream().toArray(Predicate[]::new));
        }

        q.orderBy(b.desc(root.get("id")));

        TypedQuery<ChiNhanh> query = entityManager.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int pagesize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

                query.setMaxResults(pagesize);
                query.setFirstResult((p - 1) * pagesize);
            }
        }
        return query.getResultList();
    }

    @Override
    public ChiNhanh getChiNhanhById(int id) {
        Session s = entityManager.unwrap(Session.class);
        return s.get(ChiNhanh.class, id);
    }

    @Override
    public boolean deleteStore(int id) {
        Session s = entityManager.unwrap(Session.class);
        ChiNhanh store = this.getChiNhanhById(id);
        try{
            s.delete(store);
            return true;
        }catch(HibernateException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public ChiNhanh getChiNhanhByUser(String username) {
        Session s = entityManager.unwrap(Session.class);
        Query<ChiNhanh> q = s.createQuery("FROM ChiNhanh WHERE idNguoiDung.taiKhoan = :un ", ChiNhanh.class);
        q.setParameter("un", username);

        return (ChiNhanh) q.getSingleResult();
    }

    
}
