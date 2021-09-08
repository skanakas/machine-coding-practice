/**
 * 
 */
package com.n26.transaction.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.config.StatisticsContainerConfig;
import com.n26.transaction.model.Transaction;

/**
 * @author SridharRaj
 *
 */
@Import(StatisticsContainerConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTransactionController {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void testSussessfulPost() {
		Instant time = Instant.now();
		Transaction transaction = randomTransaction(time );
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals("Response code for Post is "+response.getStatusCode(), HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testTransactionOccuredADayBefore() {
		Transaction transaction = randomTransaction(Instant.now().minus(120, ChronoUnit.SECONDS));
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals("Response code for Post is "+response.getStatusCode(), HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testTransactionOccuredInFuture() {
		Transaction transaction = randomTransaction(Instant.now().plus(120, ChronoUnit.SECONDS));
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals("Response code for Post is "+response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	@Test
	public void testDeleteResource() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.DELETE, null, Void.class);
		Assert.assertEquals("Response code for DELETE is "+response.getStatusCode(), HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void testBadRequest() {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
		ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.POST,
                new HttpEntity<>("Test", headers), Void.class);
		Assert.assertEquals("Response code for Post is "+response.getStatusCode(), HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private Transaction randomTransaction(Instant time) {
		Random r = new Random();
		int rangeMin = 0;
		int rangeMax = 9999;
		double randomAmount = rangeMin  + (rangeMax  - rangeMin) * r.nextDouble();
		Transaction transaction = new Transaction(new BigDecimal(randomAmount).toString(), time);
		return transaction;
	}

}
