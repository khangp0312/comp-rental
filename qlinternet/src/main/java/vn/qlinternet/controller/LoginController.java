package vn.qlinternet.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vn.qlinternet.dao.UserDao;
import vn.qlinternet.entity.User;
import vn.qlinternet.view.AdminView;
import vn.qlinternet.view.LoginView;

public class LoginController {
    private UserDao userDao;
    private LoginView loginView;
    private AdminView adminView;
    
    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
    }
    
    public void showLoginView() {
        loginView.setVisible(true);
    }
    
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                // nếu đăng nhập thành công, mở màn hình quản lý sinh viên
                adminView = new AdminView();
                AdminController adminController = new AdminController(adminView);
                adminController.showAdminView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
