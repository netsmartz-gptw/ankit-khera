package com.g1app.engine.controller;

import com.g1app.engine.repositories.UserRepository;
import com.g1app.engine.userdirectory.request.RequestCrmData;
import com.g1app.engine.userdirectory.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bridge")
public class BridgeController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/saveCrmData")
    public ResponseEntity<ResponseMessage> saveCrmData(@RequestBody RequestCrmData requestCrmData) {

        //Check whether ca_id exists in our database
        //boolean isCaIdExists=userRepository.findByCaId();


        //Send Successful Response Message
        ResponseMessage responseMessage=new ResponseMessage();
        responseMessage.message="Success";
        responseMessage.statusCode=1;

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}