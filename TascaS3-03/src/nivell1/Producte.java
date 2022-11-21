package nivell1;


public class Producte {

	protected String nom;
	protected String tipus;
	protected double preu;
	
	public Producte(String nom, String tipus, double preu) {
		super();
		this.nom = nom;
		this.tipus = tipus;
		this.preu = preu;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public double getPreu() {
		return preu;
	}

	public void setPreu(double preu) {
		this.preu = preu;
	}
	
	
	
	
	
	

	
}
