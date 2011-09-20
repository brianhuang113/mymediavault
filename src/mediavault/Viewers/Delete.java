package mediavault.Viewers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class Delete extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		try {
			BlobKey blobKey = new BlobKey(req.getParameter("blob_key"));
			blobstoreService.delete(blobKey);
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
			Query q = new Query("fileinfo", fileKey);
			q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobKey);
			PreparedQuery pq = datastore.prepare(q);
			List<Key> keys = new ArrayList<Key>();
			for (Entity result : pq.asIterable()) {
				keys.add(result.getKey());
			}
			datastore.delete(keys);
			
			res.sendRedirect("msg.jsp?msg=Deleted successfully!");
		} catch (Exception e) {
			res.sendRedirect("msg.jsp?msg=Deleted failed: " + e.getMessage());
		}
	}
}
