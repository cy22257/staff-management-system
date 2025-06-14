/**
 * 検索(Query)系のSQLの実行に責任を持つクラスの雛形となる抽象クラス
 * @author Natsuko Noda
 * 
 *  modified by Shinya Kawase
 *  reason:データベース名を今回のデータベースに合うように変更した
 */

import java.sql.*;

public abstract class AbstractExecuter {
	public abstract String getSQLtemplate();
	public abstract void setQuery(PreparedStatement st) throws SQLException;
	public abstract void showResult(ResultSet r);

	public void preQuery() { } // 必要に応じてオーバーライド. 前処理がいらないならそのまま使う

	public final void queryAndShow() {
		try {
			// (必要なら)前処理 
			preQuery();

			// 接続
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/StaffManagement?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
				"root", ""
				);		 

			// SQLの前処理. getSQLtemplate() で個別のSQLを作る
			PreparedStatement st=conn.prepareStatement(getSQLtemplate());
			
			// SQLに値を埋め込む. その他、個別に必要な処理もここで行う
			setQuery(st);

			// SQLを実行して結果を得る.
			ResultSet res=st.executeQuery();

			// 結果を用いた個別処理
			showResult(res);

			// 終了処理
			res.close();
			st.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}
