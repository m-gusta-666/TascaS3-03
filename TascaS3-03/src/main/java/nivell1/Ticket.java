package nivell1;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private ArrayList<Product> salesList;
	private ObjectId id;
	
	public Ticket() {
		salesList = new ArrayList<Product>();
	}
	
	public void addProduct(Product product) {
		salesList.add(product);
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id=id;
	}

	public ArrayList<Product> getSales() {
		return salesList;
	}
	
	public float getTicketAmount() {
		
		return totalTicketPrice(salesList);
	}

	public void showProducts() {
		salesList.forEach(System.out::println);
		System.out.println("Preu final " + totalTicketPrice(salesList) + " euros.");
	}
	
    public static float totalTicketPrice(List<Product> list){
    	
        return (float) list.stream().mapToDouble(Product::getPrice).sum();
    }
	
	public String toString() {
		return "Venda " + id + ".\n";
	}
}
