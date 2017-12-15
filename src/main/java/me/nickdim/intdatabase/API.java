package me.nickdim.intdatabase;

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
      return getJSON(database.getPublicUsers());
    });

    Spark.get("/users/:id", (request, response) -> {
      response.type("application/json");
      return getJSON(database.getPublicUser(Integer.parseInt(request.params("id"))));
    });

    Spark.post("/users", (request, response) -> {
      response.type("application/json");
      JsonObject returnMsg = new JsonObject();
      try {
        database.addUser(
            request.queryParams("fName"),
            request.queryParams("lName"),
            request.queryParams("email")
        );
        database.commit();
        returnMsg.addProperty("added", true);
      } catch (Exception e) {
        response.status(500);
        returnMsg.addProperty("added", false);
      }
      return returnMsg;
    });
  }

  private String getJSON(Object model) {
    return gson.toJson(model);
  }

  public Database getDatabase() {
    return database;
  }
}
