package com.unicam.crm;

import com.unicam.crm.bexio.BexioConnector;

public class CRMConnectorFactory {
	
	 public CRMConnector getCRMConnector(String crmType){
	   	
	      if(crmType.equalsIgnoreCase("BEXIO")){
	         return new BexioConnector();
	         
	      } 
	      //here you could add other CRM connectors if needed
	      
	      return null;
	   }

}
