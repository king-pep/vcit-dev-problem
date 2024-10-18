package com.vcitdevproblem.util;


import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.model.Client;
import com.vcitdevproblem.mapper.ClientMapper;
import com.vcitdevproblem.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes mock client data on application startup.
 */
@Component
@Slf4j
public class MockDataInitializer implements CommandLineRunner {

    private final ClientService clientService;

    private final ClientMapper clientMapper;


    public MockDataInitializer(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    /**
     * Initializes mock client data on application startup.
     * uncomment the code below to initialize mock data
     *
     * @param args command-line arguments passed during the application startup.
     *             This is typically not used in this method.
     * @throws Exception if an error occurs while initializing the mock data.
     */
    @Override
    public void run(String... args) throws Exception {
        Client client1 = new Client("John", "Doe", "0712345678", "9601104800087", "123 Elm Street");
        Client client2 = new Client("Jane", "Smith", "0723456789", "9901104800081", "456 Maple Avenue");

        ClientDTO clientDto1 = clientMapper.toDTO(client1);
        ClientDTO clientDto2 = clientMapper.toDTO(client2);

        clientService.createClient(clientDto1);
        clientService.createClient(clientDto2);

        log.info("Mock data initialized.");
    }
}
