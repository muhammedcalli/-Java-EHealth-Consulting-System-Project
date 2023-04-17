package GUI;

import main.java.DatabaseConnector;
import main.java.ExportHealthInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type User edit health info.
 */
public class UserEditHealthInfo extends JDialog
{
    private JPanel MainPanel;
    private JTextArea taMedication;
    private JTextArea taOther;
    private JTextArea taAllergies;
    private JTextArea taPreExIll;
    private JButton updateButton;
    private JButton exportPdfButton;
    private JButton exportTxtButton;
    private JButton dashboardButton;


    /**
     * Instantiates a new User edit health info.
     *
     * @param parent the parent
     * @param userID the user id
     */
    public UserEditHealthInfo(JFrame parent, int userID)
    {
        super(parent);
        setTitle("Health Information");
        setContentPane(MainPanel);
        setSize(new Dimension(760, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        String[] healthInfo = databaseConnector.getHealthInfo(userID);

        taMedication.setText(healthInfo[0]);
        taAllergies.setText(healthInfo[1]);
        taOther.setText(healthInfo[2]);
        taPreExIll.setText(healthInfo[3]);

        dashboardButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                DashboardForm dashboardForm = new DashboardForm(null, userID);
            }
        });

        updateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String medication = taMedication.getText();
                String allergies = taAllergies.getText();
                String other = taOther.getText();
                String preIllness = taPreExIll.getText();
                String[] healthInfo = {medication, allergies, other, preIllness};
                databaseConnector.editHealthInfo(healthInfo, userID);
                JOptionPane.showMessageDialog(null, "Updated!");

            }
        });

        exportPdfButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new ExportHealthInfo().createPDF(userID);
                JOptionPane.showMessageDialog(null, "Downloaded!" + "\nSaved to Downloads");

            }
        });

        exportTxtButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                new ExportHealthInfo().createTXT(userID);
                JOptionPane.showMessageDialog(null, "Downloaded!" + "\nSaved to Downloads");

            }
        });
        setVisible(true);
    }

}
