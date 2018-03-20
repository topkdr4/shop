package ru.vetoshkin.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Controller
public class Test {

    @GetMapping("/now")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        modelAndView.addObject("ebota", "AHAHA");
        return modelAndView;
    }

}
