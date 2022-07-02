package nivell1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {

	//private static final String fileStock="floristeria.txt";
    //private static final String fileTickets="tickets.txt";
	
	private static String fileStock;
	private static String fileTickets;

    public static void setFileName(String name) {
    	
    	fileStock=name.toLowerCase()+"Stock.txt";
        fileTickets=name.toLowerCase()+"Tickets.txt";
    }
    
    public static void fileNotFound(String fileName) {
    	
    	try {
    	      File file = new File(fileName);
    	      if (file.createNewFile()) {
    	        System.out.println("Arxiu creat: " + file.getName());
    	      } /*else {
    	        System.out.println("L'arxiu existeix.");
    	      }*/
    	    } catch (IOException e) {
    	      System.out.println("Error.");
    	      e.printStackTrace();
    	    }
    }
    
    public static List<Product> readStock() {

        List<Product> stock=new ArrayList<Product>();
        BufferedReader br=null;
        String line=null;

        try {
        	
            br=new BufferedReader(new InputStreamReader(new FileInputStream(fileStock)));
            line=br.readLine();

            while(line!=null){
            	
                String[] dummy=line.split(";");

                if(dummy[1].equals("flor")){
                	
                    stock.add(new Flower(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));
                }
                else if (dummy[1].equals("arbre")) {
                	
                    stock.add(new Tree(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));
                }
                else {
                	
                    stock.add(new Decor(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));
                }

                line=br.readLine();
            }
        }
        catch (FileNotFoundException e) {
        	
            System.out.println("No s'ha trobat el fitxer.");   
        }
        catch (IOException e) {
        	
            System.out.println("IOException!!!!");
        }

        return stock;
    }

    public static List<Ticket> readTickets() {

        List<Ticket> tickets=new ArrayList<Ticket>();
        BufferedReader br=null;
        String line=null;

        try {
        	
            br=new BufferedReader(new InputStreamReader(new FileInputStream(fileTickets)));
            line=br.readLine();

            while (line!=null) {
            	
                String[] dummy=line.split(";");
                int ticketId=Integer.parseInt(dummy[4]);
                
                if (ticketId==tickets.size()+1) {
                	
                    tickets.add(new Ticket(ticketId));
                }

                Ticket currentTicket=tickets.get(ticketId - 1);

                /*Mentres es llegeix el fitxer, es castegen els Productes i s'afegeixen als
                 productes del Ticket corresponent*/
                if (dummy[1].equals("flor")) {

                	currentTicket.addProduct(new Flower(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));
                }
            	else if (dummy[1].equals("arbre")) {
                	
                	currentTicket.addProduct(new Tree(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));	
                }
            	else {
            		
                	currentTicket.addProduct(new Decor(dummy[0], dummy[1], Float.parseFloat(dummy[2]), dummy[3]));
                }

                line=br.readLine();
            }
        }
        catch (FileNotFoundException e) {
        	
            System.out.println("No s'ha trobat el fitxer. Es crea un fitxer nou.");
        }
        catch (IOException e) {
        	
            System.out.println("IOException!!!!");
        }

        return tickets;
    }

    static void writeStock(List<Product> stock) {

        try {
        	
            BufferedWriter br=new BufferedWriter(new FileWriter(fileStock, false));

            for (int i=0; i<stock.size(); i++) {
            	
                br.write(stock.get(i).toStringFormat() + "\r\n");
            }

            br.close();
        }
        catch (IOException e) {
        	
            System.out.println("IOException!!!!");
        }
    }

    static void writeTicket(List<Ticket> tickets) {

        try {
        	
            BufferedWriter br=new BufferedWriter(new FileWriter(fileTickets, false));

            for (int i=0; i<tickets.size(); i++) {
            	
                List<Product> productes = tickets.get(i).getSales();
                
                for (int j=0; j<productes.size(); j++) {
                	
                    br.write(productes.get(j).toStringFormat() + ";" + tickets.get(i).getId() + "\r\n");
                }
            }

            br.close();
        }
        catch (IOException e) {
        	
            System.out.println("IOException!!!!");
        }
    }
}
