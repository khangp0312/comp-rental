package vn.qlinternet.view;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import vn.qlinternet.dao.ComputerDao;
import vn.qlinternet.entity.Computer;

public class ComputerListPanel extends JPanel {
    private final ComputerDao computerDao = new ComputerDao();
    
    private JTable computerTable = new JTable();    
    private JLabel searchLbl;
    private TableRowSorter sorter;
    private JScrollPane jsp;
    private JTextField jtf;
    private TableModel model;
    
    public ComputerListPanel() {
        jtf = new JTextField(15);
        searchLbl = new JLabel("Search");
        
        
        setLayout(new FlowLayout(FlowLayout.CENTER));

        
        add(searchLbl);
        add(jtf);

        
        List<Computer> computerList = computerDao.getListComputers();        
        String[] columnNames = {"ID", "Tên máy", "Có sẵn"};        
        Object[][] data = new Object[computerList.size()][3];
        for (int i = 0; i < computerList.size(); i++) {
            Computer computer = computerList.get(i);
            data[i][0] = computer.getId();
            data[i][1] = computer.getName();
            if (computer.isAvailable())
                data[i][2] = "Có";
            else
                data[i][2] = "Không";
        }
        model = new DefaultTableModel(data, columnNames);
        computerTable = new JTable(model);
        sorter = new TableRowSorter<>(model);
        computerTable.setRowSorter(sorter);

        
        jsp = new JScrollPane(computerTable);
        add(jsp);

        jtf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(jtf.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(jtf.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // not used
            }
        });
        
        JButton addButton = new JButton("Thêm máy");
        addButton.addActionListener((ActionEvent e) -> {
            AddComputerDialog addComputerDialog = new AddComputerDialog((Window) SwingUtilities.getWindowAncestor(ComputerListPanel.this), ComputerListPanel.this);
            addComputerDialog.setVisible(true);
        });
        add(addButton);

        
        JButton removeButton = new JButton("Xoá");

        removeButton.addActionListener((ActionEvent e) -> {
            int selectedRow = computerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Chọn máy để xoá.");
                return;
            }   
            
            String value = computerTable.getValueAt(selectedRow, 2).toString();
            if (value.equals("Có")){
                
                if (selectedRow >= 0) {
                    int confirmDelete = JOptionPane.showConfirmDialog(this, "Có chắc chắn muốn xoá hay không?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirmDelete == JOptionPane.YES_OPTION){
                        int computerId = (int) computerTable.getValueAt(selectedRow, 0);
                        List<Computer> listComputers = computerDao.getListComputers();
                        int size = listComputers.size();
                        for (int i = 0; i < size; i++) {
                            if (listComputers.get(i).getId() == computerId) {
                                computerDao.delete(listComputers.get(i));   
                                refreshTable();
                                break;
                            }
                        }
                    }
                }
            }
            else
                JOptionPane.showMessageDialog(this, "Máy đang được sử dụng.");
        });
        this.add(removeButton);       
        
        JButton closeButton = new JButton("Thoát");
        closeButton.addActionListener((ActionEvent e) -> {
            Window window = SwingUtilities.getWindowAncestor(ComputerListPanel.this);
            window.dispose();
        });
        this.add(closeButton);
    }

    protected void refreshTable() {
        List<Computer> computerList = computerDao.getListComputers();
        String[] columnNames = {"ID", "Tên máy", "Có sẵn"};
        Object[][] data = new Object[computerList.size()][3];
        for (int i = 0; i < computerList.size(); i++) {
            Computer computer = computerList.get(i);
            data[i][0] = computer.getId();
            data[i][1] = computer.getName();
            if (computer.isAvailable())
                data[i][2] = "Có";
            else
                data[i][2] = "Không";
        }
        computerTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
    
    public void search(String str) {
               if (str.length() == 0) {
                  sorter.setRowFilter(null);
               } else {
                  sorter.setRowFilter(RowFilter.regexFilter("(?i)" +str));
               }
            }
}
