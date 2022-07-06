package main.test.com.deeprooted.test;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.java.com.deeprooted.dao.DSMDao;
import main.java.com.deeprooted.dao.DSMDaoImpl;
import main.java.com.deeprooted.service.DSMService;
import main.java.com.deeprooted.service.DSMServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DSMTest {

	DSMService dsmService = new DSMServiceImpl();
	DSMDao dsmDao = DSMDaoImpl.getDSMDaoInstance();
	
	//Test cases on Insert
	
	/*
	 * TestCase to Insert Supply Orders
	 */
	@Test
	public void testAInsertSupply() {
		dsmService.insertOrder("s1 09:45 tomato 24/kg 100kg");
		
		assertTrue(dsmDao.getSupplyList("tomato").size() > 0);
		assertNull(dsmDao.getSupplyList("potato"));
	}
	
	/*
	 * TestCase to Insert Demand Orders
	 */
	@Test
	public void testBInsertDemand() {
		dsmService.insertOrder("d1 09:46 brinjal 20/kg 90kg");
		
		assertTrue(dsmDao.getDemandList("brinjal").size() > 0);
		assertNull(dsmDao.getDemandList("apple"));
	}

	//Test cases on trade
	
	/*
	 * Test case to verify "within the same supply/demand price, first-in-first out rule"
	 */
	@Test
	public void testCSamePriceFIFO() {
		dsmService.insertOrder("s2 09:46 tomato 24/kg 50kg");
		dsmService.insertOrder("d2 09:47 tomato 24/kg 150kg");
		
		//First Trade was made with s1 FIFO
		assertEquals("d2 s1 24/kg 100kg", dsmService.getTrades().get(0).toString());
		assertEquals("d2 s2 24/kg 50kg", dsmService.getTrades().get(1).toString());
	}
	
	/*
	 * Test case to check Supply with lower price will be traded first
	 */
	@Test
	public void testDLowerSupplyPrice() {
		dsmService.insertOrder("s3 09:50 banana 10/kg 20kg");
		dsmService.insertOrder("s4 09:55 banana 5/kg 20kg");
		dsmService.insertOrder("d3 10:00 banana 24/kg 20kg");
		
		//First Trade was made with s4 due to lower supply price
		assertEquals("d3 s4 5/kg 20kg", dsmService.getTrades().get(2).toString());
	}
	
	/*
	 * Test case to check Demand with higher price will be traded first
	 */
	@Test
	public void testEHigherDemandPrice() {
		
		dsmService.insertOrder("d4 10:10 grapes 50/kg 20kg");
		dsmService.insertOrder("d5 10:15 grapes 30/kg 20kg");
		dsmService.insertOrder("s5 10:20 grapes 20/kg 20kg");
		
		//First Trade was made with d4 due to higher demand price
		assertEquals("d4 s5 20/kg 20kg", dsmService.getTrades().get(3).toString());
	}
}
