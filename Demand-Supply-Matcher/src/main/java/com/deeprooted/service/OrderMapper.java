package main.java.com.deeprooted.service;

import main.java.com.deeprooted.beans.Order;

public class OrderMapper {

	public static Order convert(String orderInput) {
		
		String[] split = orderInput.split(" ");
		Order order = new Order(split[0], split[1], split[2], Integer.parseInt(split[3].substring(0,split[3].length() - 3)), 
				Integer.parseInt(split[4].substring(0,split[4].length() - 2)));
		return order;
	}
}
