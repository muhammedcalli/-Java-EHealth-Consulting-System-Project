package GUI;

import main.java.DatabaseConnector;
import main.java.Hash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * The type Edit profile form.
 */
public class EditProfileForm extends JDialog
{

    private JButton Back;
    private JButton applyChangesButton;
    private JPanel MainPanel;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfDob;
    private JTextField tfPlz;
    private JTextField tfAddress;
    private JTextField tfCity;
    private JTextField tfInscurancename;
    private JComboBox cbInscurancetype;
    private JPasswordField pfPassword;
    private JPasswordField pfPassconf;

    /**
     * Instantiates a new Edit profile form.
     *
     * @param parent the parent
     * @param userID the user id
     */
    public EditProfileForm(JFrame parent, int userID)
    {
        super(parent);
        setTitle("Edit Profile");
        setContentPane(MainPanel);
        setSize(new Dimension(720, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        ResultSet userSet = databaseConnector.getResultSetUser(userID);
        String salt = "";

        try {
            userSet.first();
            tfFirstName.setText(userSet.getString("firstname"));
            tfLastName.setText(userSet.getString("lastname"));
            tfDob.setText(userSet.getString("dateOfBirth"));
            tfPlz.setText(String.valueOf(userSet.getInt("plz")));
            tfAddress.setText(userSet.getString("address"));
            tfCity.setText(userSet.getString("city"));
            tfInscurancename.setText(userSet.getString("insuranceName"));

            int insurance = userSet.getBoolean("privateInsurance") ? 1 : 0;
            cbInscurancetype.setSelectedIndex(insurance);


            pfPassword.setText("");
            pfPassconf.setText("");

            salt = userSet.getString("salt");

        } catch (Exception e) {
            e.printStackTrace();
        }


        //GO back to Dashboard button
        Back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                DashboardForm dashboardForm = new DashboardForm(null, userID);
            }
        });


        String finalSalt = salt;
        applyChangesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String password = String.valueOf(pfPassword.getPassword());
                String passconf = String.valueOf(pfPassconf.getPassword());
                String newPass;
                if (!password.equals("")) {
                    if (password.equals(passconf)) {
                        newPass = Hash.hash(password + finalSalt);

                        String sql = "UPDATE baliw43ry3s7plcfbs7z.user SET " +
                                "firstname = '" + tfFirstName.getText() + "'," +
                                "lastname = '" + tfLastName.getText() + "'," +
                                "dateOfBirth = '" + tfDob.getText() + "'," +
                                "plz = '" + Integer.parseInt(tfPlz.getText()) + "'," +
                                "address = '" + tfAddress.getText() + "'," +
                                "city = '" + tfCity.getText() + "'," +
                                "insuranceName = '" + tfInscurancename.getText() + "'," + //privateInsurance
                                "privateInsurance = '" + cbInscurancetype.getSelectedIndex() + "'," +
                                "password = '" + newPass + "'" +
                                "WHERE id = " + userID;

                        databaseConnector.createOrUpdateOrDelete(sql);
                        JOptionPane.showMessageDialog(null, "Updated!");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Passwords do not match!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String sql = "UPDATE baliw43ry3s7plcfbs7z.user SET " +
                            "firstname = '" + tfFirstName.getText() + "'," +
                            "lastname = '" + tfLastName.getText() + "'," +
                            "dateOfBirth = '" + tfDob.getText() + "'," +
                            "plz = '" + Integer.parseInt(tfPlz.getText()) + "'," +
                            "address = '" + tfAddress.getText() + "'," +
                            "city = '" + tfCity.getText() + "'," +
                            "insuranceName = '" + tfInscurancename.getText() + "'," + //privateInsurance
                            "privateInsurance = '" + cbInscurancetype.getSelectedIndex() + "'" +
                            "WHERE id = " + userID;

                    databaseConnector.createOrUpdateOrDelete(sql);
                    JOptionPane.showMessageDialog(null, "Updated!");
                }
            }
        });

        setVisible(true);
    }
}