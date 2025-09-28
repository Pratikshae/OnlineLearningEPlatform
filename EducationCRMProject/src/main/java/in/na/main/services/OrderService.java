package in.na.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.na.main.entities.Orders;
import in.na.main.repositories.OrdersRepository;

@Service
public class OrderService {
	// service operation
	@Autowired
	private OrdersRepository ordersRepository;

	public void storeUserOrders(Orders orders) {

		ordersRepository.save(orders);

	}

}
