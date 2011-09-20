package mediavault.Models;

import java.util.Date;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.*;

public class MediaFile {
	private BlobKey blobKey;
	private Date creationDate;
	private String fileName;
	private User owner;
	private long size;
	private String contentType;
	private String desc;
	private Boolean isShared;
	
	public MediaFile() {
		
	}
	
	public MediaFile(String blobkey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		Query q = new Query("fileinfo", fileKey);
		q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobkey);
		PreparedQuery pq = datastore.prepare(q);

		Entity entity =  pq.asSingleEntity();
		this.blobKey = (BlobKey)entity.getProperty("blobkey");
		this.creationDate = (Date)entity.getProperty("date");
		this.fileName = (String)entity.getProperty("filename");
		this.owner = (User)entity.getProperty("owner");
		this.size = Long.parseLong(entity.getProperty("size").toString());
		this.contentType = (String)entity.getProperty("contenttype");
		this.desc = (entity.getProperty("desc") == null ? "" : (String)entity.getProperty("desc"));
		this.isShared = (entity.getProperty("shared") == null ? false : (Boolean)entity.getProperty("shared"));	
	}
	
	public MediaFile(BlobKey blobKey, Date creationDate, String fileName, User owner,
			long size, String contentType, String desc, Boolean isShared) {
		this.blobKey = blobKey;
		this.creationDate = creationDate;
		this.fileName = fileName;
		this.owner = owner;
		this.size = size;
		this.contentType = contentType;
		this.desc = desc;
		this.isShared = isShared;
	}
	
	public BlobKey getBlobKey() {
		return this.blobKey;
	}
	
	public void setBlobKey(BlobKey key) {
		this.blobKey = key;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public void setCreationDate(Date date) {
		this.creationDate = date;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public User getOwner() {
		return this.owner;
	}
	
	public void setOwner(User user) {
		this.owner = user;
	}
	
	public long getSize() {
		return this.size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public String getContentType() {
		return this.contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Boolean IsShared() {
		return this.isShared;
	}
	
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}
	
	protected void Save(Entity fileinfo) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		fileinfo.setProperty("blobkey", this.blobKey);
		fileinfo.setProperty("date", this.creationDate);
		fileinfo.setProperty("filename", this.fileName);
		fileinfo.setProperty("owner", this.owner);
		fileinfo.setProperty("size", this.size);
		fileinfo.setProperty("contenttype", this.contentType);
		fileinfo.setProperty("desc", this.desc);
		fileinfo.setProperty("shared", this.isShared);
		
		datastore.put(fileinfo);
	}
	
	protected void Update(Entity entity) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		entity.setProperty("filename", this.fileName);
		entity.setProperty("desc", this.desc);
		entity.setProperty("shared", this.isShared);
		
		datastore.put(entity);
	}
}
