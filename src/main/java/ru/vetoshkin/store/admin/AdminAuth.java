package ru.vetoshkin.store.admin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vetoshkin.store.admin.dao.AdminService;
import ru.vetoshkin.store.admin.dao.SessionService;
import ru.vetoshkin.store.admin.dto.AdminRequest;
import ru.vetoshkin.store.core.AuthFilter;
import ru.vetoshkin.store.util.ServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;





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


    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = ServletUtil.getCookie(request, AuthFilter.cookieName);
        if (cookie == null)
            return;

        cookie.setValue("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/admin");
    }

}
