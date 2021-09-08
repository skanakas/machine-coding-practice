package com.n26.transaction.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.config.StatisticsContainerConfig;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;

@Import(StatisticsContainerConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestStatisticsController {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Before
	public void init() {
		testRestTemplate.delete("/transactions");
	}

	@Test
	public void testStatisticsComputationLogic() throws RestClientException, JsonProcessingException {
		
		BigDecimal sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		BigDecimal min = new BigDecimal(Double.MAX_VALUE).setScale(2, RoundingMode.HALF_UP);
		BigDecimal max = new BigDecimal(Double.MIN_VALUE).setScale(2, RoundingMode.HALF_UP);
		Long count = 1000l;
		Instant now = Instant.now();
		for(int i = 1; i<=count; i++) {
			Transaction transaction = randomTransaction(now);
			BigDecimal amountInDecimal = transaction.getAmountInDecimal();
			sum = sum.add(amountInDecimal);
			if(amountInDecimal.compareTo(min) < 0)
				min = amountInDecimal;
			if(amountInDecimal.compareTo(max) > 0)
				max = amountInDecimal;
			
			ResponseEntity<Void> postForEntity = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
			Assert.assertEquals("Txn persist failed "+postForEntity.getStatusCodeValue(), HttpStatus.CREATED, postForEntity.getStatusCode());

		}
		BigDecimal avg = sum.divide(new BigDecimal(count), RoundingMode.HALF_UP);

		TransactionStatistics transactionStatistics = new TransactionStatistics();
		transactionStatistics.setAvg(avg);
		transactionStatistics.setCount(count);
		transactionStatistics.setMax(max);
		transactionStatistics.setMin(min);
		transactionStatistics.setSum(sum);


		ResponseEntity<TransactionStatistics> response = testRestTemplate.getForEntity("/statistics", TransactionStatistics.class);
		Assert.assertEquals("Statistics pull was not successful. Response code is "+response.getStatusCodeValue(), HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("Precomputed Stats was "+transactionStatistics +" and api response was "+response.getBody(), transactionStatistics, response.getBody());

	}
	
	private Transaction randomTransaction(Instant time) {
		Random r = new Random();
		int rangeMin = 0;
		int rangeMax = 9999;
		double randomAmount = rangeMin  + (rangeMax  - rangeMin) * r.nextDouble();
		Transaction transaction = new Transaction(new BigDecimal(randomAmount).toString(), time);
		transaction.setAmountDecimal(new BigDecimal(randomAmount));
		return transaction;
	}
}
