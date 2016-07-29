package com.github.yracnet.example.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String password;
	@XmlElement
	private String description;
	@XmlElement
	private String sistem;
	@XmlElement
	private Long idClient;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		password = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public String getSistem() {
		return sistem;
	}

	public void setSistem(String value) {
		sistem = value;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long value) {
		idClient = value;
	}

}