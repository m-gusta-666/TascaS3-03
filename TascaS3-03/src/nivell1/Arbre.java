package nivell1;

public class Arbre extends Producte {

	
	private String altura;

	public Arbre(String nom, String tipus, double preu, String altura) {
		super(nom, tipus, preu);
		this.altura = altura;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}
	
	public String toString() {
		return nom+";"+tipus+";"+preu+";"+altura;
	}
	
}
