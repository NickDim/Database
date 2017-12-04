import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    public static void main(String[] args) {
        try {

            Database database = new Database();

            Connection con = database.getConnection();

            String uifName = "Nick";
            String uilName = "Dimitrov";
            String email = "nikolad21889@isd273.org";

//            String firstFName = database.getfNames().get(0).toString();
//            String firstLName = database.getlNames().get(0).toString();
//            String firstEmail = database.getEmails().get(0).toString();

            con.commit();
            con.close();
        } catch (Exception e) {
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

        } catch (Exception e) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {

            PreparedStatement clear = con.prepareStatement("DELETE FROM nickdimfans");
            clear.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(int PK) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM nickdimfans WHERE ID = ?");
            stmt.setInt(1, PK);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("ID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email")
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
            return "" + i;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
