package com.vcitdevproblem.util;

import com.vcitdevproblem.dto.ClientDTO;
import com.vcitdevproblem.dto.ClientRequest;

public class MockDataProvider {

    public static ClientRequest getMockClientRequest1() {
        return new ClientRequest("John", "Doe", "0812345678", "4801104800088", "123 Elm Street");
    }

    public static ClientRequest getMockClientRequest2() {
        return new ClientRequest("Jane", "Smith", "0723456789", "3901014800086", "456 Maple Avenue");
    }

    public static ClientDTO getMockClientResponse1() {
        return new ClientDTO("John", "Doe", "0712345678", "3901104800087", "123 Elm Street");
    }

    public static ClientDTO getMockClientResponse2() {
        return new ClientDTO("Jane", "Smith", "0723456789", "4501104800084", "456 Maple Avenue");
    }
}
