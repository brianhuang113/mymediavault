package mediavault.Viewers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class GenerateUploadURL extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/plain");
		PrintWriter pw = res.getWriter();
		String s = blobstoreService.createUploadUrl("/upload");
		pw.write(blobstoreService.createUploadUrl("/upload"));
		pw.flush();
		pw.close();
	}
}
