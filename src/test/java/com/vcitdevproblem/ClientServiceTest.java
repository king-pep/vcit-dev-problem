package com.vcitdevproblem;

import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.dto.ClientRequest;
import com.vcitdevproblem.exception.ClientNotFoundException;
import com.vcitdevproblem.exception.DuplicateIdException;
import com.vcitdevproblem.exception.DuplicateMobileNumberException;
import com.vcitdevproblem.exception.InvalidIdNumberException;
import com.vcitdevproblem.service.ClientService;
import com.vcitdevproblem.util.MockDataProvider;
import com.vcitdevproblem.util.validation.IdNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ClientService class, testing client creation, search, and validation logic.
 */
@SpringBootTest
public class ClientServiceTest {

    @MockBean // Use MockBean to mock the ClientService
    private ClientService clientService;

    /**
     * Setup method to mock the IdNumberValidator static method.
     * It can be used to simulate valid or invalid ID numbers for testing purposes.
     */
    @BeforeEach
    public void setup() {
//        try (MockedStatic<IdNumberValidator> mockedValidator = Mockito.mockStatic(IdNumberValidator.class)) {
//            mockedValidator.when(() -> IdNumberValidator.validate(anyString())).thenReturn(true);
//        }
    }

    /**
     * Tests successful creation of a client.
     * Ensures that when a valid ClientRequest is provided, the client is created and returned successfully.
     */
    @Test
    void testCreateClientSuccess() {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();
        ClientDTO expectedClient = MockDataProvider.getMockClientResponse1();

        when(clientService.createClient(any(ClientRequest.class))).thenReturn(expectedClient);

        ClientDTO actualClient = clientService.createClient(clientRequest);
        assertEquals("John", actualClient.getFirstName());
        assertEquals("Doe", actualClient.getLastName());

        verify(clientService).createClient(clientRequest);
    }

    /**
     * Tests that creating a client with a duplicate mobile number throws a DuplicateMobileNumberException.
     * Ensures that when two clients share the same mobile number, the service throws an exception.
     */
    @Test
    void testDuplicateMobileNumberThrowsException() {
        ClientRequest clientRequest1 = MockDataProvider.getMockClientRequest1();
        ClientRequest clientRequest2 = MockDataProvider.getMockClientRequest2();

        clientRequest2.setMobileNumber(clientRequest1.getMobileNumber());

        when(clientService.createClient(argThat(client -> client.getMobileNumber().equals(clientRequest1.getMobileNumber()))))
                .thenReturn(MockDataProvider.getMockClientResponse1());

        doThrow(new DuplicateMobileNumberException("Duplicate mobile number found."))
                .when(clientService).createClient(argThat(client -> client.getMobileNumber().equals(clientRequest1.getMobileNumber())));

        assertThrows(DuplicateMobileNumberException.class, () -> clientService.createClient(clientRequest2));

        verify(clientService).createClient(argThat(client -> client.getMobileNumber().equals(clientRequest1.getMobileNumber())));
        verify(clientService).createClient(argThat(client -> client.getMobileNumber().equals(clientRequest2.getMobileNumber())));
    }


    /**
     * Tests that creating a client with a duplicate ID throws a DuplicateIdException.
     * Ensures that when a duplicate ID is used, the service throws a DuplicateIdException.
     */
    @Test
    void testDuplicateIdThrowsException() {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();

        doThrow(new DuplicateIdException("Duplicate ID number found."))
                .when(clientService).createClient(clientRequest);

        assertThrows(DuplicateIdException.class, () -> clientService.createClient(clientRequest));

        verify(clientService).createClient(clientRequest);
    }

    /**
     * Tests successful client update.
     * Ensures that updating a client with valid data works as expected and returns the updated client.
     */
    @Test
    void testUpdateClientSuccess() {
        ClientRequest originalClientRequest = MockDataProvider.getMockClientRequest1();
        ClientRequest updatedClientRequest = MockDataProvider.getMockClientRequest2();
        ClientDTO updatedClientResponse = MockDataProvider.getMockClientResponse2();

        when(clientService.updateClient(anyString(), any(ClientRequest.class))).thenReturn(updatedClientResponse);

        ClientDTO actualUpdatedClient = clientService.updateClient(originalClientRequest.getIdNumber(), updatedClientRequest);

        assertEquals("Jane", actualUpdatedClient.getFirstName());
        assertEquals("Smith", actualUpdatedClient.getLastName());

        verify(clientService).updateClient(eq(originalClientRequest.getIdNumber()), eq(updatedClientRequest));
    }

    /**
     * Tests that updating a non-existent client throws a ClientNotFoundException.
     * Ensures that the service throws a ClientNotFoundException when the client does not exist.
     */
    @Test
    void testUpdateClientNotFoundThrowsException() {
        ClientRequest updatedClientRequest = MockDataProvider.getMockClientRequest2();

        doThrow(new ClientNotFoundException("Client not found"))
                .when(clientService).updateClient(anyString(), any(ClientRequest.class));

        assertThrows(ClientNotFoundException.class,
                () -> clientService.updateClient("nonexistent-id", updatedClientRequest));

        verify(clientService).updateClient(eq("nonexistent-id"), eq(updatedClientRequest));
    }

    /**
     * Tests that an invalid South African ID number with incorrect length throws an InvalidIdNumberException.
     * Ensures that any ID number with less or more than 13 digits causes the validation to fail.
     */
    @Test
    void testInvalidIdLengthThrowsException() {
        String shortId = "901104800081"; // Only 12 digits instead of 13

        // Assert that the exception is thrown when the ID length is incorrect
        InvalidIdNumberException thrownException = assertThrows(
                InvalidIdNumberException.class,
                () -> IdNumberValidator.validate(shortId)
        );

        // Verify that the correct exception message is displayed
        assertEquals("ID number must be exactly 13 digits.", thrownException.getMessage());
    }

    /**
     * Tests that an invalid South African ID number fails validation.
     * Ensures that even though the ID is 13 digits long, it fails validation due to other invalid criteria.
     */
    @Test
    void testInvalidIdThrowsException() {
        String invalidId = "9001015800083";
        boolean isValid = IdNumberValidator.validate(invalidId);
        assertFalse(isValid, "The ID number should fail validation due to invalid format or checksum.");
    }

    /**
     * Tests successful client search.
     * Ensures that searching for a client using valid search criteria returns the expected client.
     */
    @Test
    void testSearchClientSuccess() {
        ClientRequest clientRequest = MockDataProvider.getMockClientRequest1();
        ClientDTO expectedClient = MockDataProvider.getMockClientResponse1();

        when(clientService.searchClient(Optional.of(clientRequest.getFirstName()), Optional.empty(), Optional.empty()))
                .thenReturn(expectedClient);

        ClientDTO actualClient = clientService.searchClient(Optional.of(clientRequest.getFirstName()), Optional.empty(), Optional.empty());

        assertEquals("John", actualClient.getFirstName());
        assertEquals("Doe", actualClient.getLastName());

        verify(clientService).searchClient(Optional.of(clientRequest.getFirstName()), Optional.empty(), Optional.empty());
    }


    /**
     * Tests that searching for a non-existent client throws a ClientNotFoundException.
     * Ensures that the service throws a ClientNotFoundException when no client matches the search criteria.
     */
    @Test
    void testSearchClientNotFoundThrowsException() {
        doThrow(new ClientNotFoundException("Client not found"))
                .when(clientService).searchClient(Optional.empty(), Optional.of("nonexistent-id"), Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.searchClient(Optional.empty(), Optional.of("nonexistent-id"), Optional.empty()));

        verify(clientService).searchClient(Optional.empty(), Optional.of("nonexistent-id"), Optional.empty());
    }
}
