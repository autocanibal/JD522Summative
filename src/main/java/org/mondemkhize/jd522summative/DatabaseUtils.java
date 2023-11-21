/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.mondemkhize.jd522summative;

/**
 *
 * @author Monde
 */
import java.awt.Component;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class DatabaseUtils {
    
    private Connection connect(Component c){
        Connection conn = null;
        String connString = "jdbc:sqlserver://MONDE;databaseName=UniversityResourceManagement;integratedSecurity=true;trustServerCertificate=true";
        
        try {
            conn = DriverManager.getConnection(connString);
            
        } catch (SQLException e) {
            System.out.println("Error connecting");
            new JOptionPane().showMessageDialog(c, e.getMessage());
        }
        return conn;
    }
    
    public ArrayList<String[]> selectTable(Component c, String Table){
        String id, name, depID, facID, courseID,courseMat, grades, students;
        
        ArrayList<String[]> values = new ArrayList();
        
        try (Connection con = this.connect(c)){
            switch (Table) {
                case "Department" -> Table = "Departments";
                case "Faculty" -> Table = "Faculties";
                case "Student" -> Table = "Students";
                case "Course" -> Table = "Courses";
                default -> {
                }
            }
            
            Statement st = con.createStatement();
            String sql = "select * from "+Table;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                switch (Table) {
                    case "Departments" ->{
                        id = String.valueOf(rs.getInt("dep_id"));
                        name = rs.getString("dep_name");
                        String tableData[] = {id, name};
                        values.add(tableData);
                        break;
                    }
                    case "Students" ->{
                        id = String.valueOf(rs.getInt("stu_id"));
                        name = rs.getString("stu_name");
                        
                        depID = String.valueOf(rs.getInt("dep_id"));
                        depID = rs.wasNull() ? "null" : depID;

                        facID = String.valueOf(rs.getInt("fac_id"));
                        facID = rs.wasNull() ? "null" : facID;
                        
                        courseID =String.valueOf(rs.getInt("course_id"));
                        courseID = rs.wasNull() ? "null" : courseID;
                        
                        courseMat = rs.getString("course_materials");
                        courseMat = rs.wasNull() ? "null" : courseMat;
                        
                        grades = rs.getString("grades");
                        grades = rs.wasNull() ? "null" : grades;
                        String tableData[] = {id, name, depID, facID, courseID, courseMat, grades};
                        values.add(tableData);
                        break;
                    }
                    case "Faculties" ->{
                        id = String.valueOf(rs.getInt("fac_id"));
                        name = rs.getString("fac_name");

                        depID = String.valueOf(rs.getInt("dep_id"));
                        depID = rs.wasNull() ? "null" : depID;

                        courseID = String.valueOf(rs.getInt("course_id"));
                        courseID = rs.wasNull() ? "null" : courseID;
                        
                        String tableData[] = {id, name, depID, courseID};
                        values.add(tableData);
                        break;
                    }
                    case "Courses" ->{
                        id = String.valueOf(rs.getInt("course_id"));
                        name = rs.getString("course_name");

                        depID = String.valueOf(rs.getInt("dep_id"));
                        depID = rs.wasNull() ? "null" : depID;

                        facID = String.valueOf(rs.getInt("fac_id"));
                        facID = rs.wasNull() ? "null" : facID;

                        courseMat = rs.getString("course_materials");
                        courseMat = rs.wasNull() ? "null" : courseMat;

                        grades = rs.getString("grades");
                        grades = rs.wasNull() ? "null" : grades;

                        students = rs.getString("students_ids");
                        students = rs.wasNull() ? "null" : students;

                        String tableData[] = {id, name, depID, facID, courseMat, grades, students};
                        values.add(tableData);
                    }
                    default -> {
                        break;
                    }     
                }   
            }
        
        
        return values;
        }catch (SQLException ex) {
            new JOptionPane().showMessageDialog(c, ex.getMessage());
            return values;            
        }
        
    }
    
    public String nextId(Component c, String Table){
        int id=0;
        try(Connection con = this.connect(c)){
            String idString = "";
            switch (Table) {
                case "Department" -> {
                    Table = "Departments";
                    idString = "dep_id";
                }
                case "Faculty" -> {
                    Table = "Faculties";
                    idString = "fac_id";
                }
                case "Student" -> {
                    Table = "Students";
                    idString = "stu_id";
                }
                case "Course" -> {
                    Table = "Courses";
                    idString = "course_id";
                }
                default -> {
                }
            }
            
            String sql = "select "+idString+" from "+Table;
            Statement pstmnt = con.createStatement();
            ResultSet rs = pstmnt.executeQuery(sql);
            while(rs.next()){
                //System.out.println(rs.getInt(idString));
                id++;
            }
            id++;
            return String.valueOf(id);
            
        }
        catch (SQLException ex) {
            new JOptionPane().showMessageDialog(c, ex.getMessage());
            return "1";
        }
        
    }
    
    public void InsertInto(Component c, String Table, int ID, String Name){
        
        String sql = "";
        switch (Table) {
            case "Department" -> sql ="INSERT INTO Departments(dep_id,dep_name) VALUES(?,?)";
            case "Faculty" -> sql = "INSERT INTO Faculties(fac_id,fac_name) VALUES(?,?)";
            case "Student" -> sql = "INSERT INTO Students(stu_id,stu_name) VALUES(?,?)";
            case "Course" -> sql = "INSERT INTO Courses(course_id,course_name) VALUES(?,?)";
            default -> {
            }
        }
        

        try (Connection conn = this.connect(c); PreparedStatement pstmnt = conn.prepareStatement(sql)) {
            pstmnt.setInt(1, ID);
            pstmnt.setString(2, Name);
            pstmnt.executeUpdate();
            new JOptionPane().showMessageDialog(c, "Added "+Table+" successfullly");
        } catch (SQLException e) {
            new JOptionPane().showMessageDialog(c, e.getMessage());
        }
    }
    
}
