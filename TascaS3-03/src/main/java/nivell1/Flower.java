package nivell1;

import org.bson.types.ObjectId;

public class Flower extends Product {

	//private static int stock;
	private String color;
	//private int id;
	
	public Flower(ObjectId id, String name, String type, float price, String color) {
		super(id, name, type, price);
		this.color = color;
		//id=Product.getId();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCharacteristic(){
		return String.format("%-10s %-15s","Color:", this.color);
	}

	@Override
	public String toString() {
		return String.format("%s",this._id);

		//return "[cod= "+ this._id + ",nom=" + this.name + ", tipus=" + type + ", color=" + color + ", preu=" + price + "]";
	}
}
