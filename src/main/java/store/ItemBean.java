/* ItemBean.java - this class is used to represent items which are added to the basket. The getPrice() method returns the price of one unit of an item, while the getTotalPrice() method returns the price of all units of an item. */

package store;

public class ItemBean {
	private double price=0.0;
	private int quantity=0;
	private String title="";
	private String publisher="";
	private int productid=0;
	
	public ItemBean() {
	}

	// Constructor (used when adding an item to the shopping basket).
	public ItemBean(String title, String publisher, double price, int productid, int quantity) {
		this.title=title;
		this.publisher=publisher;
		this.price=price;
		this.quantity=quantity;
		this.productid=productid;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title;
	}
	public void setPublisher(String publisher) {
		this.publisher=publisher;
	}
	public String getPublisher() {
		return this.publisher;
	}
	public void setPrice(double price) {
		this.price=price;
	}
	public double getPrice() {
		return this.price;
	}
	public double getTotalPrice() {
		return this.price*this.quantity;
	}
	public void increaseQuantity(int change) {
		quantity=quantity+change;
	}
	public void decreaseQuantity(int change) {
		quantity=quantity-change;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	public int getProductid() {
		return this.productid;
	}
	public void setProductid(int productid) {
		this.productid=productid;
	}
}
