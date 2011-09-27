package mediavault.Models;

import java.util.Date;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

public class ZipFile extends MediaFile {

	public ZipFile() {
		super();
	}

	public ZipFile(String blobkey) {
		super(blobkey);
	}

	public ZipFile(BlobKey blobKey, Date creationDate, String fileName,
			User owner, long size, String contentType, String desc,
			Boolean isShared) {
		super(blobKey, creationDate, fileName, owner, size, contentType, desc,
				isShared);
	}
	
	public void Save() {
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

		Entity fileinfo = new Entity("fileinfo", fileKey);
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
