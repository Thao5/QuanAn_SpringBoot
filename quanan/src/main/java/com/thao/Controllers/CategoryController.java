/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.Category;
import com.thao.service.CategoryService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private Environment env;

    @RequestMapping("/cate")
    public String list(Model model, @RequestParam Map<String,String> params) {
        Map<String,String> tmp = new HashMap<>();
        List<Category> listPages = this.cateSer.getCates(tmp);
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        model.addAttribute("cates", this.cateSer.getCates(params));
        model.addAttribute("pages", Math.ceil(listPages.size()*1.0/pageSize));
        if (params != null) {
            if(params.get("page") != null && !params.get("page").isEmpty())
                model.addAttribute("currentPage", Integer.parseInt(params.get("page")));
            else 
                model.addAttribute("currentPage", 0);
        }
        return "cate";
    }

    @GetMapping("/addorupdatecate")
    public String add(Model model) {
        model.addAttribute("cate", new Category());
        return "addorupdatecate";
    }

    @GetMapping("/addorupdatecate/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("cate", this.cateSer.getCateById(id));
        return "addorupdatecate";
    }

    @PostMapping("/addorupdatecate")
    public String addOrUpdate(@ModelAttribute(value = "cate") @Valid Category cate, BindingResult rs) {
        if (!rs.hasErrors()) {
            this.cateSer.save(cate);
            return "redirect:/admin/cate";
        }
        return "addorupdatecate";
    }

    @RequestMapping("/deletecate/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.cateSer.delete(id);
    }
}
