package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.ADVStatus;
import com.olx.dto.Category;
import com.olx.service.MasterService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx-masterdata")
@CrossOrigin(origins = "*")
public class MasterdataController {

	@Autowired
	MasterService masterService;

	@GetMapping(value = "/advertises/categories", produces ={ MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Read the all Categories",notes = "Return the all Categories to client")
	public  ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<List<Category>>(masterService.getAllCategories(),HttpStatus.OK);
	}

	@GetMapping(value = "/advertises/status", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Read the all ADVStatus",notes = "Return the all ADVStatus to Client ")
	public ResponseEntity<List<ADVStatus>> getAllStatus() {
		return new ResponseEntity<List<ADVStatus>>(masterService.getAllStatus(),HttpStatus.OK);

	}

}
