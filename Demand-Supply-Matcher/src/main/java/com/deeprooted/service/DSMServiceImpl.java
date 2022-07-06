package main.java.com.deeprooted.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.deeprooted.beans.Order;
import main.java.com.deeprooted.beans.Trade;
import main.java.com.deeprooted.dao.DSMDao;
import main.java.com.deeprooted.dao.DSMDaoImpl;

public class DSMServiceImpl implements DSMService{

	private final DSMDao dsmDao;
		
	public DSMServiceImpl() {
		dsmDao = DSMDaoImpl.getDSMDaoInstance();
	}
	
	/*
	 * To process the incoming order and if required Insert it into ledger 
	 */
	@Override
	public void insertOrder(String orderInput) {
		Order order = OrderMapper.convert(orderInput);
		if(isSupplyOrder(order.getOrderId())) {
			processSupplyOrder(order);
		}else if(isDemandOrder(order.getOrderId())){
			processDemandOrder(order);
		}else {
			System.err.println("Please Enter a valid Order");
		}

	}

	/*
	 * Fetches list of supplies in ledger and tries to make a trades
	 * Even after trying to make trade if Demand quantity is left to be served then Remaining Demand Order will be inserted to ledger 
	 */
	private void processDemandOrder(Order demandOrder) {
		List<Order> supplyList = dsmDao.getSupplyList(demandOrder.getProduce());
		if(supplyList != null) {
			List<Order> supplyToRemove = new ArrayList<>();

			for (Order supplyOrder : supplyList) {
				if(demandOrder.getPrice() < supplyOrder.getPrice()) {
					break;
				}
				
				if(demandOrder.getQuantity() > supplyOrder.getQuantity()) {
					demandOrder.setQuantity(demandOrder.getQuantity() - supplyOrder.getQuantity());
					supplyToRemove.add(supplyOrder);
					dsmDao.insertTrade(new Trade(supplyOrder, demandOrder, supplyOrder.getQuantity()));
					
				}else {
					if(demandOrder.getQuantity() == supplyOrder.getQuantity()) {
						supplyToRemove.add(supplyOrder);
					}else {
						supplyOrder.setQuantity(supplyOrder.getQuantity() - demandOrder.getQuantity());
					}
					dsmDao.insertTrade(new Trade(supplyOrder, demandOrder, demandOrder.getQuantity()));
					demandOrder.setQuantity(0);
					break;
				}
				
			}
			//Removing completed supplies from the ledger
			supplyList.removeAll(supplyToRemove);
		}
		if(demandOrder.getQuantity() > 0) {
			dsmDao.insertDemand(demandOrder);
		}

		
	}

	/*
	 * Fetches list of Demands in ledger and tries to make a trades
	 * Even after trying to make trade if Supply quantity is left then Remaining Supply Order will be inserted to ledger 
	 */
	private void processSupplyOrder(Order supplyOrder) {
		List<Order> demandList = dsmDao.getDemandList(supplyOrder.getProduce());
		if(demandList != null) {
			List<Order> demandsToRemove = new ArrayList<>();
			for (Order demandOrder : demandList) {
				if(supplyOrder.getPrice() > demandOrder.getPrice()) {
					break;
				}
				
				if(supplyOrder.getQuantity() > demandOrder.getQuantity()) {
					supplyOrder.setQuantity(supplyOrder.getQuantity() - demandOrder.getQuantity());
					demandsToRemove.add(demandOrder);
					dsmDao.insertTrade(new Trade(supplyOrder, demandOrder, demandOrder.getQuantity()));
				}else {
					if(supplyOrder.getQuantity() == demandOrder.getQuantity()) {
						demandsToRemove.add(demandOrder);
					}else {
						demandOrder.setQuantity(demandOrder.getQuantity() - supplyOrder.getQuantity());
					}
					dsmDao.insertTrade(new Trade(supplyOrder, demandOrder, supplyOrder.getQuantity()));
					supplyOrder.setQuantity(0);
					break;
				}

			}
			
			//Removing served demands from the ledger
			demandList.removeAll(demandsToRemove);
		}
		if(supplyOrder.getQuantity() > 0) {
			dsmDao.insertSupply(supplyOrder);
		}
		
	}

	
	/*
	 * Verifies if current Order is Supply order
	 */
	private boolean isSupplyOrder(String orderId) {
		return orderId.toLowerCase().startsWith("s");
	}
	
	/*
	 * Verifies if current Order is Demand order
	 */
	private boolean isDemandOrder(String orderId) {
		return orderId.toLowerCase().startsWith("d");
	}
	
	/*
	 * Fetches List of trades happened till now
	 */
	@Override
	public List<Trade> getTrades() {
		return dsmDao.getTrades();
	}
	
}
