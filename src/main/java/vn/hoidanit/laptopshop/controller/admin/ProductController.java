package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ProductController {
    
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getDashboard(Model model) {
        List<Product> arrProducts = productService.getAllProducts();
        model.addAttribute("products",  arrProducts);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        model.addAttribute("newProduct", product);
        model.addAttribute("id", id);
        return "admin/product/delete";
    }
    

    @PostMapping("/admin/product/create")
    public String getDataCreateProductPage(@ModelAttribute("newProduct") @Valid Product product,
     BindingResult result,  Model model, @RequestParam("imageFile") MultipartFile file) {
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if(result.hasErrors()) {
            // model.addAttribute("newProduct", product);
            return "admin/product/create";
        }
        // Validate image
        if (file.isEmpty()) {
            model.addAttribute("imageError", "Vui lòng chọn ảnh sản phẩm");
            return "admin/product/create";
        }
        String image = uploadService.handleSaveUploadFile(file, "product");
        product.setImage(image);
        // Save product to database
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    @PostMapping("/admin/product/update")
    public String updateProductPage(@ModelAttribute("newProduct") @Valid Product newProduct,
        BindingResult result, Model model, @RequestParam("imageFile") MultipartFile file) {
        // fix bug image
        Product oldProduct = productService.getProductById(newProduct.getId());
        if(result.hasErrors()) {
            newProduct.setImage(oldProduct.getImage()); // assign old image
            model.addAttribute("newProduct", newProduct);
            return "admin/product/update";
        }    
        Product currentProduct = productService.getProductById(newProduct.getId());
        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String image = uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(image);
            }
            currentProduct.setName(newProduct.getName());
            currentProduct.setPrice(newProduct.getPrice());
            currentProduct.setDetailDesc(newProduct.getDetailDesc());
            currentProduct.setShortDesc(newProduct.getShortDesc());
            currentProduct.setQuantity(newProduct.getQuantity());
            currentProduct.setSold(newProduct.getSold());
            currentProduct.setFactory(newProduct.getFactory());
            currentProduct.setTarget(newProduct.getTarget());
            
            productService.handleSaveProduct(currentProduct);

        }
        return "redirect:/admin/product";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("newProduct") Product newProduct) {
        productService.deleteProductById(newProduct.getId());
        return "redirect:/admin/product";
    }
}
