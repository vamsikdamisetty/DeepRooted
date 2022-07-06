package main.java.com.deeprooted.service;

import java.util.List;

import main.java.com.deeprooted.beans.Trade;

public interface DSMService {

	/*
	 * To process the incoming order and if required Insert it into ledger 
	 */
	void insertOrder(String orderInput);
	
	/*
	 * Fetch all the trades happened till now
	 */
	List<Trade> getTrades();
}
