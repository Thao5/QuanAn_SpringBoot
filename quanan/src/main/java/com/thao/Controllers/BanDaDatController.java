/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.BanDaDat;
import com.thao.pojo.NguoiDung;
import com.thao.service.ChiNhanhService;
import com.thao.service.EmailService;
import com.thao.service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@EnableAsync
public class BanDaDatController {

    @Autowired
    private ChiNhanhService cnSer;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private EmailService emailSer;

    @RequestMapping("/danhsachbandat")
    public String list(Model model, HttpSession session) {
        session = ApiBanDaDatController.sessionTmp;
        if (session != null) {
            if (session.getAttribute("listBan") != null) {
                Map<String, BanDaDat> listBan = (Map<String, BanDaDat>) session.getAttribute("listBan");
                if (listBan != null) {
                    model.addAttribute("listBanDaDat", listBan.values());
                }
            }
        }
        return "bandadat";
    }
    
    @RequestMapping("/deletebandat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Model model, @PathVariable("id") String id){
        Map<String, BanDaDat> listBan = (Map<String, BanDaDat>) ApiBanDaDatController.sessionTmp.getAttribute("listBan");
        this.emailSer.sendSimpleMessage(this.ndSer.getNguoiDungById(Long.parseLong(Integer.toString(listBan.get(id).getIdNguoiDat()))).getEmail(), "Thông báo hủy bản", "Bàn của bạn đã bị hủy");
        listBan.remove(id);
        ApiBanDaDatController.sessionTmp.setAttribute("listBan", listBan);
        
    }
    
    @RequestMapping("/xacnhanbandat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void xacNhan(Model model, @PathVariable("id") String id){
        Map<String, BanDaDat> listBan = (Map<String, BanDaDat>) ApiBanDaDatController.sessionTmp.getAttribute("listBan");
        listBan.remove(id);
        ApiBanDaDatController.sessionTmp.setAttribute("listBan", listBan);
    }
}
