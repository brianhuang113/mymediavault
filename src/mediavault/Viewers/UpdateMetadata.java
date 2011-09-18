package mediavault.Viewers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mediavault.Models.AudioFile;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class UpdateMetadata extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String blobkey = req.getParameter("blobkey");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		Query q = new Query("fileinfo", fileKey);
		q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobkey);
		PreparedQuery pq = datastore.prepare(q);

		Entity entity = pq.asSingleEntity();
		String contenttype = (String)entity.getProperty("contenttype");
		contenttype = contenttype.substring(0, contenttype.indexOf("/"));
		
		if (contenttype.toLowerCase().equals(new String("audio"))) {
			AudioFile audioFile = new AudioFile(blobkey);
			try {
				audioFile.UpdateMetadata();
				res.sendRedirect("/msg.jsp?msg=Uploaded successfully");
			} catch (Exception ex) {
				res.sendRedirect("/msg.jsp?msg=Updated metadata failed:" + ex.getMessage());
			}
		}
	}
}
