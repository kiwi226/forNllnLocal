package model;

public class Balance {
	private Purchase purchase;
	private Sales	 sales;
	public Purchase getPurchase() {
		return purchase;
	}
	public Sales getSales() {
		return sales;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
}
