package vn.qlinternet.dao;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.qlinternet.rental.Info;
import vn.qlinternet.rental.InfoXML;
import vn.qlinternet.utils.FileUtils;

public class InfoDao {
    private static final String INFO_FILE_NAME = "info.xml";
    private List<Info> listInfos;
    
       
    public InfoDao() {
        this.listInfos = readListInfos();
        if (listInfos == null) {
            listInfos = new ArrayList<>();
        }
    }

    /**
     * Lưu các đối tượng info vào file info.xml
     * 
     * @param infos
     */
    public void writeListInfos(List<Info> infos) {
        InfoXML infoXML = new InfoXML();
        infoXML.setInfo(infos);
        FileUtils.writeXMLtoFile(INFO_FILE_NAME, infoXML);
    }

    /**
     * Đọc các đối tượng info từ file info.xml
     * 
     * @return list info
     */
    public final List<Info> readListInfos() {
        List<Info> list = new ArrayList<>();
        InfoXML infoXML = (InfoXML) FileUtils.readXMLFile(
                INFO_FILE_NAME, InfoXML.class);
        if (infoXML != null) {
            list = infoXML.getInfo();
        }
        return list;
    }
    

    /**
     * thêm info vào listInfos và lưu listInfos vào file
     * 
     * @param info
     */
    public void add(Info info) {
        int id = 1;
        if (listInfos != null && !listInfos.isEmpty()) {
            id = listInfos.size() + 1;
        }
        info.setId(id);
        listInfos.add(info);
        writeListInfos(listInfos);
    }
    
    /**
     * cập nhật info vào listInfos và lưu listInfos vào file
     * 
     * @param info
     */
    public void edit(Info info) {
        int size = listInfos.size();
        for (int i = 0; i < size; i++) {
            if (listInfos.get(i).getId() == info.getId()) {
                listInfos.get(i).setComputer(info.getComputer());
                listInfos.get(i).setGuest(info.getGuest());
                listInfos.get(i).setRentalTime(info.getRentalTime());
                writeListInfos(listInfos);
                break;
            }
        }
    }
    
    /**
     * xóa info từ listInfos và lưu listInfos vào file
     * 
     * @param info
     * @return 
     */
    public boolean delete(Info info) {
        boolean isFound = false;
        int size = listInfos.size();
        for (int i = 0; i < size; i++) {
            if (listInfos.get(i).getId() == info.getId()) {
                info = listInfos.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listInfos.remove(info);
            writeListInfos(listInfos);
            return true;
        }
        return false;
    }

    public void sortInfoById() {
        Collections.sort(listInfos, (Info info1, Info info2) -> {
            if (info1.getId() < info2.getId()) {
                return 1;
            }
            return -1;
        });
    }
    /**
     * sắp xếp danh sách info theo Computer name theo tứ tự tăng dần
     */
    public void sortInfoByComputerName() {
        Collections.sort(listInfos, (Info info1, Info info2) 
                -> info1.getComputer().getName().compareTo(info2.getComputer().getName()));
    }
        
    
    /**
     * ssắp xếp danh sách info theo Guest name theo tứ tự tăng dần
     */
    public void sortInfoByGuestName() {
        Collections.sort(listInfos, (Info info1, Info info2) 
                -> info1.getGuest().getName().compareTo(info2.getGuest().getName()));
    }
    
    class InfoComparator implements Comparator<Info> {
    @Override
        public int compare(Info info1, Info info2) {
            return LocalTime.parse(info1.getRentalTime()).compareTo(LocalTime.parse(info2.getRentalTime()));
        }       
    }
    
    public void sortInfoByRentalTime() {
        Collections.sort(listInfos,new InfoComparator());
    }

    public List<Info> getListInfos() {
        return listInfos;
    }

    public void setListInfos(List<Info> listInfos) {
        this.listInfos = listInfos;
    }
}
