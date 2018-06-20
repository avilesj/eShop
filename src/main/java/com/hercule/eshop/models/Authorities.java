package com.hercule.eshop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Authorities {

	@Column(nullable = false)
	private String authority;
	
	@ManyToOne
	@JoinColumn(name = "USERNAME")
	private User user;
}
