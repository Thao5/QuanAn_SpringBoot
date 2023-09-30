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
@RequestMapping("/bandatchinhanh")
@EnableAsync
public class BanDaDatController {

    @Autowired
    private ChiNhanhService cnSer;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private EmailService emailSer;

    @RequestMapping("/{id}")
    public String list(Model model, @PathVariable("id") int id, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        NguoiDung nd = this.ndSer.getNguoiDungByUsername(auth.getName());
        if (this.cnSer.getChiNhanhTheoChuChiNhanh(nd.getId()) != null) {
            model.addAttribute("listCN", this.cnSer.getChiNhanhTheoChuChiNhanh(nd.getId()));
        }
        session = ApiBanDaDatController.sessionTmp;
        if (session != null) {
            if (session.getAttribute("listBan") != null) {
                Map<Integer, ArrayList<BanDaDat>> listBan = (Map<Integer, ArrayList<BanDaDat>>) session.getAttribute("listBan");
                if (listBan != null) {
                    model.addAttribute("listBanDaDat", listBan.get(id));
                }
            }
        }
        return "bandadat";
    }
    
    @RequestMapping("/deletebandat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Model model, @PathVariable("id") String id){
        Map<Integer, ArrayList<BanDaDat>> listBan = (Map<Integer, ArrayList<BanDaDat>>) ApiBanDaDatController.sessionTmp.getAttribute("listBan");
        List<BanDaDat> listBanDaDat = (List<BanDaDat>) model.getAttribute("listBanDaDat");
        if(listBanDaDat != null){
            for(BanDaDat b : listBanDaDat){
                if(b.getIdBan().equals(id)){
                    
                    listBan.get(listBanDaDat.get(0).getIdChiNhanh()).remove(b);
                    NguoiDung nd = this.ndSer.getNguoiDungById(Long.parseLong(Integer.toString(b.getIdNguoiDat())));
                    emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao huy bo ban dat", String.format("Da huy bo ban dat %s", b.getIdBan()));
                    break;
                }
            }
        }
        ApiBanDaDatController.sessionTmp.setAttribute("listBan", listBan);
        
    }
    
    @RequestMapping("/xacnhanbandat/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void xacNhan(Model model, @PathVariable("id") String id){
        Map<Integer, ArrayList<BanDaDat>> listBan = (Map<Integer, ArrayList<BanDaDat>>) ApiBanDaDatController.sessionTmp.getAttribute("listBan");
        List<BanDaDat> listBanDaDat = (List<BanDaDat>) model.getAttribute("listBanDaDat");
        if(listBanDaDat != null){
            for(BanDaDat b : listBanDaDat){
                if(b.getIdBan().equals(id)){
                    
                    listBan.get(listBanDaDat.get(0).getIdChiNhanh()).remove(b);
                    NguoiDung nd = this.ndSer.getNguoiDungById(Long.parseLong(Integer.toString(b.getIdNguoiDat())));
                    emailSer.sendSimpleMessage(nd.getEmail(), "Thong bao xac nhan ban dat", String.format("Da xac nhan ban dat %s", b.getIdBan()));
                    break;
                }
            }
        }
        ApiBanDaDatController.sessionTmp.setAttribute("listBan", listBan);
        
    }
}
