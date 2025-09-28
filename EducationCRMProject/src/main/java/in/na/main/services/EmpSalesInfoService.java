package in.na.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.na.main.repositories.EmpSalesInfoRepository;

@Service
public class EmpSalesInfoService {
	
	@Autowired
	private EmpSalesInfoRepository empSalesInfoRepository;
	
	
	public String findTotalSalesByAllEmployee() {
		 
		return empSalesInfoRepository.findTotalSalesByAllEmpployees();
	}
	
	public List<Object[]> findTotalSalesByEachEmployee() {
		
		return empSalesInfoRepository.findTotalSalesByEachEmployee();
	}

}
