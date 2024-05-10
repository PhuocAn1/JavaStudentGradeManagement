/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import sqlhandler.MySQLHandler;

/**
 *
 * @author ADMIN
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private OutputStream outStream;
    private InputStream inStream;
    private ResultSet resultSet;
    private boolean quit = false;
    private JSONArray universalJSONArray;
    private MySQLHandler SQLHandler;
    
    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            
            in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(this.clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            
            outStream = clientSocket.getOutputStream();
            inStream = clientSocket.getInputStream();
            
            SQLHandler = new MySQLHandler();
            SQLHandler.makeConnection();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(String msg) {
        out.println(msg);
    }
    
    public String readMessage() {
        String response = null;
        try {
            if (!clientSocket.isClosed()) {
                response = in.readLine();
                System.out.println("Handler read: " + response);
            } else {
                System.out.println("Socket closed");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    public void sendStudentJSONObjectArray() {
        JSONArray jsonStudentArray = new JSONArray();
 
        try {
            this.resultSet.beforeFirst();
            while (resultSet.next()) {
                //Create a JSON object to add to the array
                JSONObject student = new JSONObject();
                student.put("MaHS", resultSet.getString(1));
                student.put("HoTen", resultSet.getString(2));
                student.put("NamSinh", resultSet.getInt(3));
                student.put("HanhKiem", resultSet.getString(4));
                student.put("MaTK", resultSet.getString(5));
                student.put("MaLop", resultSet.getString(6));
                student.put("GioiTinh", resultSet.getString(7));
                
                jsonStudentArray.put(student);
                
                //System.out.println(resultSet.getNString(1) + " " + resultSet.getNString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(jsonStudentArray);
        
        //Send the JSON array to client in bytes;
        //Use out put stream and not printwriter
        try {
            byte[] bytes = jsonStudentArray.toString().getBytes();
            outStream.write(bytes);
            System.out.println("Done sending Class info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendGVChuNhiem() {
        try {
            // Create a JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("MaGV", resultSet.getString(1));
            jsonObject.put("HoTen", resultSet.getString(2));
            jsonObject.put("NamSinh", resultSet.getString(3));
            jsonObject.put("MaTK", resultSet.getString(4));
            jsonObject.put("GioiTinh", resultSet.getString(5));
            
            // Convert JSON object to string
            String jsonString = jsonObject.toString();
            System.out.println(jsonString);
            // Send the string over the socket
            sendMessage(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done sending GV");
    }
    
    public void sendNamHoc() {
        JSONArray jsonNamHocArray = new JSONArray();
        try {
            this.resultSet.beforeFirst();            
            while (resultSet.next()) {
                // Create a JSON object
                JSONObject jsonObject = new JSONObject();
                
                jsonObject.put("NamHoc", resultSet.getString(1));
                
                jsonNamHocArray.put(jsonObject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Send the JSON array to client in bytes;
        try {
            byte bytes[] = jsonNamHocArray.toString().getBytes();
            outStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendBangDiem() {
        JSONArray jsonBangDiemArray = new JSONArray();
        
        try {
            this.resultSet.beforeFirst();//Set the ResultSet cursor befor the first row
            while (resultSet.next()) {
                //Create a JSON object to add to the array
                JSONObject diem = new JSONObject();
                
                diem.put("TenMon", resultSet.getString("TenMon"));
                diem.put("Mieng", resultSet.getFloat("Mieng"));
                diem.put("15Phut", resultSet.getFloat("15Phut"));
                diem.put("1Tiet", resultSet.getFloat("1Tiet"));
                diem.put("GiuaKy", resultSet.getFloat("GiuaKy"));
                diem.put("CuoiKy", resultSet.getFloat("CuoiKy"));
                diem.put("DiemTB", resultSet.getFloat("DiemTB"));
                
                jsonBangDiemArray.put(diem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Send the JSON array to client in bytes;
         try {
            byte[] bytes = jsonBangDiemArray.toString().getBytes();
            outStream.write(bytes);
            System.out.println("Done sending BangDiem!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendHocSinh() {
        JSONObject jsonObject = new JSONObject();
        
        try {
            jsonObject.put("HoTen", resultSet.getString("HoTen"));
            jsonObject.put("HanhKiem", resultSet.getString("HanhKiem"));
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sendMessage(jsonObject.toString());
        System.out.println("Da gui hoc sinh!");
    }
    
    public void sendMonDay() {
         JSONArray monDayArray = new JSONArray();
         
        try {
            this.resultSet.beforeFirst();//Set the ResultSet cursor befor the first row
            
            while (resultSet.next()) {
                JSONObject MonHoc = new JSONObject();
                
                MonHoc.put("MaMon", resultSet.getString("MaMon"));
                MonHoc.put("TenMon", resultSet.getString("TenMon"));
                
                monDayArray.put(MonHoc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("MonDay " + monDayArray);
        
        //Send the JSON array to client in bytes;
        try {
            byte[] bytes = monDayArray.toString().getBytes();
            outStream.write(bytes);
            System.out.println("Done sending MonDay!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendDSLopDay() {
        JSONArray dsLopDay = new JSONArray();
        
        try {
            this.resultSet.beforeFirst();
            
            while (resultSet.next()) {
                JSONObject LopHoc = new JSONObject();
                
                LopHoc.put("MaLop", resultSet.getString("MaLop"));
                
                dsLopDay.put(LopHoc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Send the JSON array to client in bytes;
        try {
            byte[] bytes = dsLopDay.toString().getBytes();
            outStream.write(bytes);
            System.out.println("Done sending DS lop day!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendJSONArray(JSONArray jsonArray) {
        try {
            byte[] bytes = jsonArray.toString().getBytes();
            outStream.write(bytes);
            System.out.println("Done sending JSON array:");
            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public JSONObject receivedJSONObject() {
        // Read the JSON string from the socket
        String jsonString = readMessage();
        
        // Parse the JSON string to a JSON object
        JSONObject jsonObject = new JSONObject(jsonString);
        
        return jsonObject;
    }
    
    public void updateDiem() {
        String MaHS, MaMon, LoaiDiem, NamHoc;
        int HocKy;
        float SoDiem;
        
        for (int i = 0; i < universalJSONArray.length(); i++) {
            MaHS = universalJSONArray.getJSONObject(i).getString("MaHS");
            MaMon = universalJSONArray.getJSONObject(i).getString("MaMon");
            LoaiDiem = universalJSONArray.getJSONObject(i).getString("LoaiDiem");
            NamHoc = universalJSONArray.getJSONObject(i).getString("NamHoc");
            HocKy = universalJSONArray.getJSONObject(i).getInt("HocKy");
            SoDiem = universalJSONArray.getJSONObject(i).getFloat("SoDiem");

            //System.out.println(MaHS + " " + MaMon + " " + LoaiDiem + " " + NamHoc + " " + HocKy + " " + SoDiem);

            if (SQLHandler.updateDiem(MaHS, MaMon, LoaiDiem, SoDiem, NamHoc, HocKy) == false) {
                System.out.println("Khong cap nhat duoc diem");
                System.out.println(MaHS + " " + MaMon + " " + LoaiDiem + " " + NamHoc + " " + HocKy + " " + SoDiem);
                return;
            }
        }
    }
    
    public JSONArray constructGVArray() {
        JSONArray tempArray = new JSONArray();
        JSONObject GiaoVien;
        
        try {
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                GiaoVien = new JSONObject();
                
                GiaoVien.put("MaGV", resultSet.getString("MaGV"));
                GiaoVien.put("HoTen", resultSet.getString("HoTen"));
                GiaoVien.put("GioiTinh", resultSet.getString("GioiTinh"));
                                
                tempArray.put(GiaoVien);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(tempArray);
        return tempArray;
    }
    
    public JSONArray constructLopHocArray() {
        JSONArray tempArray = new JSONArray();
        JSONObject MaLop;
        
        try {
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                MaLop = new JSONObject();
                
                MaLop.put("MaLop", resultSet.getString("MaLop"));
                
                tempArray.put(MaLop);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempArray;
    }
    
    public JSONArray receiveJSONArray() {
        if (universalJSONArray != null) {
            universalJSONArray.clear();//Xoa du lieu con ton dong trong json array
        }
       
        JSONArray receivedArray = null;
        try {
            byte[] bytes = new byte[4096]; // Adjust buffer size as needed
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);
            
            receivedArray = new JSONArray(jsonString);
            System.out.println(receivedArray);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receivedArray;
    }
    
    public JSONArray conStructLichGDArray() {
        JSONArray tempArray = new JSONArray();
        JSONObject GiangDay;
        
        try {
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                GiangDay = new JSONObject();
                
                GiangDay.put("MaGV", resultSet.getString("MaGV"));
                GiangDay.put("HoTen", resultSet.getString("HoTen"));
                GiangDay.put("TenMon", resultSet.getString("TenMon"));
                GiangDay.put("MaLop", resultSet.getString("MaLop"));
                
                tempArray.put(GiangDay);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempArray;
    }
    
    public void handleMessage(String msg) {
        if (msg.equals(Command.LOGIN.name())) {
            String userName = readMessage();
            String password = readMessage();
            System.out.println(userName + " " + password);
            
            boolean checkLogin = SQLHandler.checkLogin(userName, password);
            //Send true if account and password is correct
            if (checkLogin == true) {
                sendMessage(Command.TRUE.name());
            } 
            else {
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.IS_QL.name())) {
            //Goi ham de kiem tra xem giao vien co chuc vu quan ly hay khong?
            String MaGV = readMessage();
            if (SQLHandler.isQuanLy(MaGV) == true) {
                sendMessage(Command.TRUE.name());
            } else {
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_CLASS_INFO.name())) {
            //Receive the classID
            String classID = readMessage();
            System.out.println("ClassID: " + classID);
            
            boolean checkClassInfo = SQLHandler.getClassInfo(classID);
            
            if (checkClassInfo == true) {
                //System.out.println("Success");
                this.resultSet = SQLHandler.getResultSet();
                sendMessage(Command.TRUE.name());                
                sendStudentJSONObjectArray();
                
                SQLHandler.getGVChuNhiem(classID);
                this.resultSet = SQLHandler.getResultSet();
                sendGVChuNhiem();
            } 
            else {
                System.out.println("Unable to get class Info");
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_CLASS_INFO_2.name())) {
            //Dung de lay danh sach lop ma khong can phai lay giao vien chu nhiem
            //Receive the classID
            String classID = readMessage();
            System.out.println("ClassID_2: " + classID);
            
            boolean checkClassInfo = SQLHandler.getClassInfo(classID);
            
            if (checkClassInfo == true) {
                //System.out.println("Success");
                this.resultSet = SQLHandler.getResultSet();
                sendMessage(Command.TRUE.name());                
                sendStudentJSONObjectArray();
            } 
            else {
                System.out.println("Khong the lay danh sach lop");
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_NAM_HOC.name())) {
            if (SQLHandler.getNamHoc() == true) {
                sendMessage(Command.TRUE.name());
                this.resultSet = SQLHandler.getResultSet();
                sendNamHoc();
            }
            else {
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_BANG_DIEM.name())) {//Lay bang diem tu CSDL cua 1 hoc sinh
            String jsonString = readMessage();
            JSONObject jsonObject = new JSONObject(jsonString);
            
            String MaHS = jsonObject.getString("MaHS");
            String NamHoc = jsonObject.getString("NamHoc");
            String HocKy = jsonObject.getString("HocKy");
            
            if (SQLHandler.getBangDiem(MaHS, NamHoc, HocKy)) {
                sendMessage(Command.TRUE.name());
                this.resultSet = SQLHandler.getResultSet();
                sendBangDiem();
                
                //Gui ho ten hoc sinh va hanh kiem
                SQLHandler.getHocSinh(MaHS);
                this.resultSet = SQLHandler.getResultSet();
                sendHocSinh();
            } 
            else {
                sendMessage(Command.FALSE.name());
                System.out.println("Khong the xem bang diem!");
            }
        }
        else if (msg.equals(Command.GET_HOC_SINH.name())) {
            //Kiem tra xem ma hoc sinh co ton tai hay khong
            JSONObject diem;
            
            String MaHS = readMessage();
            String NamHoc, LoaiDiem, MaMon, jsonString;
            int HocKy;
            float SoDiem;
            
            if (SQLHandler.getHocSinh(MaHS)) {
                sendMessage(Command.TRUE.name());
                jsonString = readMessage();
                
                diem = new JSONObject(jsonString);
                
                NamHoc = diem.getString("NamHoc");
                LoaiDiem = diem.getString("LoaiDiem");
                MaMon = diem.getString("MaMon");
                HocKy = diem.getInt("HocKy");
                SoDiem = diem.getFloat("SoDiem");
                
                SQLHandler.updateDiem(MaHS, MaMon, LoaiDiem, SoDiem, NamHoc, HocKy);
            } else {
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_HOC_SINH_2.name())) {
            String MaHS = readMessage();
            //Kiem tra xem ma hoc sinh co ton tai hay khong
            if (SQLHandler.getHocSinh(MaHS)) {
                sendMessage(Command.TRUE.name());
            } else {
                sendMessage(Command.FALSE.name());
            }
        }
        else if (msg.equals(Command.GET_MON_DAY.name())) {
            String MaGV = readMessage(); //Doc MaGV duoc gui toi
            
            if (SQLHandler.getMonHocDay(MaGV) == true) {
                this.resultSet = SQLHandler.getResultSet();
                sendMonDay();
            } else {
                System.out.println("Khong the lay mon day!!!");
            }
        }
        else if (msg.equals(Command.GET_DS_LOP_DAY.name())) {
            //Lay ra danh sach cac lop ma minh day mon do
            //VD: danh sach cac lop minh day mon Ngu Van
            
            String MaMon = readMessage(); //Doc MaMon duoc gui toi
            SQLHandler.getDSLopDay(MaMon);
            this.resultSet = SQLHandler.getResultSet();
            sendDSLopDay();
        }
        else if (msg.equals(Command.UPDATE_DIEM.name())) {
            //Cap nhat diem
            //Doc danh sach cac hoc sinh can cap nhat diem
            if (universalJSONArray != null) {
             universalJSONArray.clear();               
            }
            universalJSONArray = receiveJSONArray();
            updateDiem();
        }
        else if (msg.equals(Command.GET_DIEM.name())) {
            //System.out.println("Bat dau lay diem FULL");
            //Lay danh sach diem cua mon vua nhap ve de hien thi
            //Nhan mang cac ma hoc sinh de lay du lieu ve
            
            universalJSONArray = receiveJSONArray();
            
            if (universalJSONArray == null) {
                System.out.println("Array null, khong the truyen");
            }
            
            JSONArray temp;
            temp = SQLHandler.xemLaiBangDiem(universalJSONArray);
            
            if (temp == null) {
                System.out.println("JsonArray null");
            } else {
                //System.out.println(temp);
                sendJSONArray(temp);
            }
            universalJSONArray.clear();
            
        }
        else if (msg.equals(Command.GET_ALL_GV.name())) {
            //Lay tat danh sach cac giao vien trong truong
            SQLHandler.getAllGiaoVien();
            this.resultSet = SQLHandler.getResultSet();
            JSONArray tempArray = constructGVArray();
            
            if (tempArray == null) {
                System.out.println("ERROR!!! Chuoi JSON rong!!!!");
            } else {
                sendJSONArray(tempArray);
            }      
        }
        else if (msg.equals(Command.GET_ALL_LOP_HOC.name())) {
            //Lay tat ca danh sach lop hoc dang co trong truong
            SQLHandler.getAllLopHoc();
            this.resultSet = SQLHandler.getResultSet();
            
            JSONArray tempArray = constructLopHocArray();
            
            sendJSONArray(tempArray);
        }
        else if (msg.equals(Command.PHAN_CONG_GD.name())) {
            //Phan cong giang day
            JSONObject temp = receivedJSONObject();
            
            System.out.println(temp);
            
            String MaGV = temp.getString("MaGV");
            String MaMon = temp.getString("MaMon");
            String MaLop = temp.getString("MaLop");
            
            SQLHandler.phanCongGD(MaGV, MaMon, MaLop);
        }
        else if (msg.equals(Command.GET_LICH_GD.name())) {
            SQLHandler.getLichGD();
            this.resultSet = SQLHandler.getResultSet();
            
            JSONArray tempArray = conStructLichGDArray();
            
            sendJSONArray(tempArray);
        }
    }
    
    @Override
    public void run() {
        //System.out.println("Running");
        while (quit == false) {
            System.out.println("Waiting for new message!");
            String msg = readMessage();
            if (msg != null) {
                //System.out.println("<SERVER>: " + msg);
                handleMessage(msg); 
            }
                      
        }
    }
}
