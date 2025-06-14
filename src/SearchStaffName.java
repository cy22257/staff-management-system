/**
 *�]�ƈ��̖��O����
 *@author cy22272 ���O�S�l
 */

import java.util.*;
import java.sql.*;

public class SearchStaffName extends AbstractExecuter {
    private Scanner scanner = new Scanner(System.in);
    private String searchName;

    @Override
    public String getSQLtemplate() {
        return "SELECT * FROM staff WHERE staffName LIKE ?";
    }

    @Override
    public void preQuery() {
        System.out.println("��������]�ƈ��̖��O����͂��Ă��������i������v�j�F");
        searchName = scanner.nextLine();
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        st.setString(1, "%" + searchName + "%");
    }

    @Override
    public void showResult(ResultSet r) {
        try {
            System.out.printf("%-10s\t%-15s\t%-15s\t%-20s\t%-10s\t%-10s\n", 
                "staffID", "staffName", "phoneNumber", "address", "birthDate", "fare");

            while (r.next()) {
                System.out.printf("%-10s\t%-15s\t%-15s\t%-20s\t%-10s\t%-10d\n",
                        r.getString("staffID"),
                        r.getString("staffName"),
                        r.getString("phoneNumber"),
                        r.getString("address"),
                        r.getString("birthDate"),
                        r.getInt("fare")
                );
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " "
                + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SearchStaffName searchStaff = new SearchStaffName();
        searchStaff.queryAndShow();
    }
}