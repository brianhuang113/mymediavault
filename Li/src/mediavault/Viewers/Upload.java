package mediavault.Viewers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import mediavault.Ctrls.*;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//System.out.println(req.getpa.getQueryString());
		//Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		//System.out.println(blobs.size());
		System.out.println("aaaaa");
		//BlobKey blobKey = blobs.get("name");
		//System.out.println(blobKey.getKeyString());

		/*try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
			BlobKey blobKey = blobs.get("myFile");
			
			//delete the same name files
			BlobInfoFactory blobinfofactory = new BlobInfoFactory();
			BlobInfo blobinfo = blobinfofactory.loadBlobInfo(blobKey);
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

			Query q = new Query("fileinfo", fileKey);
			q.addFilter("filename", Query.FilterOperator.EQUAL, blobinfo.getFilename());
			PreparedQuery pq = datastore.prepare(q);

			List<Key> keys = new ArrayList<Key>();
			for (Entity result : pq.asIterable()) {
				keys.add(result.getKey());
				blobstoreService.delete((BlobKey)result.getProperty("blobkey"));
			}
			datastore.delete(keys);
			//end
			
			String desc = req.getParameter("desc");
            Boolean isShared = Boolean.parseBoolean(req.getParameter("isShared"));
			
			String result = Ctrl.UploadFile(blobKey, desc, isShared, user);
			
			res.sendRedirect("/msg.jsp?msg=" + result);
			
		} catch (Exception ex) {
			res.sendRedirect("/msg.jsp?msg=Upload failed:" + ex.getMessage());
		}*/
	}
}