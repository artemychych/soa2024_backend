package se.ifmo.ru.backend.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import se.ifmo.ru.backend.web.model.Error;
import java.time.Instant;

@ApplicationScoped
public class ResponseUtils {
    public Response buildResponseWithMessage(Response.Status status, String message) {
        return Response
                .status(status)
                .entity(Error
                        .builder()
                        .message(message)
                        .code(String.valueOf(status.getStatusCode()))
                        .date(Instant.now().toString())
                        .build()
                )
                .build();
    }
}
