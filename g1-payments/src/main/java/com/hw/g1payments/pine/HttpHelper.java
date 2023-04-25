package com.hw.g1payments.pine;

import java.util.HashMap;

public class HttpHelper {
    public static String PreparePOSTForm(String url, HashMap<String, String> data)
    {
        try
        {
            //Set a name for the form
        	String formID = "PostForm";

            //Build the form using the specified data to be posted.
            StringBuilder strForm = new StringBuilder();
            strForm.append("<form id=\"" + formID + "\" name=\"" + formID + "\" action=\"" + url + "\" method=\"POST\">");
        	for (HashMap.Entry<String, String> e : data.entrySet())
    		{
    			if (e.getValue() != null)
    			{
    			
    					 strForm.append("<input type=\"hidden\" name=\"" + e.getKey() + "\" value=\"" + e.getValue() + "\">");
    					
    				
    			}
    		}
           
            strForm.append("</form>");

            //Build the JavaScript which will do the Posting operation.
            StringBuilder strScript = new StringBuilder();
            strScript.append("<script language='javascript'>");
            strScript.append("var v" + formID + " = document." + formID + ";");
            strScript.append("v" + formID + ".submit();");
            strScript.append("</script>");

            //Return the form and the script concatenated. (The order is important, Form then JavaScript)
            return strForm.toString() + strScript.toString();
        }
        catch (Exception ex)
        {
          
            return null;
        }
    }
 
}
