/* BasketBean.java - this class is used to create the shopping basket object. It uses an ArrayList to hold the ItemBean objects (items). */

package store;
import java.util.ArrayList;

public class BasketBean {  
	private double totalPrice=0;
	private ItemBean item=new ItemBean();
	private ArrayList <ItemBean> itemsInBasket=new ArrayList<ItemBean>();
	boolean containsItem=false;

	// Add an item to the basket.
	public void setItemBean(ItemBean item) {
		for (int i=0; i<itemsInBasket.size(); i++) {
			// First, find out if the item has already been added to the basket.
			ItemBean alreadyAddedItem=getItemBean(i);
			// If it has, update the quantity of the item.
			if (alreadyAddedItem.getProductid()==item.getProductid()) {
				alreadyAddedItem.increaseQuantity(item.getQuantity());	
				// (This increases quantity by the number of identical items you're adding).
				containsItem=true;
			}
		}
		if (!containsItem) {
			// If it hasn't, add the item to the basket and reset the boolean for next time.
			itemsInBasket.add(item);
			containsItem=false;
		}
	}

	// Retrieve an item from the specified position in the basket.
	public ItemBean getItemBean(int position) {
		return (ItemBean)itemsInBasket.get(position);
	}

	// Retrieve the number of different product types in the basket.
	public int getSize() {
		return itemsInBasket.size();
	}

	// Remove an item type.
	public void removeItemBean(int position) {
		item = getItemBean(position);
		totalPrice=totalPrice-item.getTotalPrice();
		itemsInBasket.remove(position);
	}

	// Retrieve the total price.
	public double getTotalPrice() {
		totalPrice=0;
		for (int x=0; x<itemsInBasket.size(); x++) {
			item=(ItemBean)itemsInBasket.get(x);
			totalPrice=totalPrice+item.getTotalPrice();
		}
		return totalPrice;
	}
}
