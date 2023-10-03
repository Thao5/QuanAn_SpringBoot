/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.HoaDonChiTietTaiCho;
import java.util.List;

/**
 *
 * @author Chung Vu
 */
public interface HoaDonChiTietTaiChoService {
    List<HoaDonChiTietTaiCho> getHDs();
    void save(HoaDonChiTietTaiCho hd);
    void delete(Long id);
    HoaDonChiTietTaiCho getHoaDonChiTietById(Long id);
}
