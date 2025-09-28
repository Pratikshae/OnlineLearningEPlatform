package in.na.main.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.na.main.entities.Orders;
import in.na.main.services.OrderService;

//This is rest api 
//RestController = ResponseBody + controller
@RestController
@RequestMapping("/api")
public class OrdersApi {

	@Autowired
	private OrderService orderService;

//	@PostMapping("/storeOderDetails")
//	public ResponseEntity<String> storeUserOrdersDetails(@RequestBody Orders orders) {
//		
//		orderService.storeUserOrders(orders);
//		
//		return ResponseEntity.ok("Order details stored successfully!!!");
//	}

	@PostMapping("/storeOderDetails")
	public ResponseEntity<String> storeUserOrdersDetails(@RequestBody Orders orders) throws RazorpayException {

		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_R8IyBt7ELw4AGT", "FoBhDsH5RQ5d4ItUno1g6SiR");

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", orders.getCourseAmount());
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "rcpt_id_" + System.currentTimeMillis());

		Order order = razorpayClient.orders.create(orderRequest);

		//System.out.println(order);
		orders.setOrderId(order.get("id"));
		orderService.storeUserOrders(orders);

		return ResponseEntity.ok("Order details stored successfully!!!");
	}

}
