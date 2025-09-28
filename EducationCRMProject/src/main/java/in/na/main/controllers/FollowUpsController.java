package in.na.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sessionEmp")
public class FollowUpsController {

	@GetMapping("/followUps")
	public String openFollowUpsPage() {

		return "follow-ups";
	}

}
