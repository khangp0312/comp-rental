package vn.qlinternet.view;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import vn.qlinternet.dao.GuestDao;
import vn.qlinternet.entity.Guest;

public class GuestListPanel extends JPanel {
    private final GuestDao guestDao = new GuestDao();
    
    private final JTable guestTable;
    private final JTextField searchField;
    
    public GuestListPanel() {
        List<Guest> guestList = guestDao.getListGuests();
        
        String[] columnNames = {"ID", "Tên khách", "Điểm thưởng"};        
        Object[][] data = new Object[guestList.size()][3];
        for (int i = 0; i < guestList.size(); i++) {
            Guest guest = guestList.get(i);
            data[i][0] = guest.getId();
            data[i][1] = guest.getName();
            data[i][2] = guest.getPoint();
        }
        TableModel model = new DefaultTableModel(data, columnNames);
        guestTable = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        guestTable.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(guestTable);
        this.add(scrollPane);
        
        searchField = new JTextField();
        this.add(searchField);
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener((ActionEvent e) -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                RowFilter<TableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
                sorter.setRowFilter(rowFilter);
            } else {
                sorter.setRowFilter(null);
            }
        });
        this.add(searchButton);
        
        JButton addButton = new JButton("Thêm khách");
        addButton.addActionListener((ActionEvent e) -> {
            AddGuestDialog addGuestDialog = new AddGuestDialog((Window) SwingUtilities.getWindowAncestor(GuestListPanel.this), GuestListPanel.this);
            addGuestDialog.setVisible(true);
        });

        this.add(addButton);        
        
        JButton closeButton = new JButton("Thoát");
        closeButton.addActionListener((ActionEvent e) -> {
            Window window = SwingUtilities.getWindowAncestor(GuestListPanel.this);
            window.dispose();
        });
        this.add(closeButton);
    }
    
    protected void refreshTable() {
        List<Guest> guestList = guestDao.getListGuests();
        String[] columnNames = {"ID", "Tên khách", "Điểm thưởng"};
        Object[][] data = new Object[guestList.size()][3];
        for (int i = 0; i < guestList.size(); i++) {
            Guest guest = guestList.get(i);
            data[i][0] = guest.getId();
            data[i][1] = guest.getName();
            data[i][2] = guest.getPoint();
        }
        TableModel model = new DefaultTableModel(data, columnNames);
        guestTable.setModel(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        guestTable.setRowSorter(sorter);
    }
}
