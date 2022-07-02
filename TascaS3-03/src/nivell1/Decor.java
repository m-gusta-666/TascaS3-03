package nivell1;

public class Decor extends Product {

	String material;
	
	public Decor(String name, String type, float price, String material) {
		
		super(name, type, price);
		this.material=material;
	}
	
	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toStringFormat() {
		return name+";"+type+";"+price+";"+material;
	}
	
	@Override
	public String toString() {
		return "[nom=" + name + ", tipus=" + type + ", material=" + material + ", preu=" + price + "]";
	}
}
