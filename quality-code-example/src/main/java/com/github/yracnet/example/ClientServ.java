package com.github.yracnet.example;
import com.github.yracnet.example.data.Client;
import java.util.List;

public interface ClientServ {
	public List<Client> 
								filterClient
								(Client filter) 
																throws Exception;

	public Client 
								createClient
								(Client
																value) 
																throws Exception;

	public Client 
								updateClient
								(Client 
																value) throws Exception;

	public Client removeClient
								(Client
																value) throws Exception;
}