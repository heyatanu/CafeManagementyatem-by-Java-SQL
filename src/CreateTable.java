import java.sql.*;

public class CreateTable {

  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/cafemanagementsystem";

  static final String USER = "root";
  static final String PASS = "";

  public static void Create() {
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      System.out.println("Creating table in given database...");
      stmt = conn.createStatement();

      String sql = "CREATE TABLE  cafelist" +
        "(pid  INTEGER(100) not NULL, " +
        " pname VARCHAR(100), " +
        " pprice INTEGER(200), " +
        " pdiscount INTEGER(10), " +
        " pinstock INTEGER(100), " +
        " PRIMARY KEY ( pid ))";

      stmt.executeUpdate(sql);
      //      System.out.println("Created table in given database...");

      System.out.println("Cafe table creation successfull...");
    } catch (SQLException se) {
      
      System.out.println("Cafe table already have in database... OR SOMETHING WRONG");
    } catch (Exception e) {
      
      e.printStackTrace();
    } finally {
      
      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {} 
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    }  //for cafe

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      String adminsql = "CREATE TABLE  cafeadmin " +
        "(adminname  VARCHAR(100) not NULL, " +
        " password VARCHAR(100), " +
        " PRIMARY KEY ( adminname ))";

      stmt.executeUpdate(adminsql);
      System.out.println("Admin table creation successfull...");
    } catch (SQLException se) {
      
      System.out.println("ADMIN TABLE IS ALREAY PRESENT OR SOMETHING WRONG");
    } catch (Exception e) {
      
      e.printStackTrace();
    } finally {
      
      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {} 
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    } // for cafe admin

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Inserting records into the table...");
      stmt = conn.createStatement();

      String sql = "INSERT INTO cafeadmin VALUES ('root', 'admin')";
      stmt.executeUpdate(sql);
      System.out.println("Username records into the table... Successfull");

    } catch (SQLException se) {
      
      System.out.println("Username already have in database TRY ANOTHER");
    } catch (Exception e) {
      
      System.out.println(" SOMETHING WRONG");
    } finally {
      
      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {} 
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    } 

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      stmt = conn.createStatement();

      String adminsql = "CREATE TABLE  productdiscount " +
        "(discountcode  VARCHAR(100) not NULL, " +
        " discountnum INT(10), " +
        " PRIMARY KEY ( discountcode ))";

      stmt.executeUpdate(adminsql);
      System.out.println("Discount table creation successfull...");
    } catch (SQLException se) {
      
      System.out.println("DISCOUNT TABLE IS ALREAY PRESENT OR SOMETHING WRONG");
    } catch (Exception e) {
      
      e.printStackTrace();
    } finally {
      
      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {} 
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    } 

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Inserting records into the table...");
      stmt = conn.createStatement();

      String sql = "INSERT INTO productdiscount VALUES ('cafe', 50)";
      stmt.executeUpdate(sql);
      System.out.println("Discount code records into the table... Successfull");

    } catch (SQLException se) {
      
      System.out.println("Discount code already have in database TRY ANOTHER");
    } catch (Exception e) {
      
      System.out.println(" SOMETHING WRONG");
    } finally {
      
      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {} 
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    } 
  } 
}