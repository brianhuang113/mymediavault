package mediavault.Viewers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Viewer {

	public static String ListAllFiles(String fileType) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		UserService userService = UserServiceFactory.getUserService();
		User curUser = userService.getCurrentUser();
		Query q = new Query("fileinfo", fileKey);

		PreparedQuery pq = datastore.prepare(q);

		List<Entity> entities = new ArrayList<Entity>();
		for (Entity result : pq.asIterable()) {
			String contentType = (String) result.getProperty("contenttype");
			User owner = (User) result.getProperty("owner");
			Boolean isShared = (result.getProperty("shared") == null ? false
					: (Boolean) result.getProperty("shared"));

			if (fileType.equals(new String("1"))
					|| (fileType.equals(new String("2")) && contentType
							.substring(0, contentType.indexOf("/")).equals(
									new String("image")))
					|| (fileType.equals(new String("3")) && contentType
							.substring(0, contentType.indexOf("/")).equals(
									new String("audio")))
					|| (fileType.equals(new String("4")) && contentType
							.substring(0, contentType.indexOf("/")).equals(
									new String("video")))
					|| (fileType.equals(new String("5")) && contentType
							.substring(0, contentType.indexOf("/")).equals(
									new String("text")))) {
				if (curUser.getEmail().equals(owner.getEmail()) || isShared)
					entities.add(result);
			}
		}

		String output = OutPutEntities(entities, curUser);

		return output;
	}

	private static String OutPutEntities(List<Entity> entities, User curUser) {
		String output = "";

		for (Entity entity : entities) {
			BlobKey blobKey = (BlobKey) entity.getProperty("blobkey");
			Date createdDate = (Date) entity.getProperty("date");
			String fileName2 = (String) entity.getProperty("filename");
			User user = (User) entity.getProperty("owner");
			Long fileSize = Long.parseLong(entity.getProperty("size")
					.toString());
			String contentType = (String) entity.getProperty("contenttype");

			output += "<tr><td class=\"spec\"><a href=\"/serve?blob_key="
					+ blobKey.getKeyString() + "\">" + fileName2 + "</a></td>";
			output += "<td class=\"spec\">" + createdDate.toString() + "</td>";
			output += "<td class=\"spec\">" + contentType + "</td>";
			output += "<td class=\"spec\">" + fileSize.toString() + "</td>";
			output += "<td class=\"spec\">" + user.getNickname();

			output += "</td>";
			if (entity.getProperty("desc") == null)
				output += "<td class=\"spec\"></td>";
			else
				output += "<td class=\"spec\">" + entity.getProperty("desc")
						+ "</td>";

			output += "<td class=\"spec\"><a href=\"/download?blob_key="
					+ blobKey.getKeyString() + "\">download</a></td>";
			if (curUser.getEmail().equals(user.getEmail())) {
				output += "<td class=\"spec\"><a href=\"/delete?blob_key="
						+ blobKey.getKeyString() + "\">delete</a></td>";
				output += "<td class=\"spec\"><a href=\"/edit.jsp?blob_key="
						+ blobKey.getKeyString() + "\">edit</a></td>";
			} else {
				output += "<td class=\"spec\"></td><td class=\"spec\"></td>";
			}
			output += "</tr>";
		}

		return output;
	}

	public static String SearchByName(String searchName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		UserService userService = UserServiceFactory.getUserService();
		User curUser = userService.getCurrentUser();
		Query q = new Query("fileinfo", fileKey);

		PreparedQuery pq = datastore.prepare(q);

		List<Entity> entities = new ArrayList<Entity>();
		for (Entity result : pq.asIterable()) {
			String filename = (String) result.getProperty("filename");
			filename = filename.substring(0, filename.lastIndexOf("."))
					.toLowerCase();
			User owner = (User) result.getProperty("owner");
			Boolean isShared = (result.getProperty("shared") == null ? false
					: (Boolean) result.getProperty("shared"));

			if (filename.indexOf(searchName.toLowerCase()) >= 0) {

				if (curUser.getEmail().equals(owner.getEmail()) || isShared)
					entities.add(result);
			}
		}
		String output = OutPutEntities(entities, curUser);

		return output;
	}
}
