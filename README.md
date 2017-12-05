* This is an Android Application where you can use this to do CRUD methods on a Database running on the server.

* You can use this App for following uses :

1] Create/Drop Tables
2] Alter Tables
3] Insert Rows
4] To view data stored in row 
5] Execute any SQL command on Database on local server and Shows the results in Android app.

* How does it work ?

    User sets an IP address of local server in the android app. 
    Then the SQL_QUERIES entered by user is sent to the local server where the PHP scripts are used to receive the GET/POST methods and executes the QUERY on database which is given in the php file /PHP_scripts/dbms/include/DbConnect.php 
    Then the result given after executing the SQL_QUERY on the database the output is sent back to the Client in JSON format.
    After receiving the JSON data it is parced into usable data and then the UI is updated accordingly.

* How to run ?

    In order to run the Project you should have a XAMPP server installed and setted up on your PC.
    After installing XAMPP server create an empty Database using phpmyadmin to your personal computer and then set the credentials of your SQL databse of XAMPP server in the /PHP_scripts/dbms/include/DbConnect.php file line 24 in format given on line 23 
    After installing the XAMPP server copy the /PHP_scripts/dbms folder to your XAMPP working directory so that you can access the folder through the url : http(s)://localhost:<port>/dbms/
    Then install the app-debug.apk file located in /Android_App/DBMS/app/build/outputs/apk folder
    Before running the app make sure both Android device and your PC are in a LAN connection and find out the LAN ipv4 address of your PC.
    And enter the address in the android app in settings section.
    After setting the address the app is ready to use for querying on the database.

    Hooray !!!! You are ready to use the app ...


