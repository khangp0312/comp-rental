package vn.qlinternet.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.qlinternet.entity.Guest;
import vn.qlinternet.entity.GuestXML;
import vn.qlinternet.utils.FileUtils;

public class GuestDao {
    private static final String GUEST_FILE_NAME = "guest.xml";
    private List<Guest> listGuests;
    
       
    public GuestDao() {
        this.listGuests = readListGuests();
        if (listGuests == null) {
            listGuests = new ArrayList<>();
        }
    }

    /**
     * Lưu các đối tượng guest vào file guest.xml
     * 
     * @param guests
     */
    public void writeListGuests(List<Guest> guests) {
        GuestXML guestXML = new GuestXML();
        guestXML.setGuest(guests);
        FileUtils.writeXMLtoFile(GUEST_FILE_NAME, guestXML);
    }

    /**
     * Đọc các đối tượng guest từ file guest.xml
     * 
     * @return list guest
     */
    public final List<Guest> readListGuests() {
        List<Guest> list = new ArrayList<>();
        GuestXML guestXML = (GuestXML) FileUtils.readXMLFile(
                GUEST_FILE_NAME, GuestXML.class);
        if (guestXML != null) {
            list = guestXML.getGuest();
        }
        return list;
    }
    

    /**
     * thêm guest vào listGuests và lưu listGuests vào file
     * 
     * @param guest
     */
    public void add(Guest guest) {
        int id = 1;
        if (listGuests != null && !listGuests.isEmpty()) {
            id = listGuests.size() + 1;
        }
        guest.setId(id);
        listGuests.add(guest);
        writeListGuests(listGuests);
    }
    
    /**
     * cập nhật guest vào listGuests và lưu listGuests vào file
     * 
     * @param guest
     */
    public void edit(Guest guest) {
        int size = listGuests.size();
        for (int i = 0; i < size; i++) {
            if (listGuests.get(i).getId() == guest.getId()) {
                listGuests.get(i).setName(guest.getName());
                listGuests.get(i).setPoint(guest.getPoint());
                writeListGuests(listGuests);
                break;
            }
        }
    }
    
    /**
     * xóa guest từ listGuests và lưu listGuests vào file
     * 
     * @param guest
     * @return 
     */
    public boolean delete(Guest guest) {
        boolean isFound = false;
        int size = listGuests.size();
        for (int i = 0; i < size; i++) {
            if (listGuests.get(i).getId() == guest.getId()) {
                guest = listGuests.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listGuests.remove(guest);
            writeListGuests(listGuests);
            return true;
        }
        return false;
    }

    public void sortGuestById() {
        Collections.sort(listGuests, (Guest guest1, Guest guest2) -> {
            if (guest1.getId() < guest2.getId()) {
                return 1;
            }
            return -1;
        });
    }
    /**
     * sắp xếp danh sách guest theo name theo tứ tự tăng dần
     */
    public void sortGuestByName() {
        Collections.sort(listGuests, (Guest guest1, Guest guest2) 
                -> guest1.getName().compareTo(guest2.getName()));
    }
        
    
    /**
     * sắp xếp danh sách guest theo point
     */
    public void sortGuestByPoint() {
        Collections.sort(listGuests, (Guest guest1, Guest guest2) -> {
            if (guest1.getPoint() > guest2.getPoint()) {
                return 1;
            }
            return -1;
        });    }

    public List<Guest> getListGuests() {
        return listGuests;
    }

    public void setListGuests(List<Guest> listGuests) {
        this.listGuests = listGuests;
    }
}
