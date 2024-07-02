package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MAIN {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clinicdb"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "root"; 

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Scanner sc = new Scanner(System.in);
        int operation;

        do {
            System.out.println("-----------------------------------");
            System.out.println("**** CLINIC MANAGEMENT SYSTEM ****");
            System.out.println("----------------------------------");
            
            System.out.println(" *** PATIENT INFORMATION *** ");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("----------------------------------");
            
            System.out.println(" *** DOCTOR INFORMATION *** ");
            System.out.println("5. Add Doctor");
            System.out.println("6. Display Doctors");
            System.out.println("7. Update Doctor");
            System.out.println("8. Delete Doctor");
            System.out.println("----------------------------------");
            
            System.out.println(" *** MEDICAL RECORD INFORMATION *** ");
            System.out.println("9. Add Medical Record");
            System.out.println("10. Display Medical Records");
            System.out.println("11. Update Medical Record");
            System.out.println("12. Delete Medical Record");
            System.out.println("----------------------------------");
            
            System.out.println(" *** CLINIC INFORMATION *** ");
            System.out.println("13. Add Clinic");
            System.out.println("14. Display Clinics");
            System.out.println("15. Update Clinic");
            System.out.println("16. Delete Clinic");
            System.out.println("17. Exit");
            System.out.println("----------------------------------");
            System.out.print("Please Enter an Operation number you want to Perform: ");
            operation = sc.nextInt();
            sc.nextLine();

            switch (operation) {
                case 1:
                    createPatient(connection, sc);
                    break;
                case 2:
                    retrievePatients(connection);
                    break;
                case 3:
                    updatePatient(connection, sc);
                    break;
                case 4:
                    deletePatient(connection, sc);
                    break;
                case 5:
                    createDoctor(connection, sc);
                    break;
                case 6:
                    retrieveDoctors(connection);
                    break;
                case 7:
                    updateDoctor(connection, sc);
                    break;
                case 8:
                    deleteDoctor(connection, sc);
                    break;
                case 9:
                    createMedicalRecord(connection, sc);
                    break;
                case 10:
                    retrieveMedicalRecords(connection);
                    break;
                case 11:
                    updateMedicalRecord(connection, sc);
                    break;
                case 12:
                    deleteMedicalRecord(connection, sc);
                    break;
                case 13:
                    createClinic(connection, sc);
                    break;
                case 14:
                    retrieveClinics(connection);
                    break;
                case 15:
                    updateClinic(connection, sc);
                    break;
                case 16:
                    deleteClinic(connection, sc);
                    break;
                case 17:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (operation != 17);

        sc.close();
        connection.close();
    }

    // Methods for Patient CRUD Operations
    private static void createPatient(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Patient ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patient Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Patient Contact Info: ");
        String contactInfo = sc.nextLine();

        String createSql = "INSERT INTO Patient (PatientID, Name, Age, Address, ContactInfo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement createStatement = connection.prepareStatement(createSql)) {
            createStatement.setInt(1, patientID);
            createStatement.setString(2, name);
            createStatement.setInt(3, age);
            createStatement.setString(4, address);
            createStatement.setString(5, contactInfo);

            int rowsAffected = createStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient.");
            }
        }
    }

    private static void retrievePatients(Connection connection) throws SQLException {
        String readSql = "SELECT * FROM Patient";
        try (Statement readStatement = connection.createStatement()) {
            ResultSet resultSet = readStatement.executeQuery(readSql);
            while (resultSet.next()) {
                int patientID = resultSet.getInt("PatientID");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String address = resultSet.getString("Address");
                String contactInfo = resultSet.getString("ContactInfo");
                System.out.println("ID: " + patientID + ", Name: " + name + ", Age: " + age + ", Address: " + address + ", Contact Info: " + contactInfo);
            }
        }
    }

    private static void updatePatient(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Patient ID to update: ");
        int patientIDToUpdate = sc.nextInt();
        sc.nextLine();

        System.out.println("Choose what to update:");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Address");
        System.out.println("4. Update Contact Info");
        System.out.print("Enter your choice: ");
        int updateChoice = sc.nextInt();
        sc.nextLine();

        String updateSql;
        PreparedStatement updateStatement;
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Patient Name: ");
                String newName = sc.nextLine();
                updateSql = "UPDATE Patient SET Name = ? WHERE PatientID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newName);
                break;
            case 2:
                System.out.print("Enter new Patient Age: ");
                int newAge = sc.nextInt();
                updateSql = "UPDATE Patient SET Age = ? WHERE PatientID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, newAge);
                break;
            case 3:
                System.out.print("Enter new Patient Address: ");
                String newAddress = sc.nextLine();
                updateSql = "UPDATE Patient SET Address = ? WHERE PatientID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newAddress);
                break;
            case 4:
                System.out.print("Enter new Patient Contact Info: ");
                String newContactInfo = sc.nextLine();
                updateSql = "UPDATE Patient SET ContactInfo = ? WHERE PatientID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newContactInfo);
                break;
            default:
                System.out.println("Invalid choice for update. Please try again.");
                return;
        }

        updateStatement.setInt(2, patientIDToUpdate);
        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Patient not found or update failed.");
        }
    }

    private static void deletePatient(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Patient ID to delete: ");
        int patientIDToDelete = sc.nextInt();

        String deleteSql = "DELETE FROM Patient WHERE PatientID = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, patientIDToDelete);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("Patient not found or delete failed.");
            }
        }
    }

    // Methods for Doctor CRUD Operations
    private static void createDoctor(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor ID: ");
        int doctorID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Doctor Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Enter Contact Info: ");
        String contactInfo = sc.nextLine();

        String createSql = "INSERT INTO Doctor (DoctorID, Name, Specialization, ContactInfo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement createStatement = connection.prepareStatement(createSql)) {
            createStatement.setInt(1, doctorID);
            createStatement.setString(2, name);
            createStatement.setString(3, specialization);
            createStatement.setString(4, contactInfo);

            int rowsAffected = createStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor added successfully.");
            } else {
                System.out.println("Failed to add doctor.");
            }
        }
    }

    private static void retrieveDoctors(Connection connection) throws SQLException {
        String readSql = "SELECT * FROM Doctor";
        try (Statement readStatement = connection.createStatement()) {
            ResultSet resultSet = readStatement.executeQuery(readSql);
            while (resultSet.next()) {
                int doctorID = resultSet.getInt("DoctorID");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                String contactInfo = resultSet.getString("ContactInfo");
                System.out.println("ID: " + doctorID + ", Name: " + name + ", Specialization: " + specialization + ", Contact Info: " + contactInfo);
            }
        }
    }

    private static void updateDoctor(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor ID to update: ");
        int doctorIDToUpdate = sc.nextInt();
        sc.nextLine();

        System.out.println("Choose what to update:");
        System.out.println("1. Update Name");
        System.out.println("2. Update Specialization");
        System.out.println("3. Update Contact Info");
        System.out.print("Enter your choice: ");
        int updateChoice = sc.nextInt();
        sc.nextLine();

        String updateSql;
        PreparedStatement updateStatement;
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Doctor Name: ");
                String newName = sc.nextLine();
                updateSql = "UPDATE Doctor SET Name = ? WHERE DoctorID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newName);
                break;
            case 2:
                System.out.print("Enter new Doctor Specialization: ");
                String newSpecialization = sc.nextLine();
                updateSql = "UPDATE Doctor SET Specialization = ? WHERE DoctorID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newSpecialization);
                break;
            case 3:
                System.out.print("Enter new Doctor Contact Info: ");
                String newContactInfo = sc.nextLine();
                updateSql = "UPDATE Doctor SET ContactInfo = ? WHERE DoctorID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newContactInfo);
                break;
            default:
                System.out.println("Invalid choice for update. Please try again.");
                return;
        }

        updateStatement.setInt(2, doctorIDToUpdate);
        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Doctor updated successfully.");
        } else {
            System.out.println("Doctor not found or update failed.");
        }
    }

    private static void deleteDoctor(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor ID to delete: ");
        int doctorIDToDelete = sc.nextInt();

        String deleteSql = "DELETE FROM Doctor WHERE DoctorID = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, doctorIDToDelete);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor deleted successfully.");
            } else {
                System.out.println("Doctor not found or delete failed.");
            }
        }
    }

    // Methods for Medical Record CRUD Operations
    private static void createMedicalRecord(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Record ID: ");
        int recordID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patient ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Doctor ID: ");
        int doctorID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("Enter Treatment: ");
        String treatment = sc.nextLine();
        System.out.print("Enter Date (yyyy-mm-dd): "); // Changed to "Date"
        Date date = Date.valueOf(sc.nextLine());

        String createSql = "INSERT INTO MedicalRecord (RecordID, PatientID, DoctorID, Diagnosis, Treatment, Date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement createStatement = connection.prepareStatement(createSql)) {
            createStatement.setInt(1, recordID);
            createStatement.setInt(2, patientID);
            createStatement.setInt(3, doctorID);
            createStatement.setString(4, diagnosis);
            createStatement.setString(5, treatment);
            createStatement.setDate(6, date); // Updated to use "Date"

            int rowsAffected = createStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medical record added successfully.");
            } else {
                System.out.println("Failed to add medical record.");
            }
        }
    }



    private static void retrieveMedicalRecords(Connection connection) throws SQLException {
        String readSql = "SELECT * FROM MedicalRecord";
        try (Statement readStatement = connection.createStatement()) {
            ResultSet resultSet = readStatement.executeQuery(readSql);
            while (resultSet.next()) {
                int recordID = resultSet.getInt("RecordID");
                int patientID = resultSet.getInt("PatientID");
                int doctorID = resultSet.getInt("DoctorID");
                String diagnosis = resultSet.getString("Diagnosis");
                String prescription = resultSet.getString("Treatment"); // Corrected to use "Treatment"
                Date date = resultSet.getDate("Date");
                System.out.println("Record ID: " + recordID + ", Patient ID: " + patientID + ", Doctor ID: " + doctorID + ", Diagnosis: " + diagnosis + ", Prescription: " + prescription + ", Date: " + date);
            }
        }
    }


    private static void updateMedicalRecord(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Record ID to update: ");
        int recordIDToUpdate = sc.nextInt();
        sc.nextLine();

        System.out.println("Choose what to update:");
        System.out.println("1. Update Diagnosis");
        System.out.println("2. Update Treatment");
        System.out.print("Enter your choice: ");
        int updateChoice = sc.nextInt();
        sc.nextLine();

        String updateSql;
        PreparedStatement updateStatement;
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Diagnosis: ");
                String newDiagnosis = sc.nextLine();
                updateSql = "UPDATE MedicalRecord SET Diagnosis = ? WHERE RecordID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newDiagnosis);
                break;
            case 2:
                System.out.print("Enter new Treatment: ");
                String newTreatment = sc.nextLine();
                updateSql = "UPDATE MedicalRecord SET Treatment = ? WHERE RecordID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newTreatment);
                break;
            default:
                System.out.println("Invalid choice for update. Please try again.");
                return;
        }

        updateStatement.setInt(2, recordIDToUpdate);
        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Medical record updated successfully.");
        } else {
            System.out.println("Medical record not found or update failed.");
        }
    }



    private static void deleteMedicalRecord(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Medical Record ID to delete: ");
        int recordIDToDelete = sc.nextInt();

        String deleteSql = "DELETE FROM MedicalRecord WHERE RecordID = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, recordIDToDelete);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medical record deleted successfully.");
            } else {
                System.out.println("Medical record not found or delete failed.");
            }
        }
    }

    // Methods for Clinic CRUD Operations
    private static void createClinic(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Clinic ID: ");
        int clinicID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Clinic Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Clinic Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Clinic Contact Info: ");
        String contactInfo = sc.nextLine();

        String createSql = "INSERT INTO Clinic (ClinicID, Name, Address, ContactInfo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement createStatement = connection.prepareStatement(createSql)) {
            createStatement.setInt(1, clinicID);
            createStatement.setString(2, name);
            createStatement.setString(3, address);
            createStatement.setString(4, contactInfo);

            int rowsAffected = createStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Clinic added successfully.");
            } else {
                System.out.println("Failed to add clinic.");
            }
        }
    }

    private static void retrieveClinics(Connection connection) throws SQLException {
        String readSql = "SELECT * FROM Clinic";
        try (Statement readStatement = connection.createStatement()) {
            ResultSet resultSet = readStatement.executeQuery(readSql);
            while (resultSet.next()) {
                int clinicID = resultSet.getInt("ClinicID");
                String name = resultSet.getString("Name");
                String address = resultSet.getString("Address");
                String contactInfo = resultSet.getString("ContactInfo");
                System.out.println("ID: " + clinicID + ", Name: " + name + ", Address: " + address + ", Contact Info: " + contactInfo);
            }
        }
    }

    private static void updateClinic(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Clinic ID to update: ");
        int clinicIDToUpdate = sc.nextInt();
        sc.nextLine();

        System.out.println("Choose what to update:");
        System.out.println("1. Update Name");
        System.out.println("2. Update Address");
        System.out.println("3. Update Contact Info");
        System.out.print("Enter your choice: ");
        int updateChoice = sc.nextInt();
        sc.nextLine();

        String updateSql;
        PreparedStatement updateStatement;
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Clinic Name: ");
                String newName = sc.nextLine();
                updateSql = "UPDATE Clinic SET Name = ? WHERE ClinicID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newName);
                break;
            case 2:
                System.out.print("Enter new Clinic Address: ");
                String newAddress = sc.nextLine();
                updateSql = "UPDATE Clinic SET Address = ? WHERE ClinicID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newAddress);
                break;
            case 3:
                System.out.print("Enter new Clinic Contact Info: ");
                String newContactInfo = sc.nextLine();
                updateSql = "UPDATE Clinic SET ContactInfo = ? WHERE ClinicID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, newContactInfo);
                break;
            default:
                System.out.println("Invalid choice for update. Please try again.");
                return;
        }

        updateStatement.setInt(2, clinicIDToUpdate);
        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Clinic updated successfully.");
        } else {
            System.out.println("Clinic not found or update failed.");
        }
    }

    private static void deleteClinic(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Enter Clinic ID to delete: ");
        int clinicIDToDelete = sc.nextInt();

        String deleteSql = "DELETE FROM Clinic WHERE ClinicID = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, clinicIDToDelete);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Clinic deleted successfully.");
            } else {
                System.out.println("Clinic not found or delete failed.");
            }
        }
    }
}

