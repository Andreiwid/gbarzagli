package br.edu.ifsp.tcc.gbarzagli.embrapa.share;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import junit.framework.TestCase;

public class ParseDatabase extends TestCase {
	
	@Test
	public void testParse() {
		try {
			String familyUrl = "http://localhost:8080/family";
			String plantUrl = "http://localhost:8080/plant";

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost familyPost = new HttpPost(familyUrl);
			HttpPost plantPost = new HttpPost(plantUrl);
			
			String line = null;
			File csv = new File("C:\\Users\\vntgaba\\Repository\\embrapa.share\\src\\main\\sql\\plants.csv");
			BufferedReader br = new BufferedReader(new FileReader(csv));
			while ((line = br.readLine()) != null) {
				String[] columns = line.split(",");
				
				System.out.println(columns[1]);
				
				Long family = null;
				
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("name", columns[1]));
				familyPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse response = client.execute(familyPost);
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					String responseStr = EntityUtils.toString(entity);
					System.out.println(responseStr);
					family = Long.valueOf(responseStr);
				}
				
				System.out.println(columns[0]);
				params.clear();
				params.add(new BasicNameValuePair("name", columns[0]));
				params.add(new BasicNameValuePair("familyId", String.valueOf(family)));
				plantPost.setEntity(new UrlEncodedFormEntity(params));
				response = client.execute(plantPost);
				
			}
			br.close();
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
}
