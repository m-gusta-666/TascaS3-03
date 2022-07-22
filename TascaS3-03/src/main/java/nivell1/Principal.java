package nivell1;

import nivell3.DaoMongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Principal {
	
    private static List<Product> stock = new ArrayList<Product>();
    private static List<Ticket> ticketList = new ArrayList<Ticket>();
	public static DaoMongoDB daoMongoDB = new DaoMongoDB();
    static String floristName;
    
	public static void main(String[] args) {


		boolean sortirMenu = false;
		
//		floristName=requireString("Amb quina floristeria vols treballar?");
//
//		FileManagement.fileNotFound(floristName.toLowerCase()+"Stock.txt");
//		FileManagement.fileNotFound(floristName.toLowerCase()+"Tickets.txt");
//
//		FileManagement.setFileName(floristName);
//		stock=FileManagement.readStock();
//		ticketList=FileManagement.readTickets();
		stock = daoMongoDB.getDataCollection();

		do{
			sortirMenu = showMenu(sortirMenu);
			
		}while(!sortirMenu);



	}
	
	public static boolean showMenu(boolean sortirMenu) {
		
		String indexSwitch;
		String name;
		Product product;
		float price;
		int itemToDelate;
		
        System.out.println("\n               __/)  \n" +
                "            .-(__(=:       " + "FLORISTERIA PEPA \n" +
                "            |    \\)\n" +
                "     (\\__   |              1 - AFEGIR PRODUCTE\n" +
                "     :=)__)-|  __/)        2 - RETIRAR PRODUCTE\n" +
                "      (/    |-(__(=:       3 - IMPRIMIR STOCK DE TOTS ELS PRODUCTES\n" +
                "    ______  |  _ \\)        4 - IMPRIMIR STOCK AMB QUANTITATS\n" +
                "   /      \\ | / \\          5 - MOSTRAR COMPRES ANTIGUES\n" +
                "        ___\\|/___\\         6 - CREAR TIQUET DE VENDA\n"+
                "       [         ]\\        7 - MOSTRAR VALOR STOCK \n" +
                "        \\       /  \\       8 - MOSTRAR VALOR TOTES LES VENDES\n" +
                "         \\     /           0 - SORTIR PROGRAMA" + 
                "\n          \\___/            " + 
                "--------------------------------------------------------------");
        indexSwitch=requireString("\r\nQuina opció del menú vols triar?");

        switch (indexSwitch) {

            case "1":
            	
            	name = requireString("Introdueix el nom del producte.");
            	price = requireFloatNumber("Introdueix el preu del producte.");
            	product = addNewProductMenu(name, price);
            	
            	if (product != null) {
            		daoMongoDB.addProduct(product);
            		stock.add(product);

            		
            		//FileManagement.writeStock(stock);
            		System.out.println("S'ha afegit el producte "+ name + " a l'stock.");
            	}
            	else {
            		
            		System.out.println("No s'ha creat cap producte.");
            	}

                break;
                
            case "2":
            	
            	System.out.println("Quin producte vols retirà?");
                printStock();
            	itemToDelate = requireIntNumber("Introdueix el número del producte a retirar.") - 1 ;

				daoMongoDB.removeProduct(stock.get(itemToDelate));
				removeItemFromStock(itemToDelate);
            	System.out.println("El producte s'ha eliminat!");
            	
                break;
                
            case "3":
            	
                printStock();
                
                break;
                
            case "4":
                printStockByType();
                break;
                
            case "5":
            	
                showBuys();
                
                break;
            case "6":
            	
                createTicket();
                
                break;
                
            case "7":
            	
            	System.out.println("El valor de tot l'stock es de "+currentStockValue()+" euros.\n");
            	
            	break;
            	
            case "8":
            	
            	System.out.println("El valor de totes les vendes es de "+totalSalesValue()+" euros.\n");
            	
            	break;

            case "0":
            	
    			System.out.println("Gracies per utilitzar l'aplicació. Adeu!!");
    			//FileManagement.writeStock(stock);
    			uploadTicketsDB();
    			sortirMenu=true;
                break;
                
            default:
            	
            	System.out.println("Agafa un opció del menú. ¡El número ha de ser entre 0 i 8!");
        }

		return sortirMenu;
	}
	
	public static void uploadTicketsDB() {
		
		FileManagement.writeTicket(ticketList);
	}
	
	public static Product addNewProductMenu(String name, float price) {
		
		String type;
		Product product=null;
		String index=requireString("\nIntrodueix el tipus de producte que vols afegir.\n "
				+ " 1-Arbre. \n 2-Flor.\n 3-Decoració.\n 0-Sortir opció afegir producte.");
		
		switch (index) {
		
		case "1":
			
			type="arbre";
			product=new Tree(null, name, type, price, requireString("Introdueix l'alçada de l'arbre."));
			
			break;
			
		case "2":
			
			type = "flor";
			product = new Flower(null, name, type, price, requireString("Introdueix el color de la flor."));
			
			break;
			
		case "3":
			
			type = "decoracio";
			product = new Decor(null,name, type, price, requireString("Introdueix el material."));
			
			break;
			
		case "0":
			
			break;
			
		default:
			
			System.out.println("Agafa un opció del menú. ¡El número ha de ser entre 0 i 3!");
			addNewProductMenu (name, price);
		}
		
		return product;
	}
	
	public static void printStock() {
		
    	int i=0;
    	
    	for(Product product : stock){
    		
    		i++;
    		System.out.println(i + "- " + product);
    	}
	}
	
	public static void printStockByType() {
		
		int treeStock=(int) stock.stream()
                .filter(p->p.getType().equalsIgnoreCase("arbre")).count();

		int flowerStock =(int) stock.stream()
                .filter(p->p.getType().equalsIgnoreCase("flor")).count();
		
		int decorStock =(int) stock.stream()
                .filter(p->p.getType().equalsIgnoreCase("decoració")).count();
		
		System.out.println("Stock d'arbres: "+treeStock+" unitats.");
		System.out.println("Stock de flors: "+flowerStock+" unitats.");
		System.out.println("Stock de decoració: "+decorStock+" unitats.");	
	}
	
	public static void removeItemFromStock(int itemToDelate) {

		System.out.println(stock.get(itemToDelate).get_id());
    	stock.remove(itemToDelate);
		//FileManagement.writeStock(stock);
	}
	
	public static void showBuys() {
		
		ticketList=FileManagement.readTickets();
    	
    	for(Ticket ticket:ticketList){
    		
    		System.out.println("\n"+ticket);
    		ticket.showProducts();
    	}
	}
	
    public static void createTicket() {

        if (stock.size() > 0) {

            Ticket ticket = new Ticket(ticketList.size() + 1);
            int valueSelected;

            do {
            	printStock();
        		System.out.println("\nIntrodueix ID producte per afegir al carro.");
                valueSelected = requireIntNumber("Escriu ID [ 0 - FINALITZAR]: ");

                if (valueSelected == 0) {

                    if (ticket.getSales().size() > 0) {
                        System.out.println("Acabant la compra...Registrant el ticket...");
                        ticketList.add(ticket);
                        uploadTicketsDB();   
                    }else {
                        System.out.println("Sortint. No se ha fet cap compra...");
                    }

                } else if ( 0 < valueSelected && valueSelected <= stock.size()) {

                    ticket.addProduct(stock.get(valueSelected - 1));
                    removeItemFromStock(valueSelected - 1);
                    
                } else {

                    System.out.println("S'ha introduït un ID incorrecte");
                }
                
            } while (valueSelected != 0 && stock.size() > 0);

            //Print the ticket and updating values
            ticket.showProducts();


        } else
            System.out.println("\nNo n'hi ha productes per vendre." +
                    "Botiga buida!! \n\n".toUpperCase());
    }
    
	    // Returns value of stock in terms of currency
	public static float currentStockValue() {
		 
	    return (float) stock.stream()
	    		.mapToDouble(Product::getPrice).sum();
	}
	    
	public static float totalSalesValue() {
	    return (float )ticketList.stream()
	    		.mapToDouble(t->t.getTicketPrice()).sum();
	    	
	}

		public static String requireString(String message) {
			
			Scanner sc = new Scanner(System.in);
			String string;
			
			System.out.println(message);
			string = sc.nextLine();
			
			return string;
		}
		
		public static int requireIntNumber(String message) {
			
			Scanner sc = new Scanner(System.in);
			int num;
			
			System.out.println(message);
			num = sc.nextInt();
			
			return num;
		}
		
		public static float requireFloatNumber(String message) {
			
			Scanner sc = new Scanner(System.in);
			float num;
			
			System.out.println(message);
			num = sc.nextFloat();
			
			return num;
		}
}
