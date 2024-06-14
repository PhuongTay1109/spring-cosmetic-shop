package com.cosmetics.myshop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.service.UserService;
import com.cosmetics.myshop.utils.StringUtils;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
	public static String IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static/img";
	//Should have declare absolute path
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping("/search")
	private String search(@RequestParam String keyword, Model model) {
		List<Product> productList = productService.searchProductsByKeyword(keyword);
		
		List<String> categoryNames = categoryService.findAllCategories().stream()
				.map(Category::getCategoryName)
				.collect(Collectors.toList());
		model.addAttribute("productList", productList);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("keyword", keyword);
		return "user/search";
	}
	

	@GetMapping("/")
	private String home(Model model) {
	
		List<Product> topRatingProducts = productService.findTopRatingProducts();
		List<Product> newArrivalProducts = productService.findNewArrivalProducts();
		model.addAttribute("topRatingProducts", topRatingProducts);
		model.addAttribute("newArrivalProducts", newArrivalProducts);
		
		return "user/homepage";
	}
	
	@ResponseBody
	@PostMapping("/upload/image")
	public void uploadImage (Model model, @RequestParam("image") MultipartFile file
			, Authentication authentication, HttpServletResponse response) throws Exception {
		//Handle save file
		String originalFilename = file.getOriginalFilename();
	    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Get extension of file
		String milliseconds = String.valueOf(new Date().getTime()); // Get milliseconds for unique image name
		User user = (User)authentication.getPrincipal();
		boolean isAdmin = user.getAuthorities()
				.stream()
				.anyMatch(a  -> a.getAuthority().equals("ROLE_ADMIN"));
		String filePath = "";
		String avatar = "/img";
		String redirect = "";
		if (isAdmin) {
			filePath += "/admin/"; // Store in system
			avatar += "/admin/"; // Store in database
			redirect = "/admin/profile";
		}
		else {
			filePath +="/user/";  // Store in system
			avatar +="/user/"; // Store in database
			redirect = "/profile";
		}
		final String REDIRECT = redirect;
		filePath += milliseconds + '.' + extension;
		avatar += milliseconds + '.' + extension;
		Path fileNameAndPath = Paths.get(IMAGE_UPLOAD_DIRECTORY, filePath);
		Files.write(fileNameAndPath,file.getBytes());
//		CompletableFuture<Void> editImageFuture = new CompletableFuture<>();

		userService.editImage(avatar, user);
		Thread.sleep(1000);
		response.sendRedirect(REDIRECT);
		
	}
	
	@GetMapping("/order-history")
	public String getOrderHistory() {
		return "user/order_history";
	}

}
