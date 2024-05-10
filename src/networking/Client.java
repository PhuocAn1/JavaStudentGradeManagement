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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import school.GiaoVien;
import school.Student;

/**
 *
 * @author ADMIN
 */
public class Client {
    private final int PORT = 9090;
    private final String HOST = "127.0.0.1";
    private PrintWriter out;
    private BufferedReader in;
    private InputStream inStream;
    private OutputStream outStream;
    private Socket clientSocket;
    
    private List<Student> studentList = new ArrayList<>();
    private GiaoVien gvChuNhiem;

    public List<Student> getStudentList() {
        return studentList;
    }

    public GiaoVien getGvChuNhiem() {
        return gvChuNhiem;
    }
    
    public void startConnection() {
        try {
            clientSocket = new Socket(HOST, PORT);
            
            out = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            
            inStream = clientSocket.getInputStream();
            outStream = clientSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(String msg) {
        out.println(msg);
        //System.out.println("Client:" + msg);
    }
    
    public void sendCommand(Command command) {
        sendMessage(command.name());        
    }           
    
    public void receiveStudentJSONArray() {
        try {
            byte[] bytes = new byte[4096]; // Adjust buffer size as needed
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);

            JSONArray receivedArray = new JSONArray(jsonString);
            // Process the received JSON array
            for (int i = 0; i < receivedArray.length(); i++) {
                String MaHS = receivedArray.getJSONObject(i).getString("MaHS");
                String HoTen = receivedArray.getJSONObject(i).getString("HoTen");
                int NamSinh = receivedArray.getJSONObject(i).getInt("NamSinh");
                String HanhKiem = receivedArray.getJSONObject(i).getString("HanhKiem");
                String MaTk = receivedArray.getJSONObject(i).getString("MaTK");
                String MaLop = receivedArray.getJSONObject(i).getString("MaLop");
                String GioiTinh = receivedArray.getJSONObject(i).getString("GioiTinh");
                
                Student temp = new Student(MaHS, HoTen, NamSinh, HanhKiem, MaTk, MaLop, GioiTinh);
                studentList.add(temp);
                //System.out.println(MaHS);
            }
            //System.out.println(receivedArray);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONArray receiveMonDay() {
        JSONArray receivedMonHoc = null;
        
        try {
            byte[] bytes = new byte[4096]; // Adjust buffer size as needed
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);
            
            receivedMonHoc = new JSONArray(jsonString);
            //System.out.println(receivedArray);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return receivedMonHoc;
    }
    
    public JSONArray receiveNamHocArray() {
        JSONArray receivedArray = null;
        try {
            byte[] bytes = new byte[4096]; // Adjust buffer size as needed
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);
            
            receivedArray = new JSONArray(jsonString);
            //System.out.println(receivedArray);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receivedArray;
    }
    
    public JSONArray receiveBangDiemArray() {
        JSONArray receivedArray = null;
        
        try {
            byte[] bytes  = new byte[4096];
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);
            
            receivedArray = new JSONArray(jsonString);           
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return receivedArray;
    }
    
    public JSONArray receivedJSONArray() {
        JSONArray receivedArray = null;
        
        try {
            byte[] bytes  = new byte[4096];
            int numBytesRead = inStream.read(bytes);
            String jsonString = new String(bytes, 0, numBytesRead);
            
            receivedArray = new JSONArray(jsonString);           
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return receivedArray;
    }
    
    public void sendThongTinPhanCongGD(String MaGV, String MaMon, String MaLop) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("MaGV", MaGV);
        jsonObject.put("MaMon", MaMon);
        jsonObject.put("MaLop", MaLop);
        
        // Convert JSON object to string
        String jsonString = jsonObject.toString();
        
        // Send the string over the socket
        sendMessage(jsonString);
    }
    
    public JSONObject receivedJSONObject() {
        // Read the JSON string from the socket
        String jsonString = readMessage();
        
        // Parse the JSON string to a JSON object
        JSONObject jsonObject = new JSONObject(jsonString);
        
        return jsonObject;
    }
    
    public void sendJSONArray(JSONArray universalArray) {
        //Send the JSON array to client in bytes;
        System.out.println("Client: " + universalArray);
        try {
            byte[] bytes = universalArray.toString().getBytes();
            outStream.write(bytes);
            //System.out.println("Done sending DS Diem can cap nhat!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void receiveGVChuNhiem() {
        // Read the JSON string from the socket
        String jsonString = readMessage();
        
        // Parse the JSON string to a JSON object
        JSONObject jsonObject = new JSONObject(jsonString);
        
        String MaGV = jsonObject.getString("MaGV");
        String HoTen = jsonObject.getString("HoTen");
        int NamSinh = jsonObject.getInt("NamSinh");
        String MaTK = jsonObject.getString("MaTK");
        String GioiTinh = jsonObject.getString("GioiTinh");
        
        gvChuNhiem = new GiaoVien(MaGV, HoTen, MaTK, GioiTinh, NamSinh);
        System.out.println(gvChuNhiem.toString());
    }
    
    public void clearStudentlist() {
        studentList.clear();
    }
    
    public String readMessage() {
        String msg;
        try {
          msg = in.readLine();
        } catch (IOException ex) {
            msg = "Error";
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    
    public void close() {
        try {
            clientSocket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
