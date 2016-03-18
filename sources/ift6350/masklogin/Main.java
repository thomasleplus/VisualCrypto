/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package ift6350.masklogin;

import ift6350.libcrypto.MaskChallengeOracle;
import ift6350.libcrypto.MaskChallenge;
import ift6350.libcrypto.MaskKey;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

public final class Main {
	
	private static final String triangleString = "Triangle";
	private static final String squareString   = "Carr�";
	private static final String pentagonString = "Pentagone";
	private static final String hexagonString  = "Hexagone";
	private static final String crossString    = "Croix";
	private static final String starString     = "�toile";
	private static final String circleString   = "Cercle";
	
	private static JFrame mainFrame;
	private static JTextField loginField;
	private static JButton loginButton;
	private static BufferedImage bmpImage;
	private static JRadioButton triangleButton;
	private static JRadioButton squareButton;
	private static JRadioButton pentagonButton;
	private static JRadioButton hexagonButton;
	private static JRadioButton crossButton;
	private static JRadioButton starButton;
	private static JRadioButton circleButton;
	private static JButton okButton;
	private static JButton cancelButton;
	
	private static MaskChallengeOracle oracle;
	private static MaskChallenge challenge;
	
	public static void main(String[] args) {
		
		mainFrame = new JFrame("MaskLogin");
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		
		loginField = new JTextField(50);
		
		loginButton = new JButton("Continuer");
		loginButton.addActionListener(new showMask());
		
        JPanel idPanel = new JPanel();
		TitledBorder idBorder = new TitledBorder(" Entrez votre identifiant : ");
		idPanel.setBorder(idBorder);
		idPanel.add(loginField);
		idPanel.add(loginButton);
		mainFrame.getContentPane().add(idPanel);
		
		JPanel bmpPanel = new JPanel();
		bmpImage = new BufferedImage(600, 400, BufferedImage.TYPE_BYTE_GRAY);
		clear(bmpImage);
		JLabel bmpLabel = new JLabel(new ImageIcon(bmpImage));
		bmpPanel.add(bmpLabel);
		mainFrame.getContentPane().add(bmpPanel);
		
		ButtonGroup group = new ButtonGroup();
        triangleButton = new JRadioButton(triangleString);
        squareButton = new JRadioButton(squareString);
        pentagonButton = new JRadioButton(pentagonString);
        hexagonButton = new JRadioButton(hexagonString);
        crossButton = new JRadioButton(crossString);
        starButton = new JRadioButton(starString);
        circleButton = new JRadioButton(circleString);
        group.add(triangleButton);
        group.add(squareButton);
        group.add(pentagonButton);
        group.add(hexagonButton);
        group.add(crossButton);
        group.add(starButton);
        group.add(circleButton);
		
		okButton = new JButton("Valider");
		okButton.addActionListener(new validateMask());
		
		cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new cancelMask());
		
        JPanel radioPanel = new JPanel();
		TitledBorder radioBorder = new TitledBorder(" S�lectionnez la figure manquante : ");
		radioPanel.setBorder(radioBorder);
        radioPanel.add(triangleButton);
        radioPanel.add(squareButton);
        radioPanel.add(pentagonButton);
        radioPanel.add(hexagonButton);
        radioPanel.add(crossButton);
        radioPanel.add(starButton);
        radioPanel.add(circleButton);
		radioPanel.add(okButton);
		radioPanel.add(cancelButton);
		mainFrame.getContentPane().add(radioPanel);
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		modeID();
		
        mainFrame.pack();
        mainFrame.setVisible(true);
		
		oracle = new MaskChallengeOracle();
		challenge = null;
		
	}
	
	public static void modeID() {
		loginField.setEnabled(true);
		loginButton.setEnabled(true);
		triangleButton.setEnabled(false);
		squareButton.setEnabled(false);
		pentagonButton.setEnabled(false);
		hexagonButton.setEnabled(false);
		crossButton.setEnabled(false);
		starButton.setEnabled(false);
		circleButton.setEnabled(false);
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}
	
	public static boolean checkID(String id) {
		try {
			InputStream is = new FileInputStream(id + ".key");
			MaskKey key = new MaskKey(is);
			is.close();
			challenge = oracle.generateChallenge(key);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			challenge.print(bmpImage);
			return true;
		} catch (Exception e) {
			challenge = null;
			clear(bmpImage);
			return false;
		}
	}
	
	public static void modeMask() {
		loginField.setText("");
		loginField.setEnabled(false);
		loginButton.setEnabled(false);
		triangleButton.setEnabled(true);
		squareButton.setEnabled(true);
		pentagonButton.setEnabled(true);
		hexagonButton.setEnabled(true);
		crossButton.setEnabled(true);
		starButton.setEnabled(true);
		circleButton.setEnabled(true);
		okButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}
	
	public static boolean checkMask() {
		if (triangleButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.TRIANGLE);
		if (squareButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.SQUARE);
		if (pentagonButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.PENTAGON);
		if (hexagonButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.HEXAGON);
		if (crossButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.CROSS);
		if (starButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.STAR);
		if (circleButton.isSelected())
			return oracle.verifyChallenge(challenge, MaskChallengeOracle.CIRCLE);
		return false;
	}
	
	public static void clear(BufferedImage image) {
		for (int i = image.getMinX(); i < image.getMinX() + image.getWidth(); i++)
			for (int j = image.getMinY(); j < image.getMinY() + image.getHeight(); j++)
				image.setRGB(i, j, 0);
	}
	
	private static final class showMask
		implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String id = loginField.getText();
			if (id.length() == 0)
				return;
			if (checkID(id))
				modeMask();
			else {
				loginField.setText("");
				JOptionPane.showMessageDialog(mainFrame,
											  "Identifiant inconnu.",
											  "Erreur",
											  JOptionPane.ERROR_MESSAGE);
			}
			mainFrame.repaint();
		}
		
	}
	
	private static final class validateMask
		implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (triangleButton.isSelected()
				|| squareButton.isSelected()
				|| pentagonButton.isSelected()
				|| hexagonButton.isSelected()
				|| crossButton.isSelected()
				|| starButton.isSelected()
				|| circleButton.isSelected()) {
				if (checkMask())
					JOptionPane.showMessageDialog(mainFrame,
												  "Challenge r�ussi.",
												  "Message",
												  JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(mainFrame,
												  "Challenge �chou�.",
												  "Message",
												  JOptionPane.INFORMATION_MESSAGE);
				challenge = null;
				clear(bmpImage);
				modeID();
				mainFrame.repaint();
			}
		}
		
	}
	
	private static final class cancelMask
		implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			challenge = null;
			clear(bmpImage);
			modeID();
			mainFrame.repaint();
		}
		
	}
	
}
