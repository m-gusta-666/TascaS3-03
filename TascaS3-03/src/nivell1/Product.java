package nivell1;

public class Product {
	
	protected String name;
	protected String type;
	protected float price;
	//protected static int id=0;
	
	public Product (String name, String type, float price) {
		
		this.name=name;
		this.type=type;
		this.price=price;
		//id++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public String toStringFormat() {
		return name+";"+type+";"+price+";";
	}

	/*public static int getId() {
		return id;
	}*/
}
