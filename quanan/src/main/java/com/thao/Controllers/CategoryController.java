/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.Category;
import com.thao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService cateSer;
    
    @RequestMapping("/cate")
    public String list(Model model){
        model.addAttribute("cates", this.cateSer.getCates());
        return "cate";
    }
    
    @GetMapping("/addorupdatecate")
    public String add(Model model){
        model.addAttribute("cate", new Category());
        return "addorupdatecate";
    }
    
    @GetMapping("/addorupdatecate/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        model.addAttribute("cate", this.cateSer.getCateById(id));
        return "addorupdatecate";
    }
    
    @PostMapping("/addorupdatecate")
    public String addOrUpdate(@ModelAttribute(value = "cate") Category cate){
        this.cateSer.save(cate);
        return "redirect:/admin/cate";
    }
    
    @RequestMapping("/deletecate/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        this.cateSer.delete(id);
    }
}
