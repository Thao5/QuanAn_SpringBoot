///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.thao.Controllers;
//
//import com.thao.pojo.ChiNhanh;
//import com.thao.pojo.NguoiDung;
//import com.thao.service.BanService;
//import com.thao.service.ChiNhanhService;
//import com.thao.service.DanhGiaService;
//import com.thao.service.EmailService;
//import com.thao.service.FoodService;
//import com.thao.service.NguoiDungService;
//import jakarta.validation.Valid;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
///**
// *
// * @author Chung Vu
// */
//@Controller
//@RequestMapping("/admin")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@EnableAsync
//public class ChiNhanhController {
//
//    @Autowired
//    private ChiNhanhService storeService;
//    @Autowired
//    private NguoiDungService ndSer;
//    @Autowired
//    private FoodService foodSer;
//    @Autowired
//    private EmailService emailSer;
//    @Autowired
//    private BanService banSer;
//    @Autowired
//    private DanhGiaService dgSer;
//    @Autowired
//    private Environment env;
//
//    @RequestMapping("/chinhanh")
//    public String list(Model model, @RequestParam Map<String,String> params) {
//        Map<String,String> tmp = new HashMap<>();
//        List<ChiNhanh> listPages = this.storeService.getChiNhanhs(tmp);
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//        model.addAttribute("stores", this.storeService.getChiNhanhs(params));
//        model.addAttribute("tas", this.foodSer.getThucAns(params));
//        model.addAttribute("bans", this.banSer.getBans());
//        model.addAttribute("dgs", this.dgSer.getDanhGias());
//        model.addAttribute("pages", Math.ceil(listPages.size()*1.0/pageSize));
//        return "chinhanh";
//    }
//
//    @GetMapping("/addorupdatechinhanh")
//    public String add(Model model) {
//        Map<String, String> tmp = new HashMap<>();
//        tmp.put("vaiTro", "OWNER");
//        model.addAttribute("cns", new ChiNhanh());
//        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
//
//        return "addorupdatechinhanh";
//    }
//
//    @GetMapping("/addorupdatechinhanh/{id}")
//    public String update(Model model, @PathVariable("id") Long id) {
//        Map<String, String> tmp = new HashMap<>();
//        tmp.put("vaiTro", "OWNER");
//        model.addAttribute("cns", this.storeService.getChiNhanhById(id));
//        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
//        return "addorupdatechinhanh";
//    }
//
//    @PostMapping("/addorupdatechinhanh")
//    public String addOrUpdate(Model model, @ModelAttribute(value = "cns") @Valid ChiNhanh cn, BindingResult rs) {
//        Map<String, String> tmp = new HashMap<>();
//        if (!rs.hasErrors()) {
//            if (cn.getId() == null) {
//                cn.setCreatedDate(new Date());
//                tmp.put("vaiTro", "CUSTOMER");
//                for (NguoiDung nd : this.ndSer.getNDCus(tmp)) {
//                    this.emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao chi nhanh moi cua quan an", String.format("Quan an da mo chi nhanh moi tai %s", cn.getDiaChi()));
//                }
//            }
//            if (cn.getId() != null) {
//                ChiNhanh cn2 = this.storeService.getChiNhanhById(Long.parseLong(Integer.toString(cn.getId())));
//                if (!cn.getDiaChi().equals(cn2.getDiaChi())) {
//                    tmp.put("vaiTro", "CUSTOMER");
//                    for (NguoiDung nd : this.ndSer.getNDCus(tmp)) {
//                        this.emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao doi chi nhanh cua quan an", String.format("chi nhanh tai %s da doi chi nhanh toi %s", cn2.getDiaChi(), cn.getDiaChi()));
//                    }
//                }
//            }
//            this.storeService.save(cn);
//
//            return "redirect:/admin/chinhanh";
//        }
//        tmp.put("vaiTro", "OWNER");
//        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
//        return "addorupdatechinhanh";
//    }
//
//    @RequestMapping("/deletechinhanh/{id}/")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable("id") Long id) {
//        ChiNhanh cn = this.storeService.getChiNhanhById(id);
//        Map<String, String> tmp = new HashMap<>();
//        tmp.put("vaiTro", "CUSTOMER");
//        for (NguoiDung nd : this.ndSer.getNDCus(tmp)) {
//            this.emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao dong cua chi nhanh cua quan an", String.format("Quan an da dong cua chi nhanh %s", cn.getDiaChi()));
//        }
//        this.storeService.delete(cn);
//    }
//}
