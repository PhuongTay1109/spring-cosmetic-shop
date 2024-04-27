package com.cosmetics.myshop.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmetics.myshop.dto.OrderDataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class CheckoutController {
	@GetMapping("/checkout")
	public String checkout() {
		return "/user/checkout";
	}
	
	@GetMapping("/order")
    public void order(@RequestParam(name = "cost") String cost,
                      @RequestParam(name = "data") String data) {
        try {
        	byte[] decodedBytes = Base64.getDecoder().decode(data);
        	data = new String(decodedBytes, StandardCharsets.UTF_8);
        	
    		ObjectMapper objectMapper = new ObjectMapper();
            OrderDataDTO dataDTO = objectMapper.readValue(data, OrderDataDTO.class);
            
            System.out.println(dataDTO);

            // Lấy các giá trị từ đối tượng DTO
            String name = dataDTO.getName();
            String phone = dataDTO.getPhone();

            System.out.println("NAME: " + name);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
