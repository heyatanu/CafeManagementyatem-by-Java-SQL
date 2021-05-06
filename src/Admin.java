import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/cafemanagementsystem";
  static final String USER = "root";
  static final String PASS = "";

  public static void AddProduct(int pid, String pname, int pprice, int pdiscount, int pinstock) {
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName("com.mysql.jdbc.Driver");

      //          System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("\n\tTry to add this to database...");

      stmt = conn.createStatement();

      String sql = "INSERT INTO cafelist " +
        "VALUES (" + pid + ",'" + pname + "'," + pprice + "," + pdiscount + "," + pinstock + ")";

      stmt.executeUpdate(sql);
      System.out.println("\n\tProduct id " + pid + " inserted into the table successfull..");

    } catch (SQLException se) {
      System.out.println("\n\tProduct Id " + pid + " is already present or you pass wrong input please check------Insert Failed");
    } catch (Exception e) {

      System.out.println("\n\tERROR OCCERS");
    } finally {

      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
  } //END ADD PRODUCT

  public static void DeleteProduct(int passid) {
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName("com.mysql.jdbc.Driver");

      //          System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("\n\tTry to delete this from database.....");
      stmt = conn.createStatement();
      String sql = "DELETE FROM cafelist " +
        "WHERE pid = " + passid;
      stmt.executeUpdate(sql);
      System.out.println("\n\tProduct ID " + passid + " delete from database successfull.....");
    } catch (SQLException se) {

      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {

      System.out.println("\n\tERROR OCCERS");
    } finally {

      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
  } //END REMOVE PRODUCT

  public static void UpdateItemDetails(int passid) {
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName("com.mysql.jdbc.Driver");

      //          System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("\n\tTrying to update this from database.....");
      try {

        System.out.print("\n\t1.Update the item name\n\t2.Update the item price" +
          "\n\t3.Update the item discount\n\t4.Update the product stock" + "\n\t0.Exit");
        System.out.print("\nUpdate records into the table...\nEnter the upate value no-");
        int ch = in .nextInt();
        if (ch == 1) {
          System.out.print("Enter the new item name-");
          String pname = in .next();
          stmt = conn.createStatement();
          String sql = "UPDATE cafelist SET pname = '" + pname + "' WHERE pid=" + passid;
          stmt.executeUpdate(sql);
          System.out.println("\n\tItem name for item id " + passid + " updated to " + pname + "   Successfull...");
        } else if (ch == 2) {
          System.out.print("Enter the item new price -");
          int pprice = in .nextInt();
          stmt = conn.createStatement();
          String sql = "UPDATE cafelist SET pprice=" + pprice + " WHERE pid=" + passid;
          stmt.executeUpdate(sql);
          System.out.println("\n\tItem price for item id " + passid + " updated to " + pprice + "   Successfull...");
        } else if (ch == 3) {
          System.out.print("Enter the item new discocunt - ");
          int pdiscount = in .nextInt();
          stmt = conn.createStatement();
          String sql = "UPDATE cafelist SET pdiscount=" + pdiscount + " WHERE pid=" + passid;
          stmt.executeUpdate(sql);
          System.out.println("\n\tItem discocunt for item id " + passid + " updated to " + pdiscount + "   Successfull...");
        } else if (ch == 4) {
          System.out.print("Enter the product new stock -");
          int pinstock = in .nextInt();
          stmt = conn.createStatement();
          String sql = "UPDATE cafelist SET pinstock=" + pinstock + " WHERE pid=" + passid;
          stmt.executeUpdate(sql);
          System.out.println("\n\tItem stock for item id " + passid + " updated to " + pinstock + "   Successfull...");
        } else {
          System.out.println("\n\tWrong Input Cheak Menu Once");
        }
      } catch (Exception e) {
        System.out.println("\n\tINVALID...");
      }
    } catch (SQLException se) {

      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {

      System.out.println("\n\tERROR OCCERS");
    } finally {

      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
  } //UPDATE ITEM END

  public static boolean AdminCheck(String username, String UPassword) {

    Connection conn = null;
    Statement stmt = null;
    username = username.trim();
    UPassword = UPassword.trim();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      //          System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("\n\tCheck the admin status ...");
      stmt = conn.createStatement();
      String sql = "select * from cafeadmin where adminname='" + username + "'";
      ResultSet rs = stmt.executeQuery(sql);
      int c = 0;
      while (rs.next()) {
        c = c + 1;
      }
      if (c == 0) {
        System.out.println("\n\t ADMIN NOT FOUND");
        return false;
      } else {
        sql = "select * from cafeadmin where adminname='" + username + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {

          String adminname = rs.getString("adminname");
          String password = rs.getString("password");
          if (username.equals(adminname)) {
            if (UPassword.equals(password)) {
              System.out.println("\n\tSUCCESSFULL LOGIN");
              return true;
            } else {
              System.out.println("\n\t WRONG PASSWORD");
              return false;
            }
          }

        }
      }

    } catch (SQLException se) {

      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {

      System.out.println("\n\tERROR OCCERS");
    } finally {

      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
    return false;
  } //ADMIN CHECK END

  public static int DiscountCheck(String discode) {
    discode = discode.toLowerCase();
    Connection conn = null;
    Statement stmt = null;
    discode = discode.toUpperCase();
    discode = discode.trim();
    try {

      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      stmt = conn.createStatement();
      String sql = "select * from productdiscount where discountcode='" + discode + "'";
      ResultSet rs = stmt.executeQuery(sql);
      int c = 0;
      while (rs.next()) {
        c = c + 1;
      }
      if (c == 0) {
        //              System.out.println("\n\t DISCOUNT CODE NOT FOUND");
        return -1;
      } else {
        sql = "select * from productdiscount where discountcode='" + discode + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          int dis = rs.getInt("discountnum");
          return dis;
        }
      }

    } catch (SQLException se) {

      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {

      System.out.println("\n\tERROR OCCERS");
    } finally {

      try {
        if (stmt != null)
          conn.close();
      } catch (SQLException se) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
    return -1;
  }

}