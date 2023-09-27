/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.HoaDonChiTiet;
import java.util.List;

/**
 *
 * @author Chung Vu
 */
public interface HoaDonChiTietService {
    List<HoaDonChiTiet> getHDs();
    void save(HoaDonChiTiet hd);
    void delete(Long id);
    HoaDonChiTiet getHoaDonChiTietById(Long id);
}
