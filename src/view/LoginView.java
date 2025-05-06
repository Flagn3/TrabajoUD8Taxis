package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername, lblPassword;
	private JTextField txtUsername;
	private JPasswordField passwd;
	private JButton login, registro;

	/**
	 * Create the frame.
	 */
	public LoginView() {
		super("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 352);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setBounds(155, 37, 112, 40);
		txtUsername = new JTextField();
		txtUsername.setBounds(155, 87, 112, 19);
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setBounds(155, 116, 82, 40);
		passwd = new JPasswordField();
		passwd.setBounds(155, 156, 112, 19);
		login = new JButton("Login");
		login.setForeground(Color.BLACK);
		login.setBackground(new Color(0, 128, 255));
		login.setBounds(105, 215, 100, 34);
		registro = new JButton("Registro");
		registro.setBounds(253, 215, 100, 34);
			
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(lblUsername);
		contentPane.add(txtUsername);
		contentPane.add(lblPassword);
		contentPane.add(passwd);
		contentPane.add(login);
		contentPane.add(registro);
		
		setVisible(true);
	}

}
