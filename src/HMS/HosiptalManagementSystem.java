package HMS;

import java.sql.*;
import java.util.Scanner;

public class HosiptalManagementSystem
{
    private static final String url = "jdbc:mysql://localhost:3306/HMS";

    private static final String username = "root";

    private static final String password = "rakesh@2231";

    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try
        {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);

            while(true)
            {
                System.out.println(" HOSIPTAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice : ");
                int choice = scanner.nextInt();
                switch (choice)
                {
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view patient details
                        patient.viewPatients();
                        System.out.println();
                        break;

                    case 3:
                        //view doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        //check availability of doctors
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;


                    case 5:
                        //add patient
                        return;

                    default:
                        System.out.println("Enter valid choice...");
                        break;
                }

            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
    {
        System.out.println("Enter Patient ID : ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor ID : ");
        int doctorID = scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD) : ");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorID))
        {
            if(checkavail(doctorID,appointmentDate,connection))
            {
                String appquery = "insert into appointments(patient_id,doctor_id,appointment_date) values (?,?,?)";
                try
                {
                    PreparedStatement preparedStatement = connection.prepareStatement(appquery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorID);
                    preparedStatement.setString(3,appointmentDate);

                    int rowseff = preparedStatement.executeUpdate();
                    if(rowseff > 0) System.out.println("Appointment booked");
                    else System.out.println("Failed to book appointment");


                } catch (SQLException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                System.out.println("Doctor not available this date...");
            }

        }
        else
        {
            System.out.println("Either doctor or patient does not exist ...");
        }


    }
    public static boolean checkavail(int doctorID,String appointmentDate,Connection connection)

    {
        String qurey = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(qurey);
            preparedStatement.setInt(1,doctorID);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int cnt = resultSet.getInt(1);
                if(cnt == 0) return true;
                else return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
