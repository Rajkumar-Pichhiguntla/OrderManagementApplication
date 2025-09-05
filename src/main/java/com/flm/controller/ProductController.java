package com.flm.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.flm.dto.ProductRequestDto;
import com.flm.dto.ProductResponseDto;
import com.flm.service.ProductService;
import com.flm.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	public final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping("/saveProduct")
	public ProductResponseDto saveProduct(@RequestBody ProductRequestDto productRequestDto) {
		System.out.println(productRequestDto);
		ProductResponseDto productResponseDto = productService.save(productRequestDto);
		return productResponseDto;
	}
	
	@PostMapping("/saveAll")
	public List<ProductResponseDto> saveAll(@RequestBody List<ProductRequestDto> productRequestDtos){
		List<ProductResponseDto> productsList = productService.saveAll(productRequestDtos);
		return productsList;
		
	}
	
	@GetMapping("/getProducts")
	public List<ProductResponseDto> getAllProducts(){
		List<ProductResponseDto> allProducts = productService.getAllProducts();
		return allProducts;
		
	}
	
	@GetMapping("/getById/{id}")
	public ProductResponseDto getByProductId(@PathVariable(name="id") long id) {
		ProductResponseDto productResponseDto = productService.getProduct(id);
		return productResponseDto;
		
	}
	@GetMapping("/name")
	public List<ProductResponseDto> getProductsByName(@RequestParam(name="productName") String productName){
		List<ProductResponseDto> products = productService.getProductsByName(productName);
		return products;
	}
	
	@PutMapping("/updateRating")
	public ProductResponseDto updateProductRating(@RequestParam(name="productId") long productId,@RequestParam(name="rating") double rating) {
		ProductResponseDto productResponseDto = productService.updateProductRating(productId, rating);
		return productResponseDto;
		
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name="id") long id) {
		productService.deleteProduct(id);
		return "Deleted";
		
	}
}
