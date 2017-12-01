import com.google.gson.Gson;
import spark.Spark;

public class API {

  public static void main(String[] args) {

    API api = new API();
    api.spark();

  }

  private Gson gson;
  private Database database;

  public API() {

    this.gson = new Gson();
    this.database = new Database();

  }

  public void spark() {
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
}
