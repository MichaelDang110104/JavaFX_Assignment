package controllers;

import services.CustomerService;
import services.ICustomerService;

public class CustomerEditProfileController {
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";
	
	
	public CustomerEditProfileController() {
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
	}
	
	public void EditProfile() {
		
	}
}
