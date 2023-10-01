/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.MonDat;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface ReceiptRepository {
    public boolean addReceipt(Map<String, MonDat> carts);
}
