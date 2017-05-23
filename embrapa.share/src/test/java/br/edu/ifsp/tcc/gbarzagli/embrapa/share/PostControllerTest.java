package br.edu.ifsp.tcc.gbarzagli.embrapa.share;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.http.HttpStatus;

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
		String url = "http://localhost:8080/post";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		
		File file = new File("C:\\Users\\vntgaba\\Pictures\\problem.png");
		int length = (int) file.length();
		
		byte[] fileBytes = new byte[length];
		
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(fileBytes);
		fileInputStream.close();
		
		String email = "gabriel.barzagli@hotmail.com";
		
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("sender", email);
		builder.addPart("image", fileBody);
		HttpEntity entity = builder.build();
		
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		
		assertEquals(HttpStatus.CREATED.value(), status);
	}

}
