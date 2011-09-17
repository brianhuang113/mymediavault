package mediavault.Models;

import java.util.Date;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

public class AudioFile extends MediaFile {

	public AudioFile() {
		super();
	}
	
	public AudioFile(BlobKey blobKey, Date creationDate, String fileName, User owner,
			long size, String contentType, String desc, Boolean isShared) {
		super();
	}
	
	public void Save() {
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		Entity fileinfo = new Entity("fileinfo", fileKey);
		super.Save(fileinfo);
	}
}