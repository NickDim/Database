import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static void main(String args[]){
        databaseConnector();
    }

    private static void databaseConnector() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("babapatka");
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("nickdim");

            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nickdimfans");

            con.setAutoCommit(false);

            if (con != null) {
                while(rs.next()) {
                    rs.getInt("ID");
                    rs.getString("FirstName");
                    rs.getString("LastName");
                }
                con.commit();
            }
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
