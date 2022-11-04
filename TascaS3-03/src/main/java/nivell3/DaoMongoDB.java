package nivell3;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import nivell1.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoMongoDB implements AutoCloseable {

    // Acceso a mongoDB
    private MongoClient mongoClient;
    private MongoDatabase database;

    public DaoMongoDB() {

        String uri = "mongodb://localhost:27017/?maxPoolSize=20&w=majority";

        try {
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("floristeria");
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            System.out.println("Connected successfully to server.");

        } catch (MongoException me) {
            System.err.println("An error occurred while attempting to run a command: " + me);

        }

    }

    public void addTicket(Ticket ticket) {

        if (database.getCollection("tickets") == null) {

            database.createCollection("tickets");
        } else {

            MongoCollection<Document> collection = database.getCollection("tickets");
            List<DBObject> productList = new ArrayList<DBObject>();
            Document doc = new Document();


            for (Product product : ticket.getSales()) {
                BasicDBObject doc2 =
                        new BasicDBObject("name", product.getName())
                                .append("type", product.getType())
                                .append("price", product.getPrice())
                                .append("_id",product.get_id());
                if (product instanceof Tree)
                    doc2.append("height", ((Tree) product).getHeight());
                else if (product instanceof Flower)
                    doc2.append("color", ((Flower) product).getColor());
                else if (product instanceof Decor)
                    doc2.append("material", ((Decor) product).getMaterial());
                productList.add(doc2);
            }
            doc.put("productList", productList);
            doc.put("Total_Amount", ticket.getTicketAmount());
            InsertOneResult result = collection.insertOne(doc);
            ObjectId valueId = Objects.requireNonNull(result.getInsertedId()).asObjectId().getValue();
            ticket.setId(valueId);
            System.out.println("Inserted ticket to document with the following id: "
                    + valueId);

        }
    }

    public void addProduct(Product product) {

        MongoCollection<Document> collection = database.getCollection("products");
        Document doc1 = null;

        if (product instanceof Tree) {
            Tree tree = (Tree) product;
            doc1 = new Document("name", tree.getName())
                    .append("type", tree.getType())
                    .append("price", tree.getPrice())
                    .append("height", tree.getHeight());

        } else if (product instanceof Flower) {
            Flower flower = (Flower) product;
            doc1 = new Document("name", flower.getName())
                    .append("type", flower.getType())
                    .append("price", flower.getPrice())
                    .append("color", flower.getColor());

        } else if (product instanceof Decor) {
            Decor decor = (Decor) product;
            doc1 = new Document("name", decor.getName())
                    .append("type", decor.getType())
                    .append("price", decor.getPrice())
                    .append("material", decor.getMaterial());
        }

        InsertOneResult result = collection.insertOne(doc1);
        ObjectId valueId = Objects.requireNonNull(result.getInsertedId()).asObjectId().getValue();
        product.set_id(valueId);
        System.out.println("Inserted product to document with the following id: "
                + valueId);

    }


    public void removeProduct(Product product) {

        MongoCollection<Document> collection = database.getCollection("products");
        System.out.println("OJU:" + product.get_id());
        collection.deleteOne(
                new Document("_id", new ObjectId(product.get_id().toString()))
        );
    }

    public ArrayList<Ticket> getDataCollectionTickets() {

        MongoCollection<Document> collection = database.getCollection("tickets");

        MongoCursor<Document> cursor = collection.find()
                .sort(Sorts.descending("_id"))
                .iterator();
        Gson gson = new Gson();
        ArrayList<Ticket> tickets = new ArrayList<>();

        try {

            while (cursor.hasNext()) {
                Ticket ticket = new Ticket();
                Document document = cursor.next();
                ObjectId id = document.getObjectId("_id");
                ticket.setId(id);

                List<Document> productList = document.getList("productList", Document.class);

                for (Document docProduct : productList) {

                    ObjectId productObjectId = docProduct.getObjectId("_id");
                    String tipo = docProduct.getString("type");
                    Product product = null;

                    product = getProduct(gson, docProduct, productObjectId, tipo, product);
                    //System.out.println(productObjectId.toString());
                    ticket.addProduct(product);
                }
                tickets.add(ticket);

            }

        } finally {

            cursor.close();
        }

        return tickets;
    }

    private Product getProduct(Gson gson, Document docProduct, ObjectId productObjectId, String tipo, Product product) {
        switch (tipo) {
            case "flor":
                product = gson.fromJson(docProduct.toJson(), Flower.class);
                break;
            case "arbre":
                product = gson.fromJson(docProduct.toJson(), Tree.class);
                break;
            case "decoració":
                product = gson.fromJson(docProduct.toJson(), Decor.class);
                break;
        }
        product.set_id(productObjectId);
        return product;
    }


    // Get data products from mongoDB
    public ArrayList<Product> getDataCollection() {

        MongoCollection<Document> collection = database.getCollection("products");
        //List<DBObject> productList = new ArrayList<DBObject>();
        ArrayList<Product> products = new ArrayList<>();
        Gson gson = new Gson();
//        Bson projectionFields = Projections.fields(
//                Projections.include("title", "imdb"),
//                Projections.excludeId());

        MongoCursor<Document> cursor = collection.find()
                .sort(Sorts.descending("title"))
                .iterator();
        //.projection(projectionFields)
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toJson());
                Document document = cursor.next();
                ObjectId id = document.getObjectId("_id");
                Product product = null;
                String tipo = document.getString("type");

                //System.out.println(document.toJson());
                product = getProduct(gson, document, id, tipo, product);
                products.add(product);
            }
        } finally {

            cursor.close();
        }
        return products;
    }

    @Override
    public void close(){
        if (mongoClient != null) mongoClient.close();
    }
}
