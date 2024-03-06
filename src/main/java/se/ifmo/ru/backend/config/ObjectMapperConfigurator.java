package se.ifmo.ru.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperConfigurator {
    @Produces
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }
}
