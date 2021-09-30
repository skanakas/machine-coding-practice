package com.klarna.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * 
    

 * @author arun
 *
 */

class RequestedMenu {
	private String itemName;
	private final Set<String> excludes = new HashSet<>();
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Set<String> getExcludes() {
		return excludes;
	}
	public void addExcludes(String exclude) {
		this.excludes.add(exclude);
	}
}

/**
 * Classic: strawberry, banana, pineapple, mango, peach, honey
    Freezie: blackberry, blueberry, black currant, grape juice, frozen yogurt
    Greenie: green apple, lime, avocado, spinach, ice, apple juice
    Just Desserts: banana, ice cream, chocolate, peanut, cherry
 * @author arun
 *
 */

class Menu{
	private static final HashMap<String, TreeSet<String>> menuMap = new HashMap<>();
	static {
		menuMap.put("Classic", constructIngredients(Arrays.asList("strawberry", "banana", "pineapple", "mango", "peach", "honey")));
		menuMap.put("Freezie", constructIngredients(Arrays.asList("blackberry", "blueberry", "black currant", "grape juice", "frozen yogurt")));
		menuMap.put("Greenie", constructIngredients(Arrays.asList("green apple", "lime", "avocado", "spinach", "ice", "apple juice")));
		menuMap.put("Just Desserts", constructIngredients(Arrays.asList("banana", "ice cream", "chocolate", "peanut", "cherry")));
	}
	
	private static TreeSet<String> constructIngredients(List<String> list) {
		TreeSet<String> ingredients = new TreeSet<>(Comparator.naturalOrder());
		for(String s : list) {
			ingredients.add(s);
		}
		
		return ingredients;
	}
	
	public static Set<String> getMenuItems(){
		return menuMap.keySet();
	}
	
	public static Set<String> getIngredients(String menuItem){
		if(menuItem==null|| menuItem.isEmpty())
			return null;
		return menuMap.get(menuItem);
	}
	
}

class RequestDeserializer {

	private static final String EXCLUDE_PREFIX = "-";
	private RequestDeserializer() {}
	private static RequestDeserializer instance = null;
	public static RequestDeserializer getInstance() {

		if(instance == null) {
			synchronized (RequestDeserializer.class) {
				if(instance == null) {
					instance = new RequestDeserializer(); 
				}

			}
		}
		return instance;
	}

	public RequestedMenu constructRequest(String order) throws IllegalArgumentException {

		if(order==null || order.isEmpty())
			throw new IllegalArgumentException("invalid input : 400");

		RequestedMenu requestedMenu = new RequestedMenu();

		final Set<String> menuKeys = Menu.getMenuItems();
		
		String[] inputs = order.split(",");
		String menuItem = "";
		for(int i = 0; i<inputs.length; i++) {
			
			if(i==0) {
				menuItem = inputs[i];
				if(!menuItem.chars().allMatch(Character::isLetter) || !menuKeys.contains(menuItem))
					throw new IllegalArgumentException("invalid ingredient : 400");
				requestedMenu.setItemName(menuItem);
			} else {
				String exclude = inputs[i];
				if(!exclude.startsWith(EXCLUDE_PREFIX)) {
					throw new IllegalArgumentException("invalid ingredient : 400");
				}
				
				exclude = exclude.substring(1);
				if(!exclude.chars().allMatch(Character::isLetter)) {
					throw new IllegalArgumentException("invalid ingredient : 400");
				}
				
				final Set<String> ingedients = Menu.getIngredients(menuItem);
				//consider only valid excludes
				if(ingedients.contains(exclude)) {
					requestedMenu.addExcludes(exclude);
				}
			}
		}
		return requestedMenu;
	}
}

class OrderBuilder {
	private static final String EMPTY_ORDER = "";
	private OrderBuilder() {}
	private static OrderBuilder instance = null;
	public static OrderBuilder getInstance() {

		if(instance == null) {
			synchronized (OrderBuilder.class) {
				if(instance == null) {
					instance = new OrderBuilder(); 
				}

			}
		}
		return instance;
	}
	
	public String orderDetails(RequestedMenu menu) {
		List<String> order = this.prepareOrder(menu);
		if(order!= null && !order.isEmpty()) {
			String orderDetailStr = order.toString();
			return orderDetailStr.substring(1, orderDetailStr.length() - 1).replaceAll("\\s", "");
		}
		return EMPTY_ORDER;
	}
	
	public List<String> prepareOrder(RequestedMenu menu) {
		
		List<String> orderDetails = new ArrayList<>();
		Set<String> excludes = menu.getExcludes();
		
		Set<String> ingredients = Menu.getIngredients(menu.getItemName());
		
		for(String ingeredient : ingredients) {
			
			if(excludes==null || excludes.isEmpty()) {
				orderDetails.add(ingeredient);
			} else if(excludes!= null && !excludes.isEmpty() && !excludes.contains(ingeredient)) {
				orderDetails.add(ingeredient);
			}
				
		}
		
		return orderDetails;
	}
	
}

public class Smoothie {
	
	public static void main(String[] args) {
		RequestDeserializer deserializer = RequestDeserializer.getInstance();
		/**
		 * Build request
		 */
		RequestedMenu request = deserializer.constructRequest("Classic,-strawberry");
		
		/**
		 * Prepare Order
		 */
		String orderDetails = OrderBuilder.getInstance().orderDetails(request);
		
		
		System.out.println(orderDetails);
	}
	
	public static String ingredients(String order) {


		return null;
	}
}
