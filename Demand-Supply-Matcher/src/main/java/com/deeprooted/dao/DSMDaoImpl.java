package main.java.com.deeprooted.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.deeprooted.beans.Order;
import main.java.com.deeprooted.beans.Trade;

public class DSMDaoImpl implements DSMDao{

	private HashMap<String, List<Order>> supplyLedger;
	private HashMap<String, List<Order>> demandLedger;
	private List<Trade> tradeStorage;
	private static DSMDaoImpl dsmDaoImpl;
	
	private DSMDaoImpl() {
		supplyLedger = new HashMap<>();
		demandLedger = new HashMap<>();
		tradeStorage = new ArrayList<>();
	}
	
	public static DSMDaoImpl getDSMDaoInstance() {
		if(dsmDaoImpl == null) {
			dsmDaoImpl = new DSMDaoImpl();
		}
		return dsmDaoImpl;
	}
	
	@Override
	public void insertSupply(Order order) {
		supplyLedger.computeIfAbsent(order.getProduce(), produce-> new ArrayList<>());
		
		List<Order> supplyList = getSupplyList(order.getProduce());
		int indexToInsert = supplyList.size();
		for (int i = 0; i < supplyList.size(); i++) {
			Order supply = supplyList.get(i);
			if(order.getPrice() < supply.getPrice()) {
				indexToInsert = i;
				break;
			}else if(order.getPrice() == supply.getPrice()) {
				if(order.getTime().compareTo(supply.getTime()) >= 0) {
					continue;
				}
				indexToInsert = i;
				break;
			}
		}
		supplyList.add(indexToInsert,order);
	}

	@Override
	public void insertDemand(Order order) {
		demandLedger.computeIfAbsent(order.getProduce(), produce-> new ArrayList<>());
		
		List<Order> demandList = getDemandList(order.getProduce());
		int indexToInsert = demandList.size();
		for (int i = 0; i < demandList.size(); i++) {
			Order demand = demandList.get(i);
			if(order.getPrice() > demand.getPrice()) {
				indexToInsert = i;
				break;
			}else if(order.getPrice() == demand.getPrice()) {
				if(order.getTime().compareTo(demand.getTime()) >= 0) {
					continue;
				}
				indexToInsert = i;
				break;
			}
		}
		demandList.add(indexToInsert,order);
	}

	@Override
	public List<Order> getSupplyList(String produce) {
		return supplyLedger.get(produce);
	}

	@Override
	public List<Order> getDemandList(String produce) {
		return demandLedger.get(produce);
	}

	@Override
	public void insertTrade(Trade trade) {
		tradeStorage.add(trade);
	}

	@Override
	public List<Trade> getTrades() {
		return tradeStorage;
	}

}
