/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.DanhGia;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface CustomDanhGiaRepository {
    List<DanhGia> getComments(int storeId);
    List<DanhGia> getCommentsByUser(Map<String, String> params);
    List<DanhGia> getCommentsFood(int foodId);
}
