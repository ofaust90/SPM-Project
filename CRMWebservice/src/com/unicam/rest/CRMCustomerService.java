package com.unicam.rest;

import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;
//import com.unicam.rest.model.Response;
import javax.ws.rs.core.Response;

public interface CRMCustomerService {
	
	public Customer addCustomer(CustomerExchange c);
	
	public CustomerExchange getCustomer(int id);
	


}
