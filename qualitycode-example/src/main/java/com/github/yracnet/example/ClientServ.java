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
package com.github.yracnet.example;

import com.github.yracnet.example.data.Client;
import java.util.List;

public interface ClientServ {
	public List<Client> filterClient(Client filter) throws Exception;

	public Client createClient(Client value) throws Exception;

	public Client updateClient(Client value) throws Exception;

	public Client removeClient(Client value) throws Exception;
}