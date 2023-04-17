package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Dashboard form.
 */
public class DashboardForm extends JDialog
{
    private JButton editProfileButton;
    private JButton makeAppointmentButton;
    private JButton cancelShiftAppointmentButton;
    private JPanel MainPanel;
    private JButton logoutButton;
    private JButton editHealthInformationButton;


    /**
     * Instantiates a new Dashboard form.
     *
     * @param parent the parent
     * @param userID the user id
     */
    public DashboardForm(JFrame parent, int userID)
    {
        super(parent);
        setTitle("Dashboard");
        setContentPane(MainPanel);
        setSize(new Dimension(720, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        editProfileButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                EditProfileForm editProfileForm = new EditProfileForm(null, userID);
            }
        });


        //GOTO MAKE APPOINTMENT FORM
        makeAppointmentButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                MakeAppointmentForm makeAppointmentForm = new MakeAppointmentForm(null, userID);
            }
        });

        //Cancel/Shift-Appointment button
        cancelShiftAppointmentButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                CancelShiftAppForm cancelShiftAppForm = new CancelShiftAppForm(null, userID);
            }
        });

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

        //
        editHealthInformationButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                UserEditHealthInfo userEditHealthInfo = new UserEditHealthInfo(null, userID);
            }
        });

        setVisible(true);
    }
}
