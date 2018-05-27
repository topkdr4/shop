package ru.vetoshkin.store.admin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vetoshkin.store.admin.dao.AdminService;
import ru.vetoshkin.store.admin.dao.SessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/api/admin", produces = "application/json", consumes = "application/json")
public class AdminAuth {
    public static final int expiry = 14 * 24 * 60 * 60;


    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public void getAdmin(@RequestBody AdminRequest admin, HttpServletResponse response) throws AdminException {
        Admin transfer = AdminService.exist(admin.transfer());

        if (transfer == null)
            throw new AdminException("Неверный логин или пароль");

        String session = SessionService.save(transfer);

        Cookie cookie = new Cookie("sessionId", session);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);

        response.addCookie(cookie);
    }

}
