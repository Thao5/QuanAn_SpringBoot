/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface StatsService {
    List<Object[]> getTongTienMoiThucAn(Map<String,String> params);
    List<Object[]> getTongTienMoiThucAnOff(Map<String, String> params);
}
