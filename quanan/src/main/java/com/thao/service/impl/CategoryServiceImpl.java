/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.Category;
import com.thao.repository.CategoryRepository;
import com.thao.repository.CustomCategoryRepository;
import com.thao.service.CategoryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository cateRepo;
    @Autowired
    private CustomCategoryRepository cusCateRepo;

    @Override
    public List<Category> getCates() {
        return this.cateRepo.findAll();
    }

    @Override
    public void save(Category cate) {
        this.cateRepo.save(cate);
    }

    @Override
    public void delete(Long id) {
        Category cate = this.cateRepo.getReferenceById(id);
        this.cateRepo.delete(cate);
    }

    @Override
    public Category getCateById(Long id) {
        return this.cateRepo.getReferenceById(id);
    }

    @Override
    public List<Category> getCates(Map<String, String> params) {
        return this.cusCateRepo.getCates(params);
    }
    
    
    
}
