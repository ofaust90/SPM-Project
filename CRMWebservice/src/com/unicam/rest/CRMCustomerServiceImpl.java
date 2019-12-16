package com.unicam.rest;

import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.unicam.crm.CRMConnector;
import com.unicam.crm.CRMConnectorFactory;
import com.unicam.crm.bexio.BexioConnector;
import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;
//import com.unicam.rest.model.Response;

@Path("/customer")
public class CRMCustomerServiceImpl implements CRMCustomerService {

	
	CRMConnector crm;
	static ObjectMapper mapper = new ObjectMapper();
	
	
	
	public CRMCustomerServiceImpl() {
		
		
		 CRMConnectorFactory crmFactory = new CRMConnectorFactory();
	     this.crm = crmFactory.getCRMConnector("BEXIO");
	
	
		
	}
	
	@Override
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerExchange addCustomer(CustomerExchange c) {
	
	
		return (CustomerExchange) crm.addCustomer(c); 
		
	}

	@Override
	@GET
	@Path("/{id}/get")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerExchange getCustomer(@PathParam("id") int id) {
		
		return 	(CustomerExchange) crm.getCustomer(id);
	}

}
