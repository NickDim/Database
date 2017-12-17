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
    enableCORS("*", "GET,POST,PUT,DELETE",
        "Content-Type, Access-Control-Allow-Headers, " +
            "Authorization, X-Requested-With");
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

  // Enables CORS on requests. This method is an initialization method and should be called once.
  private void enableCORS(final String origin, final String methods, final String headers) {

    Spark.options("/*", (request, response) -> {

      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Request-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
      // Note: this may or may not be necessary in your particular application
      response.type("application/json");
    });
  }

  private String getJSON(Object model) {
    return gson.toJson(model);
  }

  public Database getDatabase() {
    return database;
  }
}
