package GUI;

import main.java.DatabaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Login form.
 */
public class LoginForm extends JDialog
{
    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    private JTextField pLogin;
    private JPasswordField pfPassword;
    private JButton loginButton;
    private JButton createNewAccountButton;
    private JPanel MainPanel;

    /**
     * Instantiates a new Login form.
     *
     * @param parent the parent
     */
    public LoginForm(JFrame parent)
    {
        super(parent);
        setTitle("Smart E-Health Consulting");
        setContentPane(MainPanel);
        setSize(new Dimension(720, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //Redirect to registration form
        createNewAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();//Closes login frame
                RegistrationForm registration = new RegistrationForm(null);//initialize new jframe
            }
        });

        //Login into dashboard
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //always lower case
                String login = pLogin.getText().toLowerCase();
                String password = String.valueOf(pfPassword.getPassword());

                if (databaseConnector.isLoginCorrect(login, password)) {
                    int userID = databaseConnector.getID(login);
                    if (databaseConnector.isAdmin(userID)) {
                        dispose();
                        AdminForm adminForm = new AdminForm(null);
                    } else {
                        dispose();
                        DashboardForm dashboardForm = new DashboardForm(null, userID);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "ERROR! Password/Login is not correct.",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }
}
