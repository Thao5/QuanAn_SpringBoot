/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.controllers;

import com.thao.service.ChiNhanhService;
import com.thao.service.FoodService;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Thao
 */
@Controller
@ControllerAdvice
//@PropertySource("classpath:configs.properties")
public class IndexController {

    @Autowired
    private ChiNhanhService storeService;

    @Autowired
    private FoodService foodService;

    @ModelAttribute
    public void commonAttr(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("catestores", this.catestoreService.getLoaiChiNhanhs());
        model.addAttribute("stores", this.storeService.getChiNhanhs());
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("foods", this.foodService.getThucAns());

        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        return "login";
    }
    
}
