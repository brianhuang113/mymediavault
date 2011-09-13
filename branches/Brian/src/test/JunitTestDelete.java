package test;

import static org.junit.Assert.assertEquals;
import mediavault.Delete;

import org.junit.Test;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;

public class JunitTestDelete {
	
    @Test
	public void TestDelete() throws Exception{
        Delete s = new Delete();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setupAddParameter("blob_key" , "1");		
		//request.setupAddParameter("msg","Deleted successfully!");
		
		//BlobInfoFactory blobinfofactory = new BlobInfoFactory();
		
		//Iterator<BlobInfo> itBlobinfo = blobinfofactory.queryBlobInfos();
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		BlobKey blobKey = new BlobKey(request.getParameter("blob_key"));
		blobstoreService.serve(blobKey, response);
		Query q = new Query("blob_key");
		//blobstoreService.delete(blobKey);
		s.doGet(request, response);
		int i;
		if(q.equals(null)){
			i = 0;
		}
		else i = 1;
		//while(itBlobinfo.hasNext()){
		//	BlobInfo blobinfo = itBlobinfo.next();
		//	i++;
		//}
		//Query q = new Query("fileinfo", fileKey);
		//Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
		//response.setExpectedContentType("text/html");

		//response.verify();
		
		assertEquals(0,i);
		//something should be added here....
	}
    
}
