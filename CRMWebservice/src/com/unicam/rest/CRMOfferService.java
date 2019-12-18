package com.unicam.rest;

import com.unicam.rest.model.EmailExchange;
import com.unicam.rest.model.OfferExchange;

public interface CRMOfferService {
	
	public OfferExchange createOffer(OfferExchange offer);
	
	public boolean sendOffer(int id, String email);
	
	public boolean acceptOffer(int id);
	
	public int createInvoiceFromOffer(int id);
	
	public boolean sendInvoice(int id, String email);

}
