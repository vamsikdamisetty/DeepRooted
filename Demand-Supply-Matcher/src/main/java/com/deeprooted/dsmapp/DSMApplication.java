package main.java.com.deeprooted.dsmapp;

import main.java.com.deeprooted.beans.Trade;
import main.java.com.deeprooted.service.DSMService;
import main.java.com.deeprooted.service.DSMServiceImpl;

public class DSMApplication {
	
	//Inserting/Processing the orders are served in O(n) n-> orders in any of the ledger
	public static void main(String[] args) {
		DSMService dsmService = new DSMServiceImpl();
		
//		dsmService.insertOrder("s1 09:45 tomato 24/kg 100kg");
//		dsmService.insertOrder("s2 09:46 tomato 20/kg 90kg");
//		dsmService.insertOrder("d1 09:47 tomato 22/kg 110kg");
//		dsmService.insertOrder("d2 09:48 tomato 21/kg 10kg");
//		dsmService.insertOrder("d3 09:49 tomato 21/kg 40kg");
//		dsmService.insertOrder("s3 09:50 tomato 19/kg 50kg");

		dsmService.insertOrder("d1 09:47 tomato 110/kg 1kg");
		dsmService.insertOrder("d2 09:45 potato 110/kg 10kg");
		dsmService.insertOrder("d3 09:48 tomato 110/kg 10kg");
		dsmService.insertOrder("s1 09:45 potato 110/kg 1kg");
		dsmService.insertOrder("s2 09:45 potato 110/kg 7kg");
		dsmService.insertOrder("s3 09:45 potato 110/kg 2kg");
		dsmService.insertOrder("s4 09:45 tomato 110/kg 11kg");

		System.out.println("Trades Generated---------------------------------");
		dsmService.getTrades().forEach(trade -> System.out.println(trade));
		
		if(Trade.profit == 0) {
			System.out.println("\n\nHey there! No profits this time.. Lets work on it");
		}else {
			System.out.println("\n\nKudos for Profit earned : " + Trade.profit);
		}

	} 
	
}
