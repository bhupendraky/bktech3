package com.techy.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techy.common.annotation.Traceble;
import com.techy.url.service.UrlService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlService service;

	@Traceble
	@ApiOperation("Get the URL")
	@GetMapping("/{hashCode}")
	public String getAllUser(@PathVariable String hashCode) {
		return service.getUrl(hashCode);
	}

	@Traceble
	@ApiOperation("Shorten URL")
	@PostMapping()
	public String shortenUrl(@RequestBody String url) {
		return service.shortenUrl(url);
	}

}
