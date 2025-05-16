package vn.hoidanit.laptopshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    

    public ProductService(ProductRepository productRepository, CartDetailRepository cartDetailRepository, 
    CartRepository cartRepository, UserService userService, OrderDetailRepository orderDetailRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);
        
        if(user != null) {
            // check user in cart? if not -> create new cart
            Cart cart = cartRepository.findByUser(user);
            if (cart == null) {
                // create new cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);
                
                cart = cartRepository.save(otherCart);
            }
            // get product by id
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent()) {
                Product realProduct = product.get();
                // check sản phẩm đã từng được them vào giỏ hàng chưa
                CartDetail oldDetail = cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if(oldDetail == null) {
                    // save cart_detail
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);
                    cartDetailRepository.save(cartDetail);
                    // update sum (cart)
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    cartDetailRepository.save(oldDetail);
                }
            }
        }

    }

    public Cart fetchByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetail = cartDetailRepository.findById(cartDetailId);
        if(cartDetail.isPresent()) {
            CartDetail realCartDetail = cartDetail.get();
            Cart cart = realCartDetail.getCart();
            // delete cart_detail
            cartDetailRepository.deleteById(cartDetailId);

            // update sum (cart)
            if (cart.getSum() > 1) {
                // update current cart
                int sum = cart.getSum() - 1;
                cart.setSum(sum);
                session.setAttribute("sum", sum);
                cartRepository.save(cart);
            } else {
                // delete cart (sum = 1)
                cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for( CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetail.getId());
            if(cartDetailOptional.isPresent()) {
                CartDetail realCartDetail = cartDetailOptional.get();
                // update quantity
                realCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetailRepository.save(realCartDetail);
            }
        }
    }


    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverPhone,
            String receiverAddress) {
        // create order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order = orderRepository.save(order);
        
        // created order detail
        // step 1: get cart by user
        Cart cart = cartRepository.findByUser(user);

        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {
                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart detail and cart
                for (CartDetail cartDetail : cartDetails) {
                    cartDetailRepository.deleteById(cartDetail.getId());
                }

                cartRepository.deleteById(cart.getId());

                // step 3: update session
                session.setAttribute("sum", 0);
            }
        }
    }
}
