# 勤怠管理システム（Staff Management System）

このリポジトリは、アルバイトや社員の勤怠・給与情報を管理するシステム「勤怠管理システム」のソースコードおよび関連ファイルを含みます。

## 概要

本システムは、以下のような機能を備えたデータベース連携型のJavaアプリケーションです：

## 実装機能一覧

以下の6つの機能を提供します。`StaffManagementSystem` クラスを実行すると、対話形式でこれらの機能を選択できます。

1. **全アルバイト情報表示**  
   パート・アルバイト従業員の全情報（氏名、連絡先、住所、生年月日、交通費など）を一覧表示します。

2. **全社員情報表示**  
   正社員の全情報（上記と同様）を一覧表示します。

3. **全店舗情報表示**  
   店舗の基本情報（店舗名、住所、電話番号など）を一覧表示します。

4. **従業員名前検索**  
   入力された文字列に一致する従業員の名前で部分一致検索し、該当情報を表示します。

5. **勤務情報検索**  
   従業員IDをもとに、その従業員の勤務記録（勤務日、開始時刻、終了時刻など）を表示します。

6. **給与明細表示**  
   従業員IDをもとに、勤務時間と時給から給与を計算し、給与明細として表示します。

## フォルダ構成

```
StaffManagementSystem/
├── src/                          # Javaソースファイル
│   ├── StaffManagementSystem.java
│   ├── AbstractExecuter.java
│   ├── PrintAllPTJInformation.java
│   ├── PrintAllEmployeeInformation.java
│   ├── PrintAllShopInformation.java
│   ├── SearchStaffName.java
│   ├── SearchWorkData.java
│   ├── Payment.java
│   └── PaymentAlter.java
├── sql/
│   └── cy22257.sql               # データベース構築用SQL
├── lib/
│   └── mysql-connector-j-8.4.0.jar  # JDBCドライバ（MySQL）
├── mysql-8.3.0-winx64/           # MySQL本体
└── README.md
```

## 実行環境

- OS: Windows 10/11
- JDK: 17以上
- MySQL: 8.3.0
- コマンドプロンプト使用（PowerShellは非推奨）

## セットアップ手順（初回のみ）

1. 任意のディレクトリにプロジェクトフォルダ（`StaffManagementSystem`）を配置。

2. MySQLサーバーを起動する（初回必須）  
   以下の手順で、MySQLのサーバー（`mysqld`）を起動します：

   ```cmd
   cd StaffManagementSystem/mysql-8.3.0-winx64/bin
   mysqld
   ```

   ※黒い画面で止まったままになりますが、それで正常です。閉じずに次の手順へ進んでください。

3. 新しいコマンドプロンプトをもう一つ開き、以下を実行してMySQLに接続：

   ```cmd
   cd StaffManagementSystem/mysql-8.3.0-winx64/bin
   mysql -u root
   ```

4. データベースとテーブルを作成：

   ```sql
   CREATE DATABASE assignment CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   USE assignment;
   SOURCE ../../sql/cy22257.sql;
   ```

5. Javaソースをコンパイル：

   ```cmd
   cd C:/Users/yourname/Desktop/StaffManagementSystem
   javac -cp ".;lib/mysql-connector-j-8.4.0.jar" src/*.java
   ```

6. メインプログラムを実行：

   ```cmd
   java -cp ".;lib/mysql-connector-j-8.4.0.jar;src" StaffManagementSystem
   ```

## 注意点

- 文字化けする場合、コマンドプロンプトで `chcp 65001` を実行するか、ファイルエンコーディング設定を `UTF-8` に統一してください。
- JDBCドライバは `lib` に配置済みであり、特別な設定は不要です。
- 初回実行時にDB作成と初期データ投入を行ってください。
