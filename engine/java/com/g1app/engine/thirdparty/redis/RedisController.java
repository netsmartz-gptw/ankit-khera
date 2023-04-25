package com.g1app.engine.thirdparty.redis;

import com.g1app.engine.userdirectory.response.ResponsePaymentStatus;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class RedisController {

    RedisClient client;
    RedisCommands<String, String> commands;
    private final long CACHE_EXPIRE_TIME = 3600;
    private final long OTP_EXPIRE_TIME = 600;
    private final long REMOVE_CALL_AFTER = 60*1000; // Seconds*Millis

//    @Autowired
//    JsonUtils jsonUtils;

    RedisController(){
        client = RedisClient.create("redis://b840fc02d524045429941cc15f59e41cb7be6c52@139.59.89.214:6379");
        //client = new RedisClient(RedisURI.create("redis://b840fc02d524045429941cc15f59e41cb7be6c52@139.59.89.214:6379"));
        StatefulRedisConnection<String, String> connection = client.connect();
        commands = connection.sync();

    }

    public RedisClient getClient(){
        return client;
    }

    public void setPaymentCallbackResult(String bookingReference, Map<String, String> data){
        commands.hmset(bookingReference, data);
    }

    public ResponsePaymentStatus getPaymentStatus(String bookingReference){

        ResponsePaymentStatus responsePaymentStatus = new ResponsePaymentStatus();
        responsePaymentStatus.statusCode = -1;
        responsePaymentStatus.message = "Payment status not yet received";

        List<KeyValue<String, String>> rawValues = commands.hmget(bookingReference,
                "ppc_TxnResponseCode","ppc_Amount","ppc_PaymentMode","ppc_TxnResponseMessage", "ppc_PinePGTransactionID");

        for(KeyValue kv:rawValues){

            String value = "";

            if(kv.getKey().toString().equalsIgnoreCase("ppc_TxnResponseCode")){
                value = kv.getValue().toString();
                if(value.equalsIgnoreCase("1")){
                    responsePaymentStatus.statusCode = 1;
                }else{
                    responsePaymentStatus.statusCode = -1;
                }
            }else if(kv.getKey().toString().equalsIgnoreCase("ppc_Amount")){

                responsePaymentStatus.amount = kv.getValue().toString();

            }else if(kv.getKey().toString().equalsIgnoreCase("ppc_PaymentMode")){

                value = kv.getValue().toString();
                if(value.equalsIgnoreCase("1")){
                    responsePaymentStatus.paymentMode = "CREDIT/DEBIT CARD";
                }else if(value.equalsIgnoreCase("2")){
                    responsePaymentStatus.paymentMode = "SAVED CARD";
                }else if(value.equalsIgnoreCase("3")){
                    responsePaymentStatus.paymentMode = "NET BANKING";
                }else if(value.equalsIgnoreCase("4")){
                    responsePaymentStatus.paymentMode = "EMI";
                }else if(value.equalsIgnoreCase("5")){
                    responsePaymentStatus.paymentMode = "REWARDS";
                }else if(value.equalsIgnoreCase("6")){
                    responsePaymentStatus.paymentMode = "EZECLICK";
                }

            }else if(kv.getKey().toString().equalsIgnoreCase("ppc_TxnResponseMessage")){
                value = kv.getValue().toString();
                if(value.equalsIgnoreCase("success")){
                    responsePaymentStatus.statusCode = 1;
                }else{
                    responsePaymentStatus.statusCode = -1;
                }
            }else if(kv.getKey().toString().equalsIgnoreCase("ppc_PinePGTransactionID")){

                responsePaymentStatus.bookingId = kv.getValue().toString();

            }
        }

        return  responsePaymentStatus;
    }

}
