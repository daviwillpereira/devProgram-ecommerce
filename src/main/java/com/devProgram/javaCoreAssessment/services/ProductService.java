package com.devProgram.javaCoreAssessment.services;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.devProgram.javaCoreAssessment.entities.Product;
import com.devProgram.javaCoreAssessment.enums.CategoryEnum;
import com.devProgram.javaCoreAssessment.repositories.ProductRepository;

@Service
public class ProductService {

	private Boolean system = true;
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void initial(Scanner scanner) {
		while (system) {
			System.out.println("\nWhat action of product would you like to do?");
			System.out.println("0 - Main Menu");
			System.out.println("1 - Save");
			System.out.println("2 - List");
			System.out.println("3 - Search");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				save(scanner);
				break;
			case 2:
				list(scanner);
				break;
			case 3:
				search(scanner);
				break;
			default:
				system = false;
				break;
			}

		}

	}

	private void save(Scanner scanner) {
		System.out.println("Enter the product name");
		String name = scanner.next();
		
		System.out.println("Enter the product price");
        BigDecimal price = scanner.nextBigDecimal();

        System.out.println("Enter the product description");
        String description = scanner.next();

        System.out.println("Enter the product quantity");
        Integer quantity = scanner.nextInt();

        System.out.println("Enter the product category");
        String category = scanner.next();
        
      
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setDescription(description);
		product.setQuantity(quantity);
		product.setCategory(category.equals(CategoryEnum.MONITORS.name()) ? CategoryEnum.MONITORS : category.equals(CategoryEnum.COMPUTER_ACCESSORIES.name()) ? CategoryEnum.COMPUTER_ACCESSORIES : null);
        
        
		productRepository.save(product);
		System.out.println("Saved");
	}
	
	private void list(Scanner scanner) {
		System.out.println("\nProducts saved:");
		productRepository.findAll().forEach(product -> System.out.println("\n- " + product.getName()));
		
	}


	private void search(Scanner scanner) {
		System.out.println("\nEnter the name of the product:");
		String name = scanner.next();
		
		System.out.println("\nResult:");
		productRepository.searchPriceAndDescriptionByName(name).forEach(product -> System.out.println("\nProduct: "+ product.getName() + "\nPrice: " + product.getPrice() + "\nDescription: " + product.getDescription()));
	}
}