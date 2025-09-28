package in.na.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.na.main.entities.Feedback;
import in.na.main.repositories.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	
	public void sendFeedback(Feedback feedback){
		
		feedbackRepository.save(feedback);
	}
	
	public Page<Feedback> getAllFeedbacksByPagination(Pageable pageable) {
		 	return feedbackRepository.findAll(pageable);
	}
	
	public boolean updateFeedbackStatus(Long id, String stauts) {
		
		//Find the feedback by its ID
		Feedback feedback = feedbackRepository.findById(id).orElseThrow(null);
		
		
		//if  feedback exists, update its status
		if(feedback != null) {
			feedback.setReadStatus(stauts);
			feedbackRepository.save(feedback);
			return true; //Return true if the update was successfull
		}
		
		//else return false if feedback with the given id was not found
		return false;
		
	}


}
