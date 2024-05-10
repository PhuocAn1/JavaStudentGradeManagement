/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sqlhandler;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import school.Diem;

/**
 *
 * @author ADMIN
 */
public class MySQLHandler {
    private String server = "GIGABYTE\\SQLEXPRESS";
    private String user = "sa";
    private String password = "c9q9md";
    private String db = "StudentData_Test";
    private int port = 1433;

    private SQLServerDataSource ds;
    private Connection conn;
    private ResultSet resultSet = null;
    private Statement statement = null;
    
    public MySQLHandler() {
        
    }
    
    public SQLServerDataSource getDs() {
        return ds;
    }

    public void setDs(SQLServerDataSource ds) {
        this.ds = ds;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    public void makeConnection() {
        ds = new SQLServerDataSource();
        
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        ds.setTrustServerCertificate(true);
        
        try {
            conn = ds.getConnection();
            try {
                statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException ex) {
                Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean executeStatement(String SQL_Statement) {
        //Note use execute to execute statement
        boolean success = false;
        try {
            statement.execute(SQL_Statement);
            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return success;
    }
    
    public boolean executeQuery(String SQL_Query) {
        boolean success = false;
        try {
            this.resultSet = statement.executeQuery(SQL_Query);
            /*
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
             */
            
            if (resultSet.next()) {
                success = true;
                //System.out.println(resultSet.getString(1));
            } 
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return success;
    }
    
    public boolean executeUpdate(String SQL_Update) {
        //Use when INSERT, DELETE, UPDATE or SQL DDL statement
        boolean success = false;
        
        try {
            statement.executeUpdate(SQL_Update);
            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return success;
    }
    
    public boolean getBangDiem(String MaHS, String NamHoc, String HocKy) {
        boolean success = false;
        
        String SQL_Statement = "select M.TenMon, D.Mieng, D.[15Phut], D.[1Tiet], D.GiuaKy, D.CuoiKy, D.DiemTB, HK.HocKy, N.NamHoc " +
                               "from HOC_SINH as H, DIEM as D, MON_HOC as M, HOC_KY as HK, NAM_HOC as N " +
                               "where H.MaHS = D.MaHS " +
                               "and H.MaHS = '" + MaHS + "' COLLATE SQL_Latin1_General_CP1_CS_AS " +
                               "and D.MaMon = M.MaMon " +
                               "and D.HocKy = HK.HocKy " +
                               "and HK.HocKy = " + HocKy + " " +
                               "and D.NamHoc = N.NamHoc " +
                               "and N.NamHoc = '" + NamHoc + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        if (executeQuery(SQL_Statement)) {
            success = true;
        }
        
        return success;
    }
    
    public boolean getNamHoc() {
        boolean success = false;
        String SQL_Statement = "select * from NAM_HOC";
        
        if (executeQuery(SQL_Statement)) {
            success = true;
        } 
        
        return success;
    }
    
    public boolean getClassInfo(String classID) {
        boolean success = false;
        //Check database to get the info of the given class ID
        //Step 1: create a query statement
        String SQL_Statement = "select H.* "
                + "from HOC_SINH as H, LOP_HOC as L "
                + "where H.MaLop = L.MaLop and L.MaLop = '" + classID + "' ";
        
        try {
            //Step 2: exectute query
            if (executeQuery(SQL_Statement) && resultSet.next()) {
                /*
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
                */
                success = true;
                resultSet.first();
            } else {
                System.out.println("Unable to execute statement");
                success = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean getGVChuNhiem(String classID) {
        boolean success = false;
        String SQL_Statement = "select G.*"
                + " from GIAO_VIEN as G, CHU_NHIEM as C"
                + " where G.MaGV = C.MaGV and C.MaLop = '" + classID + "'";
        
        if (executeQuery(SQL_Statement)) {
            success = true;
        } else {
            System.out.println("Unable to find GV chu nhiem");
            success = false;
        }
        return success;
    }
    
    public boolean getMonHocDay(String MaGV) {
        boolean success = false;
        String SQL_Statement = "select *"
                + " into temp"
                + " from DAY as D"
                + " where MaGV = '" + MaGV + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + " "
                + " select distinct M.MaMon, M.TenMon"
                + " from temp as T, MON_HOC as M"
                + " where T.MaMon = M.MaMon COLLATE SQL_Latin1_General_CP1_CS_AS"
                + " "
                + " drop table temp";
        
        if(executeQuery(SQL_Statement)) {
            success = true;
        }
        
        return success;
    }
    
    public boolean getDSLopDay(String MaMon) {
        boolean success = false;
        
        String SQL_Statement = "select distinct D.MaLop"
                + " from MON_HOC as M, DAY as D"
                + " where M.MaMon = D.MaMon"
                + "	and D.MaMon = '" + MaMon + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        if (executeQuery(SQL_Statement)) {
            success = true;
        }
        
        return success;
    }
    
    public boolean getHocSinh(String MaHS) {
        boolean success = false;
        
        String SQL_Statement = "select H.HoTen, H.HanhKiem"
                + " from HOC_SINH as H"
                + " where H.MaHS = '" + MaHS + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        if (executeQuery(SQL_Statement)) {
            success = true;
        } else {
            System.out.println("MaHS khong ton tai!!!");
        }
        
        return success;
    }
    
    public boolean isQuanLy(String userName) {
        //Khong can kiem tra password do ham duoc goi sau ham kiem tra dang nhap
        boolean isQL = false;
        String SQL_Statement = "select G.MaGV, T.IsQL"
                + " from GIAO_VIEN as G, TAI_KHOAN as T"
                + " where G.MaTK = T.MaTK COLLATE SQL_Latin1_General_CP1_CS_AS"
                + " and G.MaGV = '" + userName + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        executeQuery(SQL_Statement);
        
        try {
            resultSet.beforeFirst();
            
            if (resultSet.next()) {
                if (resultSet.getInt("isQL") == 1) {
                    System.out.println("La quan ly");
                    isQL = true;
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isQL;
    }
    
    public boolean checkLogin(String userName, String password) {
        //Check database to see if userName or password if correct
        //Return false if fail
        boolean success = false;
            
        try {
            
            //Step1: create a temp table to store all the info of all the account in the database
            String SQL_Statement = "select P.MaPH as ID, T.MaTK as MaTK, T.Password "
                    + "into temp "
                    + "from PHU_HUYNH as P, TAI_KHOAN as T "
                    + "where P.MaTK = T.MaTK "
                    + "union "
                    + "select H.MaHS as ID, T.MaTK as MaTK, T.Password "
                    + "from HOC_SINH as H, TAI_KHOAN as T "
                    + "where H.MaTK = T.MaTK "
                    + "union "
                    + "select G.MaGV as ID, T.MaTK as MaTK, T.Password "
                    + "from GIAO_VIEN as G, TAI_KHOAN as T "
                    + "where G.MaTK = T.MaTK ";
            if (!executeStatement(SQL_Statement)) {
                System.out.println("Unable to execute statement");
                SQL_Statement = "drop table temp";
                executeStatement(SQL_Statement);
                return false;
            }
            
            //Step2: check to see if username exist or password is correct then drop the table afterward
            SQL_Statement = "select * from temp "
                    + "where temp.ID = '" + userName + "'" + " COLLATE SQL_Latin1_General_CP1_CS_AS" + " and temp.Password = '" + password + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
             
            if (!executeQuery(SQL_Statement)) {
                System.out.println("Unable to execute query");
                SQL_Statement = "drop table temp";
                executeStatement(SQL_Statement);
                return false;
            }
            resultSet = statement.executeQuery(SQL_Statement);
            
            if (resultSet.next()) { //The account userName and password is correct
                success = true;
            } else {
                success = false;
            }
            
            SQL_Statement = "drop table temp";
            if (!executeStatement(SQL_Statement)) {
                System.out.println("Unable to execute statement");
                return false;
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return success;
    }
    
    public void getDiemFull(String MaHS, String MaMon, String NamHoc, int HocKy) {
        //Lay diem cua mon do theo tung hoc sinh
        
        String SQL_Statement = "select D.MaHS, H.HoTen, D.Mieng, D.[15Phut], D.[1Tiet], D.GiuaKy, D.CuoiKy, D.DiemTB "
                + " from DIEM as D, MON_HOC as M, HOC_SINH as H"
                + " where D.MaMon = M.MaMon COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.MaMon = '" + MaMon + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.MaHS = '" + MaHS +  "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.MaHS = H.MaHS COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.NamHoc = '" + NamHoc + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.HocKy =" + HocKy;
        
        if (executeQuery(SQL_Statement)) {
            
        } else {
            System.out.println("Loi khong lay duoc Diem FULL!!!");
        }
    }
    
    public JSONArray xemLaiBangDiem(JSONArray jsonArray) {
        JSONArray xemLaiDiemJSONArray = new JSONArray();
        
        String MaHS, MaMon, NamHoc;
        int HocKy;
        
        for (int i = 0; i < jsonArray.length(); i++) {
            MaHS = jsonArray.getJSONObject(i).getString("MaHS");
            MaMon = jsonArray.getJSONObject(i).getString("MaMon");
            NamHoc = jsonArray.getJSONObject(i).getString("NamHoc");
            HocKy = jsonArray.getJSONObject(i).getInt("HocKy");
            
            getDiemFull(MaHS, MaMon, NamHoc, HocKy);
            
            try {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    JSONObject Diem = new JSONObject();
                    
                    Diem.put("MaHS", resultSet.getString("MaHS"));
                    Diem.put("HoTen", resultSet.getString("HoTen"));
                    Diem.put("Mieng", resultSet.getFloat("Mieng"));
                    Diem.put("15Phut", resultSet.getFloat("15Phut"));
                    Diem.put("1Tiet", resultSet.getFloat("1Tiet"));
                    Diem.put("GiuaKy", resultSet.getFloat("GiuaKy"));
                    Diem.put("CuoiKy", resultSet.getFloat("CuoiKy"));
                    Diem.put("DiemTB", resultSet.getFloat("DiemTB"));
                    
                    xemLaiDiemJSONArray.put(Diem);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return xemLaiDiemJSONArray;
    }
    
    public Diem getDiem(String MaHS, String MaMon, int HocKy, String NamHoc) {
        Diem temp = null;
        
        String SQL_Statement = "select  * "
                + " from DIEM as D"
                + " where D.MaHS = '" + MaHS + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.MaMon = '" + MaMon + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.NamHoc = '" + NamHoc + "' COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	and D.HocKy = '" + HocKy + "'";
        
        if (executeQuery(SQL_Statement)) {
            try {
                temp = new Diem(resultSet.getString("MaHS"),
                        resultSet.getString("MaMon"),
                        resultSet.getString("HocKy"),
                        resultSet.getString("NamHoc"),
                        resultSet.getFloat("Mieng"),
                        resultSet.getFloat("15Phut"), 
                        resultSet.getFloat("1Tiet"), 
                        resultSet.getFloat("GiuaKy"), 
                        resultSet.getFloat("CuoiKy"), 
                        resultSet.getFloat("DiemTB"));
            } catch (SQLException ex) {
                Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Loi khong lay duoc Diem!!!");
        }
        return temp;
    }
    
    public boolean updateDiem(String MaHS, String MaMon, String LoaiDiem, float soDiem, String NamHoc, int HocKy) {
        boolean success = false;
        Diem temp;
        
        temp = getDiem(MaHS, MaMon, HocKy, NamHoc);

        if (temp == null) {
            return false;
        }

        temp.capNhatDiem(LoaiDiem, soDiem);
        
        String SQL_Statement = "update DIEM"
                + " set [" + LoaiDiem + "] = " + Float.toString(soDiem)
                + " , DiemTB" + " = " + temp.getDiemTB()
                + " where DIEM.MaHS = '" + MaHS + "' COLLATE SQL_Latin1_General_CP1_CS_AS "
                + " and DIEM.MaMon = '" + MaMon + "' COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        if (executeUpdate(SQL_Statement)) {
            success = true;
        } else {
            System.out.println("Khong the cap nhat diem!!!");
        }
        
        return success;
    }
    
    public void getAllGiaoVien() {
        String SQL_Statement = "select G.MaGV, G.HoTen, G.GioiTinh"
                + " from GIAO_VIEN as G";
        
        if (executeQuery(SQL_Statement) == false) {
            System.out.println("Khong the lay danh sach cac giao vien!!!!");
        }
    }
    
    public void getAllLopHoc() {
        String SQL_Statement = "select L.MaLop"
                + " from LOP_HOC as L";
        
        if (executeQuery(SQL_Statement) == false) {
            System.out.println("Khong the lay danh sach cac lop hoc!!!!");
        }
    }
    
    public void phanCongGD(String MaGV, String MaMon, String MaLop) {
        String SQL_Statement = "insert into DAY"
                + " values ('" + MaGV + "', '" + MaMon + "', '" + MaLop + "')";
        
        if (executeUpdate(SQL_Statement) == false) {
            System.out.println("Khong the them giang day!!!!");
        }
    }
    
    public void getLichGD() {
        String SQL_Statement = "select D.MaGV, G.HoTen, M.TenMon, D.MaLop "
                + " from DAY as D, MON_HOC as M, GIAO_VIEN as G, LOP_HOC as L"
                + " where D.MaGV = G.MaGV COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	 and M.MaMon = D.MaMon COLLATE SQL_Latin1_General_CP1_CS_AS"
                + "	 and L.MaLop = D.MaLop COLLATE SQL_Latin1_General_CP1_CS_AS";
        
        if (executeQuery(SQL_Statement) == false) {
            System.out.println("Khong lay duoc lich giang day!!!!");
        }
    }
    
    public static void main(String args[]) throws SQLException, UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF8"));
        MySQLHandler sqlHandler = new MySQLHandler();
        sqlHandler.makeConnection();
        
        sqlHandler.getLichGD();
        
        sqlHandler.resultSet.beforeFirst();
        
        while (sqlHandler.resultSet.next()) {
            System.out.println(sqlHandler.resultSet.getString("HoTen") + " " + sqlHandler.resultSet.getString("TenMon") + " " + sqlHandler.resultSet.getString("MaLop"));
        }
        
    }
}
