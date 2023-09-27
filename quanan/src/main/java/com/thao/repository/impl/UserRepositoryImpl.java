/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.NguoiDung;
import com.thao.repository.UserRepository;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thao
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

//    @Autowired
//    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private EntityManager entityManager;

    @Override
    public NguoiDung getUserByUsername(String username) {
        Session s = entityManager.unwrap(Session.class);
        Query<NguoiDung> q = s.createQuery("FROM NguoiDung WHERE taiKhoan = :un", NguoiDung.class);
        q.setParameter("un", username);

        return (NguoiDung) q.getSingleResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        NguoiDung u = this.getUserByUsername(username);
        
        return this.passEncoder.matches(password, u.getMatKhau());
    }

    @Override
    public NguoiDung addUser(NguoiDung user) {
        Session s = entityManager.unwrap(Session.class);
        user.setActive(Boolean.TRUE);
        s.save(user);
        
        return user;
    }

}
