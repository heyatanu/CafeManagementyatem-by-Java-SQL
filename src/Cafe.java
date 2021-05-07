import java.sql.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cafe {

  public static void main(String[] args) {
    Store S = new Store();
    S.Cafe1();
  }
}

class Store {
  void Cafe1() {
    System.out.println("\n\t\tWELCOME TO ABC CAFE ");
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    Customer Customer1 = new Customer();
    Product Product1 = new Product();

    while (true) {
      Product1.ProductDisplay(0);
      System.out.print("\n\t1.Add item in list\n\t2.Remove item from list\n\t3.Display your list\n\t0.Exit");
      System.out.print("\nEnter your choice:- ");
      String s = in .next();
      s = s.toLowerCase();
      s = s.trim();
      if (s.equals("admin")) {
        try {
          @SuppressWarnings("resource")
          Scanner sc = new Scanner(System.in);
          System.out.print("\nEnter your username(Case Sensitive) - ");
          String adminusername = sc.nextLine();
          System.out.print("\n Enter the password(Case Sensitive) - ");
          String adminpassword = sc.nextLine();
          if (Admin.AdminCheck(adminusername, adminpassword)) {
            System.out.println("\n\t WELCOME TO ADMIN PANAL (Double check before do anything this panal is sensitive)");
            while (true) {
              System.out.print("\n\tADMIN\n\t1.Add a new item \n\t2.Remove item\n\t3.Display item list\n\t4.Update item items\n\t0.Exit");
              try {
                System.out.print("\n Enter your interest -");
                int AoptionCh = in .nextInt();
                if (AoptionCh == 1) {
                  int randid = (int)(1000 * Math.random()); //CREATE A RANDOM ID 
                  System.out.print("\n Enter item name -");
                  String itemname=in.next();
                  System.out.print("\n Enter item price -");
                  int randprice=in.nextInt();
                  System.out.print("\n Enter item discount(%) -");
                  int randdiscount=in.nextInt();
                  System.out.print("\n Enter item stck -");
                  int randstock=in.nextInt();
                  Admin.AddProduct(randid, itemname, randprice, randdiscount, randstock);
                } else if (AoptionCh == 2) {
                  try {
                    System.out.print("\nEnter the id of product you want to delete - ");
                    int checksts = in .nextInt();
                    Admin.DeleteProduct(checksts);
                  } catch (Exception e) {
                    System.out.println("\n\tINVALID...");
                    break;
                  }
                } else if (AoptionCh == 3) {
                  Product1.ProductDisplay(1);
                } else if (AoptionCh == 4) {
                  try {
                    System.out.print("\nEnter the id of product you want to update - ");
                    int checksts = in .nextInt();
                    Admin.UpdateItemDetails(checksts);
                  } catch (Exception e) {
                    System.out.println("\n\tINVALID...");
                    break;
                  }
                } else if (AoptionCh == 0) {
                  System.out.print("\n \tExiting from admin panel. ");
                  break;
                } else {
                  System.out.print("\nMaybe you enter wrong choice.");
                }
              } catch (Exception e) {
                System.out.println("\n\tINVALID...");
                break;
              }

            }

          }
        } catch (Exception e) {
          System.out.println("\n\tINVALID...");
          break;
        }
      } else if (s.equals("createtable")) {
        CreateTable.Create();
      } else {
        try {
          int ch = Integer.parseInt(s);
          if (ch == 1) {
            try {
              System.out.print("\n Enter product ID  - ");
              int pid = in .nextInt();
              System.out.print("\n Enter the quantits - ");
              int productq = in .nextInt();
              Customer1.AddToMyList(pid, productq, Product1);
            } catch (Exception e) {
              System.out.println("\n\tINVALID...");
              break;
            }
          } else if (ch == 2) {
            try {
              System.out.print("\n Enter product ID you want to remove  - ");
              int pid = in .nextInt();
              System.out.print("\n Enter the quantits you want to remove - ");
              int productq = in .nextInt();
              Customer1.RemoveFromList(pid, productq);
            } catch (Exception e) {
              System.out.println("\n\tINVALID...");
              break;
            }
          } else if (ch == 3) {
            try {
              if (Customer1.DisplayFinalList(Product1)) {
                System.out.print("\nDo you want to check out(1/0) - ");
                int checksts = in .nextInt();
                if (checksts == 1) {
                  Customer1.CheckOut(Product1);
                  break;
                } else {
                  System.out.print("\nPlease continue...");
                }
              }
            } catch (Exception e) {
              System.out.println("\n\tINVALID...");
              break;
            }

          } else if (ch == 0) {
            break;
          } else {
            System.out.println("\nNOT IN THE MENU CHECK THE MENU AGAIN");
          }
        } catch (Exception e) {
          System.out.print("NOT SUPPORTED");
        }
      }

    }
  }
}

class Product {
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/cafemanagementsystem";
  static final String USER = "root"; //USERNAME
  static final String PASS = ""; //PASSWORD
  Scanner in = new Scanner(System.in);
  Boolean CheckAvalability(int passid, int noOfProduct) { // CHEAK THE VALID ITEM
    int instock = 0;
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName("com.mysql.jdbc.Driver");
      // System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("\n\tChecking avalibility of the product...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM cafelist WHERE pid=" + passid;
      ResultSet rs = stmt.executeQuery(sql);
      int count = 0;

      while (rs.next()) {
        ++count;
        int pinstock = rs.getInt("pinstock");
        instock = pinstock;
      }
      if (count == 0 || instock < noOfProduct) {
        return false;
      }

      //          System.out.println("\n\tChecking successfull");
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException se) {
      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {
      System.out.println("\n\tERROR OCCERS");
    } finally {
      try {
        if (stmt != null)
          stmt.close();
      } catch (SQLException se2) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
    return true;
  }

  void ProductDisplay(int forwhose) { // DISPLAY THE AVALABLE ITEMS LIST 
    Connection conn = null;
    Statement stmt = null;
    int c = 0;
    try {

      Class.forName("com.mysql.jdbc.Driver");

      // System.out.println("\n\tConnecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      if (forwhose == 1) {
        System.out.println("\n\tHey admin here are your databse lists");
      } else {
        System.out.println("\nOur products ,");
      }

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM cafelist";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        c = c + 1;
        int pid = rs.getInt("pid");
        String pname = rs.getString("pname");
        int pprice = rs.getInt("pprice");
        int pdiscount = rs.getInt("pdiscount");
        int pinstock = rs.getInt("pinstock");
        System.out.print("ID: " + pid);
        System.out.print(", Item name: " + pname);
        System.out.print(", Price: " + pprice);
        System.out.print(", Discount: " + pdiscount + "%");
        if (forwhose == 1) {
          System.out.print(", Sttock: " + pinstock);
        }
        if (pinstock <= 0) {
          System.out.print(", ITEM OUT OF STOCK \n");
        } else {
          System.out.print("\n");
        }
      }
      rs.close();
      stmt.close();
      conn.close();
      //          System.out.println("\n\tData fetch successfull...");
    } catch (SQLException se) {
      System.out.println("\n\tSQL ERROR");
    } catch (Exception e) {
      System.out.println("\n\tERROR OCCERS");
    } finally {
      try {
        if (stmt != null)
          stmt.close();
      } catch (SQLException se2) {}
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      }
    }
    if (c == 0) {
      System.out.println("Menu Is Empty Ask The owner to add some");
    }
  }

  void DisplayCustomerListDetails(Integer[] myProductList, Integer[] myNoOfProducts) { // DISPLAY THE CUSTOMER MOVIE LIST THAT THEY CHOOSE
    // System.out.println("\n\tConnecting to database...");
    System.out.println("\n\tFetching data from database....\nYour List \n");
    for (int i = 0; i < myProductList.length; i++) {
      if (myNoOfProducts[i] == -9999) {
        continue;
      }
      Connection conn = null;
      Statement stmt = null;
      try {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT * FROM cafelist WHERE pid =" + myProductList[i];
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
          int pid = rs.getInt("pid");
          String pname = rs.getString("pname");
          int pprice = rs.getInt("pprice");
          int pdiscount = rs.getInt("pdiscount");
          int pinstock = rs.getInt("pinstock");
          System.out.print("ID: " + pid);
          System.out.print(", Item name: " + pname);
          System.out.print(", Price: " + pprice);
          System.out.print(", Discount: " + pdiscount + "%");

          if (pinstock <= 0) {
            System.out.print(", PRODUCT OUT OF STOCK TRY LATER \n");
          } else {
            System.out.print(", Quantity: " + myNoOfProducts[i] + "\n");
          }
        }

        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      } catch (Exception e) {
        System.out.println("\n\tERROR OCCERS");
      } finally {
        try {
          if (stmt != null)
            stmt.close();
        } catch (SQLException se2) {}
        try {
          if (conn != null)
            conn.close();
        } catch (SQLException se) {
          System.out.println("\n\tSQL ERROR");
        }
      }
    }
    //        System.out.println("\n\tData fetch successfull...");
  }

  void CustomerCheckOut(Integer[] myProductList, Integer[] myNoOfProducts) { //CHECKOUT THE CUSTOMER GENARATE BILL 
    int finalprice = 0;
    System.out.println("\nBill\nID  Price  Qantity  Discount  TotalPrice\n");
    for (int i = 0; i < myProductList.length; i++) {
      if (myNoOfProducts[i] == -9999) {
        continue;
      }
      Connection conn = null;
      Statement stmt = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
        // System.out.println("\n\tConnecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        //            System.out.println("\n\tFetching data from database....");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT * FROM cafelist WHERE pid =" + myProductList[i];
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
          int pid = rs.getInt("pid");
          int productprice = rs.getInt("pprice");
          int productstock = rs.getInt("pinstock");
          int productdiscount = rs.getInt("pdiscount");
          finalprice = finalprice + productprice;
          finalprice = finalprice * myNoOfProducts[i];
          finalprice = finalprice - ((finalprice * productdiscount) / 100);
          int newstockafterco = productstock - myNoOfProducts[i]; // UPDATE THE DATABASE AFTER SUCCESSFULL CHECKOUT
          stmt = conn.createStatement();
          String updatesql = "UPDATE cafelist SET pinstock=" + newstockafterco + " WHERE pid=" + myProductList[i];
          stmt.executeUpdate(updatesql);
          int x = productprice * myNoOfProducts[i];
          System.out.println(pid + "\t" + productprice + "\t" + myNoOfProducts[i] + "\t" + productdiscount + " \t" + (x - ((x * productdiscount) / 100)));
          //              System.out.println("\n\tYour Final price is " + finalprice);

        }

        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException se) {
        System.out.println("\n\tSQL ERROR");
      } catch (Exception e) {
        System.out.println("\n\tERROR OCCERS");
      } finally {
        try {
          if (stmt != null)
            stmt.close();
        } catch (SQLException se2) {}
        try {
          if (conn != null)
            conn.close();
        } catch (SQLException se) {
          System.out.println("\n\tSQL ERROR");
        }
      }
    }
    System.out.println("\n\tYour Final price is " + finalprice);
    //DISCOUNT
    while (true) {
      try {
        System.out.print("Do you have any Discount code(1/0) :- ");
        int disch = in .nextInt();
        if (disch == 1) {
          System.out.print("Enter code here :- ");
          String discode = in .next();
          if (Admin.DiscountCheck(discode) == -1) {
            System.out.println("\nDiscount code not valid");
            break;
          } else {
            int d = Admin.DiscountCheck(discode);
            System.out.println("\nCONGRATULATION '" + discode + "' DISCOUNT CODE IS VALID YOU GOT ADDITIONAL " + d + "% OFF.");
            finalprice = finalprice - ((finalprice * d) / 100);
            break;
          }
        } else {
          break;
        }
      } catch (Exception e) {
        System.out.println("INVALID INPUT");
        break;
      }
    }
    System.out.println("\n\tYour Grand price is " + finalprice);
    System.out.println("\n\t THANK YOU ");
  }

} //END PRODUCT

class Customer { //CUSTOMER CLASS
  Integer myProductList[] = {}; //STORE THE CUSTOMER MOVIE IDS
  Integer myNoOfProducts[] = {}; //STORE THE CUSTOMER MOVIE IDS

  void AddToMyList(int myProductId, int noOfProduct, Product product) { // ADD THE MOVIE IN CUSTOMER LIST
    if (product.CheckAvalability(myProductId, noOfProduct)) {
      System.out.println("\n\tProduct Id " + myProductId + " added to your list. With quantity " + noOfProduct + ".");
      ArrayList < Integer > myList = new ArrayList < Integer > (Arrays.asList(myProductList));
      myList.add(myProductId);
      myProductList = myList.toArray(myProductList);

      ArrayList < Integer > myList1 = new ArrayList < Integer > (Arrays.asList(myNoOfProducts));
      myList1.add(noOfProduct);
      myNoOfProducts = myList1.toArray(myNoOfProducts);
    } else {
      System.out.println("\n\tProduct ID " + myProductId + " is not avalable in our store OR It is stock out.");
    }
  }

  void RemoveFromList(int myProductId, int noOfProduct) { // REMOVE PRODUCT FROM CUSTOMER LIST
    int b = 0;
    if (myProductList.length == 0) {
      System.out.println("\n\tAdd some products in your list first");
    } else {

      for (int i = 0; i < myProductList.length; i++) {
        if (myProductList[i] == myProductId) {
          b = 1;
          if (myNoOfProducts[i] < noOfProduct) {
            b = 2;
            myProductList[i] = -9999;
            myNoOfProducts[i] = -9999;
            break;
          }
          myNoOfProducts[i] = myNoOfProducts[i] - noOfProduct;
          if (myNoOfProducts[i] <= 0) {
            myProductList[i] = -9999;
            myNoOfProducts[i] = -9999;
          }
          break;
        }
      }
      if (b == 1) {
        System.out.println("\n\t" + noOfProduct + " products of product id " + myProductId + " remove from list");
      } else if (b == 2) {
        System.out.println("\n\tAll products of product id " + myProductId + " remove from list");
      } else {
        System.out.println("\n\tProduct id " + myProductId + " No found in list");
      }
    }
  }

  boolean DisplayFinalList(Product product) {
    if (myProductList.length == 0) {
      System.out.println("\n\tYou have nothing in the list!");
      return false;
    }
    product.DisplayCustomerListDetails(myProductList, myNoOfProducts);
    return true;
  }

  void CheckOut(Product product) {
    product.CustomerCheckOut(myProductList, myNoOfProducts);
  }
}