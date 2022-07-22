package nivell3;

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import nivell1.Decor;
import nivell1.Flower;
import nivell1.Product;
import nivell1.Tree;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;

import java.util.ArrayList;

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

    public void addProduct(Product product) {

        MongoCollection<Document> collection = database.getCollection("products");
        Document doc1 = null;
        if (product instanceof Tree) {
            Tree tree = (Tree) product;
            doc1 = new Document("name", tree.getName())
                    .append("type", tree.getType()).append("price", tree.getPrice())
                    .append("height", tree.getHeight());

        } else if (product instanceof Flower) {
            Flower flower = (Flower) product;
            doc1 = new Document("name", flower.getName())
                    .append("type", flower.getType()).append("price", flower.getPrice())
                    .append("color", flower.getColor());

        } else if (product instanceof Decor) {
            Decor decor = (Decor) product;
            doc1 = new Document("name", decor.getName())
                    .append("type", decor.getType()).append("price", decor.getPrice())
                    .append("material", decor.getMaterial());
        }

        InsertOneResult result = collection.insertOne(doc1);
        ObjectId valueId = result.getInsertedId().asObjectId().getValue();
        product.set_id(valueId);
        System.out.println("Inserted a document with the following id: "
                + valueId);

    }


    public void removeProduct(Product product) {

        MongoCollection<Document> collection = database.getCollection("products");
        System.out.println("OJU:"   + product.get_id());
        collection.deleteOne(
                new Document("_id", new ObjectId(product.get_id().toString()))
        );

    }

    public ArrayList<Product> getDataCollection() {

        MongoCollection<Document> collection = database.getCollection("products");
        ArrayList<Product> products = new ArrayList<>();
        Gson gson = new Gson();
//        Bson projectionFields = Projections.fields(
//                Projections.include("title", "imdb"),
//                Projections.excludeId());

        MongoCursor<Document> cursor = collection.find()
                //.projection(projectionFields)
                .sort(Sorts.descending("title")).iterator();
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toJson());
                Document document = cursor.next();
                ObjectId id = document.getObjectId("_id");
                Product product = null;
                String tipo = document.getString("type");

               //System.out.println(document.toJson());
                if (tipo.equals("flor")) {
                    product = gson.fromJson(document.toJson(), Flower.class);
                } else if (tipo.equals("arbre")) {
                    product = gson.fromJson(document.toJson(), Tree.class);
                } else if (tipo.equals("decoracio")) {
                    product = gson.fromJson(document.toJson(), Decor.class);
                }
                product.set_id(id);
                products.add(product);
            }
        } finally {

            cursor.close();
        }
        return products;
    }

    @Override
    public void close() throws Exception {
        if (mongoClient != null) mongoClient.close();
    }
}
