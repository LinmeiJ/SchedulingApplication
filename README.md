#Title
SchedulingApplication

#Purpose of this app
This is a GUI-based scheduling desktop application. It is for office users only to schedule appointments for customers and save customer information, appointments and more to the MYSQL database.

#Author
Linmei Mills

#Contact Information
linmeijiang@hotmail.com

#App Versions
v2022.02.13

#Date
02/13/2022

#IDE Version Number
IntelliJ IDEA Community Edition 2021.1.1 x64

#Java Version Number
Java SE 11.4

#JavaFX
openjfx-11.0.2_windows-x64_bin-sdk
SceneBuilder-17.0.0

#Description of Additional report - report three
A report that counts the total number of customers by country name.

#MYSQL connector version number
mysql-connector-java-8.0.25

#How To Run
Please user IDE version as instructed. 
Configuration setup:
    1. On IntelliJ, select "File" on the menu bar, then select "Project Structure".
        under "project setting", please check:
        - "project": project SDK = JDK sets as 11 version 11.0.11 
        - "module": Module JDK = JDK 11
                    external jars clicks "+" and add javafx-swt, mysql-connector-java-8.0.25, javafx.fxml.jar javafx-swt.jar
        - "libraries": click the "+" to import javafx-swt and mysql-connector-java-8.0.25
    2. On IntelliJ, select "Run" on the menu bar then select the "Run Configurations"
        - Java version is 11
        - add below into the module:
            "--module-path C:\Users\LabUser\Desktop\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml"
        - work directory set as where your application locates.







