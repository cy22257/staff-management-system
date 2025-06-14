
# 勤怠管理システム（Staff Management System）

このリポジトリは、JavaおよびMySQLを用いて開発された「勤怠管理システム」のソースコードおよび関連ファイルを含みます。

## 概要

本システムは、アルバイトおよび社員の勤務情報や給与情報、所属店舗情報をデータベースと連携して一元管理できるJavaアプリケーションです。

## 実装機能一覧

本アプリケーションは以下の6機能を提供し、`StaffManagementSystem` クラスを実行することでコンソール形式で操作が可能です。

1. **全アルバイト情報表示** (`PrintAllPTJInformation.java`)  
   アルバイトの基本情報（名前、住所、電話番号、生年月日、交通費、時給など）を表示。

2. **全社員情報表示** (`PrintAllEmployeeInformation.java`)  
   社員の基本情報と給与（固定給）を表示。

3. **全店舗情報表示** (`PrintAllShopInformation.java`)  
   全店舗のID、名称、住所、電話番号などを一覧で表示。

4. **従業員名前検索** (`SearchStaffName.java`)  
   入力された文字列を部分一致で検索し、該当する従業員情報を表示。

5. **勤務情報検索** (`SearchWorkData.java`)  
   指定された従業員IDに紐づく勤務記録（出退勤時間、勤務日、休憩時間）を表示。

6. **給与明細表示** (`Payment.java`, `PaymentAlter.java`)  
   勤務時間と時給または固定給を基に給与を計算し、明細として出力。

## フォルダ構成

```
StaffManagementSystem/
├── src/                          # Javaソースコード
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
│   └── cy22257.sql               # データベース構築用SQLスクリプト
├── lib/
│   └── mysql-connector-j-8.4.0.jar  # JDBCドライバ
├── .gitignore
└── README.md
```

## 環境構築と実行手順

### 対応環境

- OS: Windows 10 / 11
- Java: JDK 17以上
- MySQL: 8.3.0（インストールはユーザーが別途行う）
- JDBCドライバ: mysql-connector-j-8.4.0.jar

### セットアップ手順

1. 任意のディレクトリに `StaffManagementSystem` を配置。

2. MySQLサーバーの起動（インストール場所に応じてパスを変更）:

```bash
"[絶対パス]/mysql-8.3.0-winx64/bin/mysqld"
```

3. MySQLクライアントを起動し、SQLを投入：

```bash
"[絶対パス]/mysql-8.3.0-winx64/bin/mysql" -u root
```

```sql
CREATE DATABASE assignment CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE assignment;
SOURCE "[絶対パス]/StaffManagementSystem/sql/cy22257.sql";
```

4. Javaファイルのコンパイル：

```bash
javac -cp ".;lib/mysql-connector-j-8.4.0.jar" src/*.java
```

5. アプリケーションの実行：

```bash
java -cp ".;lib/mysql-connector-j-8.4.0.jar;src" StaffManagementSystem
```

### 文字化け対策

文字化けが発生した場合は以下の対策を講じてください：

```bash
chcp 65001
```

または Java 実行時に次のオプションを付けてください：

```bash
java -Dfile.encoding=UTF-8 -cp ".;lib/mysql-connector-j-8.4.0.jar;src" StaffManagementSystem
```

## 注意点

- `mysql-8.3.0-winx64` フォルダはファイルサイズ制限により GitHub 上では共有できません。MySQL の公式サイトより必要なバージョンをダウンロードしてください。
- `.gitignore` により `mysql-8.3.0-winx64/` ディレクトリは除外されています。
- SQL スクリプト (`cy22257.sql`) はプロジェクトに含まれているため、DB初期化は可能です。

---

本READMEにより、任意の場所にダウンロードしたプロジェクトでも正しくセットアップ・動作確認が可能です。
