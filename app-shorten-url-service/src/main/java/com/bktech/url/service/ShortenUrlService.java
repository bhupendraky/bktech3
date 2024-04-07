package com.bktech.url.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bktech.url.Constants;
import com.bktech.url.entity.Counter;
import com.bktech.url.entity.WebUrl;
import com.bktech.url.execp.AppException;
import com.bktech.url.execp.ExceptionCode;
import com.bktech.url.repository.CounterRepository;
import com.bktech.url.repository.WebUrlRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShortenUrlService {

	private static String base62Alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Autowired
	private WebUrlRepository webUrlRepository;
	@Autowired
	private CounterRepository counterRepository;

	public String getUrl(String hashCode) {
		return webUrlRepository.findByHashCode(hashCode)
				.map(e -> e.getUrl())
				.orElseThrow(() -> new AppException(ExceptionCode.URLSVC_0005));
	}

	public List<String> getAllUrl() {
		return webUrlRepository.findAll()
				.stream()
				.map(e -> e.getUrl())
				.collect(Collectors.toList());
	}

	@Transactional
	public String shortenUrl(String url) {
		UrlValidator urlValidator = new UrlValidator(new String[] { "http", "https" });
		if(!urlValidator.isValid(url)) {
			throw new AppException(ExceptionCode.URLSVC_0006);
		}
		Long nextCouterValue = getNextCounterValue();
		String hashCode = generateBase62Hash(nextCouterValue);
		WebUrl webUrl = new WebUrl(hashCode, url);
		webUrlRepository.save(webUrl);

		return hashCode;
	}

	private String generateBase62Hash(Long counter) {
		StringBuffer hashStr = new StringBuffer();
		while(counter > 0) {
			long r = counter % 62;
			hashStr.append(base62Alphabets.charAt((int)r));
			counter = counter / 62;
		}
		return hashStr.reverse().toString();
	}

	@Transactional
	public Long getNextCounterValue() {
		counterRepository.updateCounterValue();
		return counterRepository.findById(1)
				.map(Counter::getValue)
				.orElse(1L);
	}

	@Transactional
	public String deleteAllUrl() {
		webUrlRepository.deleteAll();
		counterRepository.resetCounterValue();
		return Constants.SUCCESS;
	}

	@PostConstruct
	public void postConstruct() {
		Optional<Counter> counter = counterRepository.findById(1);
		if(!counter.isPresent()) {
			counterRepository.save(new Counter());
		}
	}

}
