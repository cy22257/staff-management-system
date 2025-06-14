/**
 * 従業員管理システムの本体となるクラス
 * @author cy22232 川瀬慎也
 * 
 */

import java.util.*;
import java.sql.*;

public class StaffManagementSystem{
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);

        AbstractExecuter e1=new PrintAllPTJInformation();
        AbstractExecuter e2=new PrintAllEmployeeInformation();
        AbstractExecuter e3=new PrintAllShopInformation();
        AbstractExecuter e4=new SearchStaffName();
        AbstractExecuter e5=new SearchWorkData();
        AbstractExecuter e6=new Payment();

        boolean exit=false;

        while(!exit){
            System.out.println("===従業員情報管理システム===");
            System.out.println("メニューを選んでください");
            System.out.println("1:全アルバイト情報表示");
            System.out.println("2:全社員情報表示");
            System.out.println("3:全店舗情報表示");
            System.out.println("4:従業員名前検索");
            System.out.println("5:勤務情報検索");
            System.out.println("6:給与明細表示");
            System.out.println("7:終了");

            String line=scanner.nextLine();
            switch(line) {
                case "1":
                    e1.queryAndShow();
                    System.out.println("\n");
                    break;
                case "2":
                    e2.queryAndShow();
                    System.out.println("\n");
                    break;
                case "3":
                    e3.queryAndShow();
                    System.out.println("\n");
                    break;
                case "4":
                    e4.queryAndShow();
                    System.out.println("\n");
                    break;
                case "5":
                    e5.queryAndShow();
                    System.out.println("\n");
                    break;
                case "6":
                    e6.queryAndShow();
                    System.out.println("\n");
                    break;
                case "7":
                    exit=true;
                    break;
                default:
                    System.out.println("Incorrect number");
            }
		}
        System.out.println("システムを終了します");
        scanner.close();
    } 
    
}