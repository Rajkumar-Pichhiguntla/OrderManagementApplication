package com.flm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flm.dto.ProductRequestDto;
import com.flm.dto.ProductResponseDto;

@Service
public interface ProductService {
	
	public ProductResponseDto save(ProductRequestDto productRequestDto);
	
	public List<ProductResponseDto> saveAll(List<ProductRequestDto> productRequestDtos);
	
	public List<ProductResponseDto> getAllProducts();
	
	public ProductResponseDto getProduct(long id);
	
	public List<ProductResponseDto> getProductsByName(String name);
	
	public ProductResponseDto updateProductRating(long id,double rating);
	
	public void deleteProduct(long id);
	
}
