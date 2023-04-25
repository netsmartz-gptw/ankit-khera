package com.g1app.engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g1app.engine.PaymentGateway.HashingAlgorithm;
import com.g1app.engine.base.ResponseBase;
import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.thirdparty.redis.RedisController;
import com.g1app.engine.userdirectory.request.RequestByReferenceId;
import com.g1app.engine.userdirectory.request.RequestHashCreation;
import com.g1app.engine.userdirectory.response.ResponseHash;
import com.g1app.engine.userdirectory.response.ResponsePaymentStatus;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RedisController redisController;

    @PostMapping("/paymentStatus")
    public ResponseEntity<ResponsePaymentStatus> getPaymentStatus(@RequestBody RequestByReferenceId body, @RequestHeader("Authorization") String token){

        ResponsePaymentStatus response = new ResponsePaymentStatus();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        response = redisController.getPaymentStatus(String.valueOf(body.referenceId));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/pgCallback")
    public ResponseEntity<ResponseBase> paymentCallback(@RequestParam MultiValueMap<String,String> paramMap){

        Map<String, String> parsedCallback = paramMap.toSingleValueMap();

        String bookingReference = parsedCallback.get("ppc_UniqueMerchantTxnID");
        redisController.setPaymentCallbackResult(bookingReference, parsedCallback);

        System.out.println(paramMap);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/paymentGateway/hashingRequest")
    public ResponseEntity<ResponseHash> getHashKey(@RequestBody RequestHashCreation request,
                                                   @RequestHeader("Authorization") String token) throws JsonProcessingException {
        ResponseHash response = new ResponseHash();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            if(request != null){

                ObjectMapper Obj = new ObjectMapper();
                String jsonStr = Obj.writeValueAsString(request);

                HashMap<String,String> requestMap = new Gson().fromJson(jsonStr, HashMap.class);

                // Map<String, String> requestMap = new ObjectMapper().readValue(jsonStr, HashMap.class);

                String key =  HashingAlgorithm.GenerateHash(requestMap,"DDAB9C3BC7DC4866B925AAF84868F30E","ppc_DIA_SECRET");


                response.ppc_DIA_SECRET = key;
                response.statusCode = 1;
                response.message ="Success";
            }

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
