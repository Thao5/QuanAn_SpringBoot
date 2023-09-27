/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.Category;
import com.thao.repository.CategoryRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thao
 */
@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository{
//    @Autowired
//    private LocalSessionFactoryBean factory;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Category> getCategorys() {
        Session s = entityManager.unwrap(Session.class);
        Query<Category> q = s.createQuery("FROM Category", Category.class);
        
        return q.getResultList();
    }

    @Override
    public Category getCateById(int id) {
        Session s = entityManager.unwrap(Session.class);
        return s.get(Category.class, id);
    }
    
    
}
