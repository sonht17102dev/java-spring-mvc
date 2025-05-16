package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class ItemController {
    
    private final ProductService productService;
    
    private final UserService userService;
    
    public ItemController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping("/product/{id}")
    public String getProductPageString(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        long productId = id;
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        productService.handleAddProductToCart(email, productId, session);
        
        return "redirect:/";
    }
    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        long cartDetailId = id;
        HttpSession session = request.getSession(false);

        productService.handleRemoveCartDetail(cartDetailId, session);
        
        return "redirect:/cart";
    }
    
    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        // Get the user ID from the session
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id);

        Cart cart = productService.fetchByUser(user);
        // Get the cart details if the cart is not null
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        
        return "client/cart/show";
    }
    
}
