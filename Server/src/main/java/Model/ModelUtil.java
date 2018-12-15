package Model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;

public class ModelUtil {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        boolean isTomCat = System.getProperty("user.dir").toLowerCase().contains("tomcat");
        File f = null;
        if (isTomCat) {
            f = new File("..\\..\\Server\\res\\hibernate.cfg.xml");
        } else {
            f = new File("res\\hibernate.cfg.xml");
        }
        Configuration conf = new Configuration();
        conf.configure(f);
        serviceRegistry = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
        try {
            sessionFactory = conf.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}