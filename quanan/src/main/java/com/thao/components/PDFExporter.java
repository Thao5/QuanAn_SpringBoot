/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.components;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.thao.pojo.MonDatTaiCho;
import com.thao.service.BanService;
import com.thao.service.FoodService;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @author Chung Vu
 */
@Component
public class PDFExporter {
    
    public static final String FONT = "/fonts/arial.ttf";

    public static final Font FONTF = FontFactory.getFont(FontFactory.HELVETICA);

    @Autowired
    private BanService banSer;

    private void writeTableHeader(PdfPTable table) throws DocumentException, IOException {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Mô tả bàn", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Tên món ăn", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Đơn giá", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Số Lượng", font));
        table.addCell(cell);

    }

    private long writeTableData(PdfPTable table, Map<String, MonDatTaiCho> carts, Long sum) {
        for (MonDatTaiCho m : carts.values()) {
            table.addCell(String.valueOf(this.banSer.getBanById(Long.parseLong(Integer.toString(m.getIdBan()))).getMoTa()));
            table.addCell(m.getName());
            table.addCell(Integer.toString(m.getDonGia()));
            table.addCell(Integer.toString(m.getSoLuong()));
            sum += (m.getDonGia() * m.getSoLuong());
        }
        return sum;
    }

    public void export(HttpServletResponse response, Map<String, MonDatTaiCho> carts) throws DocumentException, IOException {
        long sum = 0;
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);

        Paragraph p = new Paragraph(String.format("Hóa Đơn %s", strDate), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        sum = writeTableData(table, carts, sum);

        Paragraph tong = new Paragraph(String.format("Tổng tiền là: %d VND", sum), font);

        document.add(table);
        document.add(tong);
        document.close();
    }

    private void writeTableHeader2(PdfPTable table) throws DocumentException, IOException {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Tên món ăn", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Doanh thu", font));
        table.addCell(cell);

//        cell.setPhrase(new Phrase("Đơn giá", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Số Lượng", font));
//        table.addCell(cell);
    }

    private long writeTableData2(PdfPTable table, List<Object[]> ob, long sum) {
        for (Object[] m : ob) {
            table.addCell((String) m[0]);
            table.addCell(m[1].toString());
            sum += (long) m[1];
        }
        return sum;
    }

    public void export2(HttpServletResponse response, String mode, Map<String, String> params, List<Object[]> ob) throws DocumentException, IOException {
        long sum = 0;
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);

        Paragraph p = new Paragraph(String.format("Thống kê %s", mode), font);
        if (params != null) {
            if (params.get("m") != null && !params.get("m").isEmpty() && params.get("y") != null && !params.get("y").isEmpty()) {
                p = new Paragraph(String.format("Thống kê %s theo tháng %s năm %s", mode, params.get("m"), params.get("y")), font);

            } else if (params.get("m") != null && !params.get("m").isEmpty()) {
                p = new Paragraph(String.format("Thống kê %s theo tháng %s", mode, params.get("m")), font);
            } else if (params.get("y") != null && !params.get("y").isEmpty()) {
                p = new Paragraph(String.format("Thống kê %s theo năm %s", mode, params.get("y")), font);
            }
        }

        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader2(table);
        sum = writeTableData2(table, ob, sum);

        Paragraph tong = new Paragraph(String.format("Tổng tiền là: %d VND", sum), font);

        document.add(table);
        document.add(tong);
        document.close();
    }
}
