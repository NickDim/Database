import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

public class API {

  public static void main(String[] args) {
    try {

      API api = new API();
      api.startServer();

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
          api.getDatabase().close();
          System.exit(1);
      }));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Gson gson;
  private Database database;

  public API() {
    this.gson = new Gson();
    this.database = new Database();
  }

  private void startServer() {
    Spark.get("/users", (request, response) -> {
      response.type("application/json");
      return getJSONs(database.getPublicUsers());
    });

    Spark.get("/users/:id", (request, response) -> {
      response.type("application/json");
      return getJSON(database.getPublicUser(Integer.parseInt(request.params("id"))));
    });

    Spark.post("/users", (request, response) -> {
      response.type("application/json");
      database.addUser(
          request.queryParams("fName"),
          request.queryParams("lName"),
          request.queryParams("email")
      );
      database.commit();
      JsonObject returnMsg = new JsonObject();
      returnMsg.addProperty("added", true);
      return returnMsg;
    });
  }

  private String getJSON(PublicUser user) {
    return gson.toJson(user);
  }

  private String getJSONs(PublicUser[] users) {
    return gson.toJson(users);
  }

  public Database getDatabase() {
    return database;
  }
}
