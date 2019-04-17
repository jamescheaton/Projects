package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    private Connection con;

    public DBconnector(){
        this.con = connect();
    }

    private Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_group65",
                    "cs4400_group65",
                    "1pz5OA9a");
            if (!con.isClosed())
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return con;
    }

    public void closeConnection(){
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
        }
    }

    public Connection getCon(){
        return con;
    }
}
