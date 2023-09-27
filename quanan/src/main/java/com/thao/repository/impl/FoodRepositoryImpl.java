/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.ThucAn;
import com.thao.repository.FoodRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
public class FoodRepositoryImpl implements FoodRepository {

//    @Autowired
//    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ThucAn> getThucAns(Map<String, String> params) {
        Session s = entityManager.unwrap(Session.class);
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class);
        Root root = q.from(ThucAn.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

//            String kw = params.get("kw");
//            if (kw != null && !kw.isEmpty()) {
//                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
//            }
//
//            String fromPrice = params.get("fromPrice");
//            if (fromPrice != null && !fromPrice.isEmpty()) {
//                predicates.add(b.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice)));
//            }
//
//            String toPrice = params.get("toPrice");
//            if (toPrice != null && !toPrice.isEmpty()) {
//                predicates.add(b.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice)));
//            }
//
            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("idLoai"), Integer.parseInt(cateId)));
            }
            String storeId = params.get("storeId");
            if (storeId != null && !storeId.isEmpty()) {
                predicates.add(b.equal(root.get("idChiNhanh"), Integer.parseInt(storeId)));
            }

            q.where(predicates.stream().toArray(Predicate[]::new));
        }

        q.orderBy(b.desc(root.get("id")));

        TypedQuery query = entityManager.createQuery(q);

//        if (params != null) {
//            String page = params.get("page");
//            if (page != null && !page.isEmpty()) {
//                int p = Integer.parseInt(page);
//                int pagesize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//
//                query.setMaxResults(pagesize);
//                query.setFirstResult((p - 1) * pagesize);
//            }
//        }
        return query.getResultList();
    }

    @Override
    public List<ThucAn> getThucAnByChiNhanh(int id) {
        Session s = entityManager.unwrap(Session.class);
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThucAn> q = b.createQuery(ThucAn.class
        );
        Root root = q.from(ThucAn.class
        );
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(root.get("idChiNhanh"), id));

        q.where(predicates.stream().toArray(Predicate[]::new));

        q.orderBy(b.desc(root.get("id")));

        TypedQuery query = entityManager.createQuery(q);
        return query.getResultList();
    }

    @Override
    public ThucAn getThucAnById(int id
    ) {
        Session s = entityManager.unwrap(Session.class);

        return s.get(ThucAn.class,
                id);
    }

    @Override
    public boolean addOrUpdateFood(ThucAn f) {
        Session s = entityManager.unwrap(Session.class);
        try {
            if (f.getId() == null) {
                f.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
                s.save(f);
            } else {
                s.update(f);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFood(int id) {
        Session s = entityManager.unwrap(Session.class);
        ThucAn food = this.getThucAnById(id);
        try{
            s.delete(food);
            return true;
        }catch(HibernateException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAllFood(int id) {
        Session s = entityManager.unwrap(Session.class);
        //List<ThucAn> food = this.getThucAnByChiNhanh(id);
        try{
            for(ThucAn f: this.getThucAnByChiNhanh(id))
                s.delete(f);
            return true;
        }catch(HibernateException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public ThucAn addFood(ThucAn food) {
        Session s = entityManager.unwrap(Session.class);
        
        s.save(food);
        
        return food;
    }

    
}
