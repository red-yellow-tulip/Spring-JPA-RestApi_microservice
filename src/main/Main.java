package main;


import main.java.service.ConfDataSource;
import main.java.service.ServiceDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

import static org.junit.Assert.assertNotNull;

//@SpringBootApplication
@ComponentScan
public class Main {

    private static final Logger log = LogManager.getLogger();

    private static  GenericApplicationContext ctx;

    private ServiceDatabase service;

    public static void main(String[] args) {

/*

        ctx = new AnnotationConfigApplicationContext(ConfDataSource.class);
        ServiceDatabase service = ctx.getBean(ServiceDatabase.class);
        assertNotNull(service);
*/

    }
}
