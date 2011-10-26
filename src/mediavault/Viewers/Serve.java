package mediavault.Viewers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class Serve extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		BlobKey blobKey = new BlobKey(req.getParameter("blob_key"));
		
		BlobInfoFactory blobinfofactory = new BlobInfoFactory();
		BlobInfo blobinfo = blobinfofactory.loadBlobInfo(blobKey);
		res.setContentType(blobinfo.getContentType());
		res.setContentLength((int)blobinfo.getSize());
		blobstoreService.serve(blobKey, res);
	}
}
