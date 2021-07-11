package com.ezypay;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import com.ezypay.SubscriptionApplication;


public class RestAPITest {

	/*before startup,start springboot.*/
	@BeforeClass
	public static void setup() {
		String[] args = new String[0];
		SpringApplication.run(SubscriptionApplication.class, args);
	}

	/**
	 * Test the REST API with wrong API name
	 * @throws IOException
	 */
	@Test
	public void TestURL() throws IOException {

		String SAMPLE_URL = "http://localhost:8080/createSubscription22";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(SAMPLE_URL);
		final CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(404));
		client.close();
	}

	/**
	 * Test the REST API without Parameters
	 * 
	 * @throws IOException
	 */
	@Test
	public void sendblankURLwithoutParameters() throws IOException {

		String SAMPLE_URL = "http://localhost:8080/createSubscription";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(SAMPLE_URL);

		final CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(400));
		client.close();
	}
	
	/**
	 * Test Invalid date range. Check for more than 3 months
	 * @throws IOException
	 */
	@Test
	public void TestWrongDateRange() throws IOException {

		String SAMPLE_URL = "http://localhost:8080/createSubscription";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(SAMPLE_URL);

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("startDate", "28/06/2021"));
        params.add(new BasicNameValuePair("endDate", "29/09/2021"));
        params.add(new BasicNameValuePair("price", "20.5"));
        params.add(new BasicNameValuePair("subscriptionType", "W"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        final CloseableHttpResponse response = client.execute(httpPost);
        String responseString = new BasicResponseHandler().handleResponse(response);

        assertThat(responseString.contains("Invalid Date range"), equalTo(true));
        client.close();
	}
	

	/**
	 * Test Invalid date of Week. Check day of week is between 1-7
	 * @throws IOException
	 */
	@Test
	public void TestWrongDayOfWeek() throws IOException {

		String SAMPLE_URL = "http://localhost:8080/createSubscription";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(SAMPLE_URL);

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("startDate", "28/06/2021"));
        params.add(new BasicNameValuePair("endDate", "29/06/2021"));
        params.add(new BasicNameValuePair("price", "20.5"));
        params.add(new BasicNameValuePair("subscriptionType", "W"));
        params.add(new BasicNameValuePair("dayoftheweek", "9"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        final CloseableHttpResponse response = client.execute(httpPost);
        String responseString = new BasicResponseHandler().handleResponse(response);

        assertThat(responseString.contains("Invalid day of the week"), equalTo(true));
        client.close();
	}
	
	
	/**
	 * Test Invalid day of month.Dont add in day of month
	 * @throws IOException
	 */
	@Test
	public void TestWrongDayOMonth() throws IOException {

		String SAMPLE_URL = "http://localhost:8080/createSubscription";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(SAMPLE_URL);

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("startDate", "28/06/2021"));
        params.add(new BasicNameValuePair("endDate", "29/06/2021"));
        params.add(new BasicNameValuePair("price", "20.5"));
        params.add(new BasicNameValuePair("subscriptionType", "M"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        final CloseableHttpResponse response = client.execute(httpPost);
        String responseString = new BasicResponseHandler().handleResponse(response);

        assertThat(responseString.contains("Invalid day of the month"), equalTo(true));
        client.close();
	}

}
