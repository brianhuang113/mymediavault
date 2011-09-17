package mediavault.Models;

public class AudioTag {
	private String title;
    private String artist;
    private String album;
    private String year;
    private String comment;
    private byte genre;
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getTitle() {
    	return this.title;
    }
    
    public void setArtist(String artist) {
    	this.artist = artist;
    }
    
    public String getArtist() {
    	return this.artist;
    }
    
    public void setAlbum(String album) {
    	this.album = album;
    }
    
    public String getAlbum() {
    	return this.album;
    }
    
    public void setYear(String year) {
    	this.year = year;
    }
    
    public String getYear() {
    	return this.year;
    }
    
    public void setComment(String comment) {
    	this.comment = comment;
    }
    
    public String getComment() {
    	return this.comment;
    }
    
    public void setGenre(byte genre) {
    	this.genre = genre;
    }
    
    public byte getGenre() {
    	return this.genre;
    }
}
