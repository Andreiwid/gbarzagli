package br.edu.ifsp.tcc.gbarzagli.embrapa.share;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

            File familiesCSV = new File("C:\\Users\\vntgaba\\Repository\\embrapaShare\\embrapa.share\\src\\main\\sql\\families.csv");
            File plantCSV = new File("C:\\Users\\vntgaba\\Repository\\embrapaShare\\embrapa.share\\src\\main\\sql\\plants.csv");
            BufferedReader br = new BufferedReader(new FileReader(familiesCSV));

            String lastStr = null;

            List<NameValuePair> params = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (lastStr == null || !line.equals(lastStr)) {
                    if (!params.isEmpty()) {
                        params.clear();
                    }

                    params.add(new BasicNameValuePair("name", line));
                    familyPost.setEntity(new UrlEncodedFormEntity(params));
                    HttpResponse response = client.execute(familyPost);
                    EntityUtils.consume(response.getEntity());
                    lastStr = line;
                }
            }
            br.close();

            br = new BufferedReader(new FileReader(plantCSV));
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");

                System.out.println(columns[1]);

                System.out.println(columns[0]);

                if (!params.isEmpty()) {
                    params.clear();
                }
                params.add(new BasicNameValuePair("name", columns[0]));
                params.add(new BasicNameValuePair("familyName", columns[1]));
                plantPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = client.execute(plantPost);
                EntityUtils.consume(response.getEntity());
            }
            br.close();

            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

}
