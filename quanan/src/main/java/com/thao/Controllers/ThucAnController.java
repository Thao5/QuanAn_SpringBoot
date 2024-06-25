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
    @Autowired
    private Environment env;

    @RequestMapping("/food")
    public String list(Model model, @RequestParam Map<String, String> params) {
        Map<String,String> tmp = new HashMap<>();
        List<ThucAn> listFoodPages = this.foodService.getThucAnsAll(tmp);
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        List<ThucAn> lf = this.foodService.getThucAnsAll(params);
        model.addAttribute("foods", lf);
        model.addAttribute("pages", Math.ceil(listFoodPages.size()*1.0/pageSize));
        if (params != null) {
            if(params.get("page") != null && !params.get("page").isEmpty())
                model.addAttribute("currentPage", Integer.parseInt(params.get("page")));
            else 
                model.addAttribute("currentPage", 0);
        }
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
                    this.emailSer.sendSimpleMessage(nd.getEmail(), "Thông báo quán ăn thêm món mới", String.format("Chi nhánh đã thêm món %s vào menu", food.getName()));
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
