package main.java.com.deeprooted.beans;

public class Trade {

	private final Order supply;
	private final Order demand;
	private int quantity;
	public static int profit;
	
	public Trade(Order supply, Order demand, int quantity) {
		super();
		this.supply = supply;
		this.demand = demand;
		this.quantity = quantity;
		//Calculating the profit from all the trades
		profit += (demand.getPrice() - supply.getPrice())*quantity ;
	}
	public Order getSupply() {
		return supply;
	}

	public Order getDemand() {
		return demand;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		
		return demand.getOrderId() + " " + supply.getOrderId() + " " + supply.getPrice() +
				"/kg " + quantity + "kg";
	}
}
