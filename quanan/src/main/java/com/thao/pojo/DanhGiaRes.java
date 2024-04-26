/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import lombok.Data;

/**
 *
 * @author Chung Vu
 */
@Data
public class DanhGiaRes {
    int id;
    String noi_dung;
    int danh_gia;
    String created_date;
    NguoiDung id_nguoi_dung;
    int id_chi_nhanh;
    ThucAn id_thuc_an;
}
