package in.na.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.na.main.entities.Inquiry;
import in.na.main.repositories.InquiryRepository;

@Service
public class InquiryService {

	// service methods
	@Autowired
	private InquiryRepository inquiryRepository;

	public void addNewInquiry(Inquiry inquiry) {
		inquiryRepository.save(inquiry);
	}

	public List<Inquiry> findInquiriesUsingPhno(String phoneNumber) {

		return inquiryRepository.findByPhoneno(phoneNumber);
	}

}
