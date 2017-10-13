import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static void main(String args[]){
        selectStatement();
    }

    private static void selectStatement() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("password");
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("nickdim");

            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nickdimfans");

            con.setAutoCommit(false);

            if (con != null) {
                StringBuilder selectStatement = new StringBuilder();
                while(rs.next()) {
                    selectStatement.append("ID: ");
                    selectStatement.append(rs.getInt("ID"));
                    selectStatement.append("\t First Name: ");
                    selectStatement.append(rs.getString("FirstName"));
                    selectStatement.append("\t Last Name: ");
                    selectStatement.append(rs.getString("LastName"));
                }
                System.out.println(selectStatement);
                con.commit();
            }
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
