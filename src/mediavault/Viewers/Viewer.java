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
									new String("text")))
					|| (fileType.equals(new String("6")) && contentType.toLowerCase().equals(new String("application/zip")))) {
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
					+ blobKey.getKeyString() + "\">" + fileName2 + "</a></td>\n";
			output += "<td class=\"spec\">" + createdDate.toString() + "</td>\n";
			output += "<td class=\"spec\">" + contentType + "</td>\n";
			output += "<td class=\"spec\">" + ConvertFilesize(fileSize) + "</td>\n";
			output += "<td class=\"spec\">" + user.getNickname();

			output += "</td>\n";
			if (entity.getProperty("desc") == null)
				output += "<td class=\"spec\">&nbsp;</td>\n";
			else {
				if (((String)entity.getProperty("desc")).equals(new String(""))) 
					output += "<td class=\"spec\">&nbsp;</td>\n";
				else
					output += "<td class=\"spec\">" + entity.getProperty("desc")
						+ "</td>\n";
			}
			output += "<td class=\"spec\"><a href=\"/download?blob_key="
					+ blobKey.getKeyString() + "\">download</a></td>\n";
			if (curUser.getEmail().equals(user.getEmail())) {
				output += "<td class=\"spec\"><a href=\"/delete?blob_key="
						+ blobKey.getKeyString() + "\">delete</a></td>\n";
				output += "<td class=\"spec\"><a href=\"/edit.jsp?blob_key="
						+ blobKey.getKeyString() + "&contenttype=" + contentType + "\">edit</a></td>\n";
			} else {
				output += "<td class=\"spec\">&nbsp;</td><td class=\"spec\">&nbsp;</td>";
			}
			output += "</tr>";
		}

		return output;
	}
	
	private static String ConvertFilesize(Long fileSize) {
		String result = "";
		if (fileSize < 1024) {
			result = fileSize.toString() + "b";
		}
		else if (fileSize >=1024 && fileSize < 1024*1024) {
			result = ((Long)(fileSize/1024)).toString() + "Kb";
		}
		else {
			result = ((Long)(fileSize/(1024*1024))).toString() + "M";
		}
		
		return result;
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
	
	public static String AutoSearch(String qs) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		UserService userService = UserServiceFactory.getUserService();
		User curUser = userService.getCurrentUser();
		Query q = new Query("fileinfo", fileKey);

		PreparedQuery pq = datastore.prepare(q);
		String output = "";
		for (Entity result : pq.asIterable()) {
			String filename = (String) result.getProperty("filename");
			filename = filename.substring(0, filename.lastIndexOf("."))
					.toLowerCase();
			User owner = (User) result.getProperty("owner");
			Boolean isShared = (result.getProperty("shared") == null ? false
					: (Boolean) result.getProperty("shared"));

			if (filename.indexOf(qs.toLowerCase()) >= 0) {

				if (curUser.getEmail().equals(owner.getEmail()) || isShared)
					output += filename + "\n";
			}
		}
		return output;
	}
	
	public static String OutPutShared(Boolean isShared) {
		return (isShared ? "Checked" : "");
	}
	
	public static String AdvSearch(String searchContent, Boolean isDesc, Boolean isArtist, Boolean isAlbum,
			Boolean isGenre, String rType, Date startDate, Date endDate) {
		
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
			String content = (String)result.getProperty("contenttype");
			content = content.substring(0, content.indexOf("/")).toLowerCase();
			Boolean isShared = (result.getProperty("shared") == null ? false
					: (Boolean) result.getProperty("shared"));
			
			Boolean isAdd = false;

			if (curUser.getEmail().equals(owner.getEmail()) || isShared) {
				if (filename.indexOf(searchContent.toLowerCase()) >= 0) {
					isAdd = true;
				}
				if (isDesc) {
					isAdd = false;
					String desc = (result.getProperty("desc") == null ? "" : (String) result.getProperty("desc"));
					if (desc.toLowerCase().indexOf(searchContent.toLowerCase()) >= 0) {
						isAdd = true;
					}
				}
				if (isArtist) {
					isAdd = false;
					String artist = (result.getProperty("artist") == null ? "" : (String) result.getProperty("artist"));
					if (artist.toLowerCase().indexOf(searchContent.toLowerCase()) >= 0) {
						isAdd = true;
					}
				}
				if (isAlbum) {
					isAdd = false;
					String album = (result.getProperty("album") == null ? "" : (String) result.getProperty("album"));
					if (album.toLowerCase().indexOf(searchContent.toLowerCase()) >= 0) {
						isAdd = true;
					}
				}
				if (isGenre) {
					isAdd = false;
					String genre = (result.getProperty("genre") == null ? "" : (String) result.getProperty("genre"));
					if (genre.toLowerCase().indexOf(searchContent.toLowerCase()) >= 0) {
						isAdd = true;
					}
				}
				if (startDate != null || endDate != null) {
					Date date = (Date) result.getProperty("date");
					if (startDate == null) {
						if (date.after(endDate)) {
							isAdd = false;
						}
					}
					if (endDate == null) {
						if (date.before(startDate)) {
							isAdd = false;
						}
					}
					if (startDate != null && endDate != null) {
						if (date.after(endDate) || date.before(startDate)) {
							System.out.println("bbb");
							isAdd = false;
						}
					}
				}
				if (rType.equals(new String("image"))) {
					if (content.equals(new String("image"))) {
						isAdd = true;
					}
					else {
						isAdd = false;
					}
				}
				if (rType.equals(new String("audio"))) {
					if (content.equals(new String("audio"))) {
						isAdd = true;
					}
					else {
						isAdd = false;
					}
				}
				if (rType.equals(new String("video"))) {
					if (content.equals(new String("video"))) {
						isAdd = true;
					}
					else {
						isAdd = false;
					}
				}
				if (rType.equals(new String("text"))) {
					if (content.equals(new String("text"))) {
						isAdd = true;
					}
					else {
						isAdd = false;
					}
				}
				if (rType.equals(new String("zip"))) {
					if (((String)result.getProperty("contenttype")).toLowerCase().equals(new String("image"))) {
						isAdd = true;
					}
					else {
						isAdd = false;
					}
				}
			}

			if (isAdd) {
				entities.add(result);
			}
		}
		String output = OutPutEntities(entities, curUser);
		
		return output;
	}
}
