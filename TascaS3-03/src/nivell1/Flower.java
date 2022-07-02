package nivell1;

public class Flower extends Product {

	//private static int stock;
	private String color;
	//private int id;
	
	public Flower(String name, String type, float price, String color) {
		
		super(name, type, price);
		this.color=color;
		//id=Product.getId();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toStringFormat() {
		return name+";"+type+";"+price+";"+color;
	}

	@Override
	public String toString() {
		return "[nom=" + name + ", tipus=" + type + ", color=" + color + ", preu=" + price + "]";
	}
}
