package nivell1;

import org.bson.types.ObjectId;

public class Decor extends Product {

	String material;
	
	public Decor(ObjectId id, String name, String type, float price, String material) {
		
		super(id,name, type, price);
		this.material=material;
	}
	
	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}

	
	@Override
	public String toString() {
		return "[cod= "+ this._id + ",nom=" + this.name + ", tipus=" + type + ", material=" + material + ", preu=" + price + "]";
	}
}
