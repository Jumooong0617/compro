package com.smoshi.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

		public class Coffee {
			private int id;
			private String name;
			private String type;
			private String size;
			private double price;
			private String roastLevel;
			private String origin;
			private boolean isDecaf;
			private int stock;
			private List<String> flavorNotes;
			private String brewMethod;

		public Coffee(int id, String name, String type, String size, double price, String roastLevel, String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod) {
			this.id = id;
			this.name = name;
			this.type = type;
			this.size = size;
			this.price = price;
			this.roastLevel = roastLevel;
			this.origin = origin;
			this.isDecaf = isDecaf;
			this.stock = stock;
			this.flavorNotes = flavorNotes;
			this.brewMethod = brewMethod;
		}

		public int getId() { return id; }
		public String getName() { return name; }
		public String getType() { return type; }
		public String getSize() { return size; }
		public double getPrice() { return price; }
		public String getRoastLevel() { return roastLevel; }
		public String getOrigin() { return origin; }
		public boolean isDecaf() { return isDecaf; }
		public int getStock() { return stock; }
		public List<String> getFlavorNotes() { return flavorNotes; }
		public String getBrewMethod() { return brewMethod; }
	}


	@Controller
	public class HomeController {
		private List<Coffee> coffeeList = new ArrayList<>();

		public HomeController() {
			coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10, Arrays.asList("Chocolate", "Nutty"), "Espresso"));
			coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8, Arrays.asList("Creamy", "Sweet"), "Drip"));
			coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 5.00, "Medium", "Colombia", false, 12, Arrays.asList("Fruity", "Bold"), "French Press"));
			coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 4.75, "Dark", "Guatemala", false, 6, Arrays.asList("Chocolate", "Smooth"), "Espresso"));
			coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 3.25, "Light", "Kenya", false, 15, Arrays.asList("Citrus", "Balanced"), "Drip"));
		}

		@GetMapping("/")
		public String getCoffees(Model model) {
			model.addAttribute("coffees", coffeeList);
			return "index";
		}

		@GetMapping("/delete")
		public String deleteCoffee(@RequestParam int id) {
			coffeeList.removeIf(coffee -> coffee.getId() == id);
			return "redirect:/";
		}
	}
}
