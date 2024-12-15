Dự án được xây dựng để tương tác với Microsoft SQL Server 2022 Express.

Dự án chỉ hỗ trợ tiếng Việt.

Các tính năng chính:

- Xem danh sách học sinh trong lớp

- Xem bảng điểm

- Cập nhật điểm số của học sinh

- Phân công giảng dạy

Trước khi bạn xây dựng và chạy dự án:

- Mở tập lệnh Student data và chạy nó để tạo cơ sở dữ liệu trong Microsoft SQL Server 2022 Express.

- Vào thư mục src/sqlhandler, mở tệp MySQLHandler và thay đổi các thuộc tính này thành cơ sở dữ liệu của bạn:

	+ private String server = ""; //Change this to your server
    	+ private String user = ""; // This is your user
    	+ private String password = ""; //This is your password
    	+ private String db = "StudentData"; //Change this if you change the data base name in MySQLHandler file
    	+ private int port = 1433; //Change the port if you use a different port

Để chạy dự án:

- Đi đến scr/networking và chạy tệp Server trước.

- Sau đó, vào thư mục src/main và chạy tệp Main.

Ghi chú:

- Tên người dùng sử dụng để đăng nhập sẽ là mã của người dung 
(*Xem cơ sỡ dữ liệu ở bảng TAI_KHOAN và bảng HOC_SINH, GIAO_VIEN để biết chi tiết hơn):

	+ VD: GV0001 sẽ có tài khoản TK0005 và mật khẩu là abc222, trong đó GV0001 sẽ là tên đăng nhập còn abc222 sẽ là mật khẩu

- Người dùng là sinh viên chỉ được phép xem bảng điểm của riêng mình.

- Người dùng là giáo viên chỉ được phép cập nhật điểm cho các môn học và lớp học mà họ đã dạy.

- Giáo viên là quản lý (được ký hiệu bằng IsQL trong bảng TAI_KHOAN) được phép phân công nhiệm vụ giảng dạy.
cho các giáo viên khác, họ cũng có các chức năng khác mà giáo viên người dùng sẽ có.

--------------------------------------------------------------------------------------------------------------------------
The project was build to interact with Microsoft SQL Server 2022 Express.

The project only supports Vietnamese.

Main features:

- View the list of students in the class

- View the score sheet

- Update students scores

- Assign teaching duty

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

- The username used for login will be the user's code 
(*Refer to the database in the TAI_KHOAN and HOC_SINH, GIAO_VIEN tables for more details):

	+ Example: GV0001 will have an account TK0005 and a password of abc222, where GV0001 will be the username and abc222 will be the password.

- Users that are students are only allowed to view their own score sheet.

- Users that are teachers are only allowed to update the score for subjects and classes that they taught.

- Teachers that are admin (denoted by IsQL in the TAI_KHOAN table) are allowed to assign teaching duty.
to other teachers, they also have other functions that the user teacher would have.
