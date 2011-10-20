package mediavault.Viewers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class MultiDownload extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			String[] blobkeys = req.getParameterValues("mdow");
		
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ZipOutputStream zipFile = new ZipOutputStream(os);
			for (String sBlobkey : blobkeys) {
				BlobKey blobkey = new BlobKey(sBlobkey);
				BlobInfoFactory blobinfofactory = new BlobInfoFactory();
				BlobInfo blobinfo = blobinfofactory.loadBlobInfo(blobkey);
				zipFile.putNextEntry(new ZipEntry(blobinfo.getFilename()));
				long startIndex = 0;
				long endIndex = BlobstoreService.MAX_BLOB_FETCH_SIZE > blobinfo.getSize() ? blobinfo.getSize() : BlobstoreService.MAX_BLOB_FETCH_SIZE - 1;
				do {
					zipFile.write(blobstoreService.fetchData(blobkey, startIndex, endIndex));
					startIndex = endIndex + 1;
					endIndex = endIndex + BlobstoreService.MAX_BLOB_FETCH_SIZE  > blobinfo.getSize() ? blobinfo.getSize() : endIndex + BlobstoreService.MAX_BLOB_FETCH_SIZE - 1;
				} while (endIndex < blobinfo.getSize());
				zipFile.closeEntry();
			}
			zipFile.close();
			byte[] bs = os.toByteArray();
		
			//response
			res.setContentType("application/zip");
			res.setContentLength(bs.length);
			res.setHeader("Content-Disposition","attachment; filename=\"filepack.zip\"");
	
			res.getOutputStream().write(bs);
		} catch (Exception e) {
			res.sendRedirect("/msg.jsp?msg=" + e.getMessage());
		}
	}
}
