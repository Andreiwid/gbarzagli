package br.edu.ifsp.tcc.gbarzagli.embrapa.share;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * JUnit test case for the Post REST Endpoint. 
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 *
 */
public class PostControllerTest extends TestCase {
	
	
	@Test
	/** This method test the upload of an image */
	public void testUploadImage() throws ClientProtocolException, IOException {
//		String url = "http://localhost:8080/post";
//
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpPost post = new HttpPost(url);
//		
//		File file = new File("C:\\Users\\vntgaba\\Pictures\\memes\\giulia.zip");
//		int length = (int) file.length();
//		
//		byte[] fileBytes = new byte[length];
//		
//		FileInputStream fileInputStream = new FileInputStream(file);
//		fileInputStream.read(fileBytes);
//		fileInputStream.close();
//		
//		String email = "gabriel.barzagli@hotmail.com";
//		
//		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
//		
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		builder.addTextBody("sender", email);
//		builder.addTextBody("plantId", "1");
//		builder.addPart("image", fileBody);
//		HttpEntity entity = builder.build();
//		
//		post.setEntity(entity);
//		
//		HttpResponse response = client.execute(post);
//		int status = response.getStatusLine().getStatusCode();
//		
//		assertEquals(HttpStatus.CREATED.value(), status);
	}

}
