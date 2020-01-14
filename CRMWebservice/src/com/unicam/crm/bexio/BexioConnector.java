package com.unicam.crm.bexio;



import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.unicam.crm.CRMConnector;
import com.unicam.crm.bexio.model.BexioContact;
import com.unicam.crm.bexio.model.BexioContactGet;
import com.unicam.crm.bexio.model.BexioInvoice;
import com.unicam.crm.bexio.model.BexioOffer;
import com.unicam.crm.bexio.model.BexioOfferGet;
import com.unicam.crm.bexio.model.BexioPosition;
import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;
import com.unicam.rest.model.Offer;
import com.unicam.rest.model.OfferExchange;
import com.unicam.rest.model.OfferSendExchange;



public class BexioConnector implements CRMConnector{
	
	
	private final  String BASE_URL = "https://office.bexio.com/api2.php/";
	private final String COMPANY_ID = "lgqyjffw2bna";
	private final String USER_ID = "1";
	private final String PUBLIC_KEY = "7dfdb2b54598f20fd51095b6ab6b1300";
	private final String SIGNATURE_KEY = "d780393b3be0c82ba7e856f3526e9937";
	private String companyBexioURL;
	
	static ObjectMapper mapper = new ObjectMapper();
	
	
	
	public BexioConnector() {
		
		this.companyBexioURL = BASE_URL+COMPANY_ID+"/"+USER_ID+"/"+PUBLIC_KEY+"/";
		//System.out.println("URL:"+companyBexioURL);
	}
	
	private String calculateSignature(String method, String resource, String payload) {
		
		String uri = this.companyBexioURL+resource;
		String requestHashString = method + uri + payload + this.SIGNATURE_KEY;
		System.out.println("tobe hashed: "+requestHashString);
		String md5Hex = DigestUtils.md5Hex(requestHashString);
		
		return md5Hex;
	}
	
	
	private String post(String resource, String payload) {
		
		try {

			System.out.println("payload: "+payload);
			
			ClientConfig clientConfig = new DefaultClientConfig();              
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			Client client = Client.create(clientConfig);
			
			String uri = this.companyBexioURL+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
	
			webResource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");		
			webResource.header("Content-Length", payload.length());
		
		
			System.out.println("length: "+payload.length());


			ClientResponse response = webResource.accept("application/json").type("application/json")
			   .post(ClientResponse.class, payload);
			
			
			//200, 201 etc is ok
			if (response.getStatus() > 205 ) {
				
				
				System.out.println("Erros: Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Success: Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return "error";
	}
	
	
	private String get(String resource) {
		try {
			

			System.out.println("GET: "+resource);
			Client client = Client.create();

			String uri = this.companyBexioURL+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
			
			String signature = calculateSignature("get" ,resource, "");
			System.out.println("Signature: "+signature);
			
			webResource.header("Signature",signature);
			webResource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			webResource.header("Accept", "application/json");
			
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
				
				System.out.println("Err. Response "+response.toString());
				String output = response.getEntity(String.class);

				System.out.println("Output from Server .... \n");
				System.out.println(output);
				
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);
			
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		
		return "error";
	}

	

	
	
	//****************Customer**************************/
	
	@Override
	public Customer customerMapper(CustomerExchange c) {
		
		BexioContact bc = new BexioContact();
		bc.setName_2(c.getName());
		bc.setName_1(c.getFamilyname());
		bc.setAddress(c.getAddress());
		bc.setCity(c.getCity());
		bc.setMail(c.getEmail());
		bc.setRemarks(c.getCustomerType());
		bc.setPostcode(String.valueOf(c.getPostcode()));
		bc.setPhone_fixed(c.getPhone());
		
		
		return bc;
		
	}
	
	
	
	@Override
	public <C extends Customer> CustomerExchange crmCustomerMapper(C crmCustomer) {
		
		
		CustomerExchange ce = new CustomerExchange();
		ce.setId(((BexioContactGet) crmCustomer).getId());
		ce.setName(((BexioContactGet) crmCustomer).getName_2());
		ce.setFamilyname(((BexioContactGet) crmCustomer).getName_1());
		ce.setAddress(((BexioContactGet) crmCustomer).getAddress());
		ce.setCity(((BexioContactGet) crmCustomer).getCity());
		ce.setCustomerType(((BexioContactGet) crmCustomer).getRemarks());
		ce.setEmail(((BexioContactGet) crmCustomer).getMail());
		ce.setPhone(((BexioContactGet) crmCustomer).getPhone_fixed());
		String strPostcode = ((BexioContactGet) crmCustomer).getPostcode();
		if(strPostcode.length() > 0) {
			ce.setPostcode(Integer.parseInt(strPostcode));
		}
		
		
		return ce;
	}

	
	@Override
	public Customer addCustomer(CustomerExchange c) {
		
		
		BexioContact bc = (BexioContact) customerMapper(c);

		try {
		
			String jsonInString = this.mapper.writeValueAsString(bc);
			
			String response = this.post("contact",jsonInString);
			
			String result = "Customer saved : " + c + " response: "+response;
			
			BexioContactGet bexRet = mapper.readValue(response, BexioContactGet.class);
			CustomerExchange exchangeCustomer = crmCustomerMapper(bexRet);
			
			//Customer contact = mapper.readValue(response, Customer.class);
			
			return exchangeCustomer;//Response.status(201).entity(contact).build();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public CustomerExchange getCustomer(int id) {
		

		
		try {
			
			String contactJson = this.get("contact/"+id);
			//CustomerExchange contact;
			//contact = mapper.readValue(contactJson, CustomerExchange.class);
			BexioContactGet bexioContactget = mapper.readValue(contactJson, BexioContactGet.class);
			CustomerExchange customer = crmCustomerMapper(bexioContactget);
			
			return customer;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	//************************OFFER****************************3
	
	@Override
	public Offer offerMapper(OfferExchange oe) {
		
		BexioOffer bo = new BexioOffer();
		bo.setContact_id(oe.getContact_id());
		
		BexioPosition pos = new BexioPosition();
		pos.setAmount(oe.getAmount());
		pos.setUnit_price(oe.getPrice());
		pos.setDiscount_in_percent(oe.getDiscount());
		pos.setText(oe.getPositions());
		ArrayList<BexioPosition> positions = new ArrayList<BexioPosition> ();
		positions.add(pos);	
		bo.setPositions(positions);
		
		return bo;
		
	}
	@Override
	public <O extends Offer> OfferExchange crmOfferMapper(O crmOffer) {
		
		
		OfferExchange oe = new OfferExchange();
		
		oe.setId(((BexioOfferGet) crmOffer).getId());
		oe.setContact_id(((BexioOfferGet) crmOffer).getContact_id());
		//oe.setPositions(((BexioOfferGet) crmOffer).getPositions());
		BexioPosition bexPos = ((BexioOfferGet) crmOffer).getPositions().get(0);
		
		oe.setPositions(bexPos.getText());
		oe.setPrice(bexPos.getUnit_price());
		oe.setDiscount(bexPos.getDiscount_in_percent());
		oe.setAmount((int) bexPos.getAmount());
		
		oe.setTotal_price(((BexioOfferGet) crmOffer).getTotal());
		
		return oe;
	}
	
	
	@Override
	public Offer addOffer(OfferExchange offer) {
		
		BexioOffer bo = (BexioOffer) offerMapper(offer);

		try {
		
			String jsonInString = this.mapper.writeValueAsString(bo);

			System.out.println("sting: "+jsonInString);
			String response = this.post("kb_offer",jsonInString);
			
			String result = "Offer saved : " + offer + " response: "+response;
			System.out.println(result);
			
			BexioOfferGet bexRet = mapper.readValue(response, BexioOfferGet.class);
			OfferExchange exchangeOffer = crmOfferMapper(bexRet);
			
			//Customer contact = mapper.readValue(response, Customer.class);
			
			return exchangeOffer;//Response.status(201).entity(contact).build();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

	

	@Override
	public boolean acceptOffer(int id) {
		
	
			try {
			String idStr = Integer.toString(id);
			String url = "kb_offer/"+idStr+"/accept";
			
			String response = this.post(url,"");
			
			String result = "Offer accepted  response: "+response;
			System.out.println(result);
			
			}catch(Exception ex) {
				
				return false;
			}
			return true;//Response.status(201).entity(contact).build();
	
		
		
		
	}
	

	@Override
	public boolean declineOffer(int id) {
		
	
			try {
			String idStr = Integer.toString(id);
			String url = "kb_offer/"+idStr+"/reject";
			
			String response = this.post(url,"");
			
			String result = "Offer rejected  response: "+response;
			System.out.println(result);
			
			}catch(Exception ex) {
				
				return false;
			}
			return true;//Response.status(201).entity(contact).build();
	
		
		
		
	}

	@Override
	public boolean sendOffer(int id, OfferSendExchange sendExchange) {
		

		try {
		String idStr = Integer.toString(id);
		
		//issue offer
		String url = "kb_offer/"+idStr+"/issue";
		this.post(url,"");
	
		
		//send offer
		String urlSend = "kb_offer/"+idStr+"/send";
		
		/*
		 // base url
	    String baseURL = "http://localhost:8080/ProductConfiguratorWebsite/WebContent/offer.html?q=";

	    // query string
	    String query = "Dankeschön für Ihre €100";

	    // URL encode query string
	    String encodeStr = URLEncoder.encode(query, StandardCharsets.UTF_8.name());

	    // final url
	    String url = baseURL + encodeStr;

	    // print the url
	    System.out.println(url);
		
		*/
		
		
		String msgbody = "{\n" + 
				"  \"recipient_email\": \"" + sendExchange.getEmail() +"\",\n" + 
				"  \"subject\": \"Your Offer\",\n" + 
				"  \"message\": \"Dear Customer, please find your offer follwing this link: http://localhost:8080/ProductConfiguratorWebsite/WebContent/offer.html?businessKey="+sendExchange.getBusinessKey()+"&id="+idStr+"&link=[Network Link]\"\n" + 
				"}";
				
				
		System.out.println("body: "+msgbody);
		
		this.post(urlSend,msgbody);
		
		
		
		}catch(Exception ex) {
			
			return false;
		}
		return true;//Response.status(201).entity(contact).build();

	}

	@Override
	public int createInvoiceFromOffer(int id) {
		//try {
			String idStr = Integer.toString(id);
			
			//issue invoice
			String url = "kb_offer/"+idStr+"/invoice";
			String response = this.post(url,"");
			
			
			String result = "Inovice created  response: "+response;
			System.out.println(result);
			
			
			try {
			BexioInvoice invoice;
		
				invoice = mapper.readValue(response, BexioInvoice.class);
			
				return invoice.getId();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		//	}catch(Exception ex) {
				
				return 0;
		//	}
			
	}

	@Override
	public boolean sendInvoice(int id, OfferSendExchange sendExchange) {
		

		try {
		String idStr = Integer.toString(id);
		
		//issue offer
		String url = "kb_invoice/"+idStr+"/issue";
		this.post(url,"");
		
		//send offer
		String urlSend = "kb_invoice/"+idStr+"/send";
		
		String msgbody = "{\n" + 
				"  \"recipient_email\":  \"" + sendExchange.getEmail() +"\",\n" + 
				"  \"subject\": \"Your Email\",\n" + 
				"  \"message\": \"Dear Customer, please find your Inovice follwing this link: [Network Link]\"\n" + 
				"}";
		System.out.println("body: "+msgbody);
		
		this.post(urlSend,msgbody);
		
		
		
		}catch(Exception ex) {
			
			return false;
		}
		return true;
	}

	


	
}
