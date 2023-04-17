package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Admin form.
 */
public class AdminForm extends JDialog
{
    private JButton viewUserInfoButton;
    private JButton logoutButton;
    private JPanel MainPanel;

    /**
     * Instantiates a new Admin form.
     *
     * @param parent the parent
     */
    public AdminForm(JFrame parent)
    {
        super(parent);
        setTitle("Admin Dashboard");
        setContentPane(MainPanel);
        setSize(new Dimension(420, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //Logout button
        logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                LoginForm login = new LoginForm(null);
            }
        });

        //Displays all users in a table
        viewUserInfoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new ShowAllUsers().showAllUsers();
            }
        });
        setVisible(true);
    }
}
