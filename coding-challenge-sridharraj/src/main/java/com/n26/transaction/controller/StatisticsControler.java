package com.n26.transaction.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.n26.transaction.exception.StatsComputationException;
import com.n26.transaction.model.TransactionStatistics;
import com.n26.transaction.service.TransactionStatisticsService;

@RestControllerAdvice
@RestController
public class StatisticsControler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionStatisticsService statisticsService;
    
    @Autowired
	private Pair<Integer, ChronoUnit> defaultRollingPeriod;

    @RequestMapping(
    		value = "/statistics",
    		method = {RequestMethod.GET},
    		produces = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<TransactionStatistics> statistics() throws StatsComputationException {

        Instant currentTime = Instant.now();

        /**
         * Default behavior without any query params
         */
        TransactionStatistics transactionStatistics = statisticsService.getStatisticsBetween(
        		currentTime.minus(defaultRollingPeriod.getKey(), defaultRollingPeriod.getValue()), 
        		currentTime);
        
        return ResponseEntity.ok(transactionStatistics);

    }
    
    @ExceptionHandler(StatsComputationException.class)
	public final ResponseEntity<Void> handleApplicationException(StatsComputationException ex, WebRequest request) {
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		logger.error("Statistics Computation exception occured and responding as 500", ex);
		
		return responseEntity;
	}
}
