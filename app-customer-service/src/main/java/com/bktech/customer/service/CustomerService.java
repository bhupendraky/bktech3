package com.bktech.customer.service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bktech.customer.Constants;
import com.bktech.customer.entity.Customer;
import com.bktech.customer.execp.AppException;
import com.bktech.customer.execp.ExceptionCode;
import com.bktech.customer.proxy.UserServiceProxy;
import com.bktech.customer.repository.CustomerRepository;
import com.bktech.customer.vo.UserVO;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private UserServiceProxy userServiceProxy;

	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	public Customer getCustomer(Long customerId) {
		return customerRepo.findByCustomerId(customerId)
				.orElseThrow(() -> new AppException(ExceptionCode.CUSSVC_0005));
	}

	public String loadCustomerData() {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("START_AT", System.currentTimeMillis())
				.toJobParameters();
		customerRepo.deleteAll();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS;
	}

	public UserVO getUserByUserName(String username) {
		return userServiceProxy.getUserByUsername(username);
	}

	public List<UserVO> getAllUser() {
		return userServiceProxy.getAllUser();
	}

}
