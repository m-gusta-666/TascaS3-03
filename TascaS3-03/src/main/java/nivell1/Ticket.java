package nivell1;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private ArrayList<Product> salesList;
	private int id;
	
	public Ticket(int id) {
		salesList=new ArrayList<Product>();
		this.id=id;
	}
	
	public void addProduct(Product product) {
		salesList.add(product);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public ArrayList<Product> getSales() {
		return salesList;
	}
	
	public float getTicketPrice() {
		
		return totalTicketPrice(salesList);
	}

	public void showProducts() {
		salesList.forEach(System.out::println);
		System.out.println("Preu final "+totalTicketPrice(salesList)+" euros.");
	}
	
    public static float totalTicketPrice(List<Product> list){
    	
        return (float) list.stream().mapToDouble(Product::getPrice).sum();
    }
	
	public String toString() {
		return "Venda "+id+ ".\n";
	}
}
