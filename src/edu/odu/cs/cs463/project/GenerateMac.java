package edu.odu.cs.cs463.project;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Generate a MAC using a shared key
 * 
 * @author Matthew King
 */

public class GenerateMac {
	
	SecretKey secretKey;
	String message;

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JFrame frame) {
		
		//////////// Generate GUI ////////////
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(685, 13, 97, 25);
		frame.getContentPane().add(btnMainMenu);
		
		JLabel lblGenerateNewMac = new JLabel("Enter message:");
		lblGenerateNewMac.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGenerateNewMac.setBounds(26, 72, 109, 31);
		frame.getContentPane().add(lblGenerateNewMac);
		
		JButton btnNewButton = new JButton("Generate MAC");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(553, 73, 152, 29);
		frame.getContentPane().add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea.setBounds(143, 159, 395, 31);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_1.setBounds(143, 231, 395, 41);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblMac = new JLabel("MAC:");
		lblMac.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMac.setBounds(83, 231, 52, 41);
		frame.getContentPane().add(lblMac);
		
		JLabel lblSharedKey = new JLabel("Shared Key:");
		lblSharedKey.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSharedKey.setBounds(46, 159, 85, 31);
		frame.getContentPane().add(lblSharedKey);
		
		JTextField textField = new JTextField();
		textField.setBounds(143, 72, 395, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		//////////// Event handlers ////////////
		
		btnMainMenu.addActionListener(new ActionListener() {   //Event handler for MainMenu
			public void actionPerformed(ActionEvent e) {
			MainMenu menu = new MainMenu();
			menu.mainMenuReceive(frame);
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {   //Event handler for Generate button
			public void actionPerformed(ActionEvent e) {
				try {
					generateSecretKey(textArea);
					generateMac(textArea_1);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	/**
	 * Generates a secret key and prints it to textArea
	 * 
	 * @param textArea
	 * @throws NoSuchAlgorithmException
	 */
	public void generateSecretKey(JTextArea textArea) throws NoSuchAlgorithmException
	{
		KeyGenerator gen = KeyGenerator.getInstance("HmacMD5");      //uses MD5 hashing
		gen.init(160);	
		secretKey = gen.generateKey();			//generate secretKey
		
		textArea.setText("");
    	Base64.Encoder encoder = Base64.getEncoder(); 
    	String str = encoder.encodeToString(secretKey.getEncoded());
    	textArea.append(str);					//output
	}
	
	/*
	 *  Generates a MAC using MD5 and a secret key
	 */
	public void generateMac(JTextArea textArea_1) throws NoSuchAlgorithmException, InvalidKeyException
	{
		Mac mac = Mac.getInstance("HmacMD5");			
		mac.init(secretKey);					//initialize with secretKey
		
		byte[] bytes = message.getBytes(); 
    	bytes = mac.doFinal(bytes);			   //Processes the array of bytes and finishes the MAC operation.
		
    	Base64.Encoder encoder = Base64.getEncoder();
    	message = encoder.encodeToString(bytes);
    	
    	textArea_1.setText("");
    	textArea_1.append(message);			//output
		
	}
	
		
	/**
	 * Default Constructor
	 */
	public GenerateMac()
	{
		secretKey = null;
		message = "";
	}

}
