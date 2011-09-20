package mediavault.Models;

import java.io.ByteArrayInputStream;
import java.util.Date;

import com.dusbabek.lib.id3.*;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;

public class AudioFile extends MediaFile {

	private String artist;
	private Boolean hasTag;
	private String title;
	private String album;
	private String comment;
	private String track;
	private String genre;
	private String year;

	public AudioFile() {
		super();
	}

	public AudioFile(String blobkey) {
		super(blobkey);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		Query q = new Query("fileinfo", fileKey);
		q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobkey);
		PreparedQuery pq = datastore.prepare(q);

		Entity entity = pq.asSingleEntity();
		this.artist = (entity.getProperty("artist") == null ? null : (String)entity.getProperty("artist"));
		this.hasTag = (entity.getProperty("hastag") == null ? false: (Boolean)entity.getProperty("hastag"));
		this.title = (entity.getProperty("title") == null ? null : (String)entity.getProperty("title"));
		this.album = (entity.getProperty("album") == null ? null : (String)entity.getProperty("album"));
		this.comment = (entity.getProperty("comment") == null ? null : (String)entity.getProperty("comment"));
		this.track = (entity.getProperty("track") == null ? null : (String)entity.getProperty("track"));
		this.genre = (entity.getProperty("genre") == null ? null :(String)entity.getProperty("genre"));
		this.year = (entity.getProperty("year")== null ?null :(String)entity.getProperty("year"));
	}

	public AudioFile(BlobKey blobKey, Date creationDate, String fileName,
			User owner, long size, String contentType, String desc,
			Boolean isShared) {
		super(blobKey, creationDate, fileName, owner, size, contentType, desc,
				isShared);
	}

	public String getArtist() {
		return this.artist;
	}
	
	public Boolean hasTag() {
		return this.hasTag;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return this.album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getTrack() {
		return this.track;
	}
	
	public void setTrack(String track) {
		this.track = track;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
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
		q.addFilter("blobkey", Query.FilterOperator.EQUAL,
				blobKey.getKeyString());
		PreparedQuery pq = datastore.prepare(q);

		Entity entity = pq.asSingleEntity();

		super.Update(entity);
	}

	public void UpdateMetadata() throws Exception {
		BlobKey blobkey = super.getBlobKey();
		Reader reader = new Reader();
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		
		Tag tag = reader.read(new ByteArrayInputStream(blobstoreService.fetchData(blobkey, 0, 102400)), 102400);
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		Query q = new Query("fileinfo", fileKey);
		q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobkey);
		PreparedQuery pq = datastore.prepare(q);

		Entity entity = pq.asSingleEntity();
		entity.setProperty("artist", tag.getArtist());
		entity.setProperty("title", tag.getTitle());
		entity.setProperty("album", tag.getAlbum());
		entity.setProperty("comment", tag.getComment());
		entity.setProperty("track", tag.getTrack());
		entity.setProperty("genre", tag.getGenre());
		entity.setProperty("year", tag.getYear());
		entity.setProperty("hastag", true);
		this.artist = tag.getArtist();
		this.hasTag = true;
		this.title = tag.getTitle();
		this.album = tag.getAlbum();
		this.comment = tag.getComment();
		this.track = tag.getTrack();
		this.genre = tag.getGenre();
		this.year = tag.getYear();
		datastore.put(entity);
	}
}
