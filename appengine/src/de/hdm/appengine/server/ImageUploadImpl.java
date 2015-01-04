package de.hdm.appengine.server;


/*import de.hdm.clicker.server.db.DBConnection;
import de.hdm.clicker.server.db.QuestionMapper;
import de.hdm.clicker.shared.bo.Question;*/

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;


import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageUploadImpl extends HttpServlet {

	//public QuestionMapper questionMapper = QuestionMapper.questionMapper();
	
	byte[] buffer;
	int imgID;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream stream = item.openStream();	
					
					if (item.isFormField()) {
						byte[] str = new byte[stream.available()];
			            stream.read(str);
			            String pFieldValue = new String(str,"UTF8");
			            imgID = new Integer(pFieldValue);
					}
					else {
						byte[] bb = IOUtils.toByteArray(stream);						
						buffer = bb;
					}
					
				}
				
				/*URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		         try {
		             URL url = new URL("http://someurl.com");
		             Future future = fetcher.fetchAsync(url);
		  
		             // Other stuff can happen here!
		  
		             HTTPResponse response1 = (HTTPResponse) future.get();
		             byte[] content = response1.getContent();
		             URL finalUrl = response1.getFinalUrl();
		             int responseCode = response1.getResponseCode();
		             List headers = response1.getHeaders();
		  
		             for(HTTPHeader header : headers) {
		                 String headerName = header.getName();
		                 String headerValue = header.getValue();
		             }
		  
		         } catch (IOException e) {
		             // new URL throws MalformedUrlException, which is impossible for us here
		         } catch (InterruptedException e) {
		             // Exception from using java.concurrent.Future
		         } catch (ExecutionException e) {
		             // Exception from using java.concurrent.Future
		             e.printStackTrace();
		         }*/
				
				//insertIntoDB();
				
			} catch (Exception e) {
				throw new RuntimeException(e);
		}

	}
	
	/*public void insertIntoDB () throws RuntimeException {
		
		//Zun??chst wird vorsorglich das alte Image falls vorhanden gel??scht
		Vector<Integer> vi = new Vector<Integer>();
		vi.add(imgID);
		Question ques2Update = questionMapper.findByKey(vi).elementAt(0);
		questionMapper.deleteImage(ques2Update);
		
		Connection con = DBConnection.connection();
		
		try{
			// Ausführen des SQL-Statements
			//Statement stmt = con.createStatement();
			String sql = "INSERT INTO Images (id, image) VALUES (?, ?);";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, imgID);
			pstmt.setBytes(2, buffer);
			pstmt.executeUpdate();
			
			//Aktualisieren der Question
			ques2Update.setImage(true);
			questionMapper.update(ques2Update);
							
		}		
		catch (SQLException e1) {
			throw new RuntimeException("Datenbankproblem: " + e1.getMessage());
		}
						
	}*/
	
}
