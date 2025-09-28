package in.na.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.na.main.entities.FollowUps;
import in.na.main.repositories.FollowUpsRepository;

@Service
public class FollowUpsService {

	@Autowired
	private FollowUpsRepository followUpsRepository;

	public void addFollowUps(FollowUps followUps) {

		Optional<FollowUps> optional = followUpsRepository.findByPhoneno(followUps.getPhoneno());
		if (optional.isPresent()) {

			FollowUps oldFollowUps = optional.get();
			
			oldFollowUps.setFollowUpDate(followUps.getFollowUpDate());
			followUpsRepository.save(oldFollowUps);

		} else {

			followUpsRepository.save(followUps);
		}

	}

	public List<FollowUps> getMyFollowUps(String empEmail, String followUpDate) {
		
		return followUpsRepository.findByEmpEmailAndFollowUpDate(empEmail, followUpDate);
	}
	
	public void deleteByPhoneNumber(String phoneNo) {
		
		Optional<FollowUps> optionalFollowUps = followUpsRepository.findByPhoneno(phoneNo);
		
		if(optionalFollowUps.isPresent()) {
			
			FollowUps followUps = optionalFollowUps.get();
			followUpsRepository.delete(followUps);
		}
	}

}
