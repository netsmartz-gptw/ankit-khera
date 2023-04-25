package com.g1app.engine.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.g1app.engine.userdirectory.request.RequestCreateUser;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    public static final Integer OTP_EXPIRY = 2;
    public static final Integer OTP_EXPIRY_MILLIS = 2 * 60 * 1000;
    private LoadingCache<String, Integer> otpCache;

    public OtpService(){
        otpCache = CacheBuilder.newBuilder()
        .expireAfterWrite(OTP_EXPIRY, TimeUnit.MINUTES)
        .build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String s) throws Exception {
                return 0;
            }
        });
    }

    public Integer generateOTP(String userKey){
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(userKey, OTP);

        return OTP;
    }

    public Integer generateOTP(){
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);

        return OTP;
    }

    public Integer generateOTPAndSendToMobile(String mobileNumber){
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        sendOtpOnMobileNumber(mobileNumber, OTP);
        return OTP;
    }

    public void sendOtpOnMobileNumber(String mobileNumber, int otp) {
        HttpHeaders headers = new HttpHeaders();

        try {
            HttpEntity<String> req = new HttpEntity<>(headers);
            String url = "https://japi.instaalerts.zone/httpapi/QueryStringReceiver?ver=1.0&key=EJ2IW0WZxbN3ksBYQuCMsw==&encrpt=0&dest="
                    + mobileNumber +
                    "&send=HNDSTN&text=Your%20One%20Time%20Password(OTP)%20is%20" + otp + "%0aRegards%0aHindustan+Wellness";
            URL url1 = new URL(url);
            URLConnection conn = url1.openConnection();
            conn.connect();
            //get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult = "";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult = sResult + line + " ";
            }
            rd.close();
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    public Integer getOPTByKey(String key)
    {
        return otpCache.getIfPresent(key);
    }

    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
    
}
