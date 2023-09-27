/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.ChiNhanh;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Thao
 */

public interface ChiNhanhRepository extends JpaRepository<ChiNhanh, Long>{
//    List<ChiNhanh> getChiNhanhs(Map<String, String> params);
//    ChiNhanh getChiNhanhById(int id);
//    boolean deleteStore(int id);
//    ChiNhanh getChiNhanhByUser(String username);
}
