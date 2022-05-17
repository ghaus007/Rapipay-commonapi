package com.rapipay.commonapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "Notification", schema = "INF")
@Getter
@ToString
public class NotificationEntity {

	@Id
	@Column(name = "N_id")
	private String nId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "View_Status")
	private String viewStatus;
	
	@Column(name = "Creation_Date")
	private String creationDate;

}
