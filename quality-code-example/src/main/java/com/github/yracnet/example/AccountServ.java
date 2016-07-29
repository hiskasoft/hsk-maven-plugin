package com.github.yracnet.example; import com.github.yracnet.example.data.Account;
 import java.util.List;

public interface AccountServ { 	List<Account> filterAccount(Account filter) throws Exception; 	Account createAccount(Account value) throws Exception; 	Account updateAccount(Account value) throws Exception; Account removeAccount(Account value) throws Exception; }
