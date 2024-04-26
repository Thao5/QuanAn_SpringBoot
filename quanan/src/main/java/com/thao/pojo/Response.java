/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import lombok.Data;

/**
 *
 * @author Chung Vu
 */
 @Data
 public class Response {

   private Properties properties;

   @Data
   public static class Properties {
     private Object[] periods;
   }
 }
