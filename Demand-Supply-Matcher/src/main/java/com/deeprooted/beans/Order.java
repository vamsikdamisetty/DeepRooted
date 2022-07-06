package main.java.com.deeprooted.beans;

public class Order {

	private String orderId;
	private String time;
	private String produce;
	private int price;
	private int quantity;
	
	public Order(String orderId, String time, String produce, int price, int quantity) {
		super();
		this.orderId = orderId;
		this.time = time;
		this.produce = produce;
		this.price = price;
		this.quantity = quantity;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		
		return orderId + " " + time + " " + produce + " " + price + "/kg " + quantity + "kg";
	}
	
}
