package in.na.main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.na.main.services.OrdersChartService;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrdersChartController {
	
	@Autowired
	private OrdersChartService ordersChartService;
	
	
	//handler method
	@GetMapping("/adminProfile")
	public String openAdminProfilePage(HttpSession httpSession, Model model) {
		
		if(httpSession.getAttribute("sessionAdmin") == null) {
			return "redirect:/adminLogin";
		}
		
		//----For Graph One
        List<Object[]> listOfCoursesAmountTotalSales = ordersChartService.findCoursesAmountTotalSales();
		
		
		List<String> date11= new ArrayList<>();
		List<Long> totalAmount11 = new ArrayList<>();
		
		for(Object[] obj : listOfCoursesAmountTotalSales) {
			
			
			date11.add((String) obj[0]);
			totalAmount11.add(((Number) obj[1]).longValue());
			
			
		}
	
		model.addAttribute("date11", date11.isEmpty() ? Collections.emptyList() : date11);
		model.addAttribute("totalAmount11", totalAmount11.isEmpty() ? Collections.emptyList() : totalAmount11);
		
		
		
		//----For Graph Two
        List<Object[]> listOfCoursesTotalSales = ordersChartService.findCoursesTotalSales();
		
		
		List<String> courseName1 = new ArrayList<>();
		List<Long> courseCount1 = new ArrayList<>();
		
		for(Object[] obj : listOfCoursesTotalSales) {
			
			
			courseName1.add((String) obj[0]);
			courseCount1.add(((Number) obj[1]).longValue());
			
			
		}
	
		model.addAttribute("courseName1", courseName1.isEmpty() ? Collections.emptyList() : courseName1);
		model.addAttribute("courseCount1", courseCount1.isEmpty() ? Collections.emptyList() : courseCount1);
		
		
		
		//----For Graph three--------
				List<Object[]> listOfCoursesSoldPerDay = ordersChartService.findCoursesSoldPerDay();
				
				
				List<String> dates1 = new ArrayList<>();
				List<Long> counts1 = new ArrayList<>();
				
				for(Object[] obj : listOfCoursesSoldPerDay) {
					
					
					dates1.add((String) obj[0]);
					counts1.add(((Number) obj[1]).longValue());
					
					
				}
			
				model.addAttribute("dates1", dates1.isEmpty() ? Collections.emptyList() : dates1);
				model.addAttribute("counts1", counts1.isEmpty() ? Collections.emptyList() : counts1);
				
				
		return "admin-profile";
	}
  
}
