/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.DanhGia;
import java.util.List;

/**
 *
 * @author Thao
 */
public interface CommentService {
    List<DanhGia> getComments(int storeId);
    DanhGia addComment(DanhGia c);
}
