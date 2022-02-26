package com.olx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.service.AdvertisesService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx-advertises")
@CrossOrigin(origins = "*")
public class AdvertisesController {

	@Autowired
	AdvertisesService advertisesService;

	@PostMapping(value = "/advertise", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Add the advertises ", notes = "create the advertises")
	public ResponseEntity<Advertise> createAdvertises(
			@RequestBody Advertise advertises  , @RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<Advertise>(advertisesService.createAdvertises(advertises,authToken), HttpStatus.CREATED);
	}

	@PutMapping(value = "/user/advertise/{advertiseId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	 @ApiOperation(value = "modifiy the advertises by advertiseId ", notes = "the advertises modification by using advertisId")
	public ResponseEntity<Advertise> updateAdvertisesById(@PathVariable("advertiseId") int advertiseId,
			@RequestBody Advertise advertises , @RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<Advertise>(advertisesService.updateAdvertisesById(advertiseId, advertises,authToken),
				HttpStatus.OK);

	}

	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read all the advertises ", notes = "Return the advertises to client")
	public ResponseEntity<List<Advertise>> getAllAdvertises(@RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<List<Advertise>>(advertisesService.getAllAdvertises(authToken), HttpStatus.OK);

	}

	@GetMapping(value = "/user/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read the advertises of particular advertise by advertiseId ", notes = "Return the particular advertises to client")
	public ResponseEntity<Advertise> getAdvertisesById(
			@PathVariable("advertiseId") int advertiseId  , @RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<Advertise>(advertisesService.getAdvertisesById(advertiseId,authToken), HttpStatus.OK);

	}

	@DeleteMapping(value = "/user/advertise/{advertiseId}")
	public ResponseEntity<Boolean> deleteAdvertisesById(
			@PathVariable("advertiseId") int advertiseId, @RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<Boolean>(advertisesService.deleteAdvertisesById(advertiseId,authToken), HttpStatus.OK);

	}

	@GetMapping(value = "/advertise/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read the list advertises by search filter ", notes ="Return the advertises to client of matching input")
	public List<Advertise> searchAdvertisesByFilterCriteria(
			@RequestParam(name = "searchText", required = false) String searchText,
			@RequestParam(name = "categoryId", required = false) int categoryId,
			@RequestParam(name = "postedBy", required = false) String postedBy,
			@RequestParam(name = "dateCondition", required = false) String dateCondition,
			@RequestParam(name = "onDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate onDate,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(name = "sortedBy", required = false) String sortedBy,
			@RequestParam(name = "startIndex", defaultValue = "0") int startIndex,
			@RequestParam(name = "records", defaultValue = "10") int records) {
		return advertisesService.searchAdvertisesByFilterCriteria(searchText, categoryId, postedBy, dateCondition,
				onDate, fromDate, toDate, sortedBy, startIndex, records);
	}

	@GetMapping(value = "/advertise/search", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read the list advertises by search text ", notes = "Return the advertises to client of matching data")
	public ResponseEntity<List<Advertise>> getAdvertisesBySearchText(@RequestParam("searchText") String searchText) {
		return new ResponseEntity<List<Advertise>>(advertisesService.getAdvertisesBySearchText(searchText),
				HttpStatus.OK);

	}

	@GetMapping(value = "/advertise/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read the particular advertise by Id ", notes = "Return the advertise to client ")
	public ResponseEntity<Advertise> getAdvertiseById(@PathVariable("id") int id) {
		return new ResponseEntity<Advertise>(advertisesService.getAdvertiseById(id), HttpStatus.OK);

	}
	
	

}
