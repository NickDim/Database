import java.sql.SQLException;

public class UI {

    public static void main() {
        try {
            Database database = new Database();

            String function = null;
            String fName = null;
            String lName = null;
            String email = null;

            decider(function, database, fName, lName, email);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void decider(String function, Database database,
                                String fName, String lName, String email) {

        while (true) {
            if (function == "clear") {
                database.clear();
            } else if (function == "select") {
                database.selectStatement();
            } else if (function == "insert") {
                if(fName != null && lName != null && email != null) {
                    database.insertStatement(fName, lName, email);
                }
            } else {
                break;
            }
        }
    }
}