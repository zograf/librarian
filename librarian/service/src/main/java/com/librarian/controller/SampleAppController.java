package com.librarian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.librarian.service.SampleAppService;


@RestController
public class SampleAppController {
	private static Logger log = LoggerFactory.getLogger(SampleAppController.class);

	private final SampleAppService sampleService;

	@Autowired
	public SampleAppController(SampleAppService sampleService) {
		this.sampleService = sampleService;
	}

	//@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json")
	//public Model getQuestions(@RequestParam(required = true) String id, @RequestParam(required = true) String name,
	//		@RequestParam(required = true) double cost, @RequestParam(required = true) double salePrice) {

	//	return new Model();
	//}
	
	
	
}
