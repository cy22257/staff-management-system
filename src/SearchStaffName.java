/**
 * 従業員の名前検索
 * @author cy22272 少前祐人
 */

import java.util.*;
import java.sql.*;
import java.io.*;

public class SearchStaffName extends AbstractExecuter {
    // コマンドプロンプトでも文字化けしないようShift_JIS出力に変更
    static {
        try {
            System.setOut(new PrintStream(System.out, true, "MS932"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("エンコーディング設定に失敗しました: " + e);
        }
    }

    private Scanner scanner = new Scanner(System.in, "MS932");
    private String searchName;

    @Override
    public String getSQLtemplate() {
        return "SELECT * FROM staff WHERE staffName LIKE ?";
    }

    @Override
    public void preQuery() {
        System.out.print("検索する従業員の名前を入力してください（部分一致）：");
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
                System.out.printf("%-10s\t%-10s\t%-15s\t%-20s\t%-10s\t%-10d\n",
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
