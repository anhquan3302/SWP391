package com.example.securityl.controller;


import com.example.securityl.model.CartItem;
import com.example.securityl.model.Orders;
import com.example.securityl.model.Products;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.response.ShoppingCartResponse.CartResponse;
import com.example.securityl.response.ShoppingCartResponse.DeleteCartResponse;
import com.example.securityl.service.OrderService;
import com.example.securityl.service.ProductService;
import com.example.securityl.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/shoppingCart")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN','STAFF')")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class ShoppingController {
  private final ShoppingCartService shoppingCartService;
  private final ProductService productService;
  private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);


    @GetMapping("/viewCart")
    public ResponseEntity<List<CartItem>> viewCart(Model model) {
        double total = shoppingCartService.getTotal();
        List<CartItem> cartItems = (List<CartItem>) shoppingCartService.getAllCart();
        model.addAttribute("CART_ITEMS", cartItems);
        model.addAttribute("TOTAL", total);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/deleteAll")
    public ResponseEntity<String> deleteAll (){
        shoppingCartService.deleteCart();
        return ResponseEntity.ok("DeleteAllSuccess");
    }

    @DeleteMapping("/deleteCart/{productId}")
    private ResponseEntity<DeleteCartResponse> deleteCart(@PathVariable Integer productId){
        try {
            shoppingCartService.remove(productId);
            return ResponseEntity.ok().body(new DeleteCartResponse("Success","Delete Cart Success"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new DeleteCartResponse("Fail","Delete Cart Fail"));
        }
    }

    @GetMapping("/addtoCart/{productId}")
    private ResponseEntity<CartResponse> addToCart(@PathVariable Integer productId) {
        try {
            Products product1 = productService.getProductById(productId);
            CartItem cartItem = null;
            if (product1 != null) {
                cartItem = new CartItem();
                cartItem.setProductId(product1.getProductId());
                cartItem.setProductName(product1.getProductName());
                cartItem.setPrice(product1.getPrice());
                cartItem.setQuantity(1);
                cartItem.setDiscount(product1.getDiscount());
                shoppingCartService.add(cartItem);
                return ResponseEntity.ok().body(CartResponse.builder()
                        .status("Success")
                        .message("Add to cart success")
                        .payload(cartItem)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(CartResponse.builder()
                    .status("Fail")
                    .message("Not found product")
                    .payload(null)
                    .build());
        }
        return ResponseEntity.badRequest().body(CartResponse.builder()
                .status("Fail")
                .message("Add to cart fail")
                .payload(null)
                .build());
    }

    @PutMapping("/updateCart")
    private ResponseEntity<CartResponse> updateCart (@RequestParam("id") Integer id,
                                                     @RequestParam("quantity") Integer quantity){
        CartItem cartItem = shoppingCartService.updateCart(id,quantity);
        return ResponseEntity.ok().body(new CartResponse("Success","UpdateSucees",cartItem));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ResponseObject> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        try {
            List<CartItem> cartItems = (List<CartItem>) shoppingCartService.getAllCart();
           Orders order = orderService.checkout(checkoutRequest, cartItems);
            return ResponseEntity.ok(new ResponseObject("Success", "Checkout success", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("Error", "Failed to checkout", null));
        }
    }


}
