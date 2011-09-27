package mediavault.Ctrls;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.users.*;
import mediavault.Models.*;

public class Ctrl {
	
	public Ctrl() {
	}

	public static String UploadFile(BlobKey blobKey, String desc, Boolean isShared, User owner) throws Exception {
		String uploadResult = "";
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		BlobInfoFactory blobinfofactory = new BlobInfoFactory();
		BlobInfo blobinfo = blobinfofactory.loadBlobInfo(blobKey);
		String content = blobinfo.getContentType();
		content = content.substring(0, content.indexOf("/")).toLowerCase();
		if (!content.equals(new String("image")) && !(content.equals("audio"))
				&& !(content.equals("text")) && !(content.equals("video"))
				&& !(blobinfo.getContentType().toLowerCase().equals(new String("application/zip")))) {
			blobstoreService.delete(blobKey);
			uploadResult = "Upload failed, invalid file type! " + content;
		} else {
			if (content.equals(new String("image"))) {
				ImageFile imageFile = new ImageFile(blobKey, blobinfo.getCreation(),
						blobinfo.getFilename(), owner, blobinfo.getSize(),blobinfo.getContentType(),
						desc, isShared);
				imageFile.Save();
			}
			if (content.equals(new String("audio"))) {
				AudioFile audioFile = new AudioFile(blobKey, blobinfo.getCreation(),
						blobinfo.getFilename(), owner, blobinfo.getSize(),blobinfo.getContentType(),
						desc, isShared);
				audioFile.Save();
			}
			if (content.equals(new String("text"))) {
				TextFile textFile = new TextFile(blobKey, blobinfo.getCreation(),
						blobinfo.getFilename(), owner, blobinfo.getSize(),blobinfo.getContentType(),
						desc, isShared);
				textFile.Save();
			}
			if (content.equals(new String("video"))) {
				VideoFile videoFile = new VideoFile(blobKey, blobinfo.getCreation(),
						blobinfo.getFilename(), owner, blobinfo.getSize(),blobinfo.getContentType(),
						desc, isShared);
				videoFile.Save();
			}
			if (blobinfo.getContentType().toLowerCase().equals(new String("application/zip"))) {
				ZipFile zipFile = new ZipFile(blobKey, blobinfo.getCreation(),
						blobinfo.getFilename(), owner, blobinfo.getSize(),blobinfo.getContentType(),
						desc, isShared);
				zipFile.Save();
			}
			uploadResult = "Upload successfully!";
		}
		
		return uploadResult;
	}
	
	public static void UpdateFile(String desc, String filename, String blobkey,
			Boolean isShared, String contentType) {
		String contenttype = contentType.substring(0, contentType.indexOf("/")).toLowerCase();
		if (contenttype.toLowerCase().equals(new String("image"))) {
			ImageFile imageFile = new ImageFile(blobkey);
			String oriFilename = imageFile.getFileName();
			String extFilename = oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
			imageFile.setFileName(filename + extFilename);
			imageFile.setDesc(desc);
			imageFile.setIsShared(isShared);
			
			imageFile.Update();
		}
		else if (contenttype.toLowerCase().equals(new String("audio"))) {
			AudioFile audioFile = new AudioFile(blobkey);
			String oriFilename = audioFile.getFileName();
			String extFilename = oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
			audioFile.setFileName(filename + extFilename);
			audioFile.setDesc(desc);
			audioFile.setIsShared(isShared);
			
			audioFile.Update();
		}
		else if (contenttype.toLowerCase().equals(new String("video"))) {
			VideoFile videoFile = new VideoFile(blobkey);
			String oriFilename = videoFile.getFileName();
			String extFilename = oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
			videoFile.setFileName(filename + extFilename);
			videoFile.setDesc(desc);
			videoFile.setIsShared(isShared);
			
			videoFile.Update();
		}
		else if (contenttype.toLowerCase().equals(new String("text"))) {
			TextFile textFile = new TextFile(blobkey);
			String oriFilename = textFile.getFileName();
			String extFilename = oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
			textFile.setFileName(filename + extFilename);
			textFile.setDesc(desc);
			textFile.setIsShared(isShared);
			
			textFile.Update();
		}
		else if(contentType.toLowerCase().equals(new String("application/zip"))) {
			ZipFile zipFile = new ZipFile(blobkey);
			String oriFilename = zipFile.getFileName();
			String extFilename = oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
			zipFile.setFileName(filename + extFilename);
			zipFile.setDesc(desc);
			zipFile.setIsShared(isShared);
			
			zipFile.Update();
		}
	}
}
