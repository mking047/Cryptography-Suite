package edu.odu.cs.cs463.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Let user specify a password and sue it to generate a shared key
 * 
 * @author Matthew King
 *
 */
public class PasswordAndKey {
	
	String password;
	SecretKey secretKey;
	byte[] bytes;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JFrame frame) {
		
		//////////// Create GUI ////////////
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(685, 13, 97, 25);
		frame.getContentPane().add(btnMainMenu);
		
		JLabel lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterPassword.setBounds(12, 77, 126, 16);
		frame.getContentPane().add(lblEnterPassword);
		
		JTextField textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(150, 75, 302, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton pad = new JButton("Generate");
		pad.setBounds(464, 74, 97, 25);
		frame.getContentPane().add(pad);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textArea.setBounds(12, 247, 549, 46);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Secret Key");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(204, 226, 119, 16);
		frame.getContentPane().add(lblNewLabel);
		
		//////////// Event Handlers ////////////
		
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			MainMenu menu = new MainMenu();
			menu.mainMenuReceive(frame);
			}
		});
		
		
		pad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getAndPadPassword(textField);
					displaySharedKey(textArea);
				} catch (NoSuchAlgorithmException e1) {

					e1.printStackTrace();
				}
			}
		});
		
	}
		
	/**
	 * Pads the password and uses it to create a secret key from the hash
	 * 
	 * @param textField
	 * @throws NoSuchAlgorithmException
	 */
	public void getAndPadPassword(JTextField textField) throws NoSuchAlgorithmException
	{
		password = textField.getText().toString();
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		bytes = password.getBytes();
		bytes = digest.digest(bytes);	//completes the hash computation by performing final operations (i.e. padding)
		bytes = Arrays.copyOf(bytes, 16);  //get only the first 128 bits
		
		secretKey = new SecretKeySpec(bytes, "AES");
		
	}
	
	/**
	 * Prints the Shared Key to the corresponding text area 
	 * 
	 * @param textArea
	 */
	public void displaySharedKey(JTextArea textArea)
	{
		Base64.Encoder encoder = Base64.getEncoder(); 
    	String str = encoder.encodeToString(secretKey.getEncoded());
		textArea.setText("");
		
		Character[] charObjectArray = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
    	
    	for(int i = 0; i < charObjectArray.length; i++)
    	{
    		if( i%45 == 0  && i != 0)			
    		{
    			textArea.append(charObjectArray[i].toString() + "\n"); 
    		}
    		else
    		{
    			textArea.append(charObjectArray[i].toString());
    		}
    	}
		
	}
	
	
	/**
	 * Default Constructor
	 */
	public PasswordAndKey()
	{
		password = "";
		bytes = null;
		secretKey = null;		
	}	
	
}
