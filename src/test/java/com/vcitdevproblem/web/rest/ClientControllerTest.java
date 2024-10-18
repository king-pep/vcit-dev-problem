package com.vcitdevproblem.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.dto.ClientRequest;
import com.vcitdevproblem.exception.ClientNotFoundException;
import com.vcitdevproblem.exception.DuplicateIdException;
import com.vcitdevproblem.service.ClientService;
import com.vcitdevproblem.util.MockDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the ClientController class, testing client creation, search, and error handling.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    /**
     * Test successful client creation.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    public void testCreateClientSuccess() throws Exception {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();
        ClientDTO clientResponse = MockDataProvider.getMockClientResponse1();

        when(clientService.createClient(any(ClientRequest.class))).thenReturn(clientResponse);

        mockMvc.perform(post("/v1/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0))
                .andExpect(jsonPath("$.payload.firstName").value("John"));
    }

    /**
     * Test successful client update.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    void testUpdateClientSuccess() throws Exception {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();
        ClientDTO updatedClientResponse = MockDataProvider.getMockClientResponse1();

        when(clientService.updateClient(anyString(), any(ClientRequest.class))).thenReturn(updatedClientResponse);

        mockMvc.perform(put("/v1/clients/update/" + updatedClientResponse.getIdNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0))
                .andExpect(jsonPath("$.payload.firstName").value("John"));
    }

    /**
     * Test that updating a non-existent client throws a ClientNotFoundException.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    void testUpdateClientNotFoundThrowsException() throws Exception {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest2();

        when(clientService.updateClient(anyString(), any(ClientRequest.class)))
                .thenThrow(new ClientNotFoundException("Client not found."));

        mockMvc.perform(put("/v1/clients/update/9999999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value(404))
                .andExpect(jsonPath("$.resultMessage").value("Client not found."));
    }

    /**
     * Test that creating a client with a duplicate ID throws a DuplicateIdException.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    void testDuplicateIdThrowsException() throws Exception {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();

        when(clientService.createClient(any(ClientRequest.class))).thenThrow(new DuplicateIdException("Duplicate ID number found."));

        mockMvc.perform(post("/v1/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value(400))
                .andExpect(jsonPath("$.resultMessage").value("Duplicate ID number found."));
    }

    /**
     * Test successful client search by first name, ID number, and mobile number.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    public void testSearchClientByFirstNameIdAndMobileSuccess() throws Exception {
        ClientDTO clientResponse = MockDataProvider.getMockClientResponse1();

        when(clientService.searchClient(
                any(Optional.class), any(Optional.class), any(Optional.class))
        ).thenReturn(clientResponse);

        mockMvc.perform(get("/v1/clients/search")
                        .param("firstName", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0))
                .andExpect(jsonPath("$.payload.firstName").value("John"))
                .andExpect(jsonPath("$.payload.lastName").value("Doe"));

        mockMvc.perform(get("/v1/clients/search")
                        .param("idNumber", "9001015800083"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0))
                .andExpect(jsonPath("$.payload.firstName").value("John"))
                .andExpect(jsonPath("$.payload.lastName").value("Doe"));

        mockMvc.perform(get("/v1/clients/search")
                        .param("phoneNumber", "0712345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0))
                .andExpect(jsonPath("$.payload.firstName").value("John"))
                .andExpect(jsonPath("$.payload.lastName").value("Doe"));
    }

    /**
     * Test that searching for a client with a non-existent ID throws a ClientNotFoundException.
     *
     * @throws Exception if there is a problem with the mock request
     */
    @Test
    void testSearchClientNotFound() throws Exception {
        when(clientService.searchClient(
                any(Optional.class),
                any(Optional.class),
                any(Optional.class))
        ).thenThrow(new ClientNotFoundException("Client not found."));

        mockMvc.perform(get("/v1/clients/search")
                        .param("idNumber", "9001015800083"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value(404))
                .andExpect(jsonPath("$.resultMessage").value("Client not found."));
    }
}
