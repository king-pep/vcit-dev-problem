package com.vcitdevproblem.web.rest;

import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.dto.ClientRequest;
import com.vcitdevproblem.dto.ClientResponse;
import com.vcitdevproblem.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller responsible for handling client-related HTTP requests.
 * Supports operations for creating, updating, searching, and deleting clients.
 */
@RestController
@RequestMapping("/v1/clients")
@Validated
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Creates a new client.
     *
     * @param clientRequest the request containing client details
     * @return a {@link ResponseEntity} containing a {@link ClientResponse} with the created {@link ClientDTO} and a success message
     */
    @PostMapping("/create")
    public ResponseEntity<ClientResponse<ClientDTO>> createClient(@RequestBody @Validated ClientRequest clientRequest) {
        ClientDTO createdClient = clientService.createClient(clientRequest);
        ClientResponse<ClientDTO> response = new ClientResponse<>(
                0,
                "api-fm-012",
                "Client created successfully.",
                "Your client has been created.",
                createdClient
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing client.
     *
     * @param idNumber      the ID number of the client to update
     * @param clientRequest the request containing updated client details
     * @return a {@link ResponseEntity} containing a {@link ClientResponse} with the updated {@link ClientDTO} and a success message
     */
    @PutMapping("/update/{idNumber}")
    public ResponseEntity<ClientResponse<ClientDTO>> updateClient(
            @PathVariable String idNumber,
            @RequestBody @Validated ClientRequest clientRequest) {
        ClientDTO updatedClient = clientService.updateClient(idNumber, clientRequest);
        ClientResponse<ClientDTO> response = new ClientResponse<>(
                0,
                "api-fm-013",
                "Client updated successfully.",
                "Your client has been updated.",
                updatedClient
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a client by their ID number.
     *
     * @param idNumber the ID number of the client to delete
     * @return a {@link ResponseEntity} containing a {@link ClientResponse} with the deleted client's ID number and a success message
     */
    @DeleteMapping("/delete/{idNumber}")
    public ResponseEntity<ClientResponse<String>> deleteClient(@PathVariable String idNumber) {
        clientService.deleteClient(idNumber);
        ClientResponse<String> response = new ClientResponse<>(
                0,
                "api-fm-015",
                "Client deleted successfully.",
                "Your client has been deleted.",
                idNumber
        );
        return ResponseEntity.ok(response);
    }


    /**
     * Searches for a client by first name, ID number, or phone number.
     *
     * @param firstName   the optional first name of the client
     * @param idNumber    the optional ID number of the client
     * @param phoneNumber the optional phone number of the client
     * @return a {@link ResponseEntity} containing a {@link ClientResponse} with the found {@link ClientDTO} and a success message
     */
    @GetMapping("/search")
    public ResponseEntity<ClientResponse<ClientDTO>> searchClient(
            @RequestParam(required = false) Optional<String> firstName,
            @RequestParam(required = false) Optional<String> idNumber,
            @RequestParam(required = false) Optional<String> phoneNumber) {

        ClientDTO foundClient = clientService.searchClient(firstName, idNumber, phoneNumber);
        ClientResponse<ClientDTO> response = new ClientResponse<>(
                0,
                "api-fm-014",
                "Client found successfully.",
                "Client found.",
                foundClient
        );
        return ResponseEntity.ok(response);
    }
}
