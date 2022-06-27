package nivell1;

public class Decoracio extends Producte {

	
	private String material;

	public Decoracio(String nom, String tipus, double preu, String material) {
		super(nom, tipus, preu);
		this.material = material;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String toString() {
		return nom+";"+tipus+";"+preu+";"+material;
	}
}
