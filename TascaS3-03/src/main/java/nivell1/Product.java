package nivell1;

import org.bson.types.ObjectId;

public class Product {

	protected ObjectId _id;
	protected String name;
	protected String type;
	protected float price;
	//protected static int id=0;
	//Constructor
	public Product (ObjectId id, String name, String type, float price) {
		this._id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		//id++;
	}

	//Getters
	public ObjectId get_id() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public float getPrice() {
		return price;
	}

	//Setters
	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public void setName(String name) {
		System.out.println(name);
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCharacteristic(){
		return "";
	}


	/*public static int getId() {
		return id;
	}*/
}
