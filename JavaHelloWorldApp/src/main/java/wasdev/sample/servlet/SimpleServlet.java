package wasdev.sample.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Sentiment;
import com.ibm.watson.developer_cloud.http.ServiceCall;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String url = request.getParameter("url");
    	AlchemyLanguage service = new AlchemyLanguage();
    	service.setApiKey("68e50a8b283606a21ef4aa2a668e81f1997d7c7c");
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	params.put(service.URL, url);
    	params.put(service.SENTIMENT, 1);
    	params.put(service.KNOWLEDGE_GRAPH, 1);
    	Keywords result =  service.getKeywords(params).execute();
    	
    	List<Keyword> keywords = result.getKeywords();
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("<html>\n" +
    			"<head>\n" +
    	"<title>Webpage Analysis</title>\n" +
    	"<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n" +
    	"<link rel='stylesheet' href='style.css' />\n" +
    	"</head>\n" + 
                "  <li><b>URL:</b>: "
                + url + "\n" +
                "  <li><b>Result:</b>: ");
    	
    	sb.append("The test that has been processed is: " + result.getText() + "\n");
    	
    	sb.append(	"<table style='width:100%'>\n"
				+	"<tr> <th>Keyword</th> \n <th>Sentiment</th>  </tr>\n");
    	for(Keyword k:keywords)
    	{
    		sb.append("<tr>\n");
    		sb.append("<td>" + k.getText() + "</td><td>" + k.getSentiment().getType() + "\n");
    		sb.append("</tr>\n");
    	}
    		
    	
    	
    	
    	
        response.setContentType("text/html");
        response.getWriter().print( sb.append("</table>\n" +
                "</ul>\n" +
                "</body></html>").toString());
    }

}
