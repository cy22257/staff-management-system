/*
 * 従業員の情報を表示するクラス
 * @author 津田直輝
 */



import java.util.*;
import java.sql.*;

public class PrintAllEmployeeInformation extends AbstractExecuter {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getSQLtemplate() {
        
        return "SELECT employee.staffID, staff.staffName, staff.phoneNumber, staff.address, staff.birthDate, staff.fare, employee.position,employee.salary " +
               "FROM employee " + 
               "LEFT JOIN Staff ON employee.staffID = Staff.staffID";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
       
    }

    @Override
    public void showResult(ResultSet r) {
        try {
           
            System.out.printf("%-10s\t%-10s\t%-15s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n", 
                "staffID", "staffName", "phoneNumber", "address", "birthDate", "fare", "position","salary");

            while (r.next()) {
                System.out.printf("%-10s\t%-10s\t%-15s\t%-10s\t%-10s\t%-10d\t%-10s\t%-10d\n",
                        r.getString("staffID"),
                        r.getString("staffName"),
                        r.getString("phoneNumber"),
                        r.getString("address"),
                        r.getString("birthDate"),
                        r.getInt("fare"),
                        r.getString("position"),
                        r.getInt("salary")
                );
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " "
                + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    
}
