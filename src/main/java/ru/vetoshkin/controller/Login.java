package ru.vetoshkin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Ветошкин А.В. РИС-16бзу
 */
@Controller
@RequestMapping("/")
public class Login {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated())
            return "welcome";

        return "login";
    }


}
