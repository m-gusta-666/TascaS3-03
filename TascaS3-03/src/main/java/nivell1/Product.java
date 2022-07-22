package nivell1;

import org.bson.types.ObjectId;

public class Product {

	protected ObjectId _id;
	protected String name;
	protected String type;
	protected float price;
	//protected static int id=0;
	
	public Product (ObjectId id, String name, String type, float price) {
		this._id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		System.out.println(this._id);
		//id++;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {


		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		System.out.println(name);
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

	@Override
	public String toString() {
		return  this._id + "; " + this.name + "; " + this.type + "; " + price + "; ";
	}

	/*public static int getId() {
		return id;
	}*/
}
