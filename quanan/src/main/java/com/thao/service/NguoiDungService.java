/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.NguoiDung;
import java.util.List;

/**
 *
 * @author Chung Vu
 */
public interface NguoiDungService {
    List<NguoiDung> getNDs();
    void save(NguoiDung nd);
    void delete(Long id);
    NguoiDung getNguoiDungById(Long id);
}
