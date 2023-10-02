/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ChiNhanh;
import com.thao.pojo.NguoiDung;
import com.thao.service.BanService;
import com.thao.service.ChiNhanhService;
import com.thao.service.DanhGiaService;
import com.thao.service.EmailService;
import com.thao.service.FoodService;
import com.thao.service.NguoiDungService;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableAsync
public class ChiNhanhController {
    @Autowired
    private ChiNhanhService storeService;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private FoodService foodSer;
    @Autowired
    private EmailService emailSer;
    @Autowired
    private BanService banSer;
    @Autowired
    private DanhGiaService dgSer;
    
    @RequestMapping("/chinhanh")
    public String list(Model model){
        model.addAttribute("stores", this.storeService.getChiNhanhs());
        model.addAttribute("tas", this.foodSer.getThucAns());
        model.addAttribute("bans", this.banSer.getBans());
        model.addAttribute("dgs", this.dgSer.getDanhGias());
        return "chinhanh";
    }
    
    @GetMapping("/addorupdatechinhanh")
    public String add(Model model){
        model.addAttribute("cns", new ChiNhanh());
        model.addAttribute("nds", this.ndSer.getNDs());
        
        return "addorupdatechinhanh";
    }
    
    @GetMapping("/addorupdatechinhanh/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        model.addAttribute("cns", this.storeService.getChiNhanhById(id));
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatechinhanh";
    }
    
    @PostMapping("/addorupdatechinhanh")
    public String addOrUpdate(Model model, @ModelAttribute(value="cns") @Valid ChiNhanh cn, BindingResult rs){
        if(!rs.hasErrors()){
            if(cn.getId() == null){
                cn.setCreatedDate(new Date());
                for(NguoiDung nd : this.ndSer.getNDs()){
                    this.emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao chi nhanh moi cua quan an", String.format("Quan an da mo chi nhanh moi tai %s", cn.getDiaChi()));
                }
            }
            this.storeService.save(cn);
            

            return "redirect:/admin/chinhanh";
        }
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatechinhanh";
    }
    
    @CrossOrigin
    @RequestMapping("/deletechinhanh/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        this.storeService.delete(id);
    }
}
