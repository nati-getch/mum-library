package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckOutRecord;
import business.LibraryMember;

public class CheckoutController {
	LibraryMember l;
	Book b;
	@FXML
	TextField txtMemberId;
	@FXML
	TextField txtISBN;
	@FXML
	Button btncheckout;
	@FXML
	Label lblcheckoutStatus;
	//@FXML TableView<CheckOutRecord> tView;

	@FXML
	void checkoutBtnClick(ActionEvent e) {
		//openWindow("/ui/checkoutDisplay.fxml","Display Checkout Records");
		lblcheckoutStatus.setText("");
		l = new LibraryMember().getMember(txtMemberId.getText());
		if (l == null)
			lblcheckoutStatus.setText("Person with ID: " + txtMemberId.getText() + " is not a valid member.");
		if (lblcheckoutStatus.getText() == "") {
			b = new Book().getBook(txtISBN.getText());
			if (b == null)
				lblcheckoutStatus.setText("Book with ISBN: " + txtISBN.getText() + " is not present in library.");
		}
		if (l != null && b != null && lblcheckoutStatus.getText() == "") {
			BookCopy bookCopy = new BookCopy().getBookCopy(txtISBN.getText());
			if( bookCopy == null){
				lblcheckoutStatus.setText("Book Not Available");
			}
			else {
				//add to check out record
				new CheckOutRecord().addCheckOutRecord(txtISBN.getText(), txtMemberId.getText(), bookCopy.getUniqueCopynum(), b.getCheckoutmaxvalue());
				// update book db file
				bookCopy.editBookCopy(bookCopy.getUniqueCopynum(), bookCopy.getISBN());
				new WindowController().openWindow("/ui/checkoutDisplay.fxml", "Checkout Record");
			}
		}

	}
/*	public void openWindow(String s1,String title) {
		try {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(s1));
     //   Scene scene = new Scene(root);
     //   Stage stage = new Stage();
     //   stage.setTitle(title);
     //   stage.setScene(scene);
        CheckOutRecord cr = new CheckOutRecord();
        List<CheckOutRecord> crlist = new ArrayList<CheckOutRecord>();
        tView.getItems().addAll(crlist);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }*/
}