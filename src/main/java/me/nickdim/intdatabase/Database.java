package me.nickdim.intdatabase;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database {

    public static void main(String[] args) {
        try {

            Database database = new Database();

            Connection con = database.getConnection();

            String uifName = "Nick";
            String uilName = "Dimitrov";
            String email = "nikolad21889@isd273.org";

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

            Properties dbProperties = new Properties();
            dbProperties.load(new FileInputStream("db.properties"));

            this.dataSource = new MysqlDataSource();

            dataSource.setUser(dbProperties.getProperty("user"));
            dataSource.setPassword(dbProperties.getProperty("password"));
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName(dbProperties.getProperty("schema"));

            this.con = dataSource.getConnection();
            con.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            con.commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            con.commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int addUser(
        String uifName, String uilName, String email)
        throws SQLException {
        try {

            PreparedStatement insertStatement = con.prepareStatement("INSERT" +
                " INTO" + " nickdimfans " +
                "(FirstName, LastName, Email) VALUES (?,?,?)");

            insertStatement.setString(1, uifName);
            insertStatement.setString(2, uilName);
            insertStatement.setString(3, email);

            insertStatement.executeUpdate();

            PreparedStatement selectPK = con.prepareStatement(
                "SELECT ID from nickdimfans WHERE email = ?");

            selectPK.setString(1, email);
            ResultSet rs = selectPK.executeQuery();

            if(rs.next()) {
                return rs.getInt("ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return 0;
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

    public PublicUser getPublicUser(int PK) {
        return new PublicUser(getUser(PK));
    }

    public User[] getUsers() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM nickdimfans");
            ResultSet rs = stmt.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("ID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email")
                ));
            }
            return users.toArray(new User[users.size()]);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicUser[] getPublicUsers() {
        User[] users = getUsers();
        PublicUser[] publicUsers = new PublicUser[users.length];
        for (int i = 0; i < users.length; i++) {
            publicUsers[i] = new PublicUser(users[i]);
        }
        return publicUsers;
    }

    public String[] getEmails() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT Email FROM nickdimfans");
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> emails = new ArrayList(0);

            while (rs.next()) {
                emails.add(rs.getString("Email"));
            }
            return emails.toArray(new String[emails.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getfNames() {
        try {

            PreparedStatement stmt = con.prepareStatement("SELECT FirstName from nickdimfans");
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> fNames = new ArrayList<>();

            while (rs.next()) {
                fNames.add(rs.getString("FirstName"));
            }
            return fNames.toArray(new String[fNames.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String[] getlNames() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT LastName from nickdimfans");
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> lNames = new ArrayList<>();

            while (rs.next()) {
                lNames.add(rs.getString("LastName"));
            }
            return lNames.toArray(new String[lNames.size()]);
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