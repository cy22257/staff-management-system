/**
 *全アルバイトの情報を表示するためのクラス
 *@author cy22232 川瀬慎也
 */

import java.util.*;
import java.sql.*;

public class PrintAllPTJInformation extends AbstractExecuter {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getSQLtemplate() {
        // 正しいSQLクエリをスペースを含めて記述する
        return "SELECT ptj.staffID, staff.staffName, staff.phoneNumber, staff.address, staff.birthDate, staff.fare, ptj.position " +
               "FROM ptj " + 
               "LEFT JOIN Staff ON ptj.staffID = Staff.staffID";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        // 特定の値を埋め込む必要がないので、特に何もしない
    }

    @Override
    public void showResult(ResultSet r) {
        try {
            // ヘッダーを表示

            /*
            配列の大きさより小さい箇所があるため文字列を破壊しまう可能性あり
            今回は横一列で表示するために配列の大きさを調整した
            */
            System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-15s\n", 
                "staffID", "staffName", "phoneNumber", "address", "birthDate", "fare", "position");

            while (r.next()) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10d\t%-15s\n",
                        r.getString("staffID"),
                        r.getString("staffName"),
                        r.getString("phoneNumber"),
                        r.getString("address"),
                        r.getString("birthDate"),
                        r.getInt("fare"),
                        r.getString("position")
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
