package GUI;

import Email.Mail;
import Maps.Map;
import com.toedter.calendar.JDateChooser;
import main.java.Appointment;
import main.java.DatabaseConnector;
import main.java.Doctor;
import main.java.HealthProblem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


/**
 * The type Make appointment form.
 */
public class MakeAppointmentForm extends JDialog
{
    /**
     * The Calendar.
     */
    Calendar calendar = Calendar.getInstance();
    /**
     * The Date chooser.
     */
    JDateChooser dateChooser = new JDateChooser(calendar.getTime()); //create datechooser
    /**
     * The Date format.
     */
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * The My format obj.
     */
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    private JComboBox cbHealthProblem;
    private JTextField tfDistanceOfSearch;
    private JButton backButton;
    private JPanel MainPanel;
    private JComboBox cbReminder;
    private JPanel jpCald; //declaration of calendar icon and place in the frame
    private JButton appointmentButton;
    private JButton displayDoctorsButton;
    private JList listDoctors;
    private JFormattedTextField ftxTime;

    /**
     * Instantiates a new Make appointment form.
     *
     * @param parent the parent
     * @param userID the user id
     */
    public MakeAppointmentForm(JFrame parent, int userID)
    {
        super(parent);
        setTitle("Make Appointment");
        setContentPane(MainPanel);
        setSize(new Dimension(820, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        dateChooser.setDateFormatString("dd/MM/yyyy"); //set date format
        jpCald.add(dateChooser); //add dateChooser to panel jpCald

        DefaultComboBoxModel<HealthProblem> model = new DefaultComboBoxModel<>();
        List<HealthProblem> healthProblems = databaseConnector.getHealthProblems();
        for (HealthProblem hp : healthProblems) {
            model.addElement(hp);
        }
        cbHealthProblem.setModel(model);

        displayDoctorsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (tfDistanceOfSearch.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainPanel, "Invalid distance");
                    return;
                }

                Object selectedItem = cbHealthProblem.getSelectedItem();
                HealthProblem healthProblem = (HealthProblem) selectedItem;
                if (healthProblem == null) return;

                String doctorType = healthProblem.getDoctorType();

                List<Doctor> doctors = databaseConnector.getDoctors(healthProblem.getId());
                DefaultListModel<Doctor> doctorListModel = new DefaultListModel<>();

                int distance = Integer.parseInt(tfDistanceOfSearch.getText());
                for (Doctor doctor : doctors) {
                    try {
                        if (Map.calculate(doctor, userID, distance)) {
                            doctorListModel.addElement(doctor);
                        }
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                listDoctors.setModel(doctorListModel);
            }
        });


        //Combobox Reminder email
        cbReminder.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cbReminder.getSelectedItem();
            }
        });


        appointmentButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (ftxTime.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainPanel, "Invalid Time");
                    return;
                }

                //String date = dateFormat.format(dateChooser.getDate());
                //String time = ftxTime.getText();
                //LocalDateTime test = LocalDateTime.parse(date+" "+time, myFormatObj);
                //java.sql.Timestamp timestamp = Timestamp.valueOf(test);

                String time_text = ftxTime.getText();
                if (time_text.charAt(2) != ':') {
                    JOptionPane.showMessageDialog(MainPanel, "Invalid Time Input. Try again!");
                    return;
                }

                //doesnt work in java this way sadly
                //https://stackoverflow.com/questions/7536755/regular-expression-for-matching-hhmm-time-format/7536768
                //if(!(time_text.matches("/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/")))
                //{
                //    System.out.println(time_text);
                //    JOptionPane.showMessageDialog(MainPanel, "Invalid Time Input. Try again!");
                //    return;
                //}

                final var date = dateChooser.getDate();
                final var time = LocalTime.parse(time_text);
                final var dateTime = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(time);
                java.sql.Timestamp timestamp = Timestamp.valueOf(dateTime);
                //das hat mir jemand von nem öffentlichen discord gezeigt @nimel r/de discord

                Object selectedValue = listDoctors.getSelectedValue();
                Doctor doctor = (Doctor) selectedValue;
                if (doctor == null) {
                    JOptionPane.showMessageDialog(MainPanel, "Please choose a doctor!");
                    return;
                }
                Appointment appointment = new Appointment(timestamp, userID, doctor.getId());
                databaseConnector.makeAppointment(appointment);
                JOptionPane.showMessageDialog(MainPanel, "Appointment made");

                new Mail().sendAppointmentConfirmation(databaseConnector.getUserEmail(userID), doctor.getName(),
                        doctor.getAddress(), doctor.getPlz(), timestamp);
                new Mail().setReminder(timestamp, cbReminder.getSelectedIndex(), databaseConnector.getUserEmail(userID));

                dispose();
                DashboardForm dashboardForm = new DashboardForm(null, userID);
            }
        });


        //Goes back to Dashboard
        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Go back to dashboard form
                dispose();
                DashboardForm dashboardForm = new DashboardForm(null, userID);
            }
        });

        //Distance of search input restriction
        tfDistanceOfSearch.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                //User can only use numbers 0 to 9.
                //Everything else is locked
                char distance = e.getKeyChar();
                if (!((distance >= '0') && (distance <= '9') ||
                        (distance == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
                //User can only insert 3 or less digits
                if (tfDistanceOfSearch.getText().length() >= 3)
                    e.consume();

            }
        });

        //Distance of search input restriction
        ftxTime.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                //User can only use numbers 0 to 9 or :
                //Everything else is locked
                char time = e.getKeyChar();
                if (!((time == ':') || (time >= '0') && (time <= '9') ||
                        (time == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
                //User can only insert 5 or less digits
                if (ftxTime.getText().length() >= 5)
                    e.consume();

            }
        });
        setVisible(true);
    }

}