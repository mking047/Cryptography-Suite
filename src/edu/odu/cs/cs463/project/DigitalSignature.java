package edu.odu.cs.cs463.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Generates a digital signature and verifies the signature
 * 
 * @author Matthew
 */
public class DigitalSignature {

	KeyPair keyPairBob;
	KeyPair keyPairAlice;
	Signature signature;
	String message;
	byte[] bytes;
	byte[] signedBytes;
	
	
	/**
	 * Initialize the contents of the frame
	 * .
	 * @throws NoSuchAlgorithmException 
	 */
	public void initialize(JFrame frame) {
		
		////////////CREATE GUI////////////
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(685, 13, 97, 25);
		frame.getContentPane().add(btnMainMenu);
		
		JLabel lblSendAMessage = new JLabel("Send a message as:");
		lblSendAMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSendAMessage.setBounds(12, 66, 161, 31);
		frame.getContentPane().add(lblSendAMessage);
		
		JRadioButton rdbtnBob = new JRadioButton("Bob");
		rdbtnBob.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnBob.setBounds(181, 70, 94, 25);
		frame.getContentPane().add(rdbtnBob);
		
		JRadioButton rdbtnAlice = new JRadioButton("Alice");
		rdbtnAlice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnAlice.setBounds(279, 70, 94, 25);
		frame.getContentPane().add(rdbtnAlice);
		
		JRadioButton rdbtnBob_1 = new JRadioButton("Bob");
		rdbtnBob_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnBob_1.setBounds(227, 186, 94, 25);
		frame.getContentPane().add(rdbtnBob_1);
		
		JRadioButton rdbtnAlice_1 = new JRadioButton("Alice");
		rdbtnAlice_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnAlice_1.setBounds(325, 186, 127, 25);
		frame.getContentPane().add(rdbtnAlice_1);
		
		JLabel lblEnterMessage = new JLabel("Enter message:");
		lblEnterMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterMessage.setBounds(12, 126, 161, 31);
		frame.getContentPane().add(lblEnterMessage);
		
		JTextField textField = new JTextField();
		textField.setBounds(181, 126, 291, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea.setBounds(12, 314, 595, 226);
		frame.getContentPane().add(textArea);
		
		JLabel lblMessageverification = new JLabel("Verification");
		lblMessageverification.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMessageverification.setBounds(227, 295, 173, 16);
		frame.getContentPane().add(lblMessageverification);
		
		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSendMessage.setBounds(484, 124, 140, 31);
		frame.getContentPane().add(btnSendMessage);
		
		JButton btnVerify = new JButton("Verify");
		btnVerify.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnVerify.setBounds(484, 182, 140, 29);
		frame.getContentPane().add(btnVerify);
		
		JLabel lblVerifyMessageWas = new JLabel("Verify message was sent by:");
		lblVerifyMessageWas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVerifyMessageWas.setBounds(12, 182, 207, 31);
		frame.getContentPane().add(lblVerifyMessageWas);
		
		
		////////////Event handlers for GUI/////////////
		
		btnMainMenu.addActionListener(new ActionListener() { //event listener for MainMenu
			public void actionPerformed(ActionEvent e) {
			MainMenu menu = new MainMenu();
			menu.mainMenuReceive(frame);
			}
		});
		
		
		rdbtnBob.addActionListener(new ActionListener() {   //event listener for Bob button1
			public void actionPerformed(ActionEvent e) {
			if(rdbtnBob.isSelected())                  //if Bob's button is selected, unselect Alice
				rdbtnAlice.setSelected(false);;  
				
			}
		});
		
		rdbtnAlice.addActionListener(new ActionListener() { //event listener for Alice button1
			public void actionPerformed(ActionEvent e) {
			if(rdbtnAlice.isSelected())				//if Alice's button is selected, unselect Bob
				rdbtnBob.setSelected(false);
			}
		});
		
		rdbtnBob_1.addActionListener(new ActionListener() {   //event listener for Bob button2
			public void actionPerformed(ActionEvent e) {
			if(rdbtnBob_1.isSelected())                  //if Bob's button is selected, unselect Alice
				rdbtnAlice_1.setSelected(false);;  
				
			}
		});
		
		rdbtnAlice_1.addActionListener(new ActionListener() { //event listener for Alice button2
			public void actionPerformed(ActionEvent e) {
			if(rdbtnAlice_1.isSelected())				//if Alice's button is selected, unselect Bob
				rdbtnBob_1.setSelected(false);
			}
		});
		
		
		
		btnSendMessage.addActionListener(new ActionListener() {  //event handler for SendMessage button
			public void actionPerformed(ActionEvent e) {
				try {
					generateKeys();
				} catch (NoSuchAlgorithmException e2) {
					e2.printStackTrace();
				}
				if(rdbtnBob.isSelected())
				{
					try {
						generateAndSignBob(textField);					
					} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e1) {
						e1.printStackTrace();
					}
			    }
				else
				{
					try {
						generateAndSignAlice(textField);
					} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnVerify.addActionListener(new ActionListener() {     //event handler for verify button
			public void actionPerformed(ActionEvent e) {
				
				if(!rdbtnBob_1.isSelected() && !rdbtnBob.isSelected()
					&& !rdbtnAlice_1.isSelected() && !rdbtnAlice.isSelected())  //incase someone hits verify without sending message
					return;
				
				if(rdbtnBob_1.isSelected())
				{
					try {
						verifyBobsSignature(textArea);
					} catch (InvalidKeyException | SignatureException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					try {
						verifyAlicesSignature(textArea);
					} catch (InvalidKeyException | SignatureException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});	
	}
	
	/**
	 * Generates Key Pairs for Bob and Alice
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private void generateKeys() throws NoSuchAlgorithmException
	{
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");  //generate pub/priv key pairs
		gen.initialize(512);
		keyPairBob = gen.generateKeyPair();
		keyPairAlice = gen.generateKeyPair();
	}
	
	/**
	 * Generates a signature for Bob and signs the message
	 * with his signature
	 * 
	 * @param textField
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	private void generateAndSignBob(JTextField textField) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		signature = Signature.getInstance("SHA1withRSA");  //Returns a Signature object for Bob that implements the SHA1 with RSA algorithm
		message = textField.getText().toString();
		bytes = message.getBytes();
		
		signature.initSign(keyPairBob.getPrivate());     //Initialize this object for signing as Bob
		signature.update(bytes);
		signedBytes = signature.sign();                //used for displaying signature
	}
	
	/**
	 * Generates a signature for Alice and signs the message
	 * with her signature
	 * 
	 * @param textField
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	private void generateAndSignAlice(JTextField textField) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		signature = Signature.getInstance("SHA1withRSA");  //Returns a Signature object for Alice that implements the SHA1 with RSA algorithm
		message = textField.getText().toString();
		bytes = message.getBytes();
		
		signature.initSign(keyPairAlice.getPrivate());     //Initialize this object for signing as Alice
		signature.update(bytes);
		signedBytes = signature.sign();                //used for displaying signature
		
	}
	
	/**
	 * Checks to see the signature on the message is Bob's 
	 * and prints result to textArea
	 * 
	 * @param textArea
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	private void verifyBobsSignature(JTextArea textArea) throws InvalidKeyException, SignatureException
	{
		signature.initVerify(keyPairBob.getPublic());  //Initializes this object for verification, using the public key from the given certificate
		signature.update(bytes);					   //Updates the data to be signed or verified, using the specified array of bytes
		Base64.Encoder encoder = Base64.getEncoder();
		String str = encoder.encodeToString(signedBytes);  
    	Character[] charObjectArray = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
		
		if(signature.verify(signedBytes))          //if Bob's signature matches the signed data
		{
			textArea.setText("SIGNATURE MATCHES" + "\n");
			textArea.append("\n" + "Signature:" + "\n");
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
		else
		{
			textArea.setText("WARNING SIGNATURE DOES NOT MATCH \n \n");
			textArea.append("Expected Signature: \n");
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
		
	}
	
	/**
	 * Checks to see the signature on the message is Alice's
	 * and prints result to textArea
	 * 
	 * @param textArea
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	private void verifyAlicesSignature(JTextArea textArea) throws InvalidKeyException, SignatureException
	{
		signature.initVerify(keyPairAlice.getPublic());  //Initializes this object for verification, using the public key from the given certificate
		signature.update(bytes);					     //Updates the data to be signed or verified, using the specified array of bytes
		Base64.Encoder encoder = Base64.getEncoder();
		String str = encoder.encodeToString(signedBytes);   
    	Character[] charObjectArray = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
		
		if(signature.verify(signedBytes))          //if Alice's signature matches the signed data
		{
			textArea.setText("SIGNATURE MATCHES" + "\n");
			textArea.append("\n" + "Signature:" + "\n");
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
		else
		{
			textArea.setText("WARNING SIGNATURE DOES NOT MATCH \n \n");
			textArea.append("Expected Signature: \n");
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
		
	}
	
	/**
	 * Default constructor	
	 */
	public DigitalSignature()
	{
		keyPairBob = null;
		keyPairAlice = null;
		signature = null;
		message = "";
	}
}
