package ru.vetoshkin.store.core.configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.vetoshkin.store.util.HikariPool;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.IOException;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class WebApplicationBootstrap implements WebApplicationInitializer {
    private static final String TMP_FOLDER = "D:\\temp";
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
    private static final MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
            MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            HikariPool.init();

            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

            context.register(WebConfiguration.class);

            servletContext.addListener(new ContextLoaderListener(context));
            context.setServletContext(servletContext);

            ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
            dispatcher.setMultipartConfig(multipartConfigElement);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


}
