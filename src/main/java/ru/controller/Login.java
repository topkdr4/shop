package ru.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.Jackson;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Controller
public class Login {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(Jackson.toJson(authentication));
        System.out.println(authentication.getDetails().getClass().getCanonicalName());
        System.out.println();


        return "login";
    }


}
