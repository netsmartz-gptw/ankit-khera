package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.UserQuery;
import com.g1app.engine.repositories.UserQueryRepository;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.userdirectory.request.RequestUserQuery;
import com.g1app.engine.userdirectory.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserQueryController {

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserQueryRepository userQueryRepository;

    @PostMapping("/userQuery")
    public ResponseEntity<ResponseMessage> userQuery(@RequestBody RequestUserQuery body, @RequestHeader("Authorization") String token){
        ResponseMessage response = new ResponseMessage();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID parentCustomerId = UUID.fromString(tokenProvider.getUsername(bearer));
            UserQuery userQuery = new UserQuery();
            userQuery.commentDate = System.currentTimeMillis();
            userQuery.customerComment = body.userComment;
            userQuery.customerId = parentCustomerId;
            userQueryRepository.save(userQuery);

            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();

        }else{
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
