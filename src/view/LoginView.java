package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.Conexion;
import model.Usuario;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername, lblPassword;
	private JTextField txtUsername;
	private JPasswordField passwd;
	private JButton login, registro, exit;
	private JPanel FormaPanel;

	/**
	 * Create the frame.
	 */
	public LoginView() {
		super("Inicio de sesion");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 420);
		setLocationRelativeTo(null);
		setResizable(false);

		ImageIcon iconoVentana = new ImageIcon("file/TaxiCarga.png");
		setIconImage(iconoVentana.getImage());

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon fondo = new ImageIcon("file/fondo_login.jpg");
				g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setLayout(null);
		setContentPane(contentPane);

		FormaPanel = new JPanel();
		FormaPanel.setLayout(null);
		FormaPanel.setBackground(new Color(255, 255, 255, 210));
		FormaPanel.setBounds(60, 80, 380, 250);
		FormaPanel.setBorder(null);
		FormaPanel.setOpaque(false);
		contentPane.add(FormaPanel);

		lblUsername = new JLabel("Usuario : ");
		lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblUsername.setBounds(40, 20, 100, 25);
		lblUsername.setOpaque(true);
		lblUsername.setBackground(new Color(255, 255, 255, 200));
		lblUsername.setForeground(Color.BLACK);
		FormaPanel.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsername.setBounds(150, 20, 180, 25);
		FormaPanel.add(txtUsername);

		lblPassword = new JLabel("Contraseña : ");
		lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblPassword.setBounds(40, 70, 100, 25);
		lblPassword.setOpaque(true);
		lblPassword.setBackground(new Color(255, 255, 255, 200));
		lblPassword.setForeground(Color.BLACK);
		FormaPanel.add(lblPassword);

		passwd = new JPasswordField(30);
		passwd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwd.setBounds(150, 70, 180, 25);
		FormaPanel.add(passwd);

		login = new JButton("Iniciar Sesion");
		login.setFont(new Font("Segoe UI", Font.BOLD, 14));
		login.setBackground(Color.BLUE);
		login.setForeground(Color.WHITE);
		login.setBounds(40, 130, 140, 35);
		login.setFocusPainted(false);
		FormaPanel.add(login);

		registro = new JButton("Registrarse");
		registro.setFont(new Font("Segoe UI", Font.BOLD, 14));
		registro.setBackground(Color.GREEN);
		registro.setForeground(Color.WHITE);
		registro.setBounds(190, 130, 140, 35);
		registro.setFocusPainted(false);
		FormaPanel.add(registro);

		ImageIcon img = new ImageIcon("file/salir.png");
		exit = new JButton(img);
		exit.setBounds(460, 320, 32, 32);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit.setOpaque(false);
		contentPane.add(exit);

		ManejadorEventos m = new ManejadorEventos();
		login.addActionListener(m);
		registro.addActionListener(m);
		exit.addActionListener(m);

		setVisible(true);
	}

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == login) {
				if (txtUsername.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo username no puede estar vacío", "Error username",
							JOptionPane.ERROR_MESSAGE);
				} else if (passwd.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "El campo password no puede estar vacío", "Error password",
							JOptionPane.ERROR_MESSAGE);
				}

				else {
					PreparedStatement consulta;
					try {
						consulta = Conexion.obtener()
								.prepareStatement("SELECT id,nombre,apellido, password, username, rol, email "
										+ " FROM Usuarios " + " WHERE username= ? AND password= ?");
						consulta.setString(1, txtUsername.getText());
						consulta.setString(2, new String(passwd.getPassword()));
						ResultSet resultado = consulta.executeQuery();

						resultado.next();

						Usuario u = new Usuario(resultado.getInt("id"), resultado.getString("nombre"),
								resultado.getString("apellido"), resultado.getString("password"),
								resultado.getString("username"), resultado.getString("rol"),
								resultado.getString("email"));

						if (u.getRol().equalsIgnoreCase("ADMIN")) {
							dispose();
							new AdminView(u);
						} else if (u.getRol().equalsIgnoreCase("TAXISTA")) {
							dispose();
							new TaxistaView(u);
						} else if (u.getRol().equalsIgnoreCase("MECANICO")) {
							dispose();
							new MecanicoView(u);
						}

					} catch (SQLException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Datos de inicio de sesión incorrectos", "Login fallido",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			if (o == registro) {
				dispose();
				new UserRegistrationView();
			}

			if (o == exit) {
				System.exit(ABORT);
			}

		}

	}

}
