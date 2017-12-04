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

  public void startServer() {
    Spark.get("/user/:id", (request, response) -> {
      response.type("application/json");
      return getJSON(database.getUser(
          Integer.parseInt(request.params(":id"))
      ));
    });
  }

  private String getJSON(User user) {
    return gson.toJson(user);
  }

  public Database getDatabase() {
    return database;
  }
}
