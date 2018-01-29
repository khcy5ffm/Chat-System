package client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ClientInterfaceController {

	@FXML
	private TextFlow messageDisplay;
	
    @FXML
    private Text messages;
    
    @FXML
    private TextField messageInputField;

    @FXML
    private Button enterButton;
    
    Socket s;
    BufferedReader in;
    PrintWriter out;
    String line;
    final int PORT = 9000;

    public ClientInterfaceController() {
    }
    
    @FXML
    private void initialize() {
    	try {
    		s = new Socket("localhost", PORT);
    		
    		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
			
			messages.setText("");
			messages.setText(messages.getText() + in.readLine() + "\n"); // prints server welcome message to window
    	} catch(UnknownHostException e) {
    		e.printStackTrace();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    // event handler for processing user input
    @FXML
    public void handleEnterMessage() {
    	String s;

    	try {
    		if(messageInputField.textProperty().isNotEmpty().get()) {
    			s = messageInputField.getText();
    			
    			out.println(s); // sends user input to server for validation
    			
    			messages.setText(messages.getText() + in.readLine() + "\n"); // prints server validation message to window 
    			messageInputField.clear();
    		}    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
}