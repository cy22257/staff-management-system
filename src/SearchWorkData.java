/**
 * 従業員の勤務情報を２パターンの検索方法から検索し表示する役割を持つクラス
 * StaffIDと年月による検索、ShopIDと日付から検索ができる
 * 
 * @author cy22232 川瀬慎也
 */


import java.util.*;
import java.sql.*;


public class SearchWorkData extends AbstractExecuter {
    private String staffID;
    private String yearMonth;
    private String shopID;
    private String date;
    private int option;

    @Override
    public void preQuery() {
        Scanner scanner = new Scanner(System.in);
        // ユーザーにメニューオプションを表示
        System.out.println("メニューを選んでください");
        System.out.println("1: StaffIDと年月から検索");
        System.out.println("2: ShopIDと日付から検索");
        option = scanner.nextInt();
        scanner.nextLine(); 

        // ユーザーの選択に応じて入力を求める
        if (option == 1) {
            System.out.print("staffIDを入力してください:");
            staffID = scanner.nextLine();
            System.out.print("年月を入力してください(例:2024年6月→2406):");
            yearMonth = scanner.nextLine();
        } else if (option == 2) {
            System.out.print("shopIDを入力してください:");
            shopID = scanner.nextLine();
            System.out.print("日付を入力してください(例:2024年6月6日→240606):");
            date = scanner.nextLine();
        }
    }

    @Override
    public String getSQLtemplate() {
        // 選択に応じたSQLクエリを返す
        if (option == 1) {
            return "SELECT shopID, staffID, date, start, end, break FROM work WHERE staffID = ? AND date DIV 100 = ?";
        } else {
            return "SELECT shopID, staffID, date, start, end, break FROM work WHERE shopID = ? AND date = ?";
        }
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        // 選択に応じてPreparedStatementに値を設定
        if (option == 1) {
            st.setString(1, staffID);
            st.setInt(2, Integer.parseInt(yearMonth));
        } else {
            st.setString(1, shopID);
            st.setInt(2, Integer.parseInt(date));
        }
    }

    @Override
    public void showResult(ResultSet res) {
        try {
            //結果のヘッダーの表示
            System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n", 
                "shopID", "staffID", "date", "start", "end", "break");

            //行ごとに表示
            while (res.next()) {
                String shopID = res.getString("shopID");
                String staffID = res.getString("staffID");
                int date = res.getInt("date");
                int start = res.getInt("start");
                int end = res.getInt("end");
                int breakTime = res.getInt("break");

                // 日付を読みやすく修正
                String dateStr = String.format("%d-%02d-%02d", date / 10000, (date / 100) % 100, date % 100);
                // 結果を整形して表示
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10d\t%-10d\t%-10d\n", 
                    shopID, staffID, dateStr, start, end, breakTime);
            }
        } catch (SQLException e) {
            // SQLエラーの表示
            System.out.println("SQL Error in showResult: " + e.toString() + " " + e.getErrorCode() + " " + e.getSQLState());
        }
    }
}