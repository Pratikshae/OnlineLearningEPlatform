package in.na.main.services;

import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import in.na.main.entities.User;
import in.na.main.repositories.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Page<User> getAllUserDetailsByPagination(Pageable pageable) {
		
		return customerRepository.findAll(pageable);
		
	}
	
	public User getCustomerDetails(String userEmail) {
		return customerRepository.findByEmail(userEmail);
	}
	
	public void updateUserBanStatus(User user) {
		customerRepository.save(user);
	}
	

}
