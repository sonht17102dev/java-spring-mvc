package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
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

    @GetMapping("/checkout")
    public String getCheckOutPageString(Model model, HttpServletRequest request) {
        User currentUser = new User(); // null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = productService.fetchByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
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
    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        productService.handleUpdateCartBeforeCheckout(cartDetails);    
        
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handleOrder(HttpServletRequest request,
        @RequestParam("receiverName") String receiverName,
        @RequestParam("receiverPhone") String receiverPhone,
        @RequestParam("receiverAddress") String receiverAddress 
    ) {
        // Get the user ID from the session
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id);

        productService.handlePlaceOrder(user, session, receiverName, receiverPhone, receiverAddress);

        
        return "redirect:/thanks";
    }

    @GetMapping("/thanks") 
    public String getThanksPage() {
        
        return "client/cart/thanks";
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

        model.addAttribute("cart", cart);
        
        return "client/cart/show";
    }
    
}
