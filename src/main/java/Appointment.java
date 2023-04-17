package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * The type Appointment.
 */
public class Appointment
{
    /**
     * The Date time formatter.
     */
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private java.sql.Timestamp appointmentDate;
    private int userID;
    private int doctorID;
    private int id;

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentDate the appointment date
     * @param userID          the user id
     * @param doctorID        the doctor id
     */
    public Appointment(java.sql.Timestamp appointmentDate, int userID, int doctorID)
    {
        this.appointmentDate = appointmentDate;
        this.userID = userID;
        this.doctorID = doctorID;
    }

    /**
     * Gets appointment date.
     *
     * @return the appointment date
     */
    public java.sql.Timestamp getAppointmentDate()
    {
        return appointmentDate;
    }

    /**
     * Sets appointment date.
     *
     * @param appointmentDate the appointment date
     */
    public void setAppointmentDate(java.sql.Timestamp appointmentDate)
    {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * Gets doctor id.
     *
     * @return the doctor id
     */
    public int getDoctorID()
    {
        return doctorID;
    }

    /**
     * Sets doctor id.
     *
     * @param doctorID the doctor id
     */
    public void setDoctorID(int doctorID)
    {
        this.doctorID = doctorID;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id)
    {
        this.id = id;
    }


    @Override
    public String toString()
    {
        String doctorName = null;
        ResultSet resultSet = DatabaseConnector.getInstance().getDoctorForAppointment(id);
        try {
            resultSet.first();
            doctorName = resultSet.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Doctor: " + doctorName + " " + dateTimeFormatter.format(appointmentDate.toLocalDateTime());
    }
}
