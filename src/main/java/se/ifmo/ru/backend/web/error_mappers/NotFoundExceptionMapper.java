package se.ifmo.ru.backend.web.error_mappers;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import se.ifmo.ru.backend.util.ResponseUtils;

@Provider
@Slf4j
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Inject
    ResponseUtils responseUtils;

    @Override
    public Response toResponse(NotFoundException exception) {
        log.error(exception.getMessage(), exception);

        if (StringUtils.isEmpty(exception.getMessage())) {
            return responseUtils.buildResponseWithMessage(
                    Response.Status.NOT_FOUND,
                    "Not Found"
            );
        }
        if (exception.getCause() != null) {
            if (exception.getCause().getClass() == NumberFormatException.class) {
                return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Invalid parameters supplied");
            }
        }
        return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND, exception.getMessage());
    }
}
