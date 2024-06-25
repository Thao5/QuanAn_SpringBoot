/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.Category;
import com.thao.pojo.HoaDonChiTiet;
import com.thao.pojo.HoaDonChiTietTaiCho;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> getTongTienMoiThucAn(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<HoaDonChiTiet> root = q.from(HoaDonChiTiet.class);
//        Root<HoaDonChiTietTaiCho> root2 = q.from(HoaDonChiTietTaiCho.class);
        q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
        List<Predicate> predicates = new ArrayList<>();
        if (params == null || params.isEmpty()) {
            q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
//            q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));

        }

        if (params != null && !params.isEmpty()) {

            String y = params.get("y");
            String m = params.get("m");
            String c = params.get("c");
//            q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//            q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
            if (y == null && y.isEmpty() && m == null && m.isEmpty() && c != null && !c.isEmpty()) {
                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
            }
            if (y != null && !y.isEmpty() && !y.toLowerCase().equals("tất cả") && m != null && !m.isEmpty() && !m.toLowerCase().equals("tất cả")) {
                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.parseInt(y)));
                predicates.add(b.equal(b.function("MONTH", Integer.class, root.get("createdDate")), Integer.parseInt(m)));
//                predicates.add(b.equal(b.function("YEAR", Integer.class, root2.get("createdDate")), Integer.parseInt(y)));
//                predicates.add(b.equal(b.function("MONTH", Integer.class, root2.get("createdDate")), Integer.parseInt(m)));
            } else if (y != null && !y.isEmpty() && !y.toLowerCase().equals("tất cả")) {
                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.parseInt(y)));
//                predicates.add(b.equal(b.function("YEAR", Integer.class, root2.get("createdDate")), Integer.parseInt(y)));
            } else if (m != null && !m.isEmpty() && !m.toLowerCase().equals("tất cả")) {
                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("MONTH", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("MONTH", Integer.class, root.get("createdDate")), Integer.parseInt(m)));
//                predicates.add(b.equal(b.function("MONTH", Integer.class, root2.get("createdDate")), Integer.parseInt(m)));
            }
            if (c != null && !c.isEmpty()) {
                Long idLoai = Long.parseLong(c);
                predicates.add(b.equal(root.get("idThucAn").get("id"), idLoai));
            }
        }
        q.where(predicates.toArray(Predicate[]::new));
        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> getTongTienMoiThucAnOff(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
//        Root<HoaDonChiTiet> root = q.from(HoaDonChiTiet.class);
        Root<HoaDonChiTietTaiCho> root2 = q.from(HoaDonChiTietTaiCho.class);
        List<Predicate> predicates = new ArrayList<>();
        q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));
        if (params == null || params.isEmpty()) {
//            q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
            q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));

        }

        if (params != null && !params.isEmpty()) {

            String y = params.get("y");
            String m = params.get("m");
            String c = params.get("c");
//            q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//            q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
            if (y == null && y.isEmpty() && m == null && m.isEmpty() && c != null && !c.isEmpty()) {
                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));
            }
            if (y != null && !y.isEmpty() && !y.toLowerCase().equals("tất cả") && m != null && !m.isEmpty() && !m.toLowerCase().equals("tất cả")) {
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
//                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.parseInt(y)));
//                predicates.add(b.equal(b.function("MONTH", Integer.class, root.get("createdDate")), Integer.parseInt(m)));
                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("YEAR", Integer.class, root2.get("createdDate")), Integer.parseInt(y)));
                predicates.add(b.equal(b.function("MONTH", Integer.class, root2.get("createdDate")), Integer.parseInt(m)));
            } else if (y != null && !y.isEmpty() && !y.toLowerCase().equals("tất cả")) {
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("YEAR", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("YEAR", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")));
//                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.parseInt(y)));
                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("YEAR", Integer.class, root2.get("createdDate")), Integer.parseInt(y)));
            } else if (m != null && !m.isEmpty() && !m.toLowerCase().equals("tất cả")) {
//                q.multiselect(root.get("idThucAn").get("name"), b.sum(root.get("tongTien")), b.function("MONTH", Integer.class, root.get("createdDate"))).groupBy(root.get("idThucAn"), b.function("MONTH", Integer.class, root.get("createdDate")));
//                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("MONTH", Integer.class, root2.get("createdDate")));
//                predicates.add(b.equal(b.function("MONTH", Integer.class, root.get("createdDate")), Integer.parseInt(m)));
                q.multiselect(root2.get("idThucAn").get("name"), b.sum(root2.get("tongTien")), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate"))).groupBy(root2.get("idThucAn"), b.function("YEAR", Integer.class, root2.get("createdDate")), b.function("MONTH", Integer.class, root2.get("createdDate")));
                predicates.add(b.equal(b.function("MONTH", Integer.class, root2.get("createdDate")), Integer.parseInt(m)));
            }
            if (c != null && !c.isEmpty()) {
                Long idLoai = Long.parseLong(c);
                predicates.add(b.equal(root2.get("idThucAn").get("id"), idLoai));
            }
        }
        q.where(predicates.toArray(Predicate[]::new));
        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
