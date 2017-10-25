import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

public class Database {

    public static void main(String args[]) {
        try {

            Database database = new Database();

            String uifName = "''";
            String uilName = "''";
            String email = "''";

            Connection con = database.getConnection();
            database.insertStatement(uifName, uilName, email);

            // What sql selected easier for ui
            StringBuilder selected = database.selectStatement();

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

    public Database() throws SQLException {

        this.dataSource = new MysqlDataSource();

        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("nickdim");

        this.con = dataSource.getConnection();
        con.setAutoCommit(false);
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

    public StringBuilder selectStatement() {
        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nickdimfans");

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
                return selectStatement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder error = new StringBuilder();
        error.append("Error, check java");
        return error;
    }
}