import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class Database {
    public static void main(String args[]){
        insertStatement();
        selectStatement();
    }

    private static void insertStatement() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("password");
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("nickdim");

            Connection con = dataSource.getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement("INSERT INTO nickdimfans (ID, FirstName, LastName)\nVALUES (4, 'Kassin', 'Farah')");
            stmt.executeUpdate();
            con.commit();
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
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
                    selectStatement.append(rs.getString("LastName") + "\n");
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
