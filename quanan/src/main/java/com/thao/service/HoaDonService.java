/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.HoaDon;
import java.util.List;

/**
 *
 * @author Chung Vu
 */
public interface HoaDonService {
    List<HoaDon> getHoaDons();
    void save(HoaDon hd);
    void delete(Long id);
    HoaDon getHoaDonById(Long id);
}
