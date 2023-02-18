/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ikerf
 */
public class Data {
    Connection connector;
    public Data(){
        //System.out.println("Bienvenido a la clase Data");
    }
    //DataBase. Conexiones a la base de datos. 
    public boolean Connect(){
        try{
          Class.forName("com.mysql.cj.jdbc.Driver");  
                connector=DriverManager.getConnection(  
	"jdbc:mysql://localhost:3306/CONSULT?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","Caikfure3472");
            
        }catch(Exception e){
            System.out.println("Error en la conexión");
            System.out.println("Error: "+e);
            return false;
        }
        return true;
    }
    
    public boolean End_Connect(){
        try{
            connector.close();
        }catch(Exception e){
            System.out.println("Error"+e);
            System.out.println("No se puede cerrar");
        }
        return true;
    }
    //Métodos Usuario
    
    public boolean Login(String user, String password){
        try{
            Statement state = connector.createStatement();
            String query = "SELECT id_user from USER where id_user LIKE \'" + user + "\' AND password_user LIKE \'" + password + "\'";
            ResultSet rs = state.executeQuery(query);
            if(rs.next())
                return true;
        }catch(Exception e){
            System.out.println("Error al checar usuario: "+e);
        }
        return false;
    }
    public boolean Change_user(String newUser, String user){
        try{
            Statement state = connector.createStatement();
            String query = "UPDATE `consult`.`user` SET `id_user` = '" + newUser +"' WHERE (`id_user` = '"+user+"')";
            PreparedStatement ps = connector.prepareStatement(query);
            System.out.println(query);
            ps.execute();
            return true;
      
        }catch(Exception e){
            System.out.println("Error al cambiar el usuario: "+e);
        }
        return false;
    }
        public boolean Change_password(String newPassword, String user){
            
        try{
            Statement state = connector.createStatement();
            String query = "UPDATE `consult`.`user` SET `password_user` = '"+newPassword+"' WHERE (`id_user` = '"+user+"')";
            PreparedStatement ps = connector.prepareStatement(query);
            ps.execute();
            return true;
      
        }catch(Exception e){
            System.out.println("Error al cambiar contraseña: "+e);
        }
        return false;
    }
        //Patients
        public boolean Declare_patient(String name, String gender, String Diagnosis, String birthday, String email, String format, String ticket, String cost){
            try{
                Statement state = connector.createStatement();
                String query = "INSERT into PATIENT (id_patient, gender_patient,diagnosis_patient, birthday_patient,Email_patient,modality_patient,RFC_patient, status, cost_patient) VALUES (\'" + name + "\',  \'" + gender + "\',  \'"+Diagnosis+"\',  \'"+birthday+"\',  \'"+email+"\',  \'"+format+"\',  \'"+ticket+"\',  \'Activo\',  \'"+cost+"\')";
                PreparedStatement ps = connector.prepareStatement(query);
                ps.execute();
                return true;
            }catch(Exception e){
                System.out.println("Error añadiendo paciente: "+e);
            }
            return false;
        }
        public boolean Update_patient(String name, String gender, String Diagnosis, String birthday, String email, String format, String ticket, String status, String cost){
            try{
                Statement state = connector.createStatement();
                String query = "UPDATE `consult`.`patient` SET `gender_patient` = '"+gender+"', `diagnosis_patient` = '"+Diagnosis+"', `birthday_patient` = '"+birthday+"', `Email_patient` = '"+email+"', `modality_patient` = '"+format+"', `RFC_patient` = '"+ticket+"', `status` = '"+status+"', `cost_patient` = '"+cost+"'  WHERE (`id_patient` = '"+name+"')";
                PreparedStatement ps = connector.prepareStatement(query);
                ps.execute();
                return true;
            }catch(Exception e){
                System.out.println("Error añadiendo paciente: "+e);
            }
            return false;
        }
        public void View_patient(JTable table){
            try{
                String query = "SELECT * FROM patient";
                PreparedStatement ps = connector.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                DefaultTableModel patient = (DefaultTableModel)table.getModel();
                patient.setRowCount(0);
                while(rs.next()){
                    patient.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8), rs.getString(9)});
                }
                
            }catch(Exception e){
                System.out.println("Error: " +e);
                
            }
        }
        public void View_patient_status(JTable table, String status){
            try{
                String query = "SELECT * FROM patient WHERE `status`='"+status+"'";
                PreparedStatement ps = connector.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                DefaultTableModel patient = (DefaultTableModel)table.getModel();
                patient.setRowCount(0);
                while(rs.next()){
                    patient.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8), rs.getString(9)});
                }                
            }catch(Exception e){
                System.out.println("Error: " +e);
                
            }
        }
            public void View_patient_one(JTable table, String name){
            try{
                String query = "SELECT * FROM patient WHERE `id_patient`='"+name+"'";
                PreparedStatement ps = connector.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                DefaultTableModel patient = (DefaultTableModel)table.getModel();
                patient.setRowCount(0);
                while(rs.next()){
                    patient.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8),rs.getString(9)});
                }
                
            }catch(Exception e){
                System.out.println("Error: " +e);
                
            }
        }
            public boolean Check_patient(String patient){
                try{
                    Statement state = connector.createStatement();
                    String query = "SELECT id_patient from PATIENT where id_patient LIKE \'" + patient + "\'";
                    ResultSet rs = state.executeQuery(query);
                    if(rs.next())
                        return true;
                 }catch(Exception e){
                    System.out.println("Error al checar usuario: "+e);
                }
                    return false;
             }
            public int cost_patient(String name){
                int amount=0;
                try{
                    String query = "select cost_patient from patient WHERE id_patient ='"+name+"'";
                    Statement state = connector.createStatement();
                    ResultSet rs = state.executeQuery(query);
                    rs.next();
                    amount = rs.getInt("cost_patient");
                    return amount;
                }catch(Exception e){
                    System.out.println("Error: "+e);
                }
                return amount;
            }
            public boolean Delete_patient(String name){
                try{
                    Statement state = connector.createStatement();
                    String query = "DELETE FROM `consult`.`patient` WHERE (`id_patient` = '"+name+"')";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ps.execute();
                    return true;
                }catch(Exception e){
                    System.out.println("Error borrando paciente: "+e);
                }
                return false;
            }
        
        //Notes
        public boolean add_notes(String name, String date, int session, String notes, String notekey){
            try{
                Statement state = connector.createStatement();
                String query = "INSERT into NOTES (id_patient, date_patient, session_patient, note_patient,note_key) VALUES (\'" + name + "\',  \'" + date + "\',  \'"+session+"\',  \'"+notes+"\',  \'"+notekey+"\')";
                PreparedStatement ps = connector.prepareStatement(query);
                ps.execute();
                return true;
            }catch(Exception e){
                System.out.println("Error añadiendo notas: "+e);
            }
            return false;
        }
            public void view_notes(JTable table, String name){
                try{
                    String query = "SELECT * FROM notes WHERE `id_patient`='"+name+"'";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    DefaultTableModel patient = (DefaultTableModel) table.getModel();
                    patient.setRowCount(0);
                    while(rs.next()){
                        patient.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
                    }
                    
                }catch(Exception e){
                    System.out.println("Error: "+e);
                }
            }
        public int review_session(String name){
                int amount=0;
                try{
                    String query = "SELECT MAX(session_patient) AS recent FROM NOTES WHERE `id_patient`='"+name+"'";
                    Statement state = connector.createStatement();
                    ResultSet rs = state.executeQuery(query);
                    rs.next();
                    amount = rs.getInt("recent");
                    return amount;
                }catch(Exception e){
                    System.out.println("Error: "+e);
                }
                return amount;
        }
        //Session
        public boolean add_session(String key, String name, String date, String hour, int payment){
            try{
                Statement state = connector.createStatement();
                String query = "INSERT into SESSION (id_session, patient_session, date_session, hour_session, payment_session) VALUES (\'" + key + "\',  \'" + name + "\',  \'"+date+"\',  \'"+hour+"\',  \'"+payment+"\')";
                PreparedStatement ps = connector.prepareStatement(query);
                ps.execute();
                return true;
            }catch(Exception e){
                System.out.println("Error añadiendo session: "+e);
            }
            return false;
        }
        

        public boolean update_payment_status(String session, int status){
            try{
                String query = "UPDATE `consult`.`session` SET `payment_session`= '"+status+"' WHERE (`id_session` = '"+session+"')";
                PreparedStatement ps = connector.prepareStatement(query);
                ps.execute();
                return true;
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
            return false;
        }

            public void view_todays_session(JTable table, String date){
                try{
                    String query = "SELECT * FROM session WHERE `date_session`='"+date+"'";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    DefaultTableModel patient = (DefaultTableModel) table.getModel();
                    patient.setRowCount(0);
                    while(rs.next()){
                        patient.addRow(new String[]{rs.getString(2),rs.getString(3),rs.getString(4)});
                    }
                }catch(Exception e){
                    System.out.println("Error "+e);
                }
            }
            public void view_session(JTable table, int state, String name){
                try{
                    String query = "SELECT * FROM session WHERE `payment_session`='"+state+"' AND `patient_session`='"+name+"'";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    DefaultTableModel patient = (DefaultTableModel) table.getModel();
                    patient.setRowCount(0);
                    while(rs.next()){
                        patient.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
                    }
                }catch(Exception e){
                    System.out.println("Error "+e);
                }
            }
            public int paid_session(String name, int status){
                int amount = 0;
                try{
                    String query = "select count(patient_session) from session WHERE patient_session ='"+name+"' AND payment_session='"+status+"'" ;
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery(query);
                    rs.next();
                    amount = Integer.parseInt(rs.getString("count(patient_session)"));
                    return amount; 
                }catch(Exception e){
                    System.out.println("Error " + e);
                }
                return amount;
            }
            public int check_sessions(String date, int status){
                int amount = 0;
                try{
                    String query = "select count(date_session) from session WHERE date_session ='"+date+"' AND payment_session='"+status+"'" ;
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery(query);
                    rs.next();
                    amount = Integer.parseInt(rs.getString("count(date_session)"));
                    return amount; 
                }catch(Exception e){
                    System.out.println("Error " + e);
                }
                return amount;
            }
            //Generals

            public String total_patients(){
                String patients = "";
                try{  
                    String query = "select count(id_patient) from patient";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery(query);
                    rs.next();
                    patients = rs.getString("count(id_patient)");
                    return patients;
                }catch(Exception e){
                    System.out.println("Error contando pacientes: "+e);
                }
                return patients;
                
            }
            public int count(String name){
                int amount = 0;
                try{
                    String query = "select count(patient_session) from session WHERE patient_session ='"+name+"'";
                    PreparedStatement ps = connector.prepareStatement(query);
                    ResultSet rs = ps.executeQuery(query);
                    rs.next();
                    amount = Integer.parseInt(rs.getString("count(patient_session)"));
                    return amount;
                }catch(Exception e){
                    System.out.println("Error " + e);
                }
                return amount;
            }

            
}
