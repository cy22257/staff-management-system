import java.sql.*;
import java.util.*;

/**
 * 月給与を計算し、表示する機能を持つクラス
 * @author 西村旬生
 */
public class Payment extends AbstractExecuter {
    private String staffID;
    private int startDate;
    private PreparedStatement st;

    @Override
    public void preQuery() {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("従業員IDを入力してください:");
            staffID = scanner.nextLine();
            if(Integer.parseInt(staffID) < 2000 || Integer.parseInt(staffID) > 2999){
                System.out.println("IDは2000番台です");
                continue;
            }
            System.out.print("勤務年月を入力してください (例: 2304):");
            startDate = Integer.parseInt(scanner.nextLine());
            break;
        }
    }

    @Override
    public String getSQLtemplate() {
        return "SELECT * FROM payment WHERE staffID=? AND startDate=?";
    }
    

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        this.st = st;
        st.setString(1, staffID);
        st.setInt(2, startDate);
    }

    private int[] monthly(int startDate) throws SQLException {
        int date = startDate;
        st.setString(1, staffID);
        int[] payment = new int[2]; // 配列を正しく初期化

        ResultSet rs3 = null;
        while (true) {
            st.setInt(2, date);
            rs3 = st.executeQuery();
            if (!rs3.next()) {
                date--;
                if(date < 0){
                    System.out.println("No Data");
                    return null; // データが見つからない場合は null を返す
                }
                if (date % 100 == 0) {
                    date = date - 88;
                }
            } else {
                break;
            }
        }
        rs3.close(); // 初期の ResultSet を閉じて競合を回避

        Connection conn = st.getConnection();
        PreparedStatement st3 = conn.prepareStatement(
            "SELECT * FROM payment WHERE staffID=? AND startDate=?",
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        st3.setString(1, staffID);
        st3.setInt(2, date);
        rs3 = st3.executeQuery();
        rs3.beforeFirst();
        rs3.next();
        int hourly = rs3.getInt("hourly");

        PreparedStatement st2 = conn.prepareStatement("SELECT * FROM staff WHERE staffID=?");
        st2.setString(1, staffID);
        ResultSet rs2 = st2.executeQuery();

        if (rs2.next()) {
            int fare = rs2.getInt("fare");
            String staffName = rs2.getString("staffName");

            PreparedStatement st4 = conn.prepareStatement("SELECT * FROM work WHERE staffID=? AND date>? AND date<?");
            st4.setString(1, staffID);
            int startDate2 = startDate * 100;
            int endDate = startDate2 + 100;
            st4.setInt(2, startDate2);
            st4.setInt(3, endDate);

            ResultSet rs = st4.executeQuery();

            double sum = 0;
            int n = 0;
            while (rs.next()) {
                int start = rs.getInt("start");
                int startm = start / 100 * 60 + start % 100;
                int end = rs.getInt("end");
                int endm = end / 100 * 60 + end % 100;
                int breakm = rs.getInt("break");
                sum = sum + (endm - startm - breakm) / 60.0 * hourly;
                n++;
            }
            int totalPayment = (int) Math.floor(sum) + fare * n;

            rs.close();
            rs2.close();
            rs3.close();
            st2.close();
            st3.close();
            st4.close();

            payment[0] = fare * n;
            payment[1] = totalPayment;
            return payment;
        } else {
            System.out.println("従業員IDが見つかりませんでした。");
            return null; // データが見つからない場合は null を返す
        }
    }

    private int[] yearly(int startDate) throws SQLException {
        int sum = 0;
        int faresum = 0;
        int[] payment = new int[2];
        int i;
        int[] paym;  
        for(i = startDate; i % 100 != 0; i--){
            paym = monthly(i);
            if (paym != null) {
                faresum = faresum + paym[0];
                sum = sum + paym[1];
            }
        }
        payment[0] = faresum;
        payment[1] = sum;
        return payment;
    }

    @Override
    public void showResult(ResultSet r) {
        // 結果の表示は setQuery メソッド内で処理される
        try {
            int[] paym = monthly(startDate);
            int[] payy = yearly(startDate);
            if (paym != null) {
                System.out.println("====" +(startDate % 100) + "月分 給与明細====");
                System.out.println("|  従業員ID:" + staffID);
                System.out.print("|  月支給額:" + paym[1] + "円");
                System.out.println("|  (内交通費:" + paym[0] + "円)");
                System.out.print("|  年支給額:" + payy[1] + "円");
                System.out.println("|  (内交通費:" + payy[0] + "円)");
                System.out.println("|  扶養控除が受けられなくなるまで：" + (1030000 - payy[1] + payy[0]) + "円");
                System.out.println("|  (勤労学生控除を受ける場合：" + (1300000 - payy[1] + payy[0]) + "円)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}