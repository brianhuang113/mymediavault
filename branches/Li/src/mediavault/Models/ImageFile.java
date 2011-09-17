package mediavault.Models;

import java.util.Date;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;

public class ImageFile extends MediaFile {
	public ImageFile() {
		super();
	}
	
	public ImageFile(BlobKey blobKey, Date creationDate, String fileName, User owner,
			long size, String contentType, String desc, Boolean isShared) {
		super();
	}
	
	public void Save() {
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		Entity fileinfo = new Entity("fileinfo", fileKey);
		//for furture image unique properties
		//fileinfo.setProperty(propertyName, value)
		super.Save(fileinfo);
	}
}
