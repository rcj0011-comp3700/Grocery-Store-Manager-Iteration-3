import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class menu extends Frame
{
    private String username, password;

    public void loginMenu()
    {
        username = "";
        password = "";

        JFrame loginWindow = new JFrame("Grocery Store Manager");
        loginWindow.setSize(600,500);
        loginWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel loginTitlePanel = new JPanel();
        loginTitlePanel.add(loginTitle);
        //loginTitlePanel.setBackground(Color.BLUE);
        loginTitlePanel.setBounds(150,5,300,50);
        loginTitlePanel.setOpaque(false);

        JPanel namePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        namePanel.add(nameField);
        //namePanel.setBackground(Color.BLUE);
        namePanel.setBounds(75,100,300,50);
        namePanel.setOpaque(false);

        JLabel usernameTitle = new JLabel("Username");
        JPanel usernameTitlePanel = new JPanel();
        usernameTitlePanel.add(usernameTitle);
        //usernameTitlePanel.setBackground(Color.GRAY);
        usernameTitlePanel.setBounds(85,75,100,50);
        usernameTitlePanel.setOpaque(false);

        JPanel passwordPanel = new JPanel();
        JTextField passwordField = new JTextField(20);
        passwordPanel.add(passwordField);
        //passwordPanel.setBackground(Color.RED);
        passwordPanel.setBounds(75,175,300,50);
        passwordPanel.setOpaque(false);

        JLabel passwordTitle = new JLabel("Password");
        JPanel passwordTitlePanel = new JPanel();
        passwordTitlePanel.add(passwordTitle);
        //passwordTitlePanel.setBackground(Color.GREEN);
        passwordTitlePanel.setBounds(85,150,100,50);
        passwordTitlePanel.setOpaque(false);

        JLabel errorLabel = new JLabel("Either your username or password was incorrect.");
        errorLabel.setVisible(false);
        JPanel errorPanel = new JPanel();
        errorPanel.add(errorLabel);
        //errorPanel.setBackground(Color.WHITE);
        errorPanel.setBounds(85,215,350,30);
        errorPanel.setOpaque(false);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 40));

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.add(loginButton);
        //loginButtonPanel.setBackground(Color.WHITE);
        loginButtonPanel.setBounds(75,250,200,50);
        loginButtonPanel.setOpaque(false);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                username = nameField.getText();
                password = passwordField.getText();

                String status = "";
                try {
                    status = validateUser(username, password);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if(status.equals("Not Valid"))
                    errorLabel.setVisible(true);
                else
                {
                    System.out.println("User: " + username + ", Pass: " + password);
                    errorLabel.setVisible(false);
                    login(username, password);
                }
            }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        loginWindow.add(loginTitlePanel);
        loginWindow.add(loginButtonPanel);
        loginWindow.add(namePanel);
        loginWindow.add(passwordPanel);
        loginWindow.add(usernameTitlePanel);
        loginWindow.add(passwordTitlePanel);
        loginWindow.add(errorPanel);
        loginWindow.add(everything);

        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setVisible(true);
    }

    public void login(String name, String password)
    {
        //display login menu, validate credentials
        String status = "";

        try {
            status = validateUser(name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (status.equals("Cashier"))
        {
            //display Cashier menu
            System.out.println("Logged in as Cashier: " + username + ".");
            cashierMenu();
        }
        else if (status.equals("Manager"))
        {
            //display Manager menu
            System.out.println("Logged in as Manager: " + username + ".");
            managerMenu();
        }
        else if (status.equals("Producer"))
        {
            //display Producer menu
            System.out.println("Logged in as Producer: " + username + ".");
            producerMenu();
        }
        else if (status.equals("Not Valid"))
        {
            System.out.println("Incorrect Username or Password. Please try again.");
            //display text with above message on GUI
        }
    }

    public String validateUser(String name, String password) throws SQLException
    {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String status = "";

        String query = "SELECT * FROM users";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            if (rs.getString("Name").equals(name)) {
                if (rs.getString("Password").equals(password)) {
                    status = rs.getString("Status");
                    con.close();
                    return status;
                }
            }
        }

        con.close();

        return "Not Valid";
    }

    public void cashierMenu()
    {
        JFrame cashierMainWindow = new JFrame("Grocery Store Manager");
        cashierMainWindow.setSize(600,500);
        cashierMainWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Cashier Main Menu");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel cashierTitlePanel = new JPanel();
        cashierTitlePanel.add(menuTitle);
        //cashierTitlePanel.setBackground(Color.BLUE);
        cashierTitlePanel.setBounds(150,5,300,50);
        cashierTitlePanel.setOpaque(false);

        JButton profileButton = new JButton("Profile");
        profileButton.setPreferredSize(new Dimension(150, 40));

        JPanel profileButtonPanel = new JPanel();
        profileButtonPanel.add(profileButton);
        //profileButtonPanel.setBackground(Color.WHITE);
        profileButtonPanel.setBounds(75,125,200,50);
        profileButtonPanel.setOpaque(false);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { profileMenu(); }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel checkoutButtonPanel = new JPanel();
        checkoutButtonPanel.add(checkoutButton);
        //checkoutButtonPanel.setBackground(Color.WHITE);
        checkoutButtonPanel.setBounds(75,250,200,50);
        checkoutButtonPanel.setOpaque(false);

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    checkout();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JLabel callManagerLabel = new JLabel("Manager Called.");
        JPanel callManagerLabelPanel = new JPanel();
        callManagerLabelPanel.add(callManagerLabel);
        //callManagerLabelPanel.setBackground(Color.BLUE);
        callManagerLabelPanel.setBounds(325,175,200,50);
        callManagerLabel.setVisible(false);
        callManagerLabelPanel.setOpaque(false);

        JButton callManagerButton = new JButton("Call Manager");
        callManagerButton.setPreferredSize(new Dimension(150, 40));

        JPanel callManagerButtonPanel = new JPanel();
        callManagerButtonPanel.add(callManagerButton);
        //callManagerButtonPanel.setBackground(Color.WHITE);
        callManagerButtonPanel.setBounds(325,125,200,50);
        callManagerButtonPanel.setOpaque(false);

        callManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Manager called.");
                callManagerLabel.setVisible(true);
            }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.add(logoutButton);
        //logoutButtonPanel.setBackground(Color.WHITE);
        logoutButtonPanel.setBounds(325,250,200,50);
        logoutButtonPanel.setOpaque(false);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { cashierMainWindow.dispose(); }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        cashierMainWindow.add(cashierTitlePanel);
        cashierMainWindow.add(profileButtonPanel);
        cashierMainWindow.add(checkoutButtonPanel);
        cashierMainWindow.add(callManagerButtonPanel);
        cashierMainWindow.add(logoutButtonPanel);
        cashierMainWindow.add(callManagerLabelPanel);
        cashierMainWindow.add(everything);

        cashierMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cashierMainWindow.setVisible(true);
    }

    public void managerMenu()
    {
        JFrame managerMainWindow = new JFrame("Grocery Store Manager");
        managerMainWindow.setSize(600,500);
        managerMainWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Manager Main Menu");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel managerTitlePanel = new JPanel();
        managerTitlePanel.add(menuTitle);
        //managerTitlePanel.setBackground(Color.BLUE);
        managerTitlePanel.setBounds(150,5,300,50);
        managerTitlePanel.setOpaque(false);

        JButton profileButton = new JButton("Profile");
        profileButton.setPreferredSize(new Dimension(150, 40));

        JPanel profileButtonPanel = new JPanel();
        profileButtonPanel.add(profileButton);
        //profileButtonPanel.setBackground(Color.WHITE);
        profileButtonPanel.setBounds(75,125,200,50);
        profileButtonPanel.setOpaque(false);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { profileMenu(); }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel checkoutButtonPanel = new JPanel();
        checkoutButtonPanel.add(checkoutButton);
        //checkoutButtonPanel.setBackground(Color.WHITE);
        checkoutButtonPanel.setBounds(75,250,200,50);
        checkoutButtonPanel.setOpaque(false);

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    checkout();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton manageItemButton = new JButton("Manage Items");
        manageItemButton.setPreferredSize(new Dimension(150, 40));

        JPanel manageItemButtonPanel = new JPanel();
        manageItemButtonPanel.add(manageItemButton);
        //callManagerButtonPanel.setBackground(Color.WHITE);
        manageItemButtonPanel.setBounds(325,125,200,50);
        manageItemButtonPanel.setOpaque(false);

        manageItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { manageItems(); }
        });

        JButton manageUserButton = new JButton("Manage Users");
        manageUserButton.setPreferredSize(new Dimension(150, 40));

        JPanel manageUserButtonPanel = new JPanel();
        manageUserButtonPanel.add(manageUserButton);
        //manageUserButtonPanel.setBackground(Color.WHITE);
        manageUserButtonPanel.setBounds(325,250,200,50);
        manageUserButtonPanel.setOpaque(false);

        manageUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { manageUsers(); }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.add(logoutButton);
        //logoutButtonPanel.setBackground(Color.WHITE);
        logoutButtonPanel.setBounds(200,350,200,50);
        logoutButtonPanel.setOpaque(false);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { managerMainWindow.dispose(); }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        managerMainWindow.add(managerTitlePanel);
        managerMainWindow.add(profileButtonPanel);
        managerMainWindow.add(checkoutButtonPanel);
        managerMainWindow.add(manageItemButtonPanel);
        managerMainWindow.add(logoutButtonPanel);
        managerMainWindow.add(manageUserButtonPanel);
        managerMainWindow.add(everything);

        //managerMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerMainWindow.setVisible(true);
    }

    public void producerMenu()
    {
        JFrame producerMainWindow = new JFrame("Grocery Store Manager");
        producerMainWindow.setSize(600,500);
        producerMainWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Producer Main Menu");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel producerTitlePanel = new JPanel();
        producerTitlePanel.add(menuTitle);
        //producerTitlePanel.setBackground(Color.BLUE);
        producerTitlePanel.setBounds(150,5,300,50);
        producerTitlePanel.setOpaque(false);

        JButton profileButton = new JButton("Profile");
        profileButton.setPreferredSize(new Dimension(150, 40));

        JPanel profileButtonPanel = new JPanel();
        profileButtonPanel.add(profileButton);
        //profileButtonPanel.setBackground(Color.WHITE);
        profileButtonPanel.setBounds(75,125,200,50);
        profileButtonPanel.setOpaque(false);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { profileMenu(); }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel checkoutButtonPanel = new JPanel();
        checkoutButtonPanel.add(checkoutButton);
        //checkoutButtonPanel.setBackground(Color.WHITE);
        checkoutButtonPanel.setBounds(75,250,200,50);
        checkoutButtonPanel.setOpaque(false);

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    checkout();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JLabel callManagerLabel = new JLabel("Manager Called.");
        JPanel callManagerLabelPanel = new JPanel();
        callManagerLabelPanel.add(callManagerLabel);
        //callManagerLabelPanel.setBackground(Color.BLUE);
        callManagerLabelPanel.setBounds(325,175,200,50);
        callManagerLabel.setVisible(false);
        callManagerLabelPanel.setOpaque(false);

        JButton callManagerButton = new JButton("Call Manager");
        callManagerButton.setPreferredSize(new Dimension(150, 40));

        JPanel callManagerButtonPanel = new JPanel();
        callManagerButtonPanel.add(callManagerButton);
        //callManagerButtonPanel.setBackground(Color.WHITE);
        callManagerButtonPanel.setBounds(325,125,200,50);
        callManagerButtonPanel.setOpaque(false);

        callManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Manager Called.");
                callManagerLabel.setVisible(true);
            }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(150, 40));

        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.add(logoutButton);
        //logoutButtonPanel.setBackground(Color.WHITE);
        logoutButtonPanel.setBounds(325,250,200,50);
        logoutButtonPanel.setOpaque(false);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { producerMainWindow.dispose(); }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        producerMainWindow.add(producerTitlePanel);
        producerMainWindow.add(profileButtonPanel);
        producerMainWindow.add(checkoutButtonPanel);
        producerMainWindow.add(callManagerButtonPanel);
        producerMainWindow.add(logoutButtonPanel);
        producerMainWindow.add(callManagerLabelPanel);
        producerMainWindow.add(everything);

        //producerMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        producerMainWindow.setVisible(true);
    }

    public void profileMenu()
    {
        JFrame profileMainWindow = new JFrame("Grocery Store Manager");
        profileMainWindow.setSize(600,500);
        profileMainWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Profile Main Menu");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel profileTitlePanel = new JPanel();
        profileTitlePanel.add(menuTitle);
        //profileTitlePanel.setBackground(Color.BLUE);
        profileTitlePanel.setBounds(150,5,300,50);
        profileTitlePanel.setOpaque(false);

        JButton pictureButton = new JButton("Change Picture");
        pictureButton.setPreferredSize(new Dimension(150, 40));

        JPanel pictureButtonPanel = new JPanel();
        pictureButtonPanel.add(pictureButton);
        //pictureButtonPanel.setBackground(Color.WHITE);
        pictureButtonPanel.setBounds(75,125,200,50);
        pictureButtonPanel.setOpaque(false);

        pictureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { changePicture(); }
        });

        JButton usernameButton = new JButton("Change Username");
        usernameButton.setPreferredSize(new Dimension(150, 40));

        JPanel usernameButtonPanel = new JPanel();
        usernameButtonPanel.add(usernameButton);
        //usernameButtonPanel.setBackground(Color.WHITE);
        usernameButtonPanel.setBounds(75,250,200,50);
        usernameButtonPanel.setOpaque(false);

        usernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { changeUsername(); }
        });

        JButton passwordButton = new JButton("Change Password");
        passwordButton.setPreferredSize(new Dimension(150, 40));

        JPanel passwordButtonPanel = new JPanel();
        passwordButtonPanel.add(passwordButton);
        //passwordButtonPanel.setBackground(Color.WHITE);
        passwordButtonPanel.setBounds(325,125,200,50);
        passwordButtonPanel.setOpaque(false);

        passwordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { changePassword(); }
        });

        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setPreferredSize(new Dimension(150, 40));

        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.add(deleteButton);
        //deleteButtonPanel.setBackground(Color.WHITE);
        deleteButtonPanel.setBounds(325,250,200,50);
        deleteButtonPanel.setOpaque(false);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { deleteUser(); }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        profileMainWindow.add(profileTitlePanel);
        profileMainWindow.add(usernameButtonPanel);
        profileMainWindow.add(passwordButtonPanel);
        profileMainWindow.add(deleteButtonPanel);
        profileMainWindow.add(pictureButtonPanel);
        profileMainWindow.add(everything);

        profileMainWindow.setVisible(true);
    }

    public void changePicture()
    {
        JFrame window = new JFrame("Grocery Store Manager");
        window.setSize(600,500);
        window.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Change Profile Picture");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel titlePanel = new JPanel();
        titlePanel.add(menuTitle);
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setBounds(150,5,300,50);
        titlePanel.setOpaque(false);

        JLabel label = new JLabel("Enter new picture file name.");
        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        //labelPanel.setBackground(Color.BLUE);
        labelPanel.setBounds(150,100,300,50);
        labelPanel.setOpaque(false);

        JTextField newValueField = new JTextField(10);
        JPanel newValueFieldPanel = new JPanel();
        newValueFieldPanel.add(newValueField);
        //newValueFieldPanel.setBackground(Color.WHITE);
        newValueFieldPanel.setBounds(200,125,200,50);
        newValueFieldPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(150, 40));
        JPanel confirmButtonPanel = new JPanel();
        confirmButtonPanel.add(confirmButton);
        //confirmButtonPanel.setBackground(Color.WHITE);
        confirmButtonPanel.setBounds(200,200,200,50);
        confirmButtonPanel.setOpaque(false);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                try {
                    thing.changeUser(username, "Picture", newValueField.getText());
                    System.out.println("Changed Picture.");
                    window.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        window.add(titlePanel);
        window.add(labelPanel);
        window.add(confirmButtonPanel);
        window.add(newValueFieldPanel);
        window.add(everything);

        window.setVisible(true);
;    }

    public void changeUsername()
    {
        JFrame window = new JFrame("Grocery Store Manager");
        window.setSize(600,500);
        window.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Change Username");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel titlePanel = new JPanel();
        titlePanel.add(menuTitle);
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setBounds(150,5,300,50);
        titlePanel.setOpaque(false);

        JLabel label = new JLabel("Enter new name.");
        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        //labelPanel.setBackground(Color.BLUE);
        labelPanel.setBounds(150,100,300,50);
        labelPanel.setOpaque(false);

        JTextField newValueField = new JTextField(10);
        JPanel newValueFieldPanel = new JPanel();
        newValueFieldPanel.add(newValueField);
        //newValueFieldPanel.setBackground(Color.WHITE);
        newValueFieldPanel.setBounds(200,125,200,50);
        newValueFieldPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(150, 40));
        JPanel confirmButtonPanel = new JPanel();
        confirmButtonPanel.add(confirmButton);
        //confirmButtonPanel.setBackground(Color.WHITE);
        confirmButtonPanel.setBounds(200,200,200,50);
        confirmButtonPanel.setOpaque(false);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                try {
                    thing.changeUser(username, "Name", newValueField.getText());
                    System.out.println("Changed Username.");
                    username = newValueField.getText();
                    window.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        window.add(titlePanel);
        window.add(labelPanel);
        window.add(confirmButtonPanel);
        window.add(newValueFieldPanel);
        window.add(everything);

        window.setVisible(true);
    }

    public void changePassword()
    {
        JFrame window = new JFrame("Grocery Store Manager");
        window.setSize(600,500);
        window.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Change Password");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel titlePanel = new JPanel();
        titlePanel.add(menuTitle);
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setBounds(150,5,300,50);
        titlePanel.setOpaque(false);

        JLabel label = new JLabel("Enter new password.");
        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        //labelPanel.setBackground(Color.BLUE);
        labelPanel.setBounds(150,100,300,50);
        labelPanel.setOpaque(false);

        JTextField newValueField = new JTextField(10);
        JPanel newValueFieldPanel = new JPanel();
        newValueFieldPanel.add(newValueField);
        //newValueFieldPanel.setBackground(Color.WHITE);
        newValueFieldPanel.setBounds(200,125,200,50);
        newValueFieldPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(150, 40));
        JPanel confirmButtonPanel = new JPanel();
        confirmButtonPanel.add(confirmButton);
        //confirmButtonPanel.setBackground(Color.WHITE);
        confirmButtonPanel.setBounds(200,200,200,50);
        confirmButtonPanel.setOpaque(false);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                try {
                    thing.changeUser(username, "Password", newValueField.getText());
                    System.out.println("Changed Password.");
                    window.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        window.add(titlePanel);
        window.add(labelPanel);
        window.add(confirmButtonPanel);
        window.add(newValueFieldPanel);
        window.add(everything);

        window.setVisible(true);
    }

    public void deleteUser()
    {
        JFrame window = new JFrame("Grocery Store Manager");
        window.setSize(600,500);
        window.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel menuTitle = new JLabel("Delete User");
        menuTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel titlePanel = new JPanel();
        titlePanel.add(menuTitle);
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setBounds(150,5,300,50);
        titlePanel.setOpaque(false);

        JLabel label = new JLabel("If you're sure you want to delete your account, enter \"CONFIRM\".");
        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        //labelPanel.setBackground(Color.BLUE);
        labelPanel.setBounds(50,100,500,50);
        labelPanel.setOpaque(false);

        JTextField newValueField = new JTextField(10);
        JPanel newValueFieldPanel = new JPanel();
        newValueFieldPanel.add(newValueField);
        //newValueFieldPanel.setBackground(Color.WHITE);
        newValueFieldPanel.setBounds(200,125,200,50);
        newValueFieldPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(150, 40));
        JPanel confirmButtonPanel = new JPanel();
        confirmButtonPanel.add(confirmButton);
        //confirmButtonPanel.setBackground(Color.WHITE);
        confirmButtonPanel.setBounds(200,200,200,50);
        confirmButtonPanel.setOpaque(false);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                if(newValueField.getText().equals("CONFIRM")) {
                    try {
                        thing.deleteUser(username);
                        System.out.println("Deleted User.");
                        window.dispose();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JPanel everything = new JPanel();
        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        window.add(titlePanel);
        window.add(labelPanel);
        window.add(confirmButtonPanel);
        window.add(newValueFieldPanel);
        window.add(everything);

        window.setVisible(true);
    }

    private String labelList, printList;
    private int x;
    private double total;
    private JLabel totalLabel, itemList;

    public void checkout() throws SQLException
    {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String query = "SELECT * FROM inventory";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        labelList = "";
        printList = "";
        x = 0;
        total = 0;

        System.out.println("Checkout");
        JFrame checkoutWindow = new JFrame("Checkout");
        checkoutWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JTextField itemField = new JTextField(20);

        itemField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int temp;

                modify thing = new modify();

                String text = itemField.getText();
                try {
                    temp = thing.changeItem(text, "Quantity", "-1");
                    if(temp == 1)
                    {
                        printList += text + "\n";
                        labelList += text + "<br>";
                        x++;
                        total += thing.getPrice(text);
                    }
                    System.out.println("Item: " + text + "\nPrice: " + thing.getPrice(text) + "\nTotal: " + total);
                    totalLabel.setText(String.format("$%.2f", total));
                    itemList.setText("<html>Items:<br>" + labelList + "<html>");
                    System.out.println(printList);
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });

        itemList = new JLabel("Items:");
        totalLabel = new JLabel("$0.00");

        JRadioButton receiptButton = new JRadioButton("Print Receipt?");

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(75, 40));

        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                checkoutWindow.dispose();
                try {
                    if(receiptButton.isSelected())
                    {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                        Date date = Calendar.getInstance().getTime();
                        String day = dateFormat.format(date);

                        String receiptText = printList + "\nTotal:\n" + total;

                        File dir = new File("Receipts");
                        dir.mkdirs();

                        String filename = "/Users/Cameron/Library/Mobile Documents/com~apple~CloudDocs/Cameron/" +
                                "Semester 7 Fall 2017/Software Modeling and Design/Course Project/Iteration 2/" +
                                "Grocery Store Manager Iteration 2/Receipts/" + day + ".txt";

                        FileWriter fstream;

                        try {
                            fstream = new FileWriter(filename);
                            BufferedWriter out = new BufferedWriter(fstream);
                            out.write(receiptText);
                            out.newLine();
                            out.flush();
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    pay();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JLabel totalTitle = new JLabel("Total", SwingConstants.CENTER);
        JPanel titlePanel = new JPanel();
        JPanel totalPanel = new JPanel();
        JPanel itemPanel = new JPanel();
        JPanel itemTotalPanel = new JPanel();
        JPanel checkoutPanel = new JPanel();
        JPanel receiptPanel = new JPanel();
        JPanel everything = new JPanel();

        titlePanel.add(totalTitle);
        //titlePanel.setBackground(Color.RED);
        titlePanel.setBounds(250,5,100,100);
        totalTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        titlePanel.setOpaque(false);

        totalPanel.add(totalLabel);
        //totalPanel.setBackground(Color.GREEN);
        totalPanel.setBounds(425,75,75,50);
        totalLabel.setFont(new Font("Calibri", Font.BOLD,20));
        totalPanel.setOpaque(false);

        itemPanel.add(itemList);
        //itemPanel.setBackground(Color.GRAY);
        itemPanel.setBounds(100,135,300,250);
        totalLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        itemPanel.setOpaque(false);

        itemTotalPanel.add(itemField);
        //itemTotalPanel.setBackground(Color.WHITE);
        itemTotalPanel.setBounds(100,75,300,50);
        itemTotalPanel.setOpaque(false);

        checkoutPanel.add(payButton);
        //checkoutPanel.setBackground(Color.BLUE);
        checkoutPanel.setBounds(425,200,85,50);
        checkoutPanel.setOpaque(false);

        receiptPanel.add(receiptButton);
        //receiptPanel.setBackground(Color.RED);
        receiptPanel.setBounds(200,425,200,75);
        receiptPanel.setOpaque(false);

        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        checkoutWindow.getContentPane().add(titlePanel);
        checkoutWindow.getContentPane().add(totalPanel);
        checkoutWindow.getContentPane().add(itemPanel);
        checkoutWindow.getContentPane().add(itemTotalPanel);
        checkoutWindow.getContentPane().add(checkoutPanel);
        checkoutWindow.getContentPane().add(receiptPanel);
        checkoutWindow.getContentPane().add(everything);

        checkoutWindow.setSize(600, 500);
        checkoutWindow.setVisible(true);
    }

    public void pay() throws SQLException
    {
        JFrame payMenu = new JFrame();
        payMenu.getContentPane().setBackground(new Color(160, 250, 255));

        JPanel titlePanel = new JPanel();
        JPanel totalPanel = new JPanel();
        JPanel radioButtonsPanel = new JPanel();
        JPanel payButtonPanel = new JPanel();
        JLabel payTitle = new JLabel("Payment");
        JLabel totalLabel = new JLabel(String.format("$%.2f", total));
        JPanel everything = new JPanel();
        JButton payButton = new JButton("Finish and Pay");

        JRadioButton cash = new JRadioButton("Cash");
        JRadioButton debit = new JRadioButton("Debit Card");
        JRadioButton credit = new JRadioButton("Credit Card");
        JRadioButton check = new JRadioButton("Check");
        JRadioButton EBT = new JRadioButton("EBT");
        ButtonGroup bG = new ButtonGroup();
        bG.add(cash);
        bG.add(debit);
        bG.add(credit);
        bG.add(check);
        bG.add(EBT);
        radioButtonsPanel.add(cash);
        radioButtonsPanel.add(debit);
        radioButtonsPanel.add(credit);
        radioButtonsPanel.add(check);
        radioButtonsPanel.add(EBT);
        cash.setSelected(true);

        payMenu.getContentPane().add(titlePanel);
        payMenu.getContentPane().add(totalPanel);
        payMenu.getContentPane().add(radioButtonsPanel);
        payMenu.getContentPane().add(payButtonPanel);
        payMenu.getContentPane().add(everything);

        titlePanel.add(payTitle);
        totalPanel.add(totalLabel);
        payButtonPanel.add(payButton);

        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = Calendar.getInstance().getTime();
                String day = dateFormat.format(date);

                modify thing = new modify();
                order temp = null;

                try {
                    temp = new order(thing.getOrderNum()+1, day, total);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                try {
                    thing.newOrder(temp);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                payMenu.dispose();
            }
        });

        titlePanel.setBackground(Color.WHITE);
        payTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        titlePanel.setBounds(200,0,200,50);
        titlePanel.setOpaque(false);

        totalPanel.setBackground(Color.BLUE);
        totalLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        totalPanel.setBounds(250,125,100,50);
        totalPanel.setOpaque(false);

        radioButtonsPanel.setBackground(Color.GREEN);
        radioButtonsPanel.setBounds(75,190,450,50);
        radioButtonsPanel.setOpaque(false);

        payButtonPanel.setBackground(Color.RED);
        payButtonPanel.setBounds(225,260,150,40);
        payButtonPanel.setOpaque(false);

        everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        payMenu.setSize(600, 500);
        payMenu.setVisible(true);
    }

    private static String cat;
    private static JTextField IDTextField, nameTextField, priceTextField, quantityTextField, producerTextField, newValue, itemName;

    public void manageItems()
    {
        JFrame manageWindow = new JFrame();
        manageWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel manageTitle = new JLabel("Manage Items");
        JPanel titlePanel = new JPanel();
        JPanel radioButtonsPanel = new JPanel();
        JButton changeItemButton = new JButton("Change Item");
        newValue = new JTextField(10);
        itemName = new JTextField(10);
        JButton newItemButton = new JButton("Add New Item");
        JPanel changeItemPanelButton = new JPanel();
        JPanel newValuePanel = new JPanel();
        JLabel descriptionLabel = new JLabel("ID            Name                Price               Quantity           Producer");
        JPanel descriptionPanel = new JPanel();
        JLabel changeDescriptionLabel = new JLabel("Name                     New Value");
        JPanel changeDescriptionPanel = new JPanel();
        JPanel everything = new JPanel();

        JPanel addItemPanel = new JPanel();
        JPanel addItemPanelButton = new JPanel();
        IDTextField = new JTextField(3);
        nameTextField = new JTextField(7);
        priceTextField = new JTextField(7);
        quantityTextField = new JTextField(7);
        producerTextField = new JTextField(7);
        addItemPanel.add(IDTextField);
        addItemPanel.add(nameTextField);
        addItemPanel.add(priceTextField);
        addItemPanel.add(quantityTextField);
        addItemPanel.add(producerTextField);

        changeItemPanelButton.add(changeItemButton);
        addItemPanelButton.add(newItemButton);

        JRadioButton ID = new JRadioButton("ID");
        JRadioButton name = new JRadioButton("Name");
        JRadioButton price = new JRadioButton("Price");
        JRadioButton quantity = new JRadioButton("Quantity");
        JRadioButton producer = new JRadioButton("Producer");
        ButtonGroup bG = new ButtonGroup();
        bG.add(ID);
        bG.add(name);
        bG.add(price);
        bG.add(quantity);
        bG.add(producer);
        radioButtonsPanel.add(ID);
        radioButtonsPanel.add(name);
        radioButtonsPanel.add(price);
        radioButtonsPanel.add(quantity);
        radioButtonsPanel.add(producer);
        ID.setSelected(true);

        cat = "ID";

        ID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { cat = "ID"; }
        });
        name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { cat = "Name"; }
        });
        price.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { cat = "Price"; }
        });
        quantity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { cat = "Quantity"; }
        });
        producer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { cat = "Producer"; }
        });

        changeItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                try {
                    thing.changeItem(newValue.getText(), cat, itemName.getText());
                    System.out.println("Changed item.");
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });

        newItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();

                product temp = new product(Integer.valueOf(IDTextField.getText()), nameTextField.getText(),
                        Double.valueOf(priceTextField.getText()), Integer.valueOf(quantityTextField.getText()), producerTextField.getText());
                try {
                    thing.newItem(temp);
                    System.out.println("Added new item.");
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });

        titlePanel.add(manageTitle);
        //titlePanel.setBackground(Color.WHITE);
        manageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        titlePanel.setBounds(200,0,200,50);
        titlePanel.setOpaque(false);

        //radioButtonsPanel.setBackground(Color.BLUE);
        radioButtonsPanel.setBounds(100,75,400,40);
        radioButtonsPanel.setOpaque(false);

        //addItemPanel.setBackground(Color.GREEN);
        addItemPanel.setBounds(75,225,450,40);
        addItemPanel.setOpaque(false);

        //addItemPanelButton.setBackground(Color.RED);
        addItemPanelButton.setBounds(225,260,150,40);
        addItemPanelButton.setOpaque(false);

        //changeItemPanelButton.setBackground(Color.GRAY);
        changeItemPanelButton.setBounds(375,130,150,30);
        changeItemPanelButton.setOpaque(false);

        newValuePanel.add(newValue);
        newValuePanel.add(itemName);
        //newValuePanel.setBackground(Color.ORANGE);
        newValuePanel.setBounds(75,130,275,30);
        newValuePanel.setOpaque(false);

        descriptionPanel.add(descriptionLabel);
        //descriptionPanel.setBackground(Color.PINK);
        descriptionPanel.setBounds(75,210,450,20);
        descriptionPanel.setOpaque(false);

        changeDescriptionPanel.add(changeDescriptionLabel);
        //changeDescriptionPanel.setBackground(Color.MAGENTA);
        changeDescriptionPanel.setBounds(95,110,250,20);
        changeDescriptionPanel.setOpaque(false);

        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        manageWindow.getContentPane().add(titlePanel);
        manageWindow.getContentPane().add(radioButtonsPanel);
        manageWindow.getContentPane().add(changeItemPanelButton);
        manageWindow.getContentPane().add(addItemPanel);
        manageWindow.getContentPane().add(addItemPanelButton);
        manageWindow.getContentPane().add(newValuePanel);
        manageWindow.getContentPane().add(descriptionPanel);
        manageWindow.getContentPane().add(changeDescriptionPanel);
        manageWindow.getContentPane().add(everything);

        manageWindow.setSize(600, 500);
        manageWindow.setVisible(true);
    }

    private static String mcat;
    private static JTextField mIDTextField, usernameTextField, passwordTextField, pictureTextField, mnewValue, muserName;

    public void manageUsers()
    {
        JFrame manageUserWindow = new JFrame();
        manageUserWindow.getContentPane().setBackground(new Color(160, 250, 255));

        JLabel manageTitle = new JLabel("Manage Users");
        JPanel titlePanel = new JPanel();
        JPanel radioButtonsPanel = new JPanel();
        JButton changeUserButton = new JButton("Change User");
        mnewValue = new JTextField(10);
        muserName = new JTextField(10);
        JButton newUserButton = new JButton("Add New User");
        JPanel changeUserPanelButton = new JPanel();
        JPanel newValuePanel = new JPanel();
        JLabel descriptionLabel = new JLabel("ID        Username           Status             Picture");
        JPanel descriptionPanel = new JPanel();
        JLabel changeDescriptionLabel = new JLabel("Name                     New Value");
        JPanel changeDescriptionPanel = new JPanel();
        JPanel everything = new JPanel();

        JPanel addUserPanel = new JPanel();
        JPanel addUserPanelButton = new JPanel();
        mIDTextField = new JTextField(3);
        usernameTextField = new JTextField(7);
        passwordTextField = new JTextField(7);
        pictureTextField = new JTextField(7);
        addUserPanel.add(mIDTextField);
        addUserPanel.add(usernameTextField);
        addUserPanel.add(passwordTextField);
        addUserPanel.add(pictureTextField);

        changeUserPanelButton.add(changeUserButton);
        addUserPanelButton.add(newUserButton);

        JRadioButton ID = new JRadioButton("ID");
        JRadioButton name = new JRadioButton("Username");
        JRadioButton status = new JRadioButton("Status");
        JRadioButton password = new JRadioButton("Password");
        JRadioButton picture = new JRadioButton("Picture");
        ButtonGroup bG = new ButtonGroup();
        bG.add(ID);
        bG.add(name);
        bG.add(status);
        bG.add(password);
        bG.add(picture);
        radioButtonsPanel.add(ID);
        radioButtonsPanel.add(name);
        radioButtonsPanel.add(status);
        radioButtonsPanel.add(password);
        radioButtonsPanel.add(picture);
        ID.setSelected(true);

        mcat = "ID";

        ID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { mcat = "ID"; }
        });
        name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { mcat = "Name"; }
        });
        status.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { mcat = "Status"; }
        });
        password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { mcat = "Password"; }
        });
        picture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            { mcat = "Picture"; }
        });

        changeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();
                try {
                    thing.changeUser(mnewValue.getText(), mcat, muserName.getText());
                    System.out.println("Changed user.");
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });

        newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                modify thing = new modify();

                user temp = new user(Integer.valueOf(mIDTextField.getText()), usernameTextField.getText(),
                        passwordTextField.getText(), pictureTextField.getText());
                try {
                    thing.newUser(temp);
                    System.out.println("Added new user.");
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });

        titlePanel.add(manageTitle);
        //titlePanel.setBackground(Color.WHITE);
        manageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        titlePanel.setBounds(200,0,200,50);
        titlePanel.setOpaque(false);

        //radioButtonsPanel.setBackground(Color.BLUE);
        radioButtonsPanel.setBounds(50,75,500,40);
        radioButtonsPanel.setOpaque(false);

        //addItemPanel.setBackground(Color.GREEN);
        addUserPanel.setBounds(75,225,450,40);
        addUserPanel.setOpaque(false);

        //addUserPanelButton.setBackground(Color.RED);
        addUserPanelButton.setBounds(225,260,150,40);
        addUserPanelButton.setOpaque(false);

        //changeItemPanelButton.setBackground(Color.GRAY);
        changeUserPanelButton.setBounds(375,130,150,30);
        changeUserPanelButton.setOpaque(false);

        newValuePanel.add(mnewValue);
        newValuePanel.add(muserName);
        //newValuePanel.setBackground(Color.ORANGE);
        newValuePanel.setBounds(75,130,275,30);
        newValuePanel.setOpaque(false);

        descriptionPanel.add(descriptionLabel);
        //descriptionPanel.setBackground(Color.PINK);
        descriptionPanel.setBounds(113,210,350,20);
        descriptionPanel.setOpaque(false);

        changeDescriptionPanel.add(changeDescriptionLabel);
        //changeDescriptionPanel.setBackground(Color.MAGENTA);
        changeDescriptionPanel.setBounds(95,110,250,20);
        changeDescriptionPanel.setOpaque(false);

        //everything.setBackground(Color.YELLOW);
        everything.setOpaque(false);

        manageUserWindow.getContentPane().add(titlePanel);
        manageUserWindow.getContentPane().add(radioButtonsPanel);
        manageUserWindow.getContentPane().add(changeUserPanelButton);
        manageUserWindow.getContentPane().add(addUserPanel);
        manageUserWindow.getContentPane().add(addUserPanelButton);
        manageUserWindow.getContentPane().add(newValuePanel);
        manageUserWindow.getContentPane().add(descriptionPanel);
        manageUserWindow.getContentPane().add(changeDescriptionPanel);
        manageUserWindow.getContentPane().add(everything);

        manageUserWindow.setSize(600, 500);
        manageUserWindow.setVisible(true);
    }
}
