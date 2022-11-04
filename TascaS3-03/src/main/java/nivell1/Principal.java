package nivell1;

import nivell3.DaoMongoDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static nivell1.Utilities.*;


public class Principal {

    private static List<Product> stock = new ArrayList<Product>();
    private static List<Ticket> ticketList = new ArrayList<Ticket>();
    public static DaoMongoDB daoMongoDB = new DaoMongoDB();
    static String floristName;

    public static void main(String[] args) {

        boolean sortirMenu = false;

        // Carga los datos de Stock
        stock = daoMongoDB.getDataCollection();
        // Carga los tiquets de venta
        ticketList = daoMongoDB.getDataCollectionTickets();

        do {
            sortirMenu = showMenu(sortirMenu);

        } while (!sortirMenu);

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
                "      (/    |-(__(=:       3 - VER STOCK DE PRODUCTES\n" +
                "    ______  |  _ \\)        4 - VER STOCK AMB QUANTITATS\n" +
                "   /      \\ | / \\          5 - MOSTRAR COMPRES ANTIGUES\n" +
                "        ___\\|/___\\         6 - CREAR TIQUET DE VENDA\n" +
                "       [         ]\\         \n" +
                "        \\       /  \\      \n"  +
                "         \\     /           " + String.format("%-15s %s \n","TOTAL VENUT: ",  totalSalesValue())  +
                "          \\___/            " + String.format("%-15s %s","VALOR STOCK: " + ANSI_BLUE,  currentStockValue()) + ANSI_RESET
                + (currentStockValue().equalsIgnoreCase("0,00") ? (ANSI_RED + " (Botiga buida)" + ANSI_RESET) : "") + "\n" +
                "--------------------------------------------------------------");
        indexSwitch = requireString("\r\nSelecciona opció [0 -Sortir]: ");

        switch (indexSwitch) {

            case "1":

                name = requireString("Introdueix el nom del producte: ");
                price = requireFloatNumber("Introdueix el preu del producte: ");
                product = addNewProductMenu(name, price);
                if (product != null) {
                    daoMongoDB.addProduct(product);
                    stock.add(product);

                    //FileManagement.writeStock(stock);
                    System.out.println("S'ha afegit el producte " + name + " a l'stock.");
                } else {
                    System.out.println("No s'ha creat cap producte.");
                }
                break;

            case "2":
                System.out.println("Quin producte vols retirà?");
                getAllProducts(" A RETIRAR");
                do {

                    itemToDelate = Utilities.inputDataInt("Nº del producte a retirar [0 - Sortir]: ");

                    if (itemToDelate > stock.size())
                        System.out.println("No existeix aquest registre!");

                } while (itemToDelate > stock.size());

                if(itemToDelate != 0) {
                    daoMongoDB.removeProduct(stock.get(itemToDelate - 1));
                    removeItemFromStock(itemToDelate - 1);
                    System.out.println("El producte s'ha eliminat!");
                }
                break;

            case "3":
                getAllProducts(" EN STOCK");
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

            case "0":
                System.out.println("Gracies per utilitzar l'aplicació. Adeu!!");
                //FileManagement.writeStock(stock);

                sortirMenu = true;
                break;

            default:

                System.out.println("Tria un opció entre 0 i 6!");
        }

        return sortirMenu;
    }

    public static void uploadTicketsDB(Ticket ticket) {

        daoMongoDB.addTicket(ticket);
    }

    public static Product addNewProductMenu(String name, float price) {

        String type;
        Product product = null;
        String index = requireString("\nIntrodueix el tipus de producte que vols afegir.\n "
                + "1-Arbre. \n 2-Flor.\n 3-Decoració.\n 0-Sortir opció afegir producte.");

        switch (index) {

            case "1":

                type = "arbre";
                product = new Tree(null, name, type, price, requireString("Introdueix l'alçada de l'arbre."));

                break;

            case "2":

                type = "flor";
                product = new Flower(null, name, type, price, requireString("Introdueix el color de la flor."));

                break;

            case "3":

                type = "decoració";
                product = new Decor(null, name, type, price, requireString("Introdueix el material."));

                break;

            case "0":

                break;

            default:

                System.out.println("Agafa un opció del menú. ¡El número ha de ser entre 0 i 3!");
                addNewProductMenu(name, price);
        }

        return product;
    }

    public static void createTicket() {

        if (stock.size() > 0) {

            Ticket ticket = new Ticket();
            int valueSelected;

            do {
                getAllProducts(" PER A CREACIÓ DE TICKET");
                System.out.println("\nIntrodueix ID producte per afegir al carro.");
                valueSelected = Utilities.requireIntNumber("Escriu ID [ 0 - FINALITZAR]: ");

                if (valueSelected == 0) {

                    if (ticket.getSales().size() > 0) {
                        System.out.println("Acabant la compra...Registrant el ticket...");
                        ticketList.add(ticket);
                        uploadTicketsDB(ticket);
                    } else {
                        System.out.println("Sortint. No se ha fet cap compra...");
                    }

                } else if (0 < valueSelected && valueSelected <= stock.size()) {

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

    public static void getAllProducts(String s) {

        Utilities.printProductesHead(s);

        int i = stock.size();
        /* Sorting in decreasing order to see the last one added*/
        Collections.reverse(stock);
        // Printing data
        for (Product producte : stock) {
            System.out.printf("%-3d %-20s %-15s %-15s %10.2f  %s\n",
                    i,
                    producte.getName(),
                    producte.getType(),
                    producte.getCharacteristic(),
                    producte.getPrice(),
                    producte.get_id());
            i--;
        }
        // Back to original distribution
        Collections.reverse(stock);
        printLines(105, '-');
        // Pausing screen scroll
        if (stock.size() > 10)
            Utilities.pressEnterToContinue();

    }

    public static void printStockByType() {

        int treeStock = (int) stock.stream()
                .filter(p -> p.getType().equalsIgnoreCase("arbre")).count();

        int flowerStock = (int) stock.stream()
                .filter(p -> p.getType().equalsIgnoreCase("flor")).count();

        int decorStock = (int) stock.stream()
                .filter(p -> p.getType().equalsIgnoreCase("decoració")).count();

        System.out.println("Stock d'arbres: " + treeStock + " unitats.");
        System.out.println("Stock de flors: " + flowerStock + " unitats.");
        System.out.println("Stock de decoració: " + decorStock + " unitats.");
    }

    public static void removeItemFromStock(int itemToDelate) {

        System.out.println(stock.get(itemToDelate).get_id());
        stock.remove(itemToDelate);
        //FileManagement.writeStock(stock);
    }

    public static void showBuys() {

        for (Ticket ticket : ticketList) {

            System.out.println("\n" + ticket);
            ticket.showProducts();
        }
    }

    // Returns value of stock in terms of currency
    public static String currentStockValue() {

        return doubleFormatProperly(
                (float) stock.stream().mapToDouble(Product::getPrice).sum());
        //return (float) stock.stream().mapToDouble(Product::getPrice).sum();
    }

    public static String totalSalesValue() {

        return doubleFormatProperly(
                (float) ticketList.stream().mapToDouble(Ticket::getTicketAmount).sum());
//        return (float) ticketList.stream()
//                .mapToDouble(Ticket::getTicketAmount).sum();
    }


    public static float requireFloatNumber(String message) {

        Scanner sc = new Scanner(System.in);
        float num;

        System.out.println(message);
        num = sc.nextFloat();

        return num;
    }
}
