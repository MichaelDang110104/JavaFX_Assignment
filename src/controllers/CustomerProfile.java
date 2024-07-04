package controllers;

import services.CustomerService;
import services.ICustomerService;

public class CustomerProfile {
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";
	
	
	public CustomerProfile() {
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
	}
	
	public void EditProfile() {
		
	}
}
