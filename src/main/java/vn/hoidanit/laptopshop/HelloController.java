package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/")
	public String index() {
		return "Hello World TEST!DDDD";
	}
	@GetMapping("/user")
	public String userPage() {
		return "only users can access this page";
	}
	@GetMapping("/admin")
	public String adminPage() {
		return "only admins can access this page";
	}
}

