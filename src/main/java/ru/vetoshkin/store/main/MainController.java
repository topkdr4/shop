package ru.vetoshkin.store.main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





/**
 * Ветошкин А.В. РИС-16бзу
 * */

@Controller
public class MainController {


    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String getAdmin() {
        return "admin/login";
    }


    @RequestMapping(path = "/admin/panel", method = RequestMethod.GET)
    public String getAdminPanel() {
        return "admin/panel";
    }



    @RequestMapping(path = "/store", method = RequestMethod.GET)
    public String getStore() {
        return "welcome";
    }

}
