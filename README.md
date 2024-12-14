The project was build to interact with Microsoft SQL Server 2022 Express.

Before you build and run the project:

- Open the script Student data and run it to create the database in Microsoft SQL Server 2022 Express.

- Go into src/sqlhandler, open the file MySQLHandler and change these attributes to your data base:

	+ private String server = ""; //Change this to your server
    	+ private String user = ""; // This is your user
    	+ private String password = ""; //This is your password
    	+ private String db = "StudentData"; //Change this if you change the data base name in MySQLHandler file
    	+ private int port = 1433; //Change the port if you use a different port

To run the project:

- Go to scr/networking and run the Server file first.

- Then go src/main and run the Main file.

Notes:
- Users that are students are allowed to view their score sheet.

- Users that are teachers are allowed to update the score for subjects and classes that they taught.

- Teachers that are admin (denoted by IsQL in the TAI_KHOAN table) are allowed to assign teaching duty.
to other teachers, they also have other functions that the user teacher would have.
