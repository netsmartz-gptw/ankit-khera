package com.g1app.engine.job;

import com.g1app.engine.repositories.CityMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class DropIdleConnection {

    @Autowired
    CityMasterRepository cityMasterRepository;

    @Scheduled(fixedDelay = 120000)
    boolean cleanCalls(){
       Boolean value = cityMasterRepository.cleanAllIdleConnection();
       return true;
    }
}
