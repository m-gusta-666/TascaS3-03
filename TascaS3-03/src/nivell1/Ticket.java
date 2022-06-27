package nivell1;


import java.util.ArrayList;

public class Ticket {

	
	private ArrayList<Producte> compres;
	private int id;
	
	public Ticket(int id) {
		compres = new ArrayList<Producte>();
		this.id = id;
	}
	
	
	public void afegirProducte(Producte producte) {
		compres.add(producte);
	}
	
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ArrayList<Producte> getCompres() {
		return compres;
	}


	public void mostrarProductes() {
		compres.forEach(System.out::println);
	}
	
	public String toString() {
		return "id="+id;
	}
	
}
