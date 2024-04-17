package com.cosmetics.myshop.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.service.CategoryService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("")
@Controller
public class CategoryController {
	public static String IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static";
	@Autowired
	CategoryService categoryService;
	
	private void handleUploadFile(MultipartFile file) throws Exception {
		String originalFilename = file.getOriginalFilename();
	    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Get extension of file
		String milliseconds = String.valueOf(new Date().getTime()); // Get milliseconds for unique image name
		String filePath = IMAGE_UPLOAD_DIRECTORY + "/img/categories/" + milliseconds + "." + extension;
		Path fileNameAndPath = Paths.get(filePath);
		Files.write(fileNameAndPath,file.getBytes());
	}
	
	@ResponseBody
	@PostMapping("/admin/category")
	public  ResponseEntity<Category> processPostCategory(@RequestParam("image") MultipartFile file
			,@RequestParam Map<String, String> body, HttpServletResponse response) throws Exception {
		String categoryName = (String)body.get("categoryName");
		String originalFilename = file.getOriginalFilename();
	    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Get extension of file
		String milliseconds = String.valueOf(new Date().getTime()); // Get milliseconds for unique image name
		String filePath = IMAGE_UPLOAD_DIRECTORY + "/img/categories/" + milliseconds + "." + extension;
		Path fileNameAndPath = Paths.get(filePath);
		Files.write(fileNameAndPath,file.getBytes());
		String imageLink = "/img/categories/" + milliseconds + "." + extension;
		Date date = new Date();
		categoryName = Arrays.stream(categoryName.split(" "))
				.map(word -> word.toLowerCase())
				.collect(Collectors.joining("_"));
		Category category = new Category(categoryName, imageLink, date, date);
		Category savedCategory = categoryService.saveCategory(category);
		Thread.sleep(500);
		response.sendRedirect("/admin/categories");
		return new ResponseEntity<>(savedCategory, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping("/admin/category")
	public ResponseEntity<Category> processDeleteCategory(@RequestParam Map<String, String> body, HttpServletResponse response) throws Exception {
		String categoryName = body.get("categoryName");
		System.out.println(categoryName);
		Optional<Category> findCategory = categoryService.findCategoryByName(categoryName);
		if (findCategory.isPresent()) {
			Category existingCategory = findCategory.get();
			String filePath = IMAGE_UPLOAD_DIRECTORY + existingCategory.getImageLink();
			Path fileNameAndPath = Paths.get(filePath);
			Files.deleteIfExists(fileNameAndPath);
			categoryService.deleteCategory(existingCategory);
		}
		response.sendRedirect("/admin/categories");
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping("admin/category")
	public ResponseEntity<Category> procoessPutCategory(@RequestParam("image") MultipartFile file
			,@RequestParam Map<String, String> body, HttpServletResponse response) throws Exception {
		String categoryName = (String)body.get("categoryName");
		String originalFilename = file.getOriginalFilename();
	    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Get extension of file
		String milliseconds = String.valueOf(new Date().getTime()); // Get milliseconds for unique image name
		String imageLink = "/img/categories/" + milliseconds + "." + extension;
		String filePath = IMAGE_UPLOAD_DIRECTORY + "/img/categories/" + milliseconds + "." + extension;
		Path fileNameAndPath = Paths.get(filePath);
		Files.write(fileNameAndPath,file.getBytes());
		Date date = new Date();
		categoryName = Arrays.stream(categoryName.split(" "))
				.map(word -> word.toLowerCase())
				.collect(Collectors.joining("_"));
		Category category = new Category(categoryName, imageLink, date, date);
		Category savedCategory = categoryService.saveCategory(category);
		Thread.sleep(500);
		response.sendRedirect("/admin/categories");
		return new ResponseEntity<>(savedCategory, HttpStatus.OK);
	}
}
