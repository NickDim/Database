import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class Database {

    public static void main(String args[]){
        try {

            Database database = new Database();

            String uifName = "'Velin'";
            String uilName = "'Dimitrov'";

            Connection con = database.getConnection();
            database.insertStatement(uifName, uilName);
            database.selectStatement();

            con.commit();
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private MysqlDataSource dataSource;
    private Connection con;

    private Connection getConnection() {
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

    public void insertStatement(String uifName, String uilName) {
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO nickdimfans (FirstName, LastName)\nVALUES (+ " + uifName + "," + uilName + ")");
            stmt.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void selectStatement() {
        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nickdimfans");

            if (con != null) {
                StringBuilder selectStatement = new StringBuilder();
                selectStatement.append("ID\tFirst Name\tLast Name\n");
                while(rs.next()) {
                    selectStatement.append(rs.getInt("ID"));
                    selectStatement.append("\t");
                    selectStatement.append(rs.getString("FirstName"));
                    selectStatement.append("\t");
                    selectStatement.append(rs.getString("LastName"));
                    selectStatement.append("\n");
                }
                System.out.println(selectStatement);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}