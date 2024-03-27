/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.FoodWrapper;
import com.thao.service.BanService;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class MonDaDatController {
    @Autowired
    private BanService banSer;
    
    @RequestMapping("/danhsachfooddat/{idBan}")
    public String list(Model model, @PathVariable("idBan") String idBan, HttpSession session) {
        session = ApiDatMonTaiChoController.sessionTmp;
        if (session != null) {
            if (session.getAttribute("listFood") != null) {
                Map<String, FoodWrapper> listFood = (Map<String, FoodWrapper>) session.getAttribute("listFood");
                if (listFood != null) {
                    Map<String, FoodWrapper> listFoodTmp = listFood.entrySet().stream().filter(a->a.getKey().equals(idBan)).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
                    model.addAttribute("listFoodDaDat", listFoodTmp.values());
                }
            }
        }
        return "fooddadat";
    }
    
    @RequestMapping("/deletefooddat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Model model, @PathVariable("id") String id){
        Map<String, FoodWrapper> listFood = (Map<String, FoodWrapper>) ApiDatMonTaiChoController.sessionTmp.getAttribute("listFood");
        listFood.remove(id);
        ApiDatMonTaiChoController.sessionTmp.setAttribute("listBan", listFood);
        
    }
    
    @RequestMapping("/xacnhanfooddat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void xacNhan(Model model, @PathVariable("id") String id){
        Map<String, FoodWrapper> listFood = (Map<String, FoodWrapper>) ApiDatMonTaiChoController.sessionTmp.getAttribute("listFood");
        listFood.remove(id);
        ApiDatMonTaiChoController.sessionTmp.setAttribute("listFood", listFood);
    }
    
    @RequestMapping("/danhsachfooddat")
    public String danhSachBan(Model model){
        model.addAttribute("listBan", this.banSer.getBans());
        return "danhsachban";
    }
}
