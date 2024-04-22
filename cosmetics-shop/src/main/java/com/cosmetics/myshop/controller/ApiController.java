package com.cosmetics.myshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.JobKOctets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.dto.CartItemDTO;
import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.model.ShoppingSession;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.service.ShoppingSessionService;
import com.cosmetics.myshop.service.impl.CartItemServiceImpl;

@Controller
@RequestMapping("/api")
public class ApiController {

/*
// **************************************
// PRODUCT API
// **************************************
 */
	@Autowired
	ProductService productService;

	@ResponseBody
	@GetMapping("/related_products")
	List<Product> getRelatedProductsByPage(@RequestParam Map<String, String> param) {
		Integer id = Integer.parseInt(param.get("id"));
		Integer page = Integer.parseInt(param.get("page"));
		Integer per_page = 12;
		Product product = productService.findProductByid(id);
		Pageable pageable = PageRequest.of(page, per_page);
		return productService.findRelatedProductsByPage(product, pageable);
	}

	@ResponseBody
	@GetMapping("/products")
	List<Product> getProductsByCategory(@RequestParam Map<String, String> param) {
		String categoryName = param.get("category_name");
	
		return productService.findProductsByCategoryName(categoryName);
	}

/*
// **************************************
// CART API
// **************************************
*/

	@Autowired
	ShoppingSessionService shoppingSessionService;
	@Autowired
	CartItemServiceImpl cartItemService;

	@ResponseBody
	@GetMapping("/cart")
	public List<CartItemDTO> getCartItems(Authentication authentication) {
		if(authentication == null) {
			return null;			
		}
		
		User user = (User) authentication.getPrincipal();
		int userId = user.getUserId();

		ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUserId(userId);

		List<Object[]> result = cartItemService.findProductsAndQuantitiesByShoppingSessionId(shoppingSession.getId());
		List<CartItemDTO> productsWithQuantity = new ArrayList<>();
        for (Object[] objects : result) {
            Product product = (Product) objects[0];
            int productId = product.getId();
            int quantity = (int) objects[1];

            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setProduct(product);
            cartItemDTO.setProductId(productId);
            cartItemDTO.setQuantity(quantity);

            productsWithQuantity.add(cartItemDTO);
        }


		return productsWithQuantity;
	}

	@ResponseBody
	@PostMapping("/cart/add")
	public ResponseEntity<Void> addToCart(@RequestBody CartItemDTO addToCartDTO, Authentication authentication) {
	    if(authentication != null) {
	        User user = (User) authentication.getPrincipal();
	        int userId = user.getUserId();

	        ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUserId(userId);
	        if (shoppingSession == null) {
	            shoppingSession = new ShoppingSession();
	            shoppingSession.setUser(user);
	            shoppingSession.setCreatedAt(new Date());
	            shoppingSessionService.saveShoppingSession(shoppingSession);
	        }

	        // Check if the product is already in the cart
	        CartItem existingCartItem =
	                cartItemService.findCartItem(shoppingSession.getId(), addToCartDTO.getProductId());
	        if (existingCartItem != null) {
	            // Update quantity if the product is already in the cart
	            existingCartItem.setQuantity(existingCartItem.getQuantity() + addToCartDTO.getQuantity());
	            cartItemService.updateCartItemQuantity(shoppingSession.getId(),
	                    addToCartDTO.getProductId(), existingCartItem.getQuantity());
	            return ResponseEntity.ok().build();
	        }

	        cartItemService.addToCart(shoppingSession.getId(), addToCartDTO.getProductId(),
	                addToCartDTO.getQuantity(), new Date());
	        return ResponseEntity.ok().build();
	    } 
	    else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
	
	@ResponseBody
	@DeleteMapping("/cart/delete/{productId}")
	public ResponseEntity<Void> deleteCartItem(@PathVariable Integer productId, Authentication authentication) {
		if(authentication != null) {
			User user = (User) authentication.getPrincipal();
			int userId = user.getUserId();

			ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUserId(userId);

			cartItemService.deleteCartItem(shoppingSession.getId(), productId);
			 return ResponseEntity.ok().build();
		}
		else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }		
	}
}
