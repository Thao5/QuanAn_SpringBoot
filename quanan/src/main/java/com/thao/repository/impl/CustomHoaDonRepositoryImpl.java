/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.PayPalRESTException;
import com.thao.configs.JwtSecurityConfig;
import com.thao.pojo.HoaDon;
import com.thao.repository.CustomHoaDonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
@PropertySource("classpath:configs.properties")
public class CustomHoaDonRepositoryImpl implements CustomHoaDonRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Environment env;
    @Autowired
    private APIContext apiContext;

    @Override
    public List<HoaDon> getHDs(Map<String, String> params) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<HoaDon> q = b.createQuery(HoaDon.class);
        Root root = q.from(HoaDon.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("idNguoiDung").get("firstName"), String.format("%%%s%%", kw)));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = entityManager.createQuery(q);
        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public Payment createPayment(Long total, String currency, JwtSecurityConfig.PaypalPaymentMethod method, JwtSecurityConfig.PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl) {
        try {
            Amount amount = new Amount();
            amount.setCurrency(currency);
            amount.setTotal(String.format("%d", total));
            
            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);
            
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            
            Payer payer = new Payer();
            payer.setPaymentMethod(method.toString());
            
            Payment payment = new Payment();
            payment.setIntent(intent.toString());
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);
            apiContext.setMaskRequestId(true);
            return payment.create(apiContext);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(CustomHoaDonRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) {
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(CustomHoaDonRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
