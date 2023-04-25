package com.g1app.engine.PaymentGateway;

import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;


/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
*/

/**
 * Servlet implementation class Redirect
 */
@WebServlet("/Redirect")
public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Redirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		    String strAmount = request.getParameter("Amount");
	        String strProductCode = request.getParameter("ProductCode"); 
	        String strUniqueMerchantTxnID = request.getParameter("MerchantTxnID");
	        String strMerchantID = request.getParameter("MerchantID");
	        String strMerchantAccessCode = request.getParameter("AccessCode");
	        String strPayModeOnLandingPage = request.getParameter("PayMode");
	        String strNavigationMode = "2";
	        String strTransactionType = "1";
	        String strSecureSecretTypeProvidedByPG = "SHA256";
	        String strSecureSecretKeyProvidedByPG = "3F1AE0C64CCF48E5B76EB017D35D766E";
	        String strMerchantReturnURL = "http://192.168.126.104:8080/PinePGIntegration2/Response";
	       // String strMerchantReturnURL = "http://192.168.101.203:7050/ChargingResp.aspx";
	        
	        
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
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    
		    out.print(strForm);

	}

}
