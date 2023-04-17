package Email;

import main.java.DatabaseConnector;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;


//https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
/*There are following three steps to send email using JavaMail. They are as follows:

Get the session object that stores all the information of host like host name, username, password etc.
create the message
send the message

 */

/**
 * The type Mail.
 */
public class Mail
{
    private static String host;
    private static String tls;
    private static String socketFactory;
    private static String auth;
    private static String port;
    private static String sslTrust;
    private static String from;
    private static String accPassword;

    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    /**
     * The Timer.
     */
    Timer timer = null;

    /**
     * Instantiates a new Mail.
     */
    public Mail()
    {
        host = "smtp.gmail.com";
        tls = "true";
        socketFactory = "javax.net.ssl.SSLSocketFactory";
        auth = "true";
        port = "587";
        sslTrust = "smtp.mailtrap.io";
        from = "javaprojectfra@gmail.com";
        accPassword = "Admin2022";
    }

    private Session prepareSession()
    {
        // creating properties object
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable", tls);
        properties.put("mail.smtp.socketFactory.class", socketFactory);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", sslTrust);

        // to send a massage, a session has to be created; user and password need to be authenticated
        // https://docs.oracle.com/javaee/7/api/javax/mail/PasswordAuthentication.html
        return Session.getDefaultInstance(properties,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(from, accPassword);
                    }
                });
    }

    // setting the recipient and creating a message object
    private MimeMessage prepareMessage(String recipientEmail)
    {
        try {
            MimeMessage msg = new MimeMessage(prepareSession());
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            return msg;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send registration confirmation email to user.
     *
     * @param recipientEmail the recipient email
     */
    public void sendRegistrationActivationPatient(String recipientEmail)
    {
        int userID = databaseConnector.getID(recipientEmail);
        ResultSet resultSet = databaseConnector.getResultSetUser(userID);

        try {
            resultSet.first();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            MimeMessage msg = prepareMessage(recipientEmail);

            msg.setSubject(firstName + ": Your registration was successful!");
            msg.setText(firstName + ":  Your registration was successful!");
            msg.setContent("<h1>" + firstName + " " + lastName + " Congratulations! : Your registration was successful</h1>", "text/html");

            Transport.send(msg);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send appointment confirmation email.
     *
     * @param recipientEmail the recipient email
     */
    public void sendAppointmentConfirmation(String recipientEmail, String doctorName, String address, int plz, Timestamp timestamp)
    {
        int userID = databaseConnector.getID(recipientEmail);
        ResultSet resultSet = databaseConnector.getResultSetUser(userID);

        try {
            resultSet.first();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            MimeMessage msg = prepareMessage(recipientEmail);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String time = timestamp.toLocalDateTime().format(formatter);

            msg.setSubject(firstName + ": Your appointment was created successfully");
            msg.setText(firstName + ":  Your appointment was created successfully");
            msg.setContent("<h1>" + firstName + " " + lastName + ": Your Appointment with " + doctorName +
                    " was created successfully. The address of the doctor is "+ address + " " + plz +". The appointment " +
                    "is on the "+ time +".</h1>", "text/html");

            Transport.send(msg);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send appointment reminder email.
     *
     * @param recipientEmail the recipient email
     */
    public void sendAppointmentReminder(String recipientEmail)
    {

        int userID = databaseConnector.getID(recipientEmail);
        ResultSet resultSet = databaseConnector.getResultSetUser(userID);

        try {
            resultSet.first();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            MimeMessage msg = prepareMessage(recipientEmail);

            msg.setSubject(firstName + ": Dont forget your Appointment!");
            msg.setText(firstName + ":  Dont forget your Appointment!");
            msg.setContent("<h1>" + firstName + " " + lastName + ": Your Appointment is coming up, dont forget it!</h1>", "text/html");

            Transport.send(msg);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send appointment has been cancelled email.
     *
     * @param recipientEmail the recipient email
     * @param appointment    the appointment
     */
    public void sendAppointmentCancelled(String recipientEmail, String appointment)
    {

        int userID = databaseConnector.getID(recipientEmail);
        ResultSet resultSet = databaseConnector.getResultSetUser(userID);

        try {
            resultSet.first();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            MimeMessage msg = prepareMessage(recipientEmail);

            msg.setSubject(firstName + ": Your Appointment on " + appointment + " was cancelled.");
            msg.setText(firstName + ": Your Appointment on " + appointment + " was cancelled.");
            msg.setContent("<h1>" + firstName + " " + lastName + " Your Appointment during this time and date" +
                    " was cancelled by you: " + appointment + "</h1>", "text/html");

            Transport.send(msg);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send appointment has been shifted email.
     *
     * @param recipientEmail the recipient email
     * @param appointment    the appointment
     */
    public void sendAppointmentShifted(String recipientEmail, String appointment)
    {

        int userID = databaseConnector.getID(recipientEmail);
        ResultSet resultSet = databaseConnector.getResultSetUser(userID);

        try {
            resultSet.first();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            MimeMessage msg = prepareMessage(recipientEmail);

            msg.setSubject(firstName + ": Your Appointment has been shifted.");
            msg.setText(firstName + ": Your Appointment has been shifted.");
            msg.setContent("<h1>" + firstName + " " + lastName + " Your Appointment has been shifted to now be " +
                    "as follows: " + appointment + "</h1>", "text/html");

            Transport.send(msg);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends reminder email about appointment to user at chosen delay.
     *
     * @param timestamp the timestamp
     * @param index     the index
     * @param email     the email
     * @return the reminder
     */
    public LocalDateTime setReminder(java.sql.Timestamp timestamp, int index, String email)//Index=Auswahl des Users
    {
        timer = new Timer();

        LocalDateTime appointmentDate = timestamp.toLocalDateTime();//Convert timestamp into localDateTime
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderDate;

        if (index == 0) {
            reminderDate = appointmentDate.minus(1, ChronoUnit.WEEKS);
        } else if (index == 1) {
            reminderDate = appointmentDate.minus(3, ChronoUnit.DAYS);
        } else if (index == 2) {
            reminderDate = appointmentDate.minus(1, ChronoUnit.HOURS);
        } else {
            reminderDate = appointmentDate.minus(10, ChronoUnit.MINUTES);
        }
        now = now.truncatedTo(ChronoUnit.MINUTES);
        long delay = now.until(reminderDate, ChronoUnit.MILLIS);
        if (delay < 0) delay = 10;//Wenn reminder in der Vergangenheit liegt, wird standart auf 10millisec gesetzt

        System.out.println("Reminder Date: " + reminderDate);
        System.out.println("Now: " + now);
        System.out.println("Delay: " + delay);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                new Mail().sendAppointmentReminder(email);
                timer.cancel();
            }
        };
        timer.schedule(task, delay);
        return reminderDate;
    }
}
