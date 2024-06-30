/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.lowagie.text.DocumentException;
import com.thao.components.PDFExporter;
import com.thao.service.StatsService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class StatsController {
    @Autowired
    private StatsService statsSer;
    @Autowired
    private PDFExporter pdfEx;
    
    @RequestMapping("/stats")
    public String list(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("stats", this.statsSer.getTongTienMoiThucAn(params));
        return "thongke";
    }
    
    @GetMapping("/stats/export/pdf")
    public void exportToPDF(HttpServletResponse response, @RequestParam Map<String,String> params) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("utf-8");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=online_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        pdfEx.export2(response, "online", params, this.statsSer.getTongTienMoiThucAn(params));
         
    }
    
    @RequestMapping("/stats/offline")
    public String listOff(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("stats", this.statsSer.getTongTienMoiThucAnOff(params));
        return "thongkeoff";
    }
    
    @GetMapping("/stats/offline/export/pdf")
    public void exportToPDFOffline(HttpServletResponse response, @RequestParam Map<String,String> params) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=offline_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        pdfEx.export2(response, "offline", params, this.statsSer.getTongTienMoiThucAnOff(params));
         
    }
}
