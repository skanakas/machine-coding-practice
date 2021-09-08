package com.n26.transaction.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.transaction.exception.InvalidTransactionException;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.service.TransactionService;
import com.n26.transaction.validation.TransactionValidator;

@RestControllerAdvice
@RestController
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	@Qualifier("statisticsPreValidator")
	private TransactionValidator transactionValidator;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(
			value = "/transactions",
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<Void> transaction(@RequestBody Transaction transaction) throws InvalidTransactionException {

		/**
		 * Validate transaction occurrence
		 */
		transactionValidator.validateTransactionForStatisticsComputation(transaction);
		if(logger.isDebugEnabled()) {
			logger.debug("Validation was successful for request {}", transaction.toString());
		}

		/**
		 * Persist 
		 */
		transactionService.saveTransaction(transaction);
		if(logger.isDebugEnabled()) {
			logger.debug("Persistence was successful for request {}", transaction.toString());
		}
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/transactions",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	private ResponseEntity<Void> removeTransactions() {
		
		transactionService.clearAll();
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
	
	@ExceptionHandler(InvalidTransactionException.class)
	public final ResponseEntity<Void> handleApplicationException(InvalidTransactionException ex, WebRequest request) {
		
		ResponseEntity<Void> responseEntity = null;
		
		switch (ex.getError()) {
		case INVALID_EVENT:
			responseEntity = new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
			break;
		case NOT_ACCEPTED:
			responseEntity = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			break;
		default:
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			break;
		}
		return responseEntity;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<Void> handleRequestContentTypeError(HttpMessageNotReadableException ex, WebRequest request) {
		if (ex.getCause() instanceof InvalidFormatException)
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
