package com.tech.hub.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.hub.common.annotation.Traceble;
import com.tech.hub.customer.domain.Customer;
import com.tech.hub.customer.service.CustomerService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Traceble
	@ApiOperation("Fetch all customers")
	@GetMapping("/get-all")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@Traceble
	@ApiOperation("Get customer by id")
	@GetMapping("/get/{customerId}")
	public Customer getCustomer(@PathVariable Long customerId) {
		return customerService.getCustomer(customerId);
	}
	
	@Traceble
	@ApiOperation("Load customer data")
	@PostMapping("/load")
	public String loadCustomerData() {
		return customerService.loadCustomerData();
	}
}