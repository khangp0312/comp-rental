package vn.qlinternet.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.qlinternet.entity.Computer;
import vn.qlinternet.entity.ComputerXML;
import vn.qlinternet.utils.FileUtils;

public class ComputerDao {
    private static final String COMPUTER_FILE_NAME = "computer.xml";
    private List<Computer> listComputers;
    
       
    public ComputerDao() {
        this.listComputers = readListComputers();
        if (listComputers == null) {
            listComputers = new ArrayList<>();
        }
    }

    /**
     * Lưu các đối tượng computer vào file computer.xml
     * 
     * @param computers
     */
    public void writeListComputers(List<Computer> computers) {
        ComputerXML computerXML = new ComputerXML();
        computerXML.setComputer(computers);
        FileUtils.writeXMLtoFile(COMPUTER_FILE_NAME, computerXML);
    }

    /**
     * Đọc các đối tượng computer từ file computer.xml
     * 
     * @return list computer
     */
    public final List<Computer> readListComputers() {
        List<Computer> list = new ArrayList<>();
        ComputerXML computerXML = (ComputerXML) FileUtils.readXMLFile(
                COMPUTER_FILE_NAME, ComputerXML.class);
        if (computerXML != null) {
            list = computerXML.getComputer();
        }
        return list;
    }
    

    /**
     * thêm computer vào listComputers và lưu listComputers vào file
     * 
     * @param computer
     */
    public void add(Computer computer) {
        int id = 1;
        if (listComputers != null && !listComputers.isEmpty()) {
            id = listComputers.size() + 1;
        }
        computer.setId(id);
        listComputers.add(computer);
        writeListComputers(listComputers);
    }
    
    /**
     * cập nhật computer vào listComputers và lưu listComputers vào file
     * 
     * @param computer
     */
    public void edit(Computer computer) {
        int size = listComputers.size();
        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                listComputers.get(i).setName(computer.getName());
                listComputers.get(i).setAvailability(computer.isAvailable());
                writeListComputers(listComputers);
                break;
            }
        }
    }
    
    /**
     * xóa computer từ listComputers và lưu listComputers vào file
     * 
     * @param computer
     * @return 
     */
    public boolean delete(Computer computer) {
        boolean isFound = false;
        int size = listComputers.size();
        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                computer = listComputers.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listComputers.remove(computer);
            writeListComputers(listComputers);
            return true;
        }
        return false;
    }
    
    public void sortComputerById() {
        Collections.sort(listComputers, (Computer computer1, Computer computer2) -> {
            if (computer1.getId() < computer2.getId()) {
                return 1;
            }
            return -1;
        });
    }

    /**
     * sắp xếp danh sách computer theo name theo tứ tự tăng dần
     */
    public void sortComputerByName() {
        Collections.sort(listComputers, (Computer computer1, Computer computer2) 
                -> computer1.getName().compareTo(computer2.getName()));
    }
    
    public class ComputerComparator implements Comparator<Computer> {
    @Override
    public int compare(Computer computer1, Computer computer2) {
        int aComparison = Boolean.compare(computer2.isAvailable(), computer1.isAvailable());
        if (aComparison != 0) {
            return aComparison;
        } else {
            return computer1.getName().compareTo(computer2.getName());
        }
    }
    }
    
    /**
     * sắp xếp danh sách computer theo availability
     */
    public void sortComputerByAvailability() {
        Collections.sort(listComputers, new ComputerComparator());        
    }

    public List<Computer> getListComputers() {
        return listComputers;
    }

    public void setListComputers(List<Computer> listComputers) {
        this.listComputers = listComputers;
    }
}
