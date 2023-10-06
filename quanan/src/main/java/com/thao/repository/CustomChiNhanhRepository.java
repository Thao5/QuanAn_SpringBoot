/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.ChiNhanh;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface CustomChiNhanhRepository {
    List<ChiNhanh> getChiNhanhTheoChuChiNhanh(int id);
    List<ChiNhanh> getChiNhanhs(Map<String,String> params);
}
