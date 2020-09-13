package main.java.web.config;

import main.java.web.GroupController;
import main.java.web.StudentController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


public class RestServiceInitializer implements WebApplicationInitializer {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void onStartup(ServletContext servletContext) {

        log.info("rest service INIT....");
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        ctx.register(WebAppConfig.class);
        ctx.register(StudentController.class);
        ctx.register(GroupController.class);

        servletContext.addListener(new ContextLoaderListener(ctx));
        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic app = servletContext.addServlet("App", new DispatcherServlet(ctx));
        app.addMapping("/");

        log.info("rest service START");

    }
}
