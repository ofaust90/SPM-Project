package com.unicam.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.unicam.crm.CRMConnector;
import com.unicam.crm.CRMConnectorFactory;
import com.unicam.rest.model.EmailExchange;
import com.unicam.rest.model.OfferExchange;
import com.unicam.rest.model.OfferSendExchange;

@Path("/offer")
public class CRMOfferServiceImpl implements CRMOfferService {

	
	CRMConnector crm;
	static ObjectMapper mapper = new ObjectMapper();
	
	
	
	public CRMOfferServiceImpl() {
		
		 CRMConnectorFactory crmFactory = new CRMConnectorFactory();
	     this.crm = crmFactory.getCRMConnector("BEXIO");
	
	}
	
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public OfferExchange createOffer(OfferExchange offer) {
			System.out.println("triggerd");
			return (OfferExchange) crm.addOffer(offer);
	}

	@Override
	@POST
	@Path("/{id}/send")
	public boolean sendOffer(@PathParam("id")int id, OfferSendExchange sendExchange) {
		
		
		return crm.sendOffer(id,sendExchange);
	}

	@Override
	@POST
	@Path("/{id}/accept")
	public boolean acceptOffer(@PathParam("id") int id) {
		
		return crm.acceptOffer(id);
	}

	@Override
	@POST
	@Path("/{id}/invoice")
	public int createInvoiceFromOffer(@PathParam("id")int id) {
		// TODO Auto-generated method stub
		return crm.createInvoiceFromOffer(id);
	}

	@Override
	@POST
	@Path("/{id}/invoice/send")
	public boolean sendInvoice(@PathParam("id")int id, String email) {
		
		
		return crm.sendInvoice(id,email);
	}

}
