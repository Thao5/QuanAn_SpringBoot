/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.components;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chung Vu
 */
@Component
public class PayPalComponent {

    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }

        return url.toString();
    }
}