/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.HoaDonTaiCho;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface HoaDonTaiChoService {
    List<HoaDonTaiCho> getHoaDons();
    void save(HoaDonTaiCho hd);
    void delete(Long id);
    HoaDonTaiCho getHoaDonById(Long id);
    List<HoaDonTaiCho> getHDs(Map<String,String> params);
}
