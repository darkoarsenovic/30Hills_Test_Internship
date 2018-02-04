package test.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileReader;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import test.person.Friend;
import test.utils.JSONUtils;

import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextArea jtfArea;
	private JPanel panel;
	private JComboBox<Friend> comboBox;
	private JButton btnShowFriends;

	private LinkedList<Friend> friends;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendsGUI frame = new FriendsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FriendsGUI() {
		setTitle("Social Network");
		URL iconURL = getClass().getResource("30Hills_icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getJtfArea(), BorderLayout.CENTER);
		contentPane.add(getPanel(), BorderLayout.WEST);
	}

	private JTextArea getJtfArea() {
		if (jtfArea == null) {
			jtfArea = new JTextArea();
			jtfArea.setEditable(false);
		}
		return jtfArea;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(250, 0));
			panel.setLayout(null);
			panel.add(getComboBox());
			panel.add(getBtnShowFriends());
		}
		return panel;
	}

	private JComboBox<Friend> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<Friend>();
			comboBox.setBounds(10, 11, 230, 32);

			try {
				FileReader reader = new FileReader("assets/data.json");

				Gson gson = new GsonBuilder().create();
				JsonArray data = gson.fromJson(reader, JsonArray.class);

				reader.close();

				friends = JSONUtils.parseOsoba(data);

				for (int i = 0; i < friends.size(); i++) {
					comboBox.addItem(friends.get(i));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return comboBox;
	}

	private JButton getBtnShowFriends() {
		if (btnShowFriends == null) {
			btnShowFriends = new JButton("Show friends");
			btnShowFriends.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					String message = "";

					// first request
					// direct friends
					String message1 = "";
					message1 += "Direct friends: \t\t";

					for (int i = 0; i < friends.size(); i++) {
						if (comboBox.getSelectedItem().toString().equals(friends.get(i).toString())) {
							// loop through my friends IDs
							for (int j = 0; j < friends.get(i).getFriends().size(); j++) {
								// find my friends by their ID
								for (int k = 0; k < friends.size(); k++) {
									if (friends.get(i).getFriends().get(j).getAsInt() == friends.get(k).getId()) {
										message1 += friends.get(k).toString() + "\n\t\t";
									}
								}
							}
						}
					}

					// second request
					// friends of friends
					String message2 = "";
					message2 += "\nFriends of friends: \t";

					for (int i = 0; i < friends.size(); i++) {
						if (comboBox.getSelectedItem().toString().equals(friends.get(i).toString())) {
							// loop through my friends IDs
							for (int j = 0; j < friends.get(i).getFriends().size(); j++) {
								// find my friends by their ID
								for (int k = 0; k < friends.size(); k++) {
									if (friends.get(i).getFriends().get(j).getAsInt() == friends.get(k).getId()) {
										// loop through my friend's friends
										for (int j2 = 0; j2 < friends.get(k).getFriends().size(); j2++) {
											// find my friend's friends
											for (int k2 = 0; k2 < friends.size(); k2++) {
												if (friends.get(k).getFriends().get(j2).getAsInt() == friends.get(k2)
														.getId()) {

													if (!message2.contains(friends.get(k2).toString())
															&& friends.get(i).getId() != friends.get(k2).getId()) {
														boolean found = false;
														int inner = 0;
														while (inner < friends.get(i).getFriends().size()) {
															if (friends.get(i).getFriends().get(inner)
																	.getAsInt() != friends.get(k2).getId()) {
																found = true;
																inner++;
															} else {
																found = false;
																break;
															}
														}
														if (found)
															message2 += friends.get(k2).toString() + "\n\t\t";
													}
												}
											}
										}
									}
								}
							}
						}
					}

					// third request
					// suggested friends
					String message3 = "";
					message3 += "\nSuggested friends: \t";

					for (int i = 0; i < friends.size(); i++) {
						boolean exist = false; // for case it not exist
						if (comboBox.getSelectedItem().toString().equals(friends.get(i).toString())) {
							// loop through friends list
							// exclude me
							for (int j = 0; j < friends.size(); j++) {
								if (friends.get(i).getId() != friends.get(j).getId()) {
									int count = 0;
									boolean areFriends = false;
									// find match in my and friend's friends
									// and count
									for (int k = 0; k < friends.get(i).getFriends().size(); k++) {
										for (int k2 = 0; k2 < friends.get(j).getFriends().size(); k2++) {
											if (friends.get(j).getFriends().get(k2).getAsInt() == friends.get(i)
													.getFriends().get(k).getAsInt()) {
												count++;
												break;
											} else if (friends.get(j).getFriends().get(k2).getAsInt() == friends.get(i)
													.getId()) {
												areFriends = true;
											}
										}
										if (count >= 2 && !areFriends
												&& !message3.contains(friends.get(j).toString())) {
											message3 += friends.get(j).toString() + "\n\t\t";
											exist = true;
										}
									}

								}
							}
							if (!exist) {
								message3 += "/";
							}
						}
					}

					message = message1 + message2 + message3;
					jtfArea.setText(message);

				}
			});
			btnShowFriends.setBounds(10, 348, 230, 23);
		}
		return btnShowFriends;
	}
}
