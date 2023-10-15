/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Chung Vu
 */
@Data
public class BanDaDat {
    private int idChiNhanh;
    private int idNguoiDat;
    private String hoTen;
    private String idBan;
    private String moTa;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date ngayDat;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date ngayNhan;
    private String moTaBan;
}
