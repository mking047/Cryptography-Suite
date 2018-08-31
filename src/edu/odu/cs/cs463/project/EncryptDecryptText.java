package edu.odu.cs.cs463.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Encrypts an entered text using public and shared keys and also decrypts the cipher text
 * 
 * @author Matthew King
 */
public class EncryptDecryptText {

	String message;
	SecretKey secretKey;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JFrame frame) {
		
		
		//////////// Create GUI /////////////
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(685, 13, 97, 25);
		frame.getContentPane().add(btnMainMenu);
				
		
		JLabel lblMessageToEncrypt = new JLabel("Message to Encrypt:");
		lblMessageToEncrypt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMessageToEncrypt.setBounds(12, 131, 189, 34);
		frame.getContentPane().add(lblMessageToEncrypt);
		
		JTextField textField = new JTextField();
		textField.setBounds(213, 131, 383, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEncryptedMessage = new JLabel("Encrypted Message:");
		lblEncryptedMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEncryptedMessage.setBounds(12, 178, 142, 25);
		frame.getContentPane().add(lblEncryptedMessage);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(213, 173, 383, 30);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(213, 263, 383, 34);
		frame.getContentPane().add(textArea_1);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEncrypt.setBounds(608, 131, 97, 27);
		frame.getContentPane().add(btnEncrypt);
		
		JButton btnNewButton = new JButton("Decrypt");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(608, 173, 97, 30);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblDecryptedMessage = new JLabel("Decrypted Message:");
		lblDecryptedMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDecryptedMessage.setBounds(12, 269, 142, 28);
		frame.getContentPane().add(lblDecryptedMessage);
		
		////////////// Event Handlers /////////////
		
		btnMainMenu.addActionListener(new ActionListener() {  //Event handler for MainMenu
			public void actionPerformed(ActionEvent e) {
			MainMenu menu = new MainMenu();
			menu.mainMenuReceive(frame);
			}
		});
		
		btnEncrypt.addActionListener(new ActionListener() {  //Event handler for Encrypt button
			public void actionPerformed(ActionEvent e) {
			try {
				encrypt(textArea, textField);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException e1) {
				e1.printStackTrace();
			}			
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {  //Event handler for Decrypt Button
			public void actionPerformed(ActionEvent e) {
				try {
					decrypt(textArea_1);
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
   /**
    * Encrypts the message in the textField and prints the encrypted
    * message to the textArea
    *  
    * @param textArea
    * @param textField
    * @throws NoSuchAlgorithmException
    * @throws NoSuchPaddingException
    * @throws InvalidKeyException
    * @throws IllegalBlockSizeException
    * @throws BadPaddingException
    */
	protected void encrypt(JTextArea textArea, JTextField textField) throws NoSuchAlgorithmException, NoSuchPaddingException, 
									InvalidKeyException, IllegalBlockSizeException, 
									BadPaddingException 
	{
		message = textField.getText().toString();
		KeyGenerator keygen = KeyGenerator.getInstance("AES");  //AES key generator 
		SecureRandom rand = new SecureRandom();                 
		keygen.init(rand); 										//use random as seed to keygen
		secretKey = keygen.generateKey();                       //generate secretKey
		Cipher cipher = Cipher.getInstance("AES");		        //Returns a Cipher object that implements AES		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);            //Initializes this cipher with secret key.
		byte[] encrypted = cipher.doFinal(message.getBytes());  //Finish encryption
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedString = encoder.encodeToString(encrypted);  //convert encrypted data to printable string
		textArea.setText("");
		textArea.append(encryptedString);
		message = encryptedString;
	}
	
	/**
	 * Decrypts the message and prints it to textArea_1
	 * 
	 * @param textArea_1
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	protected void decrypt(JTextArea textArea_1) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Base64.Decoder decoder = Base64.getDecoder();
		Cipher cipher = Cipher.getInstance("AES");   
		cipher.init(Cipher.DECRYPT_MODE, secretKey);  //initialize for decryption using secret key
		String decryptedMessage = new String(cipher.doFinal(decoder.decode(message)));  //decrypt
		textArea_1.setText("");
		textArea_1.append(decryptedMessage);
	}
	
	/**
	 * Default Constructor
	 */
	 public EncryptDecryptText()
	 {
		 message = "";
		 secretKey = null;
	 }

}
