# online-rental-system

## HOW TO RUN

IDE: IntelliJ IDEA \
Database: MySQL

**Make sure you are using Java version 15 or later.**

###### HOW TO RUN ON COMMAND PROMPT
1. Create a database in MySQL and import group19db.sql into the database.
    - Run MySQL 8.0 Command Line Client and type the following commands: \
        *mysql > CREATE DATABASE <database_name>;* \
        *mysql > USE <database_name>;* \
        *mysql > SOURCE group19db.sql;* \
            &emsp; - SourceCode/src/main/Database/group19db.sql \
            &emsp; ** If it shows error, try to include the absolute path of sql file in the command for example: \
            &emsp; &emsp; *SOURCE C:/Users/This/Desktop/Group19_TC2V/SourceCode/src/main/Database/group19db.sql*

2. Open "Main.jar" file with any archiver.

3. In the archiver, edit your database properties in the "db.properties" file and save the changes.
    - Group19_TC2V/SourceCode/src/main/Database/db.properties

4. In Command Prompt, change the directory to the folder "Group19_TC2V".

5. Type the command: \
    *java -jar Main.jar*


###### HOW TO RUN ON INTELLIJ IDEA
1. Create a database in MySQL and import group19db.sql into the database.
    - Run MySQL 8.0 Command Line Client and type the following commands: \
        *mysql > CREATE DATABASE <database_name>;* \
        *mysql > USE <database_name>;* \
        *mysql > SOURCE group19db.sql;* 

2. Open this project in IntelliJ IDEA.

3. Setup SDK from the main menu, select File | Project Structure | Project Settings | Project, select SDK version 15 or later in Project SDK.

4. Also, select SDK default for the Project language level. Click Apply.

5. Then, add library in the Libraries, click on the + icon and select Java. Select "mysql-connector-java-8.0.26.jar" which is already provided in the lib folder. Click Apply and OK.

6. Edit your database properties in the "db.properties" file and save the changes.
    - Group19_TC2V/SourceCode/src/main/Database/db.properties

7. Right-click on the Main.java, select Run 'Main.main()'.
    - Group19_TC2V/SourceCode/src/main/Main.java

Note: If the source code of project is fulled with red lines, go the File | Invalidate Caches... | Invalidate and Restart.
