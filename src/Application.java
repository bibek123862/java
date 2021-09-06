import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.HeadlessException;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.Font;
import java.awt.Color;

import com.opencsv.CSVReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

public class Application extends JFrame {
    private JTable jTable;
    private JTextField txt_search;
    private JButton btn_search;
    private JButton openCSV;
    private boolean hasWinnerChanged;
    private boolean isInWinner;
    private ArrayList<String[]> listWinners;
    private JButton btn_allEligibleCustomer;
    private JButton btn_winners;
    private JButton btn_logout;
    private JFileChooser fileChooser;
    private JToolBar toolbar;
    private JComboBox<String> searchBy;
    MyDataTableModel dataTableModel;

    public Application() throws HeadlessException {
        this.setTitle("Lottery Application"); // Sets title
        this.setLocation(200, 50);
        this.setBounds(100, 100, 900, 600);
        this.setSize(1300, 720); // Sets size

        // this.setResizable(false); // Makes window unresizable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        init();
        this.setVisible(true); // Window gets visible once set to true, else no window appears
    }

    private void init() {
        listWinners = new ArrayList<String[]>();
        hasWinnerChanged = false;
        isInWinner = false;
        Font allBtnFont = new Font("Segoe UI", Font.BOLD, 16);
        openCSV = new JButton("Find your CSV");
        openCSV.setFont(allBtnFont);
        btn_logout = new JButton("Logout");
        btn_logout.setFont(allBtnFont);
        btn_logout.setBounds(1120, 600, 90, 30);
        btn_allEligibleCustomer = new JButton("All");
        btn_allEligibleCustomer.setFont(allBtnFont);
        btn_allEligibleCustomer.setBounds(1020, 180, 130, 30);
        btn_winners = new JButton("Winners");
        btn_winners.setFont(allBtnFont);
        btn_winners.setBounds(1020, 320, 130, 30);
        txt_search = new JTextField();
        txt_search.setFont(allBtnFont);
        txt_search.setBounds(100, 600, 600, 30);
        btn_search = new JButton("Search");
        btn_search.setFont(allBtnFont);
        btn_search.setBounds(820, 600, 100, 30);

        setAction();
        fileChooser = new JFileChooser("./");
        toolbar = new JToolBar();
        toolbar.setBounds(1020, 40, 170, 30);
        toolbar.add(openCSV);

        this.add(toolbar, BorderLayout.NORTH);
        this.add(btn_logout);
        this.add(btn_allEligibleCustomer);
        this.add(btn_winners);
        this.add(txt_search);
        this.add(btn_search);

        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(10, 10, 1000, 570);
        tablePanel.setLayout(new BorderLayout());
        this.add(tablePanel);
        jTable = new JTable();

        jTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 24));
        jTable.setFont(new Font("Segoe UI", Font.BOLD, 20));

        jTable.getTableHeader().setOpaque(false);
        jTable.getTableHeader().setBackground(new Color(132, 136, 203));
        jTable.getTableHeader().setForeground(new Color(255, 255, 255));
        jTable.setRowHeight(40);

        tablePanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
        dataTableModel = new MyDataTableModel();
        jTable.setModel(dataTableModel);

        dataTableModel.fireTableDataChanged();

    }

    public boolean openCSVFile(){
        int i = fileChooser.showOpenDialog(Application.this);
                if (i == fileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    ArrayList<String[]> all = readCSV(file);
                    String[] cols = all.get(0);
                    all.remove(0);
                    MyDataTableModel dataTableModel = new MyDataTableModel(all, cols);
                    jTable.setModel(dataTableModel);

                    jTable.setBounds(200, 500, 900, 200);
                    searchBy = new JComboBox<String>(cols);
                    searchBy.setBounds(930, 600, 100, 30);
                    Application.this.add(searchBy);

                    dataTableModel.fireTableDataChanged();
                    Application.this.validate();
                    return true;
                }
                return false;
    }

    private void setAction() {
        openCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCSVFile();
            }

        });

        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.this.dispose();
                Login login = new Login();
            }
        });

        btn_allEligibleCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = fileChooser.getSelectedFile();
                if (file == null) {
                    JOptionPane.showMessageDialog(Application.this,
                            "Sorry, I don't know which file to process. Choose file first", "Select CSV",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ArrayList<String[]> all = readCSV(file);
                String[] cols = all.get(0);
                all.remove(0);
                MyDataTableModel dataTableModel = new MyDataTableModel(all, cols);
                jTable.setModel(dataTableModel);

                dataTableModel.fireTableDataChanged();

                isInWinner = false;

            }
        });

        btn_winners.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fileChooser.getSelectedFile();

                if (isInWinner == false) {
                    if (listWinners.size() == 4) {
                        String[] cols = listWinners.get(0);
                        listWinners.remove(0);
                        MyDataTableModel dataTableModel = new MyDataTableModel(listWinners, cols);
                        jTable.setModel(dataTableModel);

                        dataTableModel.fireTableDataChanged();
                    }

                }
                if (hasWinnerChanged == true) {
                    int i = JOptionPane.showConfirmDialog(Application.this, "Do you want to change winners?", "Confirm",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (i == 2) {
                        return;
                    }
                }

                if (file == null) {
                    JOptionPane.showMessageDialog(Application.this,
                            "Sorry, I don't know which file to process. Choose file first", "Select CSV",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<String[]> all = readCSV(file);
                String[] cols = all.get(0);
                all.remove(0);
                Collections.shuffle(all);
                ArrayList<String[]> winners = new ArrayList<String[]>();
                for (int index = 0; index < 3; index++) {
                    winners.add(all.get(index));
                }

                MyDataTableModel dataTableModel = new MyDataTableModel(winners, cols);
                jTable.setModel(dataTableModel);

                dataTableModel.fireTableDataChanged();
                hasWinnerChanged = true;
                listWinners.add(cols);
                isInWinner = true;
                for (String[] winner : winners) {
                    listWinners.add(winner);
                }

            }
        });

        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    JOptionPane.showMessageDialog(Application.this,
                            "Sorry, I don't know which file to process. Choose file first", "Select CSV",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String searchText = txt_search.getText();
                String searchByText = searchBy.getItemAt(searchBy.getSelectedIndex());
                ArrayList<String[]> all = readCSV(file);
                String[] cols = all.get(0);
                all.remove(0);
                int searchColumnIndex = 0;
                for (String col : cols) {
                    if (col.equalsIgnoreCase(searchByText)) {
                        break;
                    } else {
                        searchColumnIndex += 1;
                    }
                }

                ArrayList<String[]> customer = new ArrayList<String[]>();
                for (String[] rows : all) {
                    if (searchText.equalsIgnoreCase(rows[searchColumnIndex])) {
                        customer.add(rows);

                    }
                    ;
                }
                MyDataTableModel dataTableModel = new MyDataTableModel(customer, cols);
                jTable.setModel(dataTableModel);

                dataTableModel.fireTableDataChanged();

            }
        });
    }

    private ArrayList<String[]> readCSV(File file) {
        ArrayList<String[]> dataOfEligibleCustomer = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader cSVReader = new CSVReader(fileReader);

            Iterator<String[]> rows = cSVReader.iterator();

            int count = 0;
            int priceColumnIndex = 0;
            while (rows.hasNext()) {
                if (count != 0) {
                    String[] cols = rows.next();
                    int i = Integer.parseInt(cols[priceColumnIndex]);
                    if (i < 10000) {
                        continue;
                    }
                    dataOfEligibleCustomer.add(cols);

                } else {
                    String[] cols = rows.next();
                    for (String col : cols) {
                        if (col.equalsIgnoreCase("total_amount")) {
                            break;
                        } else {
                            priceColumnIndex += 1;
                        }
                    }
                    dataOfEligibleCustomer.add(cols);
                    count++;
                    continue;

                }

            }

            System.out.println("MainFrame.readCSV()   ----> " + dataOfEligibleCustomer.size());
            cSVReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        }

        return dataOfEligibleCustomer;

    }

}
