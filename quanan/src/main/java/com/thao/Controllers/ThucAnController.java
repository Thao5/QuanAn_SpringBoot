/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.NguoiDung;
import com.thao.pojo.ThucAn;
import com.thao.service.CategoryService;
import com.thao.service.ChiNhanhService;
import com.thao.service.EmailService;
import com.thao.service.FoodService;
import com.thao.service.NguoiDungService;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class ThucAnController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private ChiNhanhService cnSer;
    @Autowired
    private CategoryService cateSer;
    @Autowired
    private EmailService emailSer;
    @Autowired
    private NguoiDungService ndSer;

    @RequestMapping("/food")
    public String list(Model model) {
        model.addAttribute("foods", this.foodService.getThucAns());
        return "food";
    }

    @GetMapping("/addorupdatefood")
    public String add(Model model) {
        model.addAttribute("food", new ThucAn());
        model.addAttribute("cates", this.cateSer.getCates());
//        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatefood";
    }

    @GetMapping("/addorupdatefood/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("food", this.foodService.getThucAnById(id));
        model.addAttribute("cates", this.cateSer.getCates());
//        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatefood";
    }

    @PostMapping("/addorupdatefood")
    public String addOrUpdate(Model model, @ModelAttribute(value = "food") @Valid ThucAn food, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (food.getId() == null) {
                food.setCreatedDate(new Date());
                food.setActive(Boolean.TRUE);
                food.setIdChiNhanh(this.cnSer.getChiNhanhById(Long.parseLong(String.valueOf(4))));
                Map<String, String> tmp = new HashMap<>();
                tmp.put("vaiTro", "CUSTOMER");
                for (NguoiDung nd : this.ndSer.getNDCus(tmp)) {
                    this.emailSer.sendSimpleMessage(nd.getEmail(), "Thông báo quán ăn thêm món mới", String.format("Chi nhánh %s đã thêm món %s vào menu", food.getIdChiNhanh().getDiaChi(), food.getName()));
                }
            }
            this.foodService.save(food);
            return "redirect:/admin/food";
        }
        model.addAttribute("cates", this.cateSer.getCates());
//        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatefood";
    }

    @RequestMapping("/deletefood/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        ThucAn ta = this.foodService.getThucAnById(id);
        ta.setActive(Boolean.FALSE);
        this.foodService.save(ta);
    }
}
