/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Chung Vu
 */
@Data
public class FoodWrapper {
    String id;
    List<ThucAn> foods;
}
