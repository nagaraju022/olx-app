package com.olx.dto;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Advertises DTO")
public class Advertise {

	@ApiModelProperty(value = "Advertises identifier")
	private int id;
	@ApiModelProperty(value = "Advertises title")
	private String title;
	@ApiModelProperty(value = "Advertises category")
	private int category;
	@ApiModelProperty(value = "Advertises status")
	private int status;
	@ApiModelProperty(value = "Advertises price")
	private double price;
	@ApiModelProperty(value = "Advertises descr")
	private String description;
	// @ApiModelProperty(value = "Advertises created date")
	private LocalDate createdDate;
	@ApiModelProperty(value = "Advertises modified date")
	private LocalDate modifiedDate;
	@ApiModelProperty(value = "Advertises actve")
	private int active;
	@ApiModelProperty(value = "Advertises postedby")
	private String postedBy;
	@ApiModelProperty(value = "Advertises username")
	private String username;

	public Advertise() {
	}

	public Advertise(String title, int category, int status, double price, String description, LocalDate createdDate,
			LocalDate modifiedDate, int active, String postedBy, String username) {
		super();
		this.title = title;
		this.category = category;
		this.status = status;
		this.price = price;
		this.description = description;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.active = active;
		this.postedBy = postedBy;
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		Advertise advertise = (Advertise) obj;
		if (this.title == null) {
			return false;
		}
		if (this.title.equals(advertise.title)) {
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
