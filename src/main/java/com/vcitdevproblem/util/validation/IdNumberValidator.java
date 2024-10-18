package com.vcitdevproblem.util.validation;

import com.vcitdevproblem.exception.InvalidIdNumberException;
import org.springframework.stereotype.Component;

@Component
public class IdNumberValidator {


    private IdNumberValidator() {
    }

    /**
     * Validates a South African ID number using a checksum algorithm.
     *
     * @param idNumber the ID number to validate
     * @return true if the ID number is valid, false otherwise
     * @throws InvalidIdNumberException if the input is null, empty, or contains non-numeric characters
     */
    public static Boolean validate(String idNumber) throws InvalidIdNumberException {
        if (idNumber == null || idNumber.isEmpty()) {
            throw new InvalidIdNumberException("ID number cannot be null or empty.");
        }

        if (!idNumber.matches("\\d+")) {
            throw new InvalidIdNumberException("ID number must contain only digits.");
        }

        if (idNumber.length() != 13) {
            throw new InvalidIdNumberException("ID number must be exactly 13 digits.");
        }

        int sum = 0;
        int total;
        int multiplier = 1;

        for (String number : idNumber.split("")) {
            int digit = Integer.parseInt(number);
            total = digit * multiplier;

            if (total > 9) {
                total = (total / 10) + (total % 10);
            }

            sum += total;

            multiplier = (multiplier == 1) ? 2 : 1;
        }

        return sum % 10 == 0;
    }
}
