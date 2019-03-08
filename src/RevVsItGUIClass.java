import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
/*
Thomas Lee

This program compares an iterative and recursive implementation of a function that
produces a sequence of numbers, ie: 0 1 2 5 12 29 ... where each term of the sequence is twice the previous term plus the term before the previous term. f(n) = 2(n-1)+n-2

The efficiency of each implementation type is calculated as follows:

//Efficiency Variable Definition:
//for the recursive version, the efficiency variable is defined as the number of function calls to compute the sequence
//for the iterative version, the efficiency variable is defined as the number of loops to compute the sequence

Upon exiting the program, a csv file will be generated with the corresponding efficiency of each of the methods.

*/


//Program flow:
/*
Main method creates an object of this (RevVsItGUIClass) class
The constructor of this class (RevVsItGUIClass) contains the GUI code and calls the setActionListenerMethod
The setActionListener method calls:
    nTextFieldMethod(); - creates the action listener and error checks the input text within the text field
    computeButtonMethod(); - calculates the fibonacci sequence in an iterative or recursive manner
                           - this method uses an object of the Computation class to call the Computation methods
    writeToFile(); - writes the input,output and efficiency to a csv file that can be opened by Excel
 */

//Efficiency Variable Definition:
//for the recursive version, the efficiency variable is defined as the number of function calls to compute the sequence
//for the iterative version, the efficiency variable is defined as the number of loops to compute the sequence

//THIS CLASS CONTAINS THE MAIN METHOD
public class RevVsItGUIClass extends JFrame {

    //this object will be used to call the methods within the Computation class within the computeButtonMethod
    private Computation compObj = new Computation();

    private JFrame frame = new JFrame();

    private JTextField nTextField = new JTextField();
    private JTextField resultTextField = new JTextField();
    private JTextField efficiencyTextField = new JTextField();

    private JLabel enterNLabel = new JLabel("Enter n ");
    private JLabel resultLabel = new JLabel("Result ");
    private JLabel efficiencyLabel = new JLabel("Efficiency ");

    private JRadioButton iterativeButton = new JRadioButton("Iterative");
    private JRadioButton recursiveButton = new JRadioButton("Recursive");
    private JButton computeButton = new JButton("Compute");

    //these arrayLists will be used to output to the CSV document
    private ArrayList<Integer> inputForRecursiveList = new ArrayList<>();
    private ArrayList<Integer> outputForRecursiveList = new ArrayList<>();
    private ArrayList<Integer> efficiencyForRecursiveList = new ArrayList<>();

    private ArrayList<Integer> inputForIterativeList = new ArrayList<>();
    private ArrayList<Integer> outputForIterativeList = new ArrayList<>();
    private ArrayList<Integer> efficiencyForIterativeList = new ArrayList<>();

    //create gui and call all other methods within this class
    private RevVsItGUIClass() {

        frame.setSize(new Dimension(400, 200));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fibonacci Sequence : Iterative vs Recursive Code");
        frame.setResizable(true);
        frame.setVisible(true);

        JPanel panelMain = new JPanel(); //creating panel with default flowlayout
        frame.getContentPane().add(panelMain); //adding panel to frame

        JPanel panelForm = new JPanel(new GridBagLayout());//creating panel with gridbaglayout
        panelMain.add(panelForm);//adding form panel to main panel
        GridBagConstraints c = new GridBagConstraints();//creating grid bag constraints

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;

        //RIGHT SIDE OF GUI
        panelForm.add(iterativeButton, c);
        c.gridy++;
        panelForm.add(recursiveButton, c);
        c.gridy++;
        nTextField.setPreferredSize(new Dimension(200, 20));
        panelForm.add(nTextField, c);
        c.gridy++;
        panelForm.add(computeButton, c);
        c.gridy++;
        resultTextField.setPreferredSize(new Dimension(200, 20));
        panelForm.add(resultTextField, c);
        c.gridy++;
        efficiencyTextField.setPreferredSize(new Dimension(200, 20));
        panelForm.add(efficiencyTextField, c);
        c.gridy++;

        //LEFT SIDE OF GUI
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(enterNLabel, c);
        c.gridy += 2;
        panelForm.add(resultLabel, c);
        c.gridy++;
        panelForm.add(efficiencyLabel, c);
        c.gridy++;

        //GROUPING RADIO BUTTONS
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(iterativeButton);
        buttonGroup.add(recursiveButton);

        setActionListeners();
    }

    //A METHOD TO INDIVIDUALLY CALL EACH ACTION LISTENER ASSOCIATED METHOD
    private void setActionListeners() {
        recursiveButton.setSelected(true);
        nTextFieldMethod();
        computeButtonMethod();
        writeToFile();
    }

    //ACTION LISTENER METHOD FOR TEXTFIELD
    private void nTextFieldMethod() {
        nTextField.addActionListener((ActionEvent a) -> {
            int inputN = Integer.parseInt(nTextField.getText());


            try {//ensure user is entering a numeric value

                if (inputN < 0) {
                    JOptionPane.showMessageDialog(nTextField, "Please enter a positive value", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }//end try
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(nTextField, "Please enter a numeric value", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
    }

    //This method will call the methods within the Computation Class
    private void computeButtonMethod() {

        computeButton.addActionListener((ActionEvent e) -> {

                    int inputN = Integer.parseInt(nTextField.getText());

                    if (iterativeButton.isSelected()) {

                        int computeIterativeValue = compObj.computeIterative(inputN);
                        resultTextField.setText(Integer.toString(computeIterativeValue));

                        int efficiencyValue = compObj.getEfficency();
                        efficiencyTextField.setText(Integer.toString(efficiencyValue));

                        inputForIterativeList.add(inputN);
                        outputForIterativeList.add(computeIterativeValue);
                        efficiencyForIterativeList.add(efficiencyValue);
                    }
                    else if (recursiveButton.isSelected()) {

                        int recursiveValue = compObj.computeRecursive(inputN);
                        resultTextField.setText(Integer.toString(recursiveValue));
                        int efficiencyValue = compObj.getEfficency();
                        efficiencyTextField.setText(Integer.toString(efficiencyValue));

                        inputForRecursiveList.add(inputN);
                        outputForRecursiveList.add(recursiveValue);
                        efficiencyForRecursiveList.add(efficiencyValue);
                    }
                } //endlocal definition of inner class
        );//end anon inner class
    }

    //creates a CSV file with the input, output and efficiency calculated within the Computation Class
    private void writeToFile() {

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                PrintWriter pw = null;
                try {

                    String userHome = System.getProperty("user.home") + "/Desktop";
                    pw = new PrintWriter(new File(userHome + "/RecursiveVsIterative.csv")); //will create csv file


                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(nTextField, "The output file must be closed to write to it and close the program", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                StringBuilder builder = new StringBuilder();

                builder.append(" Recursive Input " + " , " + inputForRecursiveList.toString() + "\n");
                builder.append(" Recursive Output " + " , " + outputForRecursiveList.toString() + "\n");
                builder.append(" Recursive Efficiency " + " , " + efficiencyForRecursiveList.toString() + "\n");

                builder.append(" Iterative Input " + " , " + inputForIterativeList.toString() + "\n");
                builder.append(" Iterative Output " + " , " + outputForIterativeList.toString() + "\n");
                builder.append(" Iterative Efficiency " + " , " + efficiencyForIterativeList.toString() + "\n");

                pw.write(builder.toString());
                pw.close();
            }
        });
    }


    public static void main(String[] args) {
        RevVsItGUIClass guiObj = new RevVsItGUIClass();
    }
}

//this class contains all of the backend calculations
//data from this class is passed to the RecVsItGUIClass through an Computation object within the computeButtonMethod
class Computation {

    private int efficiency;  //efficiency
    private int computeRecursiveHelperValue;

    public int computeRecursive(int n) {
        computeRecursiveHelperValue = computeRecursiveHelper(n);
        efficiency++;
        return computeRecursiveHelperValue;

    }

    private  int computeRecursiveHelper(int n) {

        switch (n) {
            case 1:
                return 1;
            case 0:
                return 0;
            default:
                break;
        }
        return 2 * computeRecursive(n - 1) + computeRecursive(n - 2);

    }

    public int computeIterative(int n) {
        efficiency = 0;
        int current = 1;
        int last = 0;
        int lastlast;
        switch (n) {
            case 0:
                current = 0;
                break;
            default:
                for (int c = 2; c < n + 1; c++) {
                    efficiency++;
                    System.out.println("this is the efficiency counter for iterative " + efficiency);
                    lastlast = last;
                    last = current;
                    current = lastlast + 2 * last;
                    System.out.println(current);
                }
                break;

        }
        return current;
    }

    public int getEfficency() {
        System.out.println("this is the efficiency counter for the getEfficency method " + efficiency);
        return efficiency;
    }
}//end sequence class
