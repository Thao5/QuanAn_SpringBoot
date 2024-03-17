/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.FoodWrapper;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class MonDaDatController {
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
}
