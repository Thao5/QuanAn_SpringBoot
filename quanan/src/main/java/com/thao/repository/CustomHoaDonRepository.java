/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.HoaDon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface CustomHoaDonRepository {
    List<HoaDon> getHDs(Map<String,String> params);
}
