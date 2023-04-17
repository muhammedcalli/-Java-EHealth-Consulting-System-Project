package GUI;

import Email.Mail;
import main.java.Appointment;
import main.java.DatabaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The type Cancel shift app form.
 */
public class CancelShiftAppForm extends JDialog
{
    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    private JButton cancelAppointmentButton;
    private JButton shiftAppointmentButton;
    private JButton dashboardButton;
    private JPanel Main;
    private JList listAppointments;

    /**
     * Instantiates a new Cancel shift app form.
     *
     * @param parent the parent
     * @param userID the user id
     */
    public CancelShiftAppForm(JFrame parent, int userID)
    {
        super(parent);
        setTitle("Cancel or Shift Appointment");
        setContentPane(Main);
        setSize(new Dimension(740, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<Appointment> appointments = databaseConnector.getAppointments(userID);
        updateAppointments(userID);

        cancelAppointmentButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!listAppointments.isSelectionEmpty()) {
                    new Mail().sendAppointmentCancelled(databaseConnector.getUserEmail(userID), listAppointments.getSelectedValue().toString());
                    databaseConnector.cancelAppointment(appointments.get(listAppointments.getSelectedIndex()).getId());
                    System.out.println(appointments.get(listAppointments.getSelectedIndex()).getId() + " cancelled");
                }
                updateAppointments(userID);
            }
        });

        shiftAppointmentButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Improvised and done on a short notice, should be redone in the future
                if (!listAppointments.isSelectionEmpty()) {

                    String newTime = JOptionPane.showInputDialog("Enter the Date and Time format in following format:\n" +
                            " dd/mm/yyyy hh:mm Example: 31/10/2022 19:30");

                    if (newTime.length() != 16) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Date and Time!");
                        return;
                    }

                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime localDateTime = LocalDateTime.parse(newTime, myFormatObj);
                    java.sql.Timestamp timestamp = Timestamp.valueOf(localDateTime);

                    new Mail().sendAppointmentShifted(databaseConnector.getUserEmail(userID), listAppointments.getSelectedValue().toString());
                    databaseConnector.shiftAppointment(appointments.get(listAppointments.getSelectedIndex()).getId(), timestamp);
                }
                updateAppointments(userID);
            }
        });


        dashboardButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                DashboardForm dashboardForm = new DashboardForm(null, userID);
            }
        });
        setVisible(true);
    }

    private void updateAppointments(int userID)
    {
        List<Appointment> appointments = databaseConnector.getAppointments(userID);
        DefaultListModel<Appointment> appointmentListModel = new DefaultListModel<>();

        for (Appointment ap : appointments) {
            appointmentListModel.addElement(ap);
        }
        listAppointments.setModel(appointmentListModel);
    }
}
