package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.AppUpdateInfo;
import com.g1app.engine.models.DeviceInfo;
import com.g1app.engine.repositories.AppUpdateInfoRepository;
import com.g1app.engine.repositories.DeviceInfoRepository;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.services.UUIDService;
import com.g1app.engine.userdirectory.response.*;
import com.g1app.engine.utils.CommonConstant;
import com.g1app.engine.utils.UrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * Created By. Mayank S.
 */
@RestController
@RequestMapping(UrlConstant.APP)
public class AppController {

    @Autowired
    AppUpdateInfoRepository appUpdateInfoRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    DeviceInfoRepository deviceInfoRepository;
    @Autowired
    UUIDService uuidService;



    /**
     * This API uses to save data related to APP INFO.
     * @param requestBody
     */
    @PostMapping(UrlConstant.UPDATE_APP_INFO)
    public ResponseEntity<ResponseMessage> updateAppInfo(@RequestBody AppUpdateInfo requestBody){
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo();
        appUpdateInfo.setID(UUID.randomUUID());
//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//        appUpdateInfo.setDate(currentTimestamp);
        appUpdateInfo.setAndroidMarketVersion(requestBody.getAndroidMarketVersion());
        appUpdateInfo.setAndroidUpdateType(requestBody.getAndroidUpdateType());
        appUpdateInfo.setIosMarketVersion(requestBody.getIosMarketVersion());
        appUpdateInfo.setIosUpdateType(requestBody.getIosUpdateType());
        appUpdateInfo.setActive(true);
        appUpdateInfoRepository.save(appUpdateInfo);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.message = ResponseStatusMessages.APP_DETAILS_UPDATED.getValue();
        responseMessage.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    /**
     * This api is to return the latest app version and its type of nature(Mandatory, Optional).
     * @param platform
     * @return
     */
    @PostMapping(UrlConstant.CHECK_APP_UPDATE)
    public ResponseEntity appUpdateCheck(@RequestHeader(value = CommonConstant.PLATFORM, required = true) String platform,
                                         @RequestHeader(name = CommonConstant.DEVICE_ID, required = true) String imei_number,
                                         @RequestHeader(name = CommonConstant.OS_VERSION, required = true) String os_version,
                                         @RequestHeader(name = CommonConstant.APP_VERSION, required = true) String app_version
                                         ){
        CommonConfigResponse response = new CommonConfigResponse();
        if(imei_number.isEmpty() || os_version.isEmpty() || app_version.isEmpty() || platform.isEmpty()){
            response.message = ResponseStatusMessages.REQUEST_HEADER_IS_MISSING.getValue();
            response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>( response,HttpStatus.OK);
        }


        DeviceInfo deviceInfo = deviceInfoRepository.findByImeiAndOsVersionAndAppVersionAndPlatform(imei_number,os_version,app_version,platform);
        String sessionToken = uuidService.getUUID().toString();

        if(deviceInfo == null) {
            DeviceInfo newDeviceInfo = new DeviceInfo();
            newDeviceInfo.setAppVersion(app_version);
            newDeviceInfo.setId(uuidService.getUUID());
            newDeviceInfo.setImei(imei_number);
            newDeviceInfo.setPlatform(platform);
            newDeviceInfo.setOsVersion(os_version);
            newDeviceInfo.setSessionToken(sessionToken);
            deviceInfoRepository.save(newDeviceInfo);
        }else {
            deviceInfo.setSessionToken(sessionToken);
            deviceInfoRepository.save(deviceInfo);
        }

        AppUpdateInfo appUpdateInfo = appUpdateInfoRepository.findByIsActive(CommonConstant.TRUE);

        if(appUpdateInfo != null) {

            response.otpDigitLength = appUpdateInfo.getOtpDigitLength();
            response.otpRetryCount = appUpdateInfo.getOtpRetryCount();
            response.otpTimeout = appUpdateInfo.getOtpTimeout();
            response.privacyPolicyUrl= appUpdateInfo.getPrivacyPolicyUrl();
            response.termsAndConditions = appUpdateInfo.getTermsAndConditionsUrl();
            response.sessionToken = sessionToken;

            if (platform.equalsIgnoreCase(CommonConstant.IOS)) {
                response.marketAppVersion = appUpdateInfo.getIosMarketVersion();
                response.updateType       = appUpdateInfo.getIosUpdateType();
            }else if (platform.equalsIgnoreCase(CommonConstant.ANDROID)) {
                response.marketAppVersion = appUpdateInfo.getAndroidMarketVersion();
                response.updateType       = appUpdateInfo.getAndroidUpdateType();
            }

            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();

        }else{
            response.message = ResponseStatusMessages.ERROR.getValue();
            response.statusCode = ResponseStatusCode.ERROR.getValue();
        }
        return new ResponseEntity<>( response,HttpStatus.OK);
    }

    @PostMapping("/otpSetting")
    public ResponseEntity<ResponseOtpSettings> otpSettings(){

        ResponseOtpSettings responseOtpSettings =new ResponseOtpSettings();


        responseOtpSettings.otpDigitCount = 6;
        responseOtpSettings.expireMinutes = 120;
        responseOtpSettings.number = "9879879870";
        responseOtpSettings.enableSeconds = 1;

        responseOtpSettings.message = ResponseStatusMessages.SUCCESS.getValue();
        responseOtpSettings.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(responseOtpSettings,HttpStatus.OK);
    }
}
