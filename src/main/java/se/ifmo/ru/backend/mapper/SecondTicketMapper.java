package se.ifmo.ru.backend.mapper;

import org.mapstruct.Mapper;

import se.ifmo.ru.ejb.service.model.Ticket;
import se.ifmo.ru.backend.web.model.TicketGetResponseDto;

@Mapper(componentModel = "jakarta")
public interface SecondTicketMapper {
    TicketGetResponseDto toDto(Ticket source);


}

