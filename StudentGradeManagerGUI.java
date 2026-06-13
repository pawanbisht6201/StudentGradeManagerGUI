import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.HashMap;

public class GradeTrackerGUI extends JFrame {


JTextField nameField;
JTextField rollField;
JTextField englishField;
JTextField mathsField;
JTextField physicsField;
JTextField chemistryField;
JTextField computerField;
JTextField hindiField;

JTextArea resultArea;

JProgressBar progressBar;

HashMap<String, Student> studentMap = new HashMap<>();

boolean darkMode = false;

public GradeTrackerGUI() {

    setTitle("Student Result Management System - Dashboard");
    setSize(900, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(
        new Color(245,245,245));
JLabel title = new JLabel(
        "STUDENT RESULT MANAGEMENT SYSTEM",
        SwingConstants.CENTER);

title.setFont(
        new Font("Times New Roman",
                Font.BOLD,
                28));

title.setForeground(
        new Color(25,118,210));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(8, 2, 10, 10));

    panel.add(new JLabel("Student Name"));
    nameField = new JTextField();
    panel.add(nameField);

    panel.add(new JLabel("Roll Number"));
    rollField = new JTextField();
    panel.add(rollField);

    panel.add(new JLabel("English"));
    englishField = new JTextField();
    panel.add(englishField);

    panel.add(new JLabel("Mathematics"));
    mathsField = new JTextField();
    panel.add(mathsField);

    panel.add(new JLabel("Physics"));
    physicsField = new JTextField();
    panel.add(physicsField);

    panel.add(new JLabel("Chemistry"));
    chemistryField = new JTextField();
    panel.add(chemistryField);

    panel.add(new JLabel("Computer"));
    computerField = new JTextField();
    panel.add(computerField);

    panel.add(new JLabel("Hindi"));
    hindiField = new JTextField();
    panel.add(hindiField);
    Font fieldFont =
        new Font(
                "Segoe UI",
                Font.PLAIN,
                16);

nameField.setFont(fieldFont);
rollField.setFont(fieldFont);
englishField.setFont(fieldFont);
mathsField.setFont(fieldFont);
physicsField.setFont(fieldFont);
chemistryField.setFont(fieldFont);
computerField.setFont(fieldFont);
hindiField.setFont(fieldFont);

    JButton calculateBtn = new JButton("Calculate");
    JButton resetBtn = new JButton("Reset");
    JButton saveBtn = new JButton("Save TXT");
    JButton searchBtn = new JButton("Search");
    JButton darkBtn = new JButton("Dark Mode");

    JPanel topButtonPanel = new JPanel();
    topButtonPanel.add(calculateBtn);
    topButtonPanel.add(resetBtn);

    JPanel sideButtonPanel = new JPanel();
    sideButtonPanel.setLayout(new GridLayout(3, 1, 5, 5));
    sideButtonPanel.add(saveBtn);
    sideButtonPanel.add(searchBtn);
    sideButtonPanel.add(darkBtn);

    resultArea = new JTextArea();
    resultArea.setFont(
        new Font(
                "Consolas",
                Font.PLAIN,
                16));

resultArea.setBorder(
        BorderFactory.createTitledBorder(
                "Student Report"));
    resultArea.setFont(new Font("Arial", Font.PLAIN, 16));

    JScrollPane scrollPane = new JScrollPane(resultArea);

    progressBar = new JProgressBar(0, 100);
    progressBar.setStringPainted(true);

    JPanel northPanel = new JPanel(new BorderLayout());
    northPanel.add(panel, BorderLayout.CENTER);
    northPanel.add(topButtonPanel, BorderLayout.SOUTH);

    JPanel headerPanel =
        new JPanel(new BorderLayout());

headerPanel.add(
        title,
        BorderLayout.NORTH);

headerPanel.add(
        northPanel,
        BorderLayout.CENTER);

add(headerPanel,
        BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(sideButtonPanel, BorderLayout.EAST);
    add(progressBar, BorderLayout.SOUTH);

    calculateBtn.addActionListener(e -> calculateResult());
    resetBtn.addActionListener(e -> resetFields());
    saveBtn.addActionListener(e -> saveReport());
    searchBtn.addActionListener(e -> searchStudent());
    darkBtn.addActionListener(e -> toggleDarkMode());

    setVisible(true);
}

private void calculateResult() {

    try {

        String studentId =
                "STU" + (System.currentTimeMillis() % 10000);

        String name = nameField.getText();
        String rollNo = rollField.getText();

        int english = Integer.parseInt(englishField.getText());
        int maths = Integer.parseInt(mathsField.getText());
        int physics = Integer.parseInt(physicsField.getText());
        int chemistry = Integer.parseInt(chemistryField.getText());
        int computer = Integer.parseInt(computerField.getText());
        int hindi = Integer.parseInt(hindiField.getText());

        if (english < 0 || english > 100 ||
            maths < 0 || maths > 100 ||
            physics < 0 || physics > 100 ||
            chemistry < 0 || chemistry > 100 ||
            computer < 0 || computer > 100 ||
            hindi < 0 || hindi > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "Marks must be between 0 and 100");

            return;
        }

        Student student = new Student(
                studentId,
                rollNo,
                name,
                english,
                maths,
                physics,
                chemistry,
                computer,
                hindi
        );

        studentMap.put(name, student);

        progressBar.setValue(
                (int) student.getPercentage());

        resultArea.setText(
                "========== STUDENT REPORT ==========\n\n" +

                "Student ID : " +
                student.getStudentId() + "\n" +

                "Roll Number : " +
                student.getRollNo() + "\n" +

                "Name : " +
                student.getName() + "\n\n" +

                "English : " + english + "\n" +
                "Mathematics : " + maths + "\n" +
                "Physics : " + physics + "\n" +
                "Chemistry : " + chemistry + "\n" +
                "Computer : " + computer + "\n" +
                "Hindi : " + hindi + "\n\n" +

                "Total Marks : " +
                student.getTotal() + "/600\n" +

                "Percentage : " +
                String.format("%.2f",
                        student.getPercentage()) +
                "%\n" +

                "Grade : " +
                student.getGrade() + "\n" +

                "Status : " +
                student.getStatus() + "\n" +

                "Position : " +
                student.getPosition()
        );

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter valid marks!");
    }
}

private void resetFields() {

    nameField.setText("");
    rollField.setText("");

    englishField.setText("");
    mathsField.setText("");
    physicsField.setText("");
    chemistryField.setText("");
    computerField.setText("");
    hindiField.setText("");

    resultArea.setText("");
    progressBar.setValue(0);
}

private void saveReport() {

    try {

        FileWriter writer =
                new FileWriter("StudentReport.txt");

        writer.write(resultArea.getText());

        writer.close();

        JOptionPane.showMessageDialog(
                this,
                "Report Saved Successfully");

    } catch (Exception e) {

        e.printStackTrace();
    }
}

private void searchStudent() {

    String name =
            JOptionPane.showInputDialog(
                    "Enter Student Name");

    Student student =
            studentMap.get(name);

    if (student != null) {

        resultArea.setText(
                "Student Found\n\n" +

                "Student ID : " +
                student.getStudentId() + "\n" +

                "Roll No : " +
                student.getRollNo() + "\n" +

                "Name : " +
                student.getName() + "\n" +

                "Percentage : " +
                String.format("%.2f",
                        student.getPercentage()) +
                "%\n" +

                "Grade : " +
                student.getGrade() + "\n" +

                "Status : " +
                student.getStatus()
        );

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Student Not Found");
    }
}

private void toggleDarkMode() {

    if (!darkMode) {

        getContentPane().setBackground(Color.DARK_GRAY);

        resultArea.setBackground(Color.BLACK);
        resultArea.setForeground(Color.WHITE);

        darkMode = true;

    } else {

        getContentPane().setBackground(Color.WHITE);

        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(Color.BLACK);

        darkMode = false;
    }
}

public static void main(String[] args) {

    try {

        for (UIManager.LookAndFeelInfo info :
                UIManager.getInstalledLookAndFeels()) {

            if ("Nimbus".equals(info.getName())) {

                UIManager.setLookAndFeel(
                        info.getClassName());

                break;
            }
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    SwingUtilities.invokeLater(
            GradeTrackerGUI::new
    );
}


}
