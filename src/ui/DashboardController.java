package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import business.User;;

public class DashboardController{
	@FXML Button btnCheckoutBook;
	@FXML Button btnManageMember;
	@FXML Button btnAddBook;
	
	@FXML void manageMemberBtnClick(ActionEvent e) {
		btnManageMember.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/ManageMemberMenu.fxml", "Manage Member");
	}
	
	@FXML void addBookBtnClick(ActionEvent e) {
		btnManageMember.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/BookSearch.fxml", "Manage Book");
	}
	
	@FXML void checkoutBookBtnClick(ActionEvent e) {
		btnCheckoutBook.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/checkoutScreen.fxml", "Checkout Book");
	}
}
