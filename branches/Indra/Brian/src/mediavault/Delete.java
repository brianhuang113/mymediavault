package mediavault;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class Delete extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		
		try {
			BlobKey blobKey = new BlobKey(req.getParameter("blob_key"));
			blobstoreService.delete(blobKey);
			
			out.println("<html><body>Deleted successfully!</body></html>");
		} catch (Exception e) {
			out.println("<html><body>Deleted failed: " + e.getMessage() + "</body></html>");
		}
	}
}
