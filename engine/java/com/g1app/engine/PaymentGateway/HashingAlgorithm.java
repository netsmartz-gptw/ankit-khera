package com.g1app.engine.PaymentGateway;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HashingAlgorithm {

	/**
	 * This method will generate key hash 
	 * @param requestMap
	 * @param strSecretKey
	 * @param strHashType
	 * @return
	 */
	public static String GenerateHash(Map<String, String> requestMap, String strSecretKey, String strHashType) {
		String strHash = ""; 
		try { 
			if (requestMap == null || requestMap.size() == 0 || !isValidString(strSecretKey) || !isValidString(strHashType)) {
				return strHash; 
			} 
			TreeMap<String, String> sortdRequestMap = new TreeMap<String, String>(requestMap);
			//Convert Secret Key to required format 
			byte[] convertedHashKey = new byte[strSecretKey.length() / 2]; 
			for (int i = 0; i < strSecretKey.length() / 2; i++) {
				convertedHashKey[i] = (byte)Integer.parseInt(strSecretKey.substring(i * 2, (i*2)+2),16); //hexNumber radix
			} 
			// Build string from map. 
			
			//byte[] convertedHashKey2={0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x10,0x11,0x12,0x13,0x14,0x15};
			
			
			StringBuilder sbMessage = new StringBuilder(); 
			for (Map.Entry<String, String> kvp : sortdRequestMap.entrySet()) {
					sbMessage.append(kvp.getKey()+ "=" + kvp.getValue() + "&"); 
			}
			sbMessage.deleteCharAt(sbMessage.length()-1);
			//generate hash 
			if (strHashType.equalsIgnoreCase("SHA256")) {
				strHash = hmacDigest(sbMessage.toString(), convertedHashKey, "HmacSHA256");
				
			} 
			else if (strHashType.equalsIgnoreCase("MD5")) {
				strHash = hmacDigest(sbMessage.toString(), convertedHashKey, "HmacMD5");
			} 
		} catch (Exception ex) {
			strHash = ""; 
		} 
		return strHash.toUpperCase(); 
	}
	
	
	private static String hmacDigest(String msg, byte[] keyString, String algo) {
	    String digest = null;
	    try {
		     SecretKeySpec key = new SecretKeySpec(keyString, algo);
		     Mac mac = Mac.getInstance(algo);
		     mac.init(key);
		     byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
		     StringBuffer hash = new StringBuffer();
		     for (int i = 0; i < bytes.length; i++) {
		       String hex = Integer.toHexString(0xFF & bytes[i]);
		       if (hex.length() == 1) {
		         hash.append('0');
		       }
		       hash.append(hex);
		     }
		     digest = hash.toString();
	    } catch (UnsupportedEncodingException e) {
//	    	logger.error("Exception occured in hashing the pine payment gateway request"+e);
	    } catch (InvalidKeyException e) {
//	    	logger.error("Exception occurred in hashing the pine payment gateway request"+e);
	    } catch (NoSuchAlgorithmException e) {
//	    	logger.error("Exception occurred in hashing the pine payment gateway request"+e);
	    }
	    return digest;
	  }
	
	public static boolean isValidString(String str){
		if(str != null && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
}
