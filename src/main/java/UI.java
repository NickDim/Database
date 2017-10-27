public class UI {

    public static void main(String[] args) {

        Database database = new Database();

        String columnSelected = "First Name";
        String function = "select";
        String fName = "fName";
        String lName = "lName";
        String email = "Email";

        decider(function, database, fName, lName, email, columnSelected);

    }

    private static void decider(String function, Database database,
                                String fName, String lName, String email, String columnSelected) {

        while (true) {
            if (function == "clear") {
                database.clear();
            } else if (function == "select") {
                database.selectStatement(columnSelected);
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