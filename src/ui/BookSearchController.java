package ui;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import business.Author;
import business.Book;
import business.BookCopy;
import business.LibraryMember;

public class BookSearchController
{
	@FXML TextField txtISBN;
	@FXML TextField txtTitle;
	@FXML TextField txtcheckoutmaxvalue;
	@FXML TextField txtNoofCopies;
	@FXML Button btnAddBook;
	@FXML Button btnGoBack;
	@FXML Button btnMenuAddBook;
	@FXML Button btnMenuEditBook;
	@FXML Label lblSearchStatus;
	@FXML Label lblBookCopyStatus;
	@FXML Alert alert;
	@FXML TextField txtISBN1;
	@FXML TextField txtcopyNumber;
	private boolean isCopy = false;
	@FXML Label lblAddFormStatus;
	@FXML RadioButton twentyOneDays;
	@FXML RadioButton sevenDays;
	@FXML ToggleGroup maxdaysbtn;
	private String ISBN;
	@FXML TextField authorCredential;
	@FXML TextField authorFirstName;
	@FXML TextField authorLastName;
	@FXML TextField authorPhone;
	@FXML Button btnAddAuthor;
	@FXML Label lblDisplayAuthors;
	@FXML Label lblDisplayTitle;
	@FXML Label lblDisplayNoOfCopies;
	@FXML Label lblAuthorFormStatus;
	@FXML Label lblBookCopyFormStatus;
	@FXML TabPane bookInfoTab;
	@FXML Tab tabBookInfo;
	
	@FXML void addBookClick(ActionEvent e) throws InvocationTargetException 
	{	
		try
		{
		Book b;
		if(isCopy){
			new BookCopy(txtcopyNumber.getText(), txtISBN.getText(), false ).addBookCopy();
			lblBookCopyFormStatus.setText("Copy Added Successfully");
			int noOfCopy = Integer.parseInt(txtcopyNumber.getText());
			noOfCopy++;
			txtcopyNumber.setText(Integer.toString(noOfCopy));
			updateBookInfoTab();
		}
		else {	
			sevenDays.setUserData("7");
			twentyOneDays.setUserData("21");
			b = new Book(txtISBN.getText(), txtTitle.getText(), maxdaysbtn.getSelectedToggle().getUserData().toString(), Integer.parseInt(txtNoofCopies.getText()));		
			b.addBook();
			lblAddFormStatus.setText("Book Added Successfully");
		}
		}catch(Exception e1){ e1.printStackTrace(); }
	}
	
	@FXML void btnBookSearchClick(ActionEvent e) throws InvocationTargetException 
	{
		try
		{
			if(txtISBN.getText().isEmpty())
			{
				lblSearchStatus.setTextFill(Color.RED);
				lblSearchStatus.setText("Please enter an ISBN");
			}
			else
			{
				lblSearchStatus.setText("");
				Book b = new Book().getBook(txtISBN.getText());
				if(b == null) 
				{
					bookInfoTab.setDisable(true);
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Book Not Found in the Library !");
					alert.setContentText("Continue to add it as a new book ?");
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK)
					{
						lblSearchStatus.getScene().getWindow().hide();
						new WindowController().openWindow("/ui/AddBook.fxml", "Add Book");
					}
				}
				else
				{
					bookInfoTab.setDisable(false);
					int noofCopies = b.getNoOfCopies(txtISBN.getText());
					// new copy number should increment by one
					txtcopyNumber.setText(Integer.toString(noofCopies+1));
					txtcopyNumber.setDisable(true);
					
					isCopy = true;						
					lblDisplayTitle.setText("Title: "+b.getTitle());
					updateBookInfoTab();
				}
				//txtISBN.getScene().getWindow().hide();
				//new WindowController().openWindow("/ui/MemberEdit.fxml", "Edit Member");
			}			
		}catch(Exception e1){ e1.printStackTrace(); }

	}	
	
	@FXML void btnMenuAddBookClick(ActionEvent e) {
		btnGoBack.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/BookSearch.fxml", "Book Search");

		//new WindowController().openWindow("/ui/AddBook.fxml", "Add Book");
	}
	
	@FXML void btnMenuEditBookClick(ActionEvent e) {
		btnGoBack.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/BookSearch.fxml", "Book Search");
	}
	
	@FXML void btnMenuGoBackClick(ActionEvent e) {
		btnGoBack.getScene().getWindow().hide();
		new WindowController().openWindow("/ui/Dashboard.fxml", "DashBoard", "");
	}
	
	@FXML void btnAddAuthorClick(ActionEvent e) {
		if(txtISBN.getText().isEmpty())
		{
			lblSearchStatus.setTextFill(Color.RED);
			lblSearchStatus.setText("Please enter an ISBN");
		}
		else {
			new Author(authorFirstName.getText(), authorLastName.getText(), authorPhone.getText(), authorCredential.getText(),txtISBN.getText()  ).addAuthor();
			lblBookCopyFormStatus.setText("Author Added Successful.");
			updateBookInfoTab();
		}
	}
	
	public void updateBookInfoTab() {
		lblDisplayNoOfCopies.setText("No Of Copies: "+Integer.toString(new Book().getNoOfCopies(txtISBN.getText())));
		String authorList = new Author().getBookAuthors(txtISBN.getText());
		lblDisplayAuthors.setText(authorList);
	}
	
}
