package GUI;

import main.java.DatabaseConnector;
import main.java.Hash;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The type Show all users.
 */
public class ShowAllUsers
{
    /**
     * The Result set.
     */
    ResultSet resultSet = null;
    /**
     * The Result set user.
     */
    ResultSet resultSetUser = null;

    /**
     * The Database connector.
     */
    DatabaseConnector databaseConnector = DatabaseConnector.getInstance();

    /**
     * Show all users.
     */
    public void showAllUsers()
    {
        resultSet = databaseConnector.getResultSetAllUsers();

        try {
            //Setup columns and size of the table
            DefaultTableCellRenderer cellRenderer;
            String[] columns = {"id", "firstname", "lastname", "dateOfBirth", "address", "plz", "city", "username", "email", "insuranceName", "privateInsurance", "password", "isAdmin"};
            String[][] data = new String[20][40];

            //get the db data and safe them in the variables
            int i = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstName");
                String lastname = resultSet.getString("lastName");
                Date dob = resultSet.getDate("dateOfBirth");
                String address = resultSet.getString("address");
                int plz = resultSet.getInt("plz");
                String city = resultSet.getString("city");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String insuranceName = resultSet.getString("insuranceName");
                boolean privateInsurance = resultSet.getBoolean("privateInsurance");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                //Set the data in the colmuns
                data[i][0] = String.valueOf(id);
                data[i][1] = firstname;
                data[i][2] = lastname;
                data[i][3] = String.valueOf(dob);
                data[i][4] = address;
                data[i][5] = String.valueOf(plz);
                data[i][6] = city;
                data[i][7] = username;
                data[i][8] = email;
                data[i][9] = insuranceName;
                data[i][10] = Boolean.toString(privateInsurance);
                data[i][11] = password;
                data[i][12] = Boolean.toString(isAdmin);
                i++;
            }
            //Create table and insert fetched data
            DefaultTableModel model = new DefaultTableModel(data, columns);//data and columns are initialized at beginning
            JTable table = new JTable(model);//create table
            table.setShowGrid(true);//draws lines around the cells
            table.setGridColor(Color.BLACK);//ste color of the grid
            //table.setShowVerticalLines(true);
            table.getTableHeader().setReorderingAllowed(false);//Prevent user to realign table columns
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//Table can not grow bigger

            //set the columns width
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
            table.getColumnModel().getColumn(2).setPreferredWidth(120);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(200);
            table.getColumnModel().getColumn(5).setPreferredWidth(90);
            table.getColumnModel().getColumn(6).setPreferredWidth(120);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(100);
            table.getColumnModel().getColumn(9).setPreferredWidth(100);
            table.getColumnModel().getColumn(10).setPreferredWidth(100);
            table.getColumnModel().getColumn(11).setPreferredWidth(100);
            table.getColumnModel().getColumn(12).setPreferredWidth(100);

            //cellRenderer = new DefaultTableCellRenderer();
            //cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            //table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
            JScrollPane pane = new JScrollPane(table);//Set scroll pane at bottom of table
            table.setDefaultEditor(Object.class, null);//Set Table not editable

            //Set colour of table header
            JTableHeader header = table.getTableHeader();
            header.setBackground(Color.ORANGE);

            //Create the frame to display the table
            JFrame frame = new JFrame("All Users");

            //Create return to dashboard button with function
            JButton cancel = new JButton("Dashboard");
            cancel.setBounds(200, 100, 100, 40);
            frame.add(cancel);

            //Set frame in the middle of the screen when it appears
            frame.pack();
            frame.setLocationRelativeTo(null);
            //frame.setResizable(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


            //Cancel button functionality
            cancel.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    frame.dispose();
                    AdminForm adminForm = new AdminForm(null);
                }
            });

            //Text fields created
            JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13;
            t1 = new JTextField("ID");
            t1.setBounds(300, 50, 150, 30);
            t2 = new JTextField("FirstName");
            t2.setBounds(300, 100, 150, 30);
            t3 = new JTextField("LastName");
            t3.setBounds(300, 150, 150, 30);
            t4 = new JTextField("dateOfBirth (YYYY-MM-DD)");
            t4.setBounds(300, 200, 150, 30);
            t5 = new JTextField("address");
            t5.setBounds(300, 250, 150, 30);
            t6 = new JTextField("plz");
            t6.setBounds(300, 300, 150, 30);
            t7 = new JTextField("city");
            t7.setBounds(300, 350, 150, 30);
            t8 = new JTextField("username");
            t8.setBounds(300, 400, 150, 30);
            t9 = new JTextField("email");
            t9.setBounds(300, 450, 150, 30);
            t10 = new JTextField("insuranceName");
            t10.setBounds(300, 500, 150, 30);
            t11 = new JTextField("Password");
            t11.setBounds(300, 550, 150, 30);
            t12 = new JTextField(" ");
            t12.setBounds(300, 600, 150, 30);
            t13 = new JTextField(" ");
            t13.setBounds(500, 600, 150, 30);
            frame.add(t1);
            frame.add(t2);
            frame.add(t3);
            frame.add(t4);
            frame.add(t5);
            frame.add(t6);
            frame.add(t7);
            frame.add(t8);
            frame.add(t9);
            frame.add(t10);
            frame.add(t11);
            frame.add(t12);
            frame.add(t13);

            //Text over the JTextfields
            JLabel JId, JfirstName, JlastName, JDoB, Jaddress, JPlz, JCity, JUsername, Jemail,
                    JInscuranceName, JPassword, UserType, InsuranceType;

            //Text over the text fields
            JId = new JLabel("ID:");
            JId.setBounds(300, 25, 150, 30);
            frame.add(JId);

            JfirstName = new JLabel("First Name:");
            JfirstName.setBounds(300, 75, 150, 30);
            frame.add(JfirstName);

            JlastName = new JLabel("Last Name:");
            JlastName.setBounds(300, 125, 150, 30);
            frame.add(JlastName);

            JDoB = new JLabel("dateOfBirth (YYYY-MM-DD):");
            JDoB.setBounds(300, 175, 200, 30);
            frame.add(JDoB);

            Jaddress = new JLabel("Address:");
            Jaddress.setBounds(300, 225, 200, 30);
            frame.add(Jaddress);

            JPlz = new JLabel("Plz:");
            JPlz.setBounds(300, 275, 200, 30);
            frame.add(JPlz);

            JCity = new JLabel("City:");
            JCity.setBounds(300, 325, 200, 30);
            frame.add(JCity);

            JUsername = new JLabel("Username:");
            JUsername.setBounds(300, 375, 200, 30);
            frame.add(JUsername);

            Jemail = new JLabel("Email:");
            Jemail.setBounds(300, 425, 200, 30);
            frame.add(Jemail);

            JInscuranceName = new JLabel("Insurance Name:");
            JInscuranceName.setBounds(300, 475, 200, 30);
            frame.add(JInscuranceName);

            JPassword = new JLabel("Password:");
            JPassword.setBounds(300, 525, 200, 30);
            frame.add(JPassword);

            UserType = new JLabel("Is Admin:");
            UserType.setBounds(300, 575, 150, 30);
            frame.add(UserType);

            InsuranceType = new JLabel("Private Insurance:");
            InsuranceType.setBounds(500, 575, 150, 30);
            frame.add(InsuranceType);

            //If a row is selected the text fields are getting filled with the data in the table
            //Mouse click event
            table.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    super.mouseClicked(e);
                    int row = table.getSelectedRow();

                    String selection = table.getModel().getValueAt(row, 0).toString();
                    resultSetUser = databaseConnector.getResultSetUser(Integer.parseInt(selection));
                    try {
                        if (resultSetUser.next()) {
                            t1.setText(String.valueOf(resultSetUser.getInt("id")));
                            t2.setText(resultSetUser.getString("firstName"));
                            t3.setText(resultSetUser.getString("lastName"));
                            t4.setText(resultSetUser.getString("dateOfBirth"));
                            t5.setText(resultSetUser.getString("address"));
                            t6.setText(String.valueOf(resultSetUser.getInt("plz")));
                            t7.setText(resultSetUser.getString("city"));
                            t8.setText(resultSetUser.getString("username"));
                            t9.setText(resultSetUser.getString("email"));
                            t10.setText(resultSetUser.getString("insuranceName"));
                            t11.setText(resultSetUser.getString("password"));
                            t12.setText(resultSetUser.getObject("isAdmin", Boolean.class).toString());
                            t13.setText(resultSetUser.getObject("privateInsurance", Boolean.class).toString());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            });

            JButton Update = new JButton("Update Data");
            Update.setBounds(550, 450, 100, 40);
            frame.add(Update);

            //update newly entered Jtextfields in the db
            Update.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try {
                        String firstName = t2.getText();
                        String lastName = t3.getText();
                        String dob = t4.getText();
                        String address = t5.getText();
                        int plz = Integer.parseInt(t6.getText());
                        String city = t7.getText();
                        String username = t8.getText();
                        String email = t9.getText();
                        String insuranceName = t10.getText();
                        String newPassword = t11.getText();

                        String salt = resultSetUser.getString("salt");
                        if (!newPassword.equals(resultSetUser.getString("password"))) {
                            //hash passowrd with alt salt
                            newPassword = Hash.hash(t11.getText() + salt);
                        }

                        String sql = "UPDATE baliw43ry3s7plcfbs7z.user SET firstName='" + firstName + "', lastName='" + lastName + "', " +
                                "dateOfBirth='" + dob + "', address='" + address + "', plz='" + plz + "', city='" + city + "', " +
                                "username='" + username + "', email='" + email + "', insuranceName='" + insuranceName + "', " +
                                "password='" + newPassword + "', isAdmin= " + Boolean.parseBoolean(t12.getText()) + ", " +
                                "privateInsurance= " + Boolean.parseBoolean(t13.getText()) + " WHERE id=" + t1.getText();

                        databaseConnector.createOrUpdateOrDelete(sql);

                        JOptionPane.showMessageDialog(null, "Updated!");
                        //Reload page otherwise changes would be not displayed immediately
                        frame.dispose();
                        showAllUsers();
                    } catch (Exception exe) {
                        JOptionPane.showMessageDialog(null, "ERROR");
                        exe.printStackTrace();
                    }

                }
            });

            //DELETE selected user
            JButton Delete = new JButton("Delete User");
            Delete.setBounds(700, 450, 100, 40);
            frame.add(Delete);

            Delete.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try {
                        String sql = "DELETE FROM baliw43ry3s7plcfbs7z.user WHERE id=" + t1.getText();

                        databaseConnector.createOrUpdateOrDelete(sql);

                        JOptionPane.showMessageDialog(null, "User deleted!");
                        //Reload page otherwise changes would be not displayed immediately
                        frame.dispose();
                        showAllUsers();
                    } catch (Exception exe) {
                        JOptionPane.showMessageDialog(null, "ERROR");
                    }

                }
            });

            //MainPanel created (everything is displayed on that)
            JPanel panel = new JPanel();
            panel.add(pane);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
