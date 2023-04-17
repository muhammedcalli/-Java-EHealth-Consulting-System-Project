package GUI;

import Email.Mail;
import main.java.DatabaseConnector;
import main.java.Hash;
import main.java.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//TODO: Code verschönern + checken ob username oder email schon vorhanden sind

/**
 * The type Registration form.
 */
public class RegistrationForm extends JDialog
{
    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfDateOfBirth;
    private JTextField tfAddress;
    private JPasswordField pfPassword;
    private JPasswordField pfPasswordConfirm;
    private JComboBox cbInsuranceType;
    private JTextField tfInsuranceName;
    private JTextField tfEmail;
    private JButton registerButton;
    private JButton cancelButton;
    private JTextField tfPlz;
    private JTextField tfCity;
    private JPanel MainPanel;
    private JLabel RegisterPanel;
    private JTextField tfUsername;


    /**
     * Instantiates a new Registration form.
     *
     * @param parent the parent
     */
    public RegistrationForm(JFrame parent)
    {
        super(parent);
        setTitle("Create new Account");
        setContentPane(MainPanel);
        setSize(new Dimension(900, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //Add user to database
        registerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (checkFields()) {
                    JOptionPane.showMessageDialog(null, "Registration done!");
                    databaseConnector.addUser(registerUser());
                    String email = tfEmail.getText();
                    new Mail().sendRegistrationActivationPatient(email);

                    dispose();
                    LoginForm login = new LoginForm(null);
                }
            }
        });

        //return to Login Page
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dispose();
                LoginForm login = new LoginForm(null);
            }
        });

        setVisible(true);
    }

    private boolean checkFields()
    {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String dateOfBirth = tfDateOfBirth.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String passwordConfirm = String.valueOf(pfPasswordConfirm.getPassword());
        String address = tfAddress.getText();
        int plz = tfPlz.getColumns();
        String city = tfCity.getText();
        String insuranceName = tfInsuranceName.getText();
        String privateInscurance = (String) cbInsuranceType.getSelectedItem();
        String username = tfUsername.getText();

        //Checking if every text field is filled
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
                || city.isEmpty() || insuranceName.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //Check if the password is the same as password confirm
        if (!password.equals(passwordConfirm)) {
            JOptionPane.showMessageDialog(this,
                    "ERROR! Password does not match!",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }


        //Password length at least 6 digits
        if (!(password.length() > 5)) {
            JOptionPane.showMessageDialog(this,
                    "ERROR! Password is to short!",
                    "Lenght >= 6",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        }

        if (!checkPassword(password)) {
            //CHeck other important password req
            JOptionPane.showMessageDialog(this,
                    "ERROR! Please use a number, Capital- and lowercase letters!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!databaseConnector.isUsernameUnique(username)) {
            //Check if username is unique
            JOptionPane.showMessageDialog(this,
                    "ERROR! Username already taken!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!databaseConnector.isEmailUnique(email)) {
            //Check if email is unique
            JOptionPane.showMessageDialog(this,
                    "ERROR! Email already in use!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //Error if no "@" is in the text field
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "ERROR! Please insert a valid Email-address",
                    "Try again!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    //Checks if the user input contains at least one lower-, one uppercast letter and a number
    private boolean checkPassword(String password)
    {
        boolean gotNumbers = false;
        boolean gotCapitalLetters = false;
        boolean gotLowerLetters = false;
        char c;
        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);
            if (Character.isDigit(c)) {
                gotNumbers = true;
            } else if (Character.isUpperCase(c)) {
                gotCapitalLetters = true;
            } else if (Character.isLowerCase(c)) {
                gotLowerLetters = true;
            }
            if (gotNumbers && gotCapitalLetters && gotLowerLetters) {
                return true;
            }
        }
        return false;
    }

    private User registerUser()
    {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String dateOfBirth = tfDateOfBirth.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String passwordConfirm = String.valueOf(pfPasswordConfirm.getPassword());
        String address = tfAddress.getText();
        int plz = Integer.parseInt(tfPlz.getText());
        String city = tfCity.getText();
        String insuranceName = tfInsuranceName.getText();
        String privateInscurance = (String) cbInsuranceType.getSelectedItem();
        String username = tfUsername.getText();

        String salt = Hash.getSalt();
        password = Hash.hash(password + salt);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dob = LocalDate.parse(dateOfBirth, format);

        boolean insuranceType;
        insuranceType = !privateInscurance.equals("Public");

        return new User(firstName, lastName, dob, address, plz, city, insuranceName, insuranceType, username, email, password, salt);

    }
}
