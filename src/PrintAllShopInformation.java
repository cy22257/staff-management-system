/**
 店舗の情報を表示するクラス
 @author津田直輝
 */





import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PrintAllShopInformation extends AbstractExecuter {
   private Scanner scanner;

   public PrintAllShopInformation() {
      this.scanner = new Scanner(System.in);
   }

   public String getSQLtemplate() {
      return "SELECT shopID, shopName, phoneNumber, shop.address FROM shop" ;
   }

   public void setQuery(PreparedStatement var1) throws SQLException {
   }

   public void showResult(ResultSet var1) {
      PrintStream var10000;
      String var10001;
      try {
         System.out.printf("%-10s\t%-20s\t%-15s\t%-20s\n", "shopID", "shopName", "phoneNumber", "address");

         while(var1.next()) {
            System.out.printf("%-10s\t%-10s\t%-15s\t%-10s\n", var1.getString("shopID"), var1.getString("shopName"), var1.getString("phoneNumber"), var1.getString("address"));
         }
      } catch (SQLException var3) {
         var10000 = System.out;
         var10001 = var3.toString();
         var10000.println("SQL Error: " + var10001 + " " + var3.getErrorCode() + " " + var3.getSQLState());
      } catch (Exception var4) {
         var10000 = System.out;
         var10001 = var4.toString();
         var10000.println("Error: " + var10001 + var4.getMessage());
      }

   }

   
}

