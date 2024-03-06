package se.ifmo.ru.backend.config;

import jakarta.ws.rs.NotFoundException;
import se.ifmo.ru.ejb.service.api.BookingService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JNDIConfig {
    public static BookingService bookingService() {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProps.put("jboss.naming.client.ejb.context", true);
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "soa");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "12Soa34!");
        try {
            final Context context = new InitialContext(jndiProps);
            return  (BookingService) context.lookup("ejb:/second-service-ejb/BookingServiceImpl!se.ifmo.ru.ejb.service.api.BookingService");
        } catch (NamingException e){
            System.out.println("Not working!");
            e.printStackTrace();
            throw new NotFoundException();
        }
    }
}
