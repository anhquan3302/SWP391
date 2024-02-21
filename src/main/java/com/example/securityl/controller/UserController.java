package com.example.securityl.controller;


import com.example.securityl.model.CartItem;
import com.example.securityl.model.Products;
import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.response.ShoppingCartResponse.CartResponse;
import com.example.securityl.response.ShoppingCartResponse.DeleteCartResponse;
import com.example.securityl.response.UserResponse.ResponseUser;
import com.example.securityl.service.ProductService;
import com.example.securityl.service.ShoppingCartService;
import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoppingCart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {
  private final ShoppingCartService shoppingCartService;
  private final ProductService productService;

    @GetMapping("/viewCart")
    public ResponseEntity<String> víewCart(Model model){
         model.addAttribute("CART_ITEMS",shoppingCartService.getAllCart());
         model.addAttribute("TOTAL",shoppingCartService.getTotal());
        return ResponseEntity.ok("Success");
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

}
