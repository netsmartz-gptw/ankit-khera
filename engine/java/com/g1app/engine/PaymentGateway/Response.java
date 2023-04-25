package PaymentGateway;

import com.g1app.engine.PaymentGateway.HashingAlgorithm;

import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Response
 */
@WebServlet("/Response")
public class Response extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Response() {
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
		//doGet(request, response);
		//string strAmount=response.getParameter("");
		//response.getWriter().append(request.getQueryString());
		//String strAmount=request.getParameter("ppc_Amount");
		//String strMerchantID=request.getParameter("ppc_MerchantID");
		
		 String strSecureSecretTypeProvidedByPG = "SHA256";
	     String strSecureSecretKeyProvidedByPG = "3F1AE0C64CCF48E5B76EB017D35D766E";
		
		Map requestMap=request.getParameterMap();
		Set requestSet = requestMap.entrySet();
	    Iterator requestIterator = requestSet.iterator();
	    
	    String strMsgResponse = "<h1> RESPONSE PARAMETERS </h1>";
	    
	    
	    HashMap<String,String> requestHashMap=new HashMap<String,String>();
		String ppc_DIA_SECRET = "ppc_DIA_SECRET";
		String ppc_DIA_SECRET_TYPE = "ppc_DIA_SECRET_TYPE";
	    while(requestIterator.hasNext()){
	    	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)requestIterator.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();

            strMsgResponse+= key + " = " + value[0] +"<BR />";
            
            if(!(key.equals(ppc_DIA_SECRET) || key.equals(ppc_DIA_SECRET_TYPE)))
            {
            	requestHashMap.put(key, value[0]);
            }
            
	    }
	    
	     String strDIA_SECRET = HashingAlgorithm.GenerateHash(requestHashMap, strSecureSecretKeyProvidedByPG, strSecureSecretTypeProvidedByPG);
	    
	     strMsgResponse+= "Hash Generate on Response Page" + " = " + strDIA_SECRET +"<BR />";
	     
         response.getWriter().append(strMsgResponse);
            
        
		
	}

}
