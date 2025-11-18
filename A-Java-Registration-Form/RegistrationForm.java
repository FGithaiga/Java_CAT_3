import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {

    private static final String DB_URL = "jdbc:sqlite:registration.db";

    JTextField txtName, txtMobile;
    JRadioButton male, female;
    JComboBox<String> dayBox, monthBox, yearBox;
    JTextArea txtAddress;
    JCheckBox terms;
    JTable table;
    DefaultTableModel tableModel;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create database + table
        createDatabase();

        JLabel lblTitle = new JLabel("Registration Form");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(120, 10, 300, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 70, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 70, 180, 25);
        add(txtName);

        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(20, 110, 80, 25);
        add(lblMobile);

        txtMobile = new JTextField();
        txtMobile.setBounds(120, 110, 180, 25);
        add(txtMobile);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(20, 150, 80, 25);
        add(lblGender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(120, 150, 80, 25);
        female.setBounds(200, 150, 80, 25);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        add(male);
        add(female);

        JLabel lblDob = new JLabel("DOB:");
        lblDob.setBounds(20, 190, 80, 25);
        add(lblDob);

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) days[i] = String.valueOf(i + 1);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        String[] years = new String[60];
        for (int i = 0; i < 60; i++) years[i] = String.valueOf(1980 + i);

        dayBox = new JComboBox<>(days);
        monthBox = new JComboBox<>(months);
        yearBox = new JComboBox<>(years);

        dayBox.setBounds(120, 190, 60, 25);
        monthBox.setBounds(185, 190, 60, 25);
        yearBox.setBounds(250, 190, 70, 25);

        add(dayBox);
        add(monthBox);
        add(yearBox);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(20, 230, 80, 25);
        add(lblAddress);

        txtAddress = new JTextArea();
        txtAddress.setBounds(120, 230, 200, 60);
        add(txtAddress);

        terms = new JCheckBox("Accept Terms & Conditions");
        terms.setBounds(20, 300, 250, 25);
        add(terms);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(20, 340, 120, 30);
        add(btnSubmit);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(160, 340, 120, 30);
        add(btnReset);

        // TABLE on the right side
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Name", "Mobile", "Gender", "DOB", "Address"});

        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(350, 20, 520, 360);
        add(scroll);

        loadTableData();

        // ACTIONS
        btnSubmit.addActionListener(e -> saveData());
        btnReset.addActionListener(e -> {
            txtName.setText("");
            txtMobile.setText("");
            txtAddress.setText("");
            bg.clearSelection();
            terms.setSelected(false);
        });

        setVisible(true);
    }

    // CREATE DATABASE + TABLE
    private void createDatabase() {
    try {
        Class.forName("org.sqlite.JDBC");  // ← ADD THIS

        Connection conn = DriverManager.getConnection(DB_URL);
        String sql = "CREATE TABLE IF NOT EXISTS users("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, mobile TEXT, gender TEXT, dob TEXT, address TEXT)";
        conn.createStatement().execute(sql);
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // SAVE DATA TO DATABASE
   private void saveData() {
    if (!terms.isSelected()) {
        JOptionPane.showMessageDialog(this, "You must accept terms.");
        return;
    }

    try {
        Class.forName("org.sqlite.JDBC");  // ← ADD THIS

        Connection conn = DriverManager.getConnection(DB_URL);
        String sql = "INSERT INTO users(name, mobile, gender, dob, address) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, txtName.getText());
        stmt.setString(2, txtMobile.getText());
        stmt.setString(3, male.isSelected() ? "Male" : "Female");
        stmt.setString(4, dayBox.getSelectedItem() + " " + monthBox.getSelectedItem() + " " + yearBox.getSelectedItem());
        stmt.setString(5, txtAddress.getText());

        stmt.executeUpdate();
        conn.close();

        JOptionPane.showMessageDialog(this, "Saved successfully!");
        loadTableData();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // LOAD DATA INTO TABLE
    private void loadTableData() {
        tableModel.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("gender"),
                        rs.getString("dob"),
                        rs.getString("address")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
