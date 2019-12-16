package com.unicam.crm;

import com.unicam.crm.bexio.model.BexioContactGet;
import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;

public interface CRMConnector {
	
	public Customer addCustomer(CustomerExchange c);
	
	public Customer getCustomer(int id);
	
	public Customer customerMapper(CustomerExchange c);
	
	public <C extends Customer> CustomerExchange crmCustomerMapper( C crmCustomer);

}
