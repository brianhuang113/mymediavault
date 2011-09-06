package mediavault;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
			BlobKey blobKey = blobs.get("myFile");
			
			String desc = req.getParameter("desc");

			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			BlobInfoFactory blobinfofactory = new BlobInfoFactory();
			BlobInfo blobinfo = blobinfofactory.loadBlobInfo(blobKey);
			String content = blobinfo.getContentType();
			content = content.substring(0, content.indexOf("/"));
			if (!content.equals(new String("image")) && !(content.equals("audio"))
					&& !(content.equals("text")) && !(content.equals("video"))) {
				blobstoreService.delete(blobKey);
				res.sendRedirect("msg.jsp?msg=Upload failed, invalid file type! "
						+ content);
			} else {
				Entity fileinfo = new Entity("fileinfo", fileKey);
				fileinfo.setProperty("blobkey", blobKey);
				fileinfo.setProperty("date", blobinfo.getCreation());
				fileinfo.setProperty("filename", blobinfo.getFilename());
				fileinfo.setProperty("owner", user);
				fileinfo.setProperty("size", blobinfo.getSize());
				fileinfo.setProperty("contenttype", blobinfo.getContentType());
				fileinfo.setProperty("desc", desc);

				datastore.put(fileinfo);

				res.sendRedirect("msg.jsp?msg=Upload successfully!");
			}
		} catch (Exception ex) {
			res.sendRedirect("msg.jsp?msg=Upload failed:" + ex.getMessage());
		}
	}
}
