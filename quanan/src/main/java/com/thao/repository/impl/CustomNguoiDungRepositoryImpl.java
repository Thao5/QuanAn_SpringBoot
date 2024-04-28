/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.NguoiDung;
import com.thao.repository.CustomNguoiDungRepository;
import com.thao.repository.NguoiDungRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Map;
import java.util.Random;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@Repository
@PropertySource("classpath:configs.properties")
public class CustomNguoiDungRepositoryImpl implements CustomNguoiDungRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Environment env;
    @Autowired
    private NguoiDungRepository ndRepo;

    @Override
    public NguoiDung getNDByUsername(String username) {
        try {
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
        }catch(NoResultException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean authNguoiDung(String taiKhoan, String matKhau, BCryptPasswordEncoder passEncoder) {
        NguoiDung nd = this.getNDByUsername(taiKhoan);
        if (nd != null) {
            return passEncoder.matches(matKhau, nd.getMatKhau());
        }
        return false;
    }

    @Override
    public List<NguoiDung> getNDCus(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<NguoiDung> q = b.createQuery(NguoiDung.class);
        Root root = q.from(NguoiDung.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(b.isTrue(root.<Boolean>get("active")));

        if (params != null) {

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("firstName"), String.format("%%%s%%", kw)));
            }
            String role = params.get("vaiTro");
            if (role != null && !role.isEmpty()) {
                predicates.add(b.equal(root.get("vaiTro"), role));
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
    public Boolean isAlreadyHave(NguoiDung nd) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<NguoiDung> q = b.createQuery(NguoiDung.class);
        Root root = q.from(NguoiDung.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.isTrue(root.<Boolean>get("active")));
        predicates.add(b.or(
                b.or(
                        b.or(b.equal(root.get("taiKhoan"), nd.getTaiKhoan()), b.equal(root.get("email"), nd.getEmail()), b.equal(root.get("taiKhoan"), nd.getTaiKhoan()), b.equal(root.get("phone"), nd.getPhone()), b.equal(root.get("phone"), nd.getPhone()), b.equal(root.get("email"), nd.getEmail())))));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = entityManager.createQuery(q);

        try {
            NguoiDung t = (NguoiDung) query.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        } catch (NonUniqueResultException ex) {
            return true;
        }

    }

    @Override
    public String changePasswordByEmail(Map<String, String> params, BCryptPasswordEncoder passwordEncoder) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<NguoiDung> q = b.createQuery(NguoiDung.class);
        Root root = q.from(NguoiDung.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.isTrue(root.<Boolean>get("active")));

        if (params != null) {
            String email = params.get("email");
            if (email != null && !email.isEmpty()) {
                predicates.add(b.equal(root.get("email"), email));
            }
        }

        q.where(predicates.toArray(Predicate[]::new));

        Query query = entityManager.createQuery(q);
        try {
            NguoiDung t = (NguoiDung) query.getSingleResult();
            String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random rnd = new Random();

            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
            }
            t.setMatKhau(passwordEncoder.encode(sb.toString()));
            this.ndRepo.save(t);
            return sb.toString();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
