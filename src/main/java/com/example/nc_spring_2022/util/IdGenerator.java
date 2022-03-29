package com.example.nc_spring_2022.util;

import java.util.UUID;

public class IdGenerator {
    public static UUID createId() {
        return UUID.randomUUID();
    }
}
