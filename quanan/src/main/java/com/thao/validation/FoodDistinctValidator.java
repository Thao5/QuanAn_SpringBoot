///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.thao.validation;
//
//import com.thao.pojo.ThucAn;
//import com.thao.repository.CustomThucAnRepository;
//import com.thao.service.FoodService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import java.io.Serializable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
//
///**
// *
// * @author Chung Vu
// */
//
//public class FoodDistinctValidator implements ConstraintValidator<FoodDistinct, ThucAn>{
//    
//    @Autowired
//    private CustomThucAnRepository cFoodRepo;
//
//    @Override
//    public void initialize(FoodDistinct constraintAnnotation) {
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//    }
//
//    @Override
//    public boolean isValid(ThucAn t, ConstraintValidatorContext cvc) {
//        if(t.getId() == null)
//            return !this.cFoodRepo.isAlreadyHaveFoodCN(t);
//        return true;
//    }
//
//
//    
//}
