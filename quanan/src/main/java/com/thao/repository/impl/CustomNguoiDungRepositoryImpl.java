/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.NguoiDung;
import com.thao.repository.CustomNguoiDungRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
public class CustomNguoiDungRepositoryImpl implements CustomNguoiDungRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public NguoiDung getNDByUsername(String username) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<NguoiDung> q = b.createQuery(NguoiDung.class);
        Root root = q.from(NguoiDung.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.isTrue(root.<Boolean>get("active")));
        predicates.add(b.equal(root.get("taiKhoan"), username));
        q.where(predicates.toArray(Predicate[]::new));

        Query query = entityManager.createQuery(q);

        return (NguoiDung) query.getSingleResult();
    }

    @Override
    public boolean authNguoiDung(String taiKhoan, String matKhau, BCryptPasswordEncoder passEncoder) {
        NguoiDung nd = this.getNDByUsername(taiKhoan);
        if (nd != null) {
            return passEncoder.matches(matKhau, nd.getMatKhau());
        }
        return false;
    }
}
