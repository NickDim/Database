import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

public class Database {

    public static void main(String args[]) {
        try {

            Database database = new Database();

            Connection con = database.getConnection();

            String uifName = "FirstName";
            String uilName = "LastName";
            String email = "example@gmail.com";

            database.insertStatement(uifName, uilName, email);

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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStatement(String uifName, String uilName, String email) {
        try {

            PreparedStatement insertStatement = con.prepareStatement("INSERT INTO" +
                    " nickdimfans (FirstName, LastName, Email)\n" +
                    "VALUES (+ " + uifName + "," + uilName + "," + email + ")");

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {

            PreparedStatement clear = con.prepareStatement("DELETE FROM nickdimfans");
            clear.executeUpdate();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectStatement() {
        try {

            PreparedStatement select = con.prepareStatement("SELECT * FROM nickdimfans WHERE FirstName = 'Nick'");
            ResultSet rs = select.executeQuery();

            if (con != null) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error check selectStatement";
    }
    public String[] getEmails() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Email FROM nickdimfans");
            String[] emails = new String[10000];
            int i = 0;
            while (rs.next()) {
                emails[i] = rs.getString("Email");
                i++;
            }
            return "error check getEmails".split("error");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}