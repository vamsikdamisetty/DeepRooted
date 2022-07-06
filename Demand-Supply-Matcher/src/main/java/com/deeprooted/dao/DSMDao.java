package main.java.com.deeprooted.dao;

import java.util.List;

import main.java.com.deeprooted.beans.Order;
import main.java.com.deeprooted.beans.Trade;

public interface DSMDao {
	
	/*
	 * To insert supply in the correct position based on price and time
	 */
	void insertSupply(Order order);
	
	/*
	 * To insert demand in the correct position based on price and time
	 */
	void insertDemand(Order order);
	
	/*
	 * Fetch list of supplies in ledger for a particular produce like tomato,potato
	 */
	List<Order> getSupplyList(String produce);
	
	/*
	 * Fetch list of demands in ledger for a particular produce like tomato,potato
	 */
	List<Order> getDemandList(String produce);
	
	/*
	 * Insert the details of Trade happened 
	 */
	void insertTrade(Trade trade);
	
	/*
	 * Fetch all the trades happened till now
	 */
	List<Trade> getTrades();
}
