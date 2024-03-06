package se.ifmo.ru.backend.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.ru.backend.config.JNDIConfig;
import se.ifmo.ru.backend.mapper.SecondTicketMapper;

import se.ifmo.ru.ejb.external.model.RestClientTicket;
import se.ifmo.ru.ejb.external.model.TicketGetResponseDto;
import se.ifmo.ru.ejb.service.api.BookingService;
import se.ifmo.ru.backend.util.ResponseUtils;

import javax.naming.NamingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Path("/service")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class BookingController {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("PING").build();
    }

    BookingService bookingService = JNDIConfig.bookingService();

    @Inject
    ResponseUtils responseUtils;

    @Inject
    SecondTicketMapper secondTicketMapper;

    public BookingController() throws NamingException {
    }

    @POST
    @Path("/sell/vip/{ticket-id}")
    public Response copyTicketVIPAndDoublePrice(@PathParam("ticket-id") int id) throws NoSuchAlgorithmException, KeyManagementException, JsonProcessingException {
        if (id >= 1) {
            TicketGetResponseDto ticket = unMarshalTicket(bookingService.copyTicketWithVipAndDoublePrice(id));
            return Response.ok().entity(ticket).build();

        }
        throw new IllegalArgumentException("Invalid parameters!");
    }

    @DELETE
    @Path("/event/{ticket-name}/{coorX}/{coorY}/cancel")
    public Response deleteAllTicketsWithNameAndCoordinates
            (@PathParam("ticket-name") String name,
             @PathParam("coorX") int x,
             @PathParam("coorY") int y) throws CertificateException, NoSuchAlgorithmException, KeyManagementException {
        boolean deleted = bookingService.deleteAllTicketsWithNameAndCoordinates(
                name, x, y
        );

        if (!deleted) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND, "Flat with name " + name + " not found");
        }

        return Response.noContent().build();
    }

    private TicketGetResponseDto unMarshalTicket(String jsonTicket) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(jsonTicket, new TypeReference<>() {
        });
    }
}
