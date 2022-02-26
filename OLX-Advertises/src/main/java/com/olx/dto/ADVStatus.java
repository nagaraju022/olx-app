package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "ADVStatus DTO")
public class ADVStatus {

	@ApiModelProperty(value = "ADVStatus identifier")
	private int id;
	@ApiModelProperty(value = "ADVStatus status like OPEN OR CLOSED")
	private String status;

	public ADVStatus() {

	}

	public ADVStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ADVStatus [id=" + id + ", status=" + status + "]";
	}

	
	
}
