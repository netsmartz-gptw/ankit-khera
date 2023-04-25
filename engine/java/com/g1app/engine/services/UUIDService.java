package com.g1app.engine.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UUIDService {

    public UUID getUUID(){
        return UUID.randomUUID();
    }

    public UUID getUUID(String uuid){
        return UUID.fromString(uuid);
    }
    
}
