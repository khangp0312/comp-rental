package vn.qlinternet.controller;

import java.util.List;

import vn.qlinternet.dao.InfoDao;
import vn.qlinternet.rental.Info;
import vn.qlinternet.view.AdminView;

public class AdminController {
    private final InfoDao infoDao;
    private final AdminView adminView;
    
    public AdminController(AdminView view){
        this.adminView = view;
        infoDao = new InfoDao();
                
    }
    
    public void showAdminView() {
        List<Info> infoList = infoDao.getListInfos();
        adminView.setVisible(true);
        adminView.showListInfo(infoList);
    }
    
       
}
