package mediavault.Models;

import java.io.IOException;
import java.util.Date;

import mediavault.Ctrls.AudioCtrl;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

public class AudioFile extends MediaFile {

	public AudioFile() {
		super();
	}
	
	public AudioFile(String blobkey) {
		super(blobkey);
	}
	public AudioFile(BlobKey blobKey, Date creationDate, String fileName, User owner,
			long size, String contentType, String desc, Boolean isShared) {
		super(blobKey, creationDate, fileName, owner, size, contentType, desc, isShared);
	}
	
	public void Save() throws Exception {
		try {
			BlobstoreInputStream in = new BlobstoreInputStream(super.getBlobKey());
			AudioTag tag = AudioCtrl.readTag(in);
			
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

			Entity fileinfo = new Entity("fileinfo", fileKey);
			
			fileinfo.setProperty("title", tag.getTitle());
			fileinfo.setProperty("artist", tag.getArtist());
			fileinfo.setProperty("album", tag.getAlbum());
			fileinfo.setProperty("year", tag.getYear());
			fileinfo.setProperty("comment", tag.getComment());
			fileinfo.setProperty("genre", tag.getGenre());
			super.Save(fileinfo);
		} catch (Exception e) {
			throw e;
		}
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
