package com.olx.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adv_status")
public class ADVStatusEntity {
	
	@Id
	@GeneratedValue
	private int id;
	private String status;
	
	public ADVStatusEntity() {
	}

	public ADVStatusEntity(String status) {
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
		return "ADVStatusEntity [id=" + id + ", status=" + status + "]";
	}
	

	

	
}
