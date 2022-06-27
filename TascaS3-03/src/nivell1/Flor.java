package nivell1;


public class Flor extends Producte {

	
	private String color;

	public Flor(String nom, String tipus, double preu, String color) {
		super(nom, tipus, preu);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString() {
		return nom+";"+tipus+";"+preu+";"+color;
	}
	
	
	
	
	
	
}
