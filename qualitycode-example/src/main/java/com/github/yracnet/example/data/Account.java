/**
 * Copyright © ${licence.year} ${licence.owner} (${licence.email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yracnet.example.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
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

	public void validateNew() throws Exception {
		validate();
	}

	public void validateEdit() throws Exception {
		validate();
	}

	public void validate() throws Exception {
		throw new Exception();
	}
}