/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.ThucAn;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Thao
 */
public interface FoodService {
    List<ThucAn> getThucAns();
//    List<ThucAn> getThucAnByChiNhanh(int id);
//    ThucAn getThucAnById(int id);
////    boolean addOrUpdateFood(ThucAn f);
//    boolean deleteFood(int id);
//    boolean deleteAllFood(int id);
////    ThucAn addFood(Map<String, String> params, MultipartFile file);
    void save(ThucAn ta);
    void delete(Long id);
    ThucAn getThucAnById(Long id);
    List<ThucAn> getThucAnByChiNhanh(int cnId);
    List<ThucAn> getThucAns(Map<String, String> params);
    List<ThucAn> getThucAnsAll(Map<String, String> params);
    ThucAn getThucAnById2(Long id);
    ThucAn addFood(Map<String, String> params, MultipartFile avatar);
    ThucAn updateFood(Long id, Map<String, String> params);
    ThucAn updateFoodAvatar(Long id, MultipartFile avatar);
}
