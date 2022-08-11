package com.tech.hub.customer.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tech.hub.customer.domain.Customer;

@Component
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		return customer;
	}
}