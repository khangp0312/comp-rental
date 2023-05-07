package vn.qlinternet;

import java.awt.EventQueue;
        
import vn.qlinternet.controller.LoginController;
import vn.qlinternet.view.LoginView;
        
public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginView view = new LoginView();
            LoginController controller = new LoginController(view);
            controller.showLoginView();
        });
    }
}
