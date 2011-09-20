package mediavault.Models;

import java.util.Date;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;

public class ImageFile extends MediaFile {
	public ImageFile() {
		super();
	}
	
	public ImageFile(String blobkey) {
		super(blobkey);
	}
	
	public ImageFile(BlobKey blobKey, Date creationDate, String fileName, User owner,
			long size, String contentType, String desc, Boolean isShared) {
		super(blobKey, creationDate, fileName, owner, size, contentType, desc, isShared);
	}
	
	public void Save() {
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		Entity fileinfo = new Entity("fileinfo", fileKey);
		//for furture image unique properties
		//fileinfo.setProperty(propertyName, value)
		super.Save(fileinfo);
	}
	
	public void Update() {
		BlobKey blobKey = super.getBlobKey();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		Query q = new Query("fileinfo", fileKey);
		q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobKey.getKeyString());
		PreparedQuery pq = datastore.prepare(q);

		Entity entity =  pq.asSingleEntity();
		
		super.Update(entity);
	}
}
