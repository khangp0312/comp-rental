package vn.qlinternet.view;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import vn.qlinternet.dao.GuestDao;
import vn.qlinternet.entity.Guest;

public class AddGuestDialog extends JDialog {
    private final GuestDao guesDao = new GuestDao();
    private final JTextField nameField = new JTextField();
    private final JButton addButton = new JButton("Thêm khách");
    private final JButton cancelButton = new JButton("Cancel");
    private final GuestListPanel guesListPanel;

    public AddGuestDialog(Window owner, GuestListPanel guesListPanel) {
        super(owner, "Thêm khách", ModalityType.APPLICATION_MODAL);
        this.guesListPanel = guesListPanel;
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Name:"));
        add(nameField);
        add(addButton);
        add(cancelButton);
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String name = nameField.getText();
                Guest gues = new Guest(0 ,name, 0);
                guesDao.add(gues);
                JOptionPane.showMessageDialog(this, "Khách đã được thêm.");
                guesListPanel.refreshTable();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding guest: " + ex.getMessage());
            }
        });
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        pack();
        setLocationRelativeTo(owner);
    }
}
