package edu.odu.cs.cs463.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The main menu that contains buttons that redirects
 * the user to the corresponding program
 * 
 * @author Matthew King
 */
public class MainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		///////////// Create GUI ////////////
		frame = new JFrame();
		frame.setBounds(100, 100, 800 , 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Generate Public/Private Key Pair");
		btnNewButton.setBounds(288, 82, 305, 74);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Generate Shared Key Using Password");
		btnNewButton_1.setBounds(288, 169, 305, 74);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Encrypt/Decrypt Text Using Public/Shared Key");
		btnNewButton_2.setBounds(287, 256, 306, 74);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Generate/Verify a Digital Signature");
		btnNewButton_3.setBounds(288, 343, 305, 74);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Generate MAC Using Shared Key");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setBounds(288, 430, 305, 74);
		frame.getContentPane().add(btnNewButton_4);
		
		//////////// Event Handlers for buttons ////////////
		
		btnNewButton.addActionListener(new ActionListener() {  //Event handler for GenerateKeys button
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				GenerateKeys gk = new GenerateKeys();
				gk.initialize(frame);
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {  //Event handler for PasswordAndKey button
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				PasswordAndKey pk = new PasswordAndKey();
				pk.initialize(frame);
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {  //Event handler for Encrypt/DecryptText button
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				EncryptDecryptText edt = new EncryptDecryptText();
				edt.initialize(frame);
			}
		});
	
		btnNewButton_3.addActionListener(new ActionListener() {  //Event handler for DigitalSignature button
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				DigitalSignature ds = new DigitalSignature();
				ds.initialize(frame);
			}
		});
	
	btnNewButton_4.addActionListener(new ActionListener() {   //Event handler for GenerateMac button
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				GenerateMac gm = new GenerateMac();
				gm.initialize(frame);
			}
		});
	
	}
	
	/**
	 * This initialize is used when returning to main menu from a subprogram
	 * 
	 * @param frame
	 */
	private void initialize(JFrame frame) {
		
		JButton btnNewButton = new JButton("Generate Public/Private Key Pair");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				GenerateKeys gk = new GenerateKeys();
				gk.initialize(frame);
			}
		});
		btnNewButton.setBounds(288, 82, 305, 74);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Generate Shared Key Using Password");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				PasswordAndKey pk = new PasswordAndKey();
				pk.initialize(frame);
			}
		});
		btnNewButton_1.setBounds(288, 169, 305, 74);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Encrypt/Decrypt Text Using Public/Shared Key");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				EncryptDecryptText edt = new EncryptDecryptText();
				edt.initialize(frame);
			}
		});
		btnNewButton_2.setBounds(287, 256, 306, 74);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Generate/Verify a Digital Signature");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				DigitalSignature ds = new DigitalSignature();
				ds.initialize(frame);
			}
		});
		btnNewButton_3.setBounds(288, 343, 305, 74);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Generate MAC Using Shared Key");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.revalidate();
				frame.repaint();
				GenerateMac gm = new GenerateMac();
				gm.initialize(frame);
			}
		});
		btnNewButton_4.setBounds(288, 430, 305, 74);
		frame.getContentPane().add(btnNewButton_4);
	}

	/**
	 * Wipes the contents of the frame after it has been reinitialized
	 * 
	 * @param frame
	 */
	public void mainMenuReceive(JFrame frame)
	{
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		initialize(frame);
	}
}
