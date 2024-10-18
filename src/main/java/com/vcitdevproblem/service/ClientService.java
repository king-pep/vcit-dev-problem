package com.vcitdevproblem.service;

import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.exception.ClientNotFoundException;
import com.vcitdevproblem.exception.DuplicateIdException;
import com.vcitdevproblem.exception.DuplicateMobileNumberException;
import com.vcitdevproblem.exception.InvalidIdNumberException;
import com.vcitdevproblem.model.Client;
import com.vcitdevproblem.util.validation.IdNumberValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for handling client-related operations such as creation, updating, searching, and deletion.
 */
@Service
public class ClientService {

    private final Map<String, Client> clients = new HashMap<>();
    private final ClientMapper clientMapper;

    public ClientService(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    /**
     * Creates a new client and adds it to the internal client repository.
     * Validates the client's ID number and ensures it is unique.
     *
     * @param clientDTO the client details to create
     * @return the created {@link ClientDTO} object
     * @throws DuplicateIdException     if a client with the same ID number already exists
     * @throws InvalidIdNumberException if the provided ID number is invalid
     */
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        validateClient(client);

        clients.put(client.getIdNumber(), client);
        return clientMapper.toDTO(client);
    }

    /**
     * Updates an existing client in the internal repository.
     *
     * @param idNumber  the ID number of the client to update
     * @param clientDTO the updated client details
     * @return the updated {@link ClientDTO} object
     * @throws ClientNotFoundException  if the client is not found
     * @throws DuplicateIdException     if the updated ID number or mobile number already exists for another client
     * @throws InvalidIdNumberException if the provided ID number is invalid
     */
    public ClientDTO updateClient(String idNumber, ClientDTO clientDTO) {
        if (!clients.containsKey(idNumber)) {
            throw new ClientNotFoundException("Client not found.");
        }

        Client client = clientMapper.toEntity(clientDTO);
        validateClient(client);

        clients.put(idNumber, client);
        return clientMapper.toDTO(client);
    }

    /**
     * Searches for a client by first name, ID number, or phone number.
     *
     * @param firstName   the optional first name of the client
     * @param idNumber    the optional ID number of the client
     * @param phoneNumber the optional phone number of the client
     * @return the found {@link ClientDTO} object
     * @throws ClientNotFoundException if no client matches the search criteria
     */
    public ClientDTO searchClient(Optional<String> firstName, Optional<String> idNumber, Optional<String> phoneNumber) {
        return clients.values().stream()
                .filter(client ->
                        firstName.map(client.getFirstName()::equalsIgnoreCase).orElse(true) &&
                                idNumber.map(client.getIdNumber()::equals).orElse(true) &&
                                phoneNumber.map(client.getMobileNumber()::equals).orElse(true)
                )
                .findFirst()
                .map(clientMapper::toDTO)
                .orElseThrow(() -> new ClientNotFoundException("Client not found."));
    }

    /**
     * Deletes a client by their ID number.
     *
     * @param idNumber the ID number of the client to delete
     * @throws ClientNotFoundException if no client with the given ID number exists
     */
    public void deleteClient(String idNumber) {
        if (!clients.containsKey(idNumber)) {
            throw new ClientNotFoundException("Client not found.");
        }
        clients.remove(idNumber);
    }

    /**
     * Validates the client for duplicate ID numbers and mobile numbers.
     *
     * @param client the client to validate
     * @throws DuplicateIdException     if a duplicate ID number or mobile number is found
     * @throws InvalidIdNumberException if the ID number is invalid
     */
    private void validateClient(Client client) {
        if (clients.containsKey(client.getIdNumber())) {
            throw new DuplicateIdException("Duplicate ID number found.");
        }

        boolean isValidate = IdNumberValidator.validate(client.getIdNumber());

        if (!isValidate) {
            throw new InvalidIdNumberException("Invalid South African ID number.");
        }

        boolean mobileNumberExists = clients.values().stream()
                .anyMatch(existingClient -> existingClient.getMobileNumber().equals(client.getMobileNumber()));
        if (mobileNumberExists) {
            throw new DuplicateMobileNumberException("Duplicate mobile number found.");
        }
    }
}
