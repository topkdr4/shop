package ru.vetoshkin.store.core;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.admin.dao.SessionService;
import ru.vetoshkin.store.util.ServletUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class AuthFilter implements Filter {
    public static final String cookieName = "sessionId";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest reqt = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        String requestUri = reqt.getRequestURI();
        Cookie cookie = ServletUtil.getCookie(reqt, cookieName);

        if (cookie == null && requestUri.equalsIgnoreCase("/admin")) {
            chain.doFilter(request, response);
            return;
        }


        if (cookie == null) {
            resp.sendRedirect("/admin");
            return;
        }


        try {
            Admin admin = SessionService.getAdmin(cookie.getValue());
            if (admin.getId() != null && requestUri.equals("/admin")) {
                reqt.getRequestDispatcher("/admin/panel").forward(request, response);
                return;
            }

            chain.doFilter(request, response);
        } catch (ExecutionException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void destroy() {

    }

}
