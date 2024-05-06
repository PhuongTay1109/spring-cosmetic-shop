package com.cosmetics.myshop.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmetics.myshop.dto.CartItemDTO;
import com.cosmetics.myshop.dto.OrderDataDTO;
import com.cosmetics.myshop.model.OrderDetails;
import com.cosmetics.myshop.model.OrderItems;
import com.cosmetics.myshop.model.PaymentMethods;
import com.cosmetics.myshop.model.ShoppingSession;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.impl.CartItemServiceImpl;
import com.cosmetics.myshop.service.impl.OrderDetailsServiceImpl;
import com.cosmetics.myshop.service.impl.OrderItemsServiceImpl;
import com.cosmetics.myshop.service.impl.PaymentMethodsServiceImpl;
import com.cosmetics.myshop.service.impl.ProductServiceImpl;
import com.cosmetics.myshop.service.impl.ShoppingSessionServiceImpl;
import com.cosmetics.myshop.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CheckoutController {
	@GetMapping("/checkout")
	public String checkout() {
		return "/user/checkout";
	}

	@Autowired
	ProductServiceImpl productServiceImpl;

	@GetMapping("/checkout/buynow")
	public String buyNow(@RequestParam("product") int productId, @RequestParam("quantity") int quantity, Model model)
			throws JsonProcessingException {
		model.addAttribute("productId", productId);
		model.addAttribute("quantity", quantity);

		return "/user/checkout";
	}

	@Autowired
	PaymentMethodsServiceImpl paymentMethodsServiceImpl;
	@Autowired
	OrderDetailsServiceImpl orderDetailsServiceImpl;
	@Autowired
	OrderItemsServiceImpl orderItemsServiceImpl;
	@Autowired
	ShoppingSessionServiceImpl shoppingSessionServiceImpl;
	@Autowired
	CartItemServiceImpl cartItemServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping("/order")
	public String order(@RequestParam(name = "cost") Double cost, @RequestParam(name = "data") String data,
			Authentication authentication, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(data);
			data = new String(decodedBytes, StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();
			OrderDataDTO dataDTO = objectMapper.readValue(data, OrderDataDTO.class);

			User user = null;
			int shoppingSessionId = -1;

			if (authentication != null) {
				user = (User) authentication.getPrincipal();
				int userId = user.getUserId();
				ShoppingSession shoppingSession = shoppingSessionServiceImpl.findShoppingSessionByUserId(userId);
				shoppingSessionId = shoppingSession.getId();
			}

			if (authentication == null) {
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("shoppingSessionId")) {
							shoppingSessionId = Integer.parseInt(cookie.getValue());
						}
					}
				}
			}

			// create order_details
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setUser(user);
			orderDetails.setTotal(cost);
			orderDetails.setCreatedAt(new Date());
			orderDetails.setModifiedAt(new Date());
			orderDetails.setName(dataDTO.getName());
			orderDetails.setAddress(dataDTO.getAddress());
			orderDetails.setPhone(dataDTO.getPhone());

			PaymentMethods paymentMethod = paymentMethodsServiceImpl.findById(dataDTO.getPaymentMethodId());
			orderDetails.setPaymentMethod(paymentMethod);

			orderDetailsServiceImpl.save(orderDetails);

			// insert order_items
			for (CartItemDTO ci : dataDTO.getOrderList()) {
				OrderItems orderItem = new OrderItems();
				orderItem.setOrderDetails(orderDetails);
				orderItem.setCreatedAt(new Date());
				orderItem.setModifiedAt(new Date());
				orderItem.setProduct(ci.getProduct());
				orderItem.setQuantity(ci.getQuantity());
				orderItem.setTotal(ci.getProduct().getPrice() * ci.getQuantity());
				orderItemsServiceImpl.save(orderItem);
			}

			// delete shopping_session
			cartItemServiceImpl.deleteCart(shoppingSessionId);
			shoppingSessionServiceImpl.deleteShoppingSession(shoppingSessionId);

			// delete cookie of unauthorized user
			if (authentication == null) {
				Cookie cookie = new Cookie("shoppingSessionId", "");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}

			// save address
			if (user != null) {
				user.setAddress(dataDTO.getAddress());
				userServiceImpl.saveUser(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Payment failed");
			model.addAttribute("status", "error");
			return "/user/cart";
		}

		return "/user/order_success";
	}

	@GetMapping("/buynow/order")
	public String buyNowOrder(@RequestParam(name = "cost") Double cost, @RequestParam(name = "data") String data,
			Authentication authentication, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(data);
			data = new String(decodedBytes, StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();
			OrderDataDTO dataDTO = objectMapper.readValue(data, OrderDataDTO.class);

			User user = null;

			if (authentication != null) {
				user = (User) authentication.getPrincipal();
			}

			// create order_details
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setUser(user);
			orderDetails.setTotal(cost);
			orderDetails.setCreatedAt(new Date());
			orderDetails.setModifiedAt(new Date());
			orderDetails.setName(dataDTO.getName());
			orderDetails.setAddress(dataDTO.getAddress());
			orderDetails.setPhone(dataDTO.getPhone());

			PaymentMethods paymentMethod = paymentMethodsServiceImpl.findById(dataDTO.getPaymentMethodId());
			orderDetails.setPaymentMethod(paymentMethod);

			orderDetailsServiceImpl.save(orderDetails);

			// insert order_items
			for (CartItemDTO ci : dataDTO.getOrderList()) {
				OrderItems orderItem = new OrderItems();
				orderItem.setOrderDetails(orderDetails);
				orderItem.setCreatedAt(new Date());
				orderItem.setModifiedAt(new Date());
				orderItem.setProduct(ci.getProduct());
				orderItem.setQuantity(ci.getQuantity());
				orderItem.setTotal(ci.getProduct().getPrice() * ci.getQuantity());
				orderItemsServiceImpl.save(orderItem);
			}

			// save address
			if (user != null) {
				user.setAddress(dataDTO.getAddress());
				userServiceImpl.saveUser(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Payment failed");
			model.addAttribute("status", "error");
			return "/user/cart";
		}

		return "/user/order_success";
	}
}
