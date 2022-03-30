package model;

public class Product {
	//상품번호, 상품명, 판매가, 매입가, 현재 재고량, 상품메모
	private int		product_no;
	private String 	product_name;
	private int		price;
	private int 	cost;
	private int 	stock;
	private String 	product_memo;
	
	public int getProduct_no() {
		return product_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public int getPrice() {
		return price;
	}
	public int getCost() {
		return cost;
	}
	public int getStock() {
		return stock;
	}
	public String getProduct_memo() {
		return product_memo;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setProduct_memo(String product_memo) {
		this.product_memo = product_memo;
	}
	
	
	
	
}
