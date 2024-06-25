/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.Ban;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface BanService {
    List<Ban> getBans();
    void save(Ban b);
    void delete(Long id);
    Ban getBanById(Long id);
    List<Ban> getBanTheoChiNhanh(int cnId);
    List<Ban> getBanCus(Map<String, String> params);
}
