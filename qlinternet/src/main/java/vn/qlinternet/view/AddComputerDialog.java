package vn.qlinternet.view;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import vn.qlinternet.dao.ComputerDao;
import vn.qlinternet.entity.Computer;

public class AddComputerDialog extends JDialog {
    private final ComputerDao computerDao = new ComputerDao();
    private final JTextField nameField = new JTextField();
    private final JButton addButton = new JButton("Thêm máy");
    private final JButton cancelButton = new JButton("Cancel");
    private final ComputerListPanel computerListPanel;

    public AddComputerDialog(Window owner, ComputerListPanel computerListPanel) {
        super(owner, "Thêm máy", ModalityType.APPLICATION_MODAL);
        this.computerListPanel = computerListPanel;
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Name:"));
        add(nameField);
        add(addButton);
        add(cancelButton);
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String name = nameField.getText();
                Computer computer = new Computer(0 ,name, true);
                computerDao.add(computer);
                JOptionPane.showMessageDialog(this, "Máy đã được thêm.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding computer: " + ex.getMessage());
            }
        });
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        pack();
        setLocationRelativeTo(owner);
    }
}
