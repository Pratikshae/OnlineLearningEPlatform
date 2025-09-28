package in.na.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.na.main.entities.Inquiry;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	List<Inquiry> findByPhoneno(String phoneno);
}
