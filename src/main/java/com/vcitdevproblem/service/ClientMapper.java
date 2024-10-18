package com.vcitdevproblem.service;

import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.model.Client;
import org.mapstruct.*;



@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client client);

    Client toEntity(ClientDTO clientDTO);


    @Named("idNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idNumber", source = "idNumber")
    ClientDTO toDtoIdNumber(Client client);

    @Named("mobileNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "mobileNumber", source = "mobileNumber")
    ClientDTO toDtoMobileNumber(Client client);

}
