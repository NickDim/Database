import com.google.gson.Gson;
import spark.Spark;
import java.sql.SQLException;

public class API {

  public static void main(String[] args) {

    API api = new API();
    api.startServer();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        api.getDatabase().getConnection().close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }));
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
      database.addUser(
          request.queryParams("fName"),
          request.queryParams("lName"),
          request.queryParams("email")
      );
      return null;
    });
  }

  private String getJSON(Object model) {
    return gson.toJson(model);
  }

  public Database getDatabase() {
    return database;
  }
}
