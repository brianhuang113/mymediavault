package mediavault.Viewers;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
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

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
			BlobKey blobKey = blobs.get("myFile");
			
			String desc = req.getParameter("desc");
            Boolean isShared = Boolean.parseBoolean(req.getParameter("isShared"));
			
			String resultString = Ctrl.UploadFile(blobKey, desc, isShared, user);
			
			res.sendRedirect("/msg.jsp?msg=" + resultString);
			
		} catch (Exception ex) {
			res.sendRedirect("/msg.jsp?msg=Upload failed:" + ex.getMessage());
		}
	}
}
