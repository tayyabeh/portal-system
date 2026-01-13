import java.sql.*;
import java.io.*;
import collegeapplication.common.DataBaseConnection;

public class UpdateAdminData {
    public static void main(String[] args) {
        try {
            Connection con = DataBaseConnection.getConnection();
            
            // Update Details
            String updateQuery = "UPDATE admin SET " +
                "collagename = ?, " +
                "website = ?, " +
                "emailid = ?, " +
                "contactnumber = ?, " +
                "facebook = ?, " +
                "instagram = ?, " +
                "twitter = ?, " +
                "linkedin = ?, " +
                "address = ?, " + 
                "logo = ? "; // No WHERE clause as there is likely only one record, or update all.

            PreparedStatement ps = con.prepareStatement(updateQuery);
            
            ps.setString(1, "Sir Syed University of Engineering and Technology");
            ps.setString(2, "https://www.ssuet.edu.pk/");
            ps.setString(3, "ssuet@edu.pk");
            ps.setString(4, "03273844645");
            ps.setString(5, "https://www.facebook.com/sirsyeduniversitykr/");
            ps.setString(6, "https://www.instagram.com/sirsyeduniversityofficial/?hl=en");
            ps.setString(7, "https://x.com/SSUET_Official");
            ps.setString(8, "https://www.linkedin.com/school/ssuetofficial/?originalSubdomain=pk");
            ps.setString(9, "Karachi, Pakistan"); // Providing a default address

            // Read Image
            File imgFile = new File("assets/university_logo_final.png");
            FileInputStream fis = new FileInputStream(imgFile);
            ps.setBinaryStream(10, fis, (int) imgFile.length());

            int rows = ps.executeUpdate();
            System.out.println("Admin Data Updated. Rows affected: " + rows);
            
            ps.close();
            fis.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
