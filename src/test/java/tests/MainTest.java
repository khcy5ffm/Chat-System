package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.Runner;

public class MainTest extends ApplicationTest {
	Parent p;
	Scene s;
	Text t;
	TextField tf;
	Button b;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/client/view/ClientInterface.fxml"));
			s = new Scene(root, 542, 442);
			
			p = root;
			
			primaryStage.setTitle("Chat Client");
			primaryStage.setScene(s);
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		es.execute(new Runnable() {
			public void run() {
				Runner.main(null);				
			}
		});
	}
	
	
	// tests if GUI has key basic elements
	// and if their initial configurations are correct
	@Test
	public void testHasGUIElements() {
		// searches for GUI elements
		// based on intended GUI element ID assignments
		b = from(p).lookup("#enterButton").query();
		tf = from(p).lookup("#messageInputField").query();
		t = from(p).lookup("#messages").query();
		
		// checks GUI element initial configuration
		// based on intended initial GUI element configuration
		assertEquals("Enter", b.getText());

		assertEquals("Type message here...", tf.getPromptText());
		assertEquals("", tf.getText());
		
		// server welcome message string trimmed
		// to ignore unknown number of clients opened
		// due to random arrangement of test runs
		assertEquals("OK Welcome to the chat server", t.getText().substring(0, 29));
	}
	
	// tests arbitrary user input
	@Test
	public void testMessageInput() {
		b = from(p).lookup("#enterButton").query();
		tf = from(p).lookup("#messageInputField").query();
		t = from(p).lookup("#messages").query();
				
		// simulates user typing "Hello"
		clickOn(tf);
		press(KeyCode.SHIFT);
		type(KeyCode.H); 
		release(KeyCode.SHIFT);
		type(KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);
		clickOn(b);
		
		String message = t.getText().split("\n")[1]; // retrieves server message after server welcome message
		
		assertTrue(message.length() != 0);
	}
}
