import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.ArrayList;
import spark.Spark;

public class Database {

    public void spark(){
        Spark.get("/user/:id", (request, response) -> "User: username=test, email=test@test.net");
    }
    public static void main(String args[]) {
        try {

            Database database = new Database();

            Connection con = database.getConnection();

            String uifName = "Nick";
            String uilName = "Dimitrov";
            String email = "nikolad21889@isd273.org";

            database.spark();

            //database.clear();

            //database.insertStatement(uifName, uilName, email);

            // What sql selected easier for ui
            database.selectStatement();

            con.commit();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private MysqlDataSource dataSource;
    private Connection con;

    Connection getConnection() {
        return con;
    }

    public Database() {
        try {

            this.dataSource = new MysqlDataSource();

            dataSource.setUser("root");
            dataSource.setPassword("password");
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("nickdim");

            this.con = dataSource.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStatement(String uifName, String uilName, String email) {
        try {

            PreparedStatement insertStatement = con.prepareStatement("INSERT INTO" +
                " nickdimfans (FirstName, LastName, Email)\n" +
                "VALUES (?,?,?)");

            insertStatement.setString(1, uifName);
            insertStatement.setString(2, uilName);
            insertStatement.setString(3, email);

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {

            PreparedStatement clear = con.prepareStatement("DELETE FROM nickdimfans");
            clear.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectStatement() {
        try {

            PreparedStatement select = con.prepareStatement("SELECT ? FROM nickdimfans WHERE FirstName = ?");

            select.setString(1, "*");
            select.setString(2, "'Richik'");

            ResultSet rs = select.executeQuery();

            StringBuilder selectStatement = new StringBuilder();
            selectStatement.append("ID\tFirst Name\tLast Name\tEmail\n");
            while (rs.next()) {
                selectStatement.append(rs.getInt("ID"));
                selectStatement.append("\t");
                selectStatement.append(rs.getString("FirstName"));
                selectStatement.append("\t");
                selectStatement.append(rs.getString("LastName"));
                selectStatement.append("\t");
                selectStatement.append(rs.getString("Email"));
                selectStatement.append("\n");
                System.out.println(selectStatement);
            }
            return selectStatement.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error check selectStatement";
    }

    public ArrayList getEmails() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT Email FROM nickdimfans");
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> emails = new ArrayList(0);

            while (rs.next()) {
                emails.add(rs.getString("Email"));
            }
            return emails;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getfNames() {
        try {

            PreparedStatement stmt = con.prepareStatement("SELECT FirstName from nickdimfans");
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> fNames = new ArrayList<>();

            while (rs.next()) {
                fNames.add(rs.getString("FirstName"));
            }
            return fNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList getlNames (){
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT LastName from nickdimfans");
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> lNames = new ArrayList<>();

            while (rs.next()) {
                lNames.add(rs.getString("LastName"));
            }
            return lNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFanCount() {
        try {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM nickdimfans");
            ResultSet rs = stmt.executeQuery();

            int i = 0;
            while(rs.next()) {
                i++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}