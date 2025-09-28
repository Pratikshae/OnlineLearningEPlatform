package in.na.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.na.main.entities.User;
import in.na.main.repositories.UserRepository;

@Service
public class UserService {

	// service methods
	@Autowired
	private UserRepository userRepository;

	public void registerUserService(User user) {

		userRepository.save(user);
	}

	public boolean loginUserService(String email, String password) {
		User user = userRepository.findByEmail(email);

		if (user != null) {
			return user.getPassword().equals(password);
		}
		return false;
	}
}
