package edu.odu.cs.cs463.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Generates a public/private key pair
 * 
 * @author Matthew King
 *
 */

public class GenerateKeys {

	 Key pub;     //public key
	 Key pvt;     //private key

	public void initialize(JFrame frame) {
		
		
		////////////Generate GUI ////////////
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(685, 13, 97, 25);
		frame.getContentPane().add(btnMainMenu);
		

		JLabel lblGenerateANew = new JLabel("Generate a new key pair");
		lblGenerateANew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGenerateANew.setBounds(12, 50, 189, 34);
		frame.getContentPane().add(lblGenerateANew);
		

		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 227, 361, 294);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(409, 227, 361, 294);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblPublicKey = new JLabel("Public Key");
		lblPublicKey.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPublicKey.setBounds(132, 198, 87, 16);
		frame.getContentPane().add(lblPublicKey);
		
		JLabel lblPrivateKey = new JLabel("Private Key");
		lblPrivateKey.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrivateKey.setBounds(542, 198, 97, 16);
		frame.getContentPane().add(lblPrivateKey);
		
		JButton btnClick = new JButton("Generate");
		btnClick.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClick.setBounds(213, 50, 97, 31);
		frame.getContentPane().add(btnClick);
		
		//////////// Event Handlers ////////////
		
		
		btnMainMenu.addActionListener(new ActionListener() {   //Event handler for MainMenu
			public void actionPerformed(ActionEvent e) {
			MainMenu menu = new MainMenu();
			menu.mainMenuReceive(frame);
			}
		});
		
		btnClick.addActionListener(new ActionListener() {		//Event handler for Generate
			public void actionPerformed(ActionEvent e) {
				try {
					genKeyPair();                               //Generate the key pair
					printPublic(textArea);						//Print public key
					printPrivate(textArea_1);					//Print private key
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}	
			}
		});
	}
   
	
    /**
     * Generates a new public/private key pair
     * @throws NoSuchAlgorithmException
     */
    private void genKeyPair() throws NoSuchAlgorithmException
    {
    	KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");  //create new generator
    	kpg.initialize(512);										 //use RSA 512bit
    	KeyPair kp = kpg.generateKeyPair();            //generate key pair
    	pub = kp.getPublic();				//assign public key
    	pvt = kp.getPrivate();				//assign private key
    }
    
    /**
     * Prints the public key to TextArea
     * @param TextArea
     */
    private void printPublic(JTextArea TextArea)
    {
    	TextArea.setText("------------------RSA PUBLIC KEY BEGIN------------------" + "\n");						  
    	Base64.Encoder encoder = Base64.getEncoder();   //used for converting to base64 for text printing of key
    	
    	String str = encoder.encodeToString(pvt.getEncoded());  //convert a string to char array to easily print newlines in TextArea 
    	Character[] charObjectArray = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
    	
    	for(int i = 0; i < charObjectArray.length; i++)
    	{
    		if( i%45 == 0  && i != 0)			
    		{
    			TextArea.append(charObjectArray[i].toString() + "\n");   //new line every 45 characters
    		}
    		else
    		{
    			TextArea.append(charObjectArray[i].toString());
    		}
    	}
	
    	TextArea.append("\n" + "------------------RSA PUBLIC KEY END------------------");
    }
    
    /**
     * Prints the private key to TextArea_1
     * @param TextArea_1
     */
    private void printPrivate(JTextArea TextArea_1)
    {
    	TextArea_1.setText("------------------RSA PRIVATE KEY BEGIN------------------" + "\n");		
    	Base64.Encoder encoder = Base64.getEncoder();  //used for converting to base64 for text printing of key
    	
    	String str = encoder.encodeToString(pvt.getEncoded());  //convert a string to char array to easily print newlines in TextArea 
    	Character[] charObjectArray = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
    	
    	for(int i = 0; i < charObjectArray.length; i++)
    	{
    		if( i%45 == 0  && i != 0)			
    		{
    			TextArea_1.append(charObjectArray[i].toString() + "\n");  //new line every 45 characters
    		}
    		else
    		{
    			TextArea_1.append(charObjectArray[i].toString());
    		}
    	}
    	
    	TextArea_1.append("\n" + "------------------RSA PRIVATE KEY END------------------");
    }
    
    /**
     * Default constructor
     */
    public GenerateKeys()
    {
    	pub = null;     //public key
    	pvt = null;     //private key
    }
    
}
