import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeManagerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JButton addButton, statsButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel avgLabel, highLabel, lowLabel;

    private ArrayList<Student> students = new ArrayList<>();

    public StudentGradeManagerGUI() {
        setTitle("Student Grade Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        nameField = new JTextField();
        gradeField = new JTextField();
        addButton = new JButton("Add Student");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("")); // empty

        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display students
        tableModel = new DefaultTableModel(new Object[]{"Name", "Grade"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel for stats
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        avgLabel = new JLabel("Average: -");
        highLabel = new JLabel("Highest: -");
        lowLabel = new JLabel("Lowest: -");
        statsButton = new JButton("Calculate Stats");

        statsPanel.add(avgLabel);
        statsPanel.add(highLabel);
        statsPanel.add(lowLabel);
        statsPanel.add(statsButton);

        add(statsPanel, BorderLayout.SOUTH);

        // Event: Add Student
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (name.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
                return;
            }

            try {
                double grade = Double.parseDouble(gradeText);
                students.add(new Student(name, grade));
                tableModel.addRow(new Object[]{name, grade});
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid grade.");
            }
        });

        // Event: Calculate Stats
        statsButton.addActionListener(e -> calculateStats());
    }

    private void calculateStats() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available.");
            return;
        }

        double sum = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        for (Student s : students) {
            sum += s.grade;
            if (s.grade > highest) highest = s.grade;
            if (s.grade < lowest) lowest = s.grade;
        }

        double avg = sum / students.size();

        avgLabel.setText("Average: " + String.format("%.2f", avg));
        highLabel.setText("Highest: " + highest);
        lowLabel.setText("Lowest: " + lowest);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeManagerGUI().setVisible(true));
    }
}
