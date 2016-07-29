package com.github.yracnet.example.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String sistem;
	@XmlElement
	private Long idAccount;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getSistem() {
		return sistem;
	}

	public void setSistem(String value) {
		sistem = value;
	}

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long value) {
		idAccount = value;
	}

	public void validateNew()
			throws Exception {
		validate();
	}

	public void validateEdit()
			throws Exception {
		validate();
	}

	public void validate()
			throws Exception {
		throw new Exception();
	}
}