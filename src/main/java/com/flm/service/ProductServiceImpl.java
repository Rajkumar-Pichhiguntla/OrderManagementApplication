package com.flm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.flm.dao.ProductRepository;
import com.flm.dto.ProductRequestDto;
import com.flm.dto.ProductResponseDto;
import com.flm.model.Product;

@Service
public class ProductServiceImpl implements ProductService{
	
	public final ProductRepository productRepository; //we use constructor injection to get type safety.
	
	
	//while creating object for productServiceImpl..it created object for repository.
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}

	@Override
	public ProductResponseDto save(ProductRequestDto productRequestDto) {
		Product product=new Product();
		product.setProductName(productRequestDto.getProductName());
		product.setPrice(productRequestDto.getPrice());
		product.setDiscount(productRequestDto.getDiscount());
		product.setStock(productRequestDto.getStock());
		if(productRequestDto.getStock()>0) {
			product.setAvailable(true);
		}
		System.out.println(product);
		Product savedProduct = productRepository.save(product);
		ProductResponseDto productResponseDto=new ProductResponseDto();
		BeanUtils.copyProperties(savedProduct, productResponseDto);
		
		return productResponseDto;
	}

	@Override
	public List<ProductResponseDto> getAllProducts() {
		List<Product> allProducts = productRepository.findAll();
		List<ProductResponseDto> productResponseDtoList = buildProductsResponseDto(allProducts);
		return productResponseDtoList;
	}

	@Override
	public ProductResponseDto getProduct(long id) {
		Product product = productRepository.findByProductId(id);
		ProductResponseDto productResponseDto=new ProductResponseDto();
		BeanUtils.copyProperties(product, productResponseDto);
		return productResponseDto;
	}

	@Override
	public List<ProductResponseDto> getProductsByName(String name) {
		List<Product> products = productRepository.findByProductNameContaining(name);
		List<ProductResponseDto> productResponseDtoList = buildProductsResponseDto(products);
		return productResponseDtoList;
	}


	@Override
	public ProductResponseDto updateProductRating(long productId, double rating) {
		Product product = productRepository.findByProductId(productId);
		product.setRating(rating);
		productRepository.save(product);
		ProductResponseDto productResponseDto=new ProductResponseDto();
		BeanUtils.copyProperties(product, productResponseDto);
		return productResponseDto;
	}

	@Override
	public List<ProductResponseDto> saveAll(List<ProductRequestDto> productRequestDtos) {
		List<Product> products = buildProductList(productRequestDtos);
		List<Product> productsList = productRepository.saveAll(products);
		List<ProductResponseDto> productsResponseDto = buildProductsResponseDto(productsList);
		return productsResponseDto;
	}
	
	@Override
	public void deleteProduct(long id) {
		productRepository.deleteById(id);
		
	}

	private List<Product> buildProductList(List<ProductRequestDto> productRequestDtos) {
		List<Product> products=new ArrayList<Product>();
		for(ProductRequestDto productRequestDto:productRequestDtos) {
			Product product=new Product();
			BeanUtils.copyProperties(productRequestDto, product);
			product.setAvailable(true);
			products.add(product);
		}
		return products;
	}

	private List<ProductResponseDto> buildProductsResponseDto(List<Product> allProducts) {
		List<ProductResponseDto> productResponseDtoList=new ArrayList<ProductResponseDto>();
		for(Product product:allProducts) {
			ProductResponseDto productResponseDto=new ProductResponseDto();
			BeanUtils.copyProperties(product, productResponseDto);
			productResponseDtoList.add(productResponseDto);
		}
		return productResponseDtoList;
	}

	
}
