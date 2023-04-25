package com.hw.g1payments.servlets;

import com.hw.g1payments.RequestByReferenceId;
import com.hw.g1payments.ResponseReferenceDetails;
import com.hw.g1payments.pine.HashingAlgorithm;
import com.hw.g1payments.pine.HttpHelper;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String bookingReference = request.getParameter("bookingReference");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        RequestByReferenceId requestByReferenceId = new RequestByReferenceId();
        requestByReferenceId.referenceId = UUID.fromString(bookingReference);

        RestTemplate restTemplate = new RestTemplate();
        ResponseReferenceDetails refDetails = restTemplate.postForObject("https://engine.dev.hindustanlab.com/booking/referenceId",
                requestByReferenceId
                ,ResponseReferenceDetails.class );


        String strAmount = String.valueOf((Integer.valueOf(refDetails.Amount) * 100));
        String strProductCode = refDetails.ProductCode;
        String strUniqueMerchantTxnID = bookingReference;
        String strMerchantID = "106600";
        String strMerchantAccessCode = "bcf441be-411b-46a1-aa88-c6e852a7d68c";
        String strPayModeOnLandingPage = "1";
        String strNavigationMode = "2";
        String strTransactionType = "1";
        String strSecureSecretTypeProvidedByPG = "SHA256";
        String strSecureSecretKeyProvidedByPG = "9A7282D0556544C59AFE8EC92F5C85F6";
        String strMerchantReturnURL = "https://engine.dev.hindustanlab.com/pgCallback";


        HashMap<String, String> requestMap = new HashMap<String, String>();

        requestMap.put("ppc_Amount", strAmount);
        requestMap.put("ppc_Product_Code", strProductCode);
        requestMap.put("ppc_UniqueMerchantTxnID", strUniqueMerchantTxnID);
        requestMap.put("ppc_MerchantID",strMerchantID);
        requestMap.put("ppc_MerchantAccessCode", strMerchantAccessCode);
        requestMap.put("ppc_PayModeOnLandingPage", strPayModeOnLandingPage);
        requestMap.put("ppc_NavigationMode", strNavigationMode);
        requestMap.put("ppc_TransactionType", strTransactionType);
        requestMap.put("ppc_LPC_SEQ", "1");
        requestMap.put("ppc_MerchantReturnURL", strMerchantReturnURL);


        String strDIA_SECRET = HashingAlgorithm.GenerateHash(requestMap, strSecureSecretKeyProvidedByPG,
                strSecureSecretTypeProvidedByPG);

        requestMap.put("ppc_DIA_SECRET_TYPE", strSecureSecretTypeProvidedByPG);
        requestMap.put("ppc_DIA_SECRET", strDIA_SECRET);


        String redirectUrl ="https://uat.pinepg.in/PinePGRedirect/index";
        String strForm = HttpHelper.PreparePOSTForm(redirectUrl, requestMap);

        out.print(strForm);

    }
}
