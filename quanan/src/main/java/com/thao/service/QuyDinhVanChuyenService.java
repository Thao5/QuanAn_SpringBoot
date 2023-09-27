/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.QuyDinhVanChuyen;
import java.util.List;

/**
 *
 * @author Chung Vu
 */
public interface QuyDinhVanChuyenService {
    List<QuyDinhVanChuyen> getQuyDinhs();
    void save(QuyDinhVanChuyen qd);
    void delete(Long id);
    QuyDinhVanChuyen getDinhVanChuyenById(Long id);
}
