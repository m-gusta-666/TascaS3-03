package nivell1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class GestioFitxer {

	private static final String fitxerStock = "floristeria.txt";
	private static final String fitxerTickets = "tickets.txt";

	
	public static List<Producte> llegirStock() {

		List<Producte> stock = new ArrayList<Producte>();
		BufferedReader br = null;
		String linea = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fitxerStock)));
			linea = br.readLine();

			while (linea != null) {
				String[] dummy = linea.split(";");
				// System.out.println(linea);

				if (dummy[1].equals("flor")) {
					stock.add(new Flor(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				} else if (dummy[1].equals("arbre")) {
					stock.add(new Arbre(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				} else {
					stock.add(new Decoracio(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				}

				linea = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No s'ha trobat el fitxer");
		} catch (IOException e) {
			System.out.println("IOException!!!!");
		}

		return stock;
	}

	public static List<Ticket> llegirTickets() {

		List<Ticket> tickets = new ArrayList<Ticket>();
		BufferedReader br = null;
		String linea = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fitxerTickets)));
			linea = br.readLine();

			while (linea != null) {
				String[] dummy = linea.split(";");

				int ticketId = Integer.parseInt(dummy[4]);
				if (ticketId == tickets.size() + 1) {
					tickets.add(new Ticket(ticketId));
				}

				Ticket ticketActual = tickets.get(ticketId - 1);

				// Mentres es llegeix el fitxer, es castegen els Productes i s'afegeixen als
				// productes del Ticket corresponent
				if (dummy[1].equals("flor")) {
					ticketActual.afegirProducte(new Flor(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				} else if (dummy[1].equals("arbre")) {
					ticketActual.afegirProducte(new Arbre(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				} else {
					ticketActual
					.afegirProducte(new Decoracio(dummy[0], dummy[1], Double.parseDouble(dummy[2]), dummy[3]));
				}

				linea = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No s'ha trobat el fitxer");
		} catch (IOException e) {
			System.out.println("IOException!!!!");
		}

		return tickets;

	}



	static void escriureStock(List<Producte> stock) {

		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(fitxerStock,false));

			for (int i=0 ; i<stock.size() ; i++) {
				br.write(stock.get(i).toString()+"\r\n");
			}

			br.close();

		} catch (IOException e){
			System.out.println("IOException!!!!");
		}
	}



	static void escriureTicket(List<Ticket> tickets) {

		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(fitxerTickets,false));

			for (int i=0 ; i<tickets.size() ; i++) {
				List<Producte> productes = tickets.get(i).getCompres();
				for (int j=0 ; j<productes.size() ; j++) {
					br.write(productes.get(j).toString()+";"+tickets.get(i).getId()+"\r\n");
				}

			}

			br.close();

		} catch (IOException e){
			System.out.println("IOException!!!!");
		}
	}
}
