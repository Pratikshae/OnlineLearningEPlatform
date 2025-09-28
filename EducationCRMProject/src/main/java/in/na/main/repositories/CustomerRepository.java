package in.na.main.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.na.main.entities.User;



@Repository
public interface CustomerRepository extends JpaRepository<User, Long>{
        
	User findByEmail(String email);

	
	  
		
	
}
