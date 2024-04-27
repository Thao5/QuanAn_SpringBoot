/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.ThucAn;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface CustomThucAnRepository {
    List<ThucAn> getThucAnByChiNhanh(int cnId);
    List<ThucAn> getThucAns(Map<String, String> params);
    List<ThucAn> getThucAnsAll(Map<String, String> params);
    Boolean isAlreadyHaveFoodCN(ThucAn ta);
}
