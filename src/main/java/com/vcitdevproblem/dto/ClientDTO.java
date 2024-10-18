package com.vcitdevproblem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClientDTO {

    @NotBlank(message = "First name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last name is mandatory.")
    private String lastName;

    @Pattern(regexp = "^(\\+27|0)[6-8][0-9]{8}$", message = "Mobile number is invalid.")
    private String mobileNumber;

    @NotBlank(message = "ID number is mandatory.")
    @Size(min = 13, max = 13, message = "ID number must be exactly 13 digits.")
    private String idNumber;
    private String physicalAddress;

    public ClientDTO() {
    }
    public ClientDTO(String firstName, String lastName, String mobileNumber, String idNumber, String physicalAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.idNumber = idNumber;
        this.physicalAddress = physicalAddress;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
