package nivell1;

import org.bson.types.ObjectId;

public class Tree extends Product {

	private String height;
	
	public Tree(ObjectId id,String name, String type, float price, String height) {
		super(id, name, type, price);
		this.height=height;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "[cod= "+ this._id + ",nom=" + this.name + ", tipus=" + this.type + ", alçada=" + this.height+", preu=" + price + "]";
	}
}
