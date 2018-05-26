package ru.vetoshkin.store.admin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vetoshkin.store.admin.dao.AdminService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/api/admin", produces = "application/json", consumes = "application/json")
public class AdminAuth {
    private static final int expiry = 14 * 24 * 60 * 60;


    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public void getAdmin(@RequestBody AdminRequest admin, HttpServletResponse response) {
        Admin result = AdminService.save(admin.transfer());

        Cookie cookie = new Cookie("sessionId", result.getSessionId());
        cookie.setPath("/");
        cookie.setMaxAge(expiry);

        response.addCookie(cookie);
    }

}
