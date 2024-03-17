/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.thao.components.PayPalComponent;
import com.thao.configs.JwtSecurityConfig;
import com.thao.configs.JwtSecurityConfig.PaypalPaymentIntent;
import com.thao.configs.JwtSecurityConfig.PaypalPaymentMethod;
import com.thao.pojo.PaymentRes;
import com.thao.service.HoaDonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiThanhToanOnlineController {

    @Autowired
    private HoaDonService hdSer;
    @Autowired
    private PayPalComponent paypalComponent;

    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    @GetMapping("/create_payment_vnpay/")
    public ResponseEntity<?> createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = JwtSecurityConfig.getRandomNumber(8);
        String vnp_IpAddr = JwtSecurityConfig.getIpAddress(req);

        long amount = 100000 * 100;

        String vnp_TmnCode = JwtSecurityConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", JwtSecurityConfig.vnp_Version);
        vnp_Params.put("vnp_Command", JwtSecurityConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");

//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", JwtSecurityConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = JwtSecurityConfig.hmacSHA512(JwtSecurityConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = JwtSecurityConfig.vnp_PayUrl + "?" + queryUrl;

        PaymentRes paymentRes = new PaymentRes();
        paymentRes.setStatus("OK");
        paymentRes.setMessage("Successfully");
        paymentRes.setURL(paymentUrl);
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

        return new ResponseEntity<>(paymentRes, HttpStatus.OK);
    }

//    @GetMapping("/payment_info/")
//    public ResponseEntity<?> transaction(@RequestParam(value = "vnp_Amount") String amount,
//                                         @RequestParam(value = "vnp_BankCode") String bankCode,
//                                         @RequestParam(value = "vnp_OrderInfo") String orderInfo,
//                                         @RequestParam(value = "vnp_ResponseCode") String responseCode){
//        
//    }
    //Paypal
    @GetMapping("/pay_paypal/")
    public String pay(HttpServletResponse httpServletResponse, HttpServletRequest request/*, @RequestParam("price") Long price*/) throws PayPalRESTException {
        String cancelUrl = this.paypalComponent.getBaseURL(request) + "/" + "admin/nguoidung";
        String successUrl = this.paypalComponent.getBaseURL(request) + "/" + "api/pay/";
        int priceTmp = 10;
        Long price = (long) priceTmp;
        Payment payment = this.hdSer.createPayment(
                price * 1000,
                "USD",
                PaypalPaymentMethod.paypal,
                PaypalPaymentIntent.sale,
                "payment description",
                cancelUrl,
                successUrl);
        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                httpServletResponse.setHeader("Location", links.getHref());
                httpServletResponse.setStatus(302);
                return String.format("redirect:/%s", links.getHref());
            }
        }

        return "redirect:/";
    }
}
