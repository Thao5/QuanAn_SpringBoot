/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.DanhGia;
import com.thao.repository.CommentRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.HibernateException;
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
public class CommentRepositoryImpl implements CommentRepository{
//    @Autowired
//    private LocalSessionFactoryBean factory;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<DanhGia> getComments(int storeId) {
        Session s = entityManager.unwrap(Session.class);
        Query<DanhGia> q = s.createQuery("From DanhGia Where idCuaHang.id=:id", DanhGia.class);
        q.setParameter("id", storeId);
        
        return q.getResultList();
    }

    @Override
    public DanhGia addComment(DanhGia c) {
        Session s = entityManager.unwrap(Session.class);
        try {
            s.save(c);
            return c;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
