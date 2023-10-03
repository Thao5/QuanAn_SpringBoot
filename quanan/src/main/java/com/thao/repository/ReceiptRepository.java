/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.MonDat;
import com.thao.pojo.MonDatTaiCho;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface ReceiptRepository {
    boolean addReceipt(Map<String, MonDat> carts);
//    boolean addReceiptOff(Map<String, MonDatTaiCho> carts);
}
