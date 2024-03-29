package com.example.microservicios.currencyexchangeservices;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment enviroment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from,
			@PathVariable String to) {
		 
		ExchangeValue exchangeValue =	repository.findByFromAndTo(from, to);

		if(exchangeValue == null) {
			// Valor por defecto
			exchangeValue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		}
		
		exchangeValue.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
		return exchangeValue; 
	}

}
