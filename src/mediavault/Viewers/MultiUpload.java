package mediavault.Viewers;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mediavault.Ctrls.Ctrl;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
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

@SuppressWarnings("serial")
public class MultiUpload extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			BlobInfoFactory blobinfofactory = new BlobInfoFactory();
			Iterator<BlobInfo> blobinfos = blobinfofactory.queryBlobInfos();
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

			Query q = new Query("fileinfo", fileKey);
			PreparedQuery pq = datastore.prepare(q);

			while (blobinfos.hasNext()) {
				BlobInfo b = blobinfos.next();
				Boolean isInDataStore = false;
				for (Entity result : pq.asIterable()) {
					if (result.getProperty("filename").equals(b.getFilename()) &&
							((User)result.getProperty("owner")).getEmail().equals(user.getEmail())) {
						isInDataStore = true;
					}
				}
				if (!isInDataStore) {
					Ctrl.UploadFile(b.getBlobKey(), "", false, user);
				}
			}
		} catch (Exception ex) {
		}		
	}
}
