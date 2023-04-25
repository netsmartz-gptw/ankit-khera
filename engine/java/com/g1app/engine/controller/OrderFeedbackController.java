package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.OrderRating;
import com.g1app.engine.repositories.OrderRatingRepository;
import com.g1app.engine.userdirectory.response.ResponseMessage;
import com.g1app.engine.utils.UrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstant.FEEDBACK)
public class OrderFeedbackController {

    @Autowired
    OrderRatingRepository orderRatingRepository;

    @PostMapping("/orderFeedback")
    public ResponseEntity<ResponseMessage> addProfileInMaster(@RequestBody OrderRating request) {
        ResponseMessage response = new ResponseMessage();
        if (request != null) {
            orderRatingRepository.save(request);
            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        } else {
            response.message = ResponseStatusMessages.WRONG_INPUT.getValue();
            response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
