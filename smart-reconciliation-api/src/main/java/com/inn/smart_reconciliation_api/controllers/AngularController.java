package com.inn.smart_reconciliation_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularController {
    
    @RequestMapping(value = {
        "/{path:[^\\.]*}",           // Single-level route (e.g., /login)
        "/**/{path:[^\\.]*}"         // Multi-level route (e.g., /dashboard/user)
    })
    public String forwardToFrontend() {
        return "forward:/index.html";
    }
}
