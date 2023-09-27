/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.ChiNhanh;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Thao
 */
public interface ChiNhanhService {

    List<ChiNhanh> getChiNhanhs();
    void save(ChiNhanh cn);
    void delete(Long id);
    ChiNhanh getChiNhanhById(Long id);
//    ChiNhanh getChiNhanhById(int id);
//    boolean deleteStore(int id);
//    ChiNhanh getChiNhanhByUser(String username);
}
