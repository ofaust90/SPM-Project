package com.unicam.crm;

import com.unicam.crm.bexio.model.BexioContactGet;
import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;
import com.unicam.rest.model.Offer;
import com.unicam.rest.model.OfferExchange;
import com.unicam.rest.model.OfferSendExchange;

public interface CRMConnector {
	
	//Customers
	public Customer addCustomer(CustomerExchange c);
	
	public Customer getCustomer(int id);
	
	public Customer customerMapper(CustomerExchange c);
	
	public <C extends Customer> CustomerExchange crmCustomerMapper( C crmCustomer);

	//Offers
	public Offer addOffer(OfferExchange offer);
	
	public boolean acceptOffer(int id);
	
	public boolean declineOffer(int id);
	
	
	public boolean sendOffer(int id, OfferSendExchange sendExchange);
	
	public Offer offerMapper(OfferExchange o);
	
	public <O extends Offer> OfferExchange crmOfferMapper(O crmOffer);
	
	//Invoice
	public int createInvoiceFromOffer(int id);
	
	public boolean sendInvoice(int id,OfferSendExchange sendExchange);
	
}
