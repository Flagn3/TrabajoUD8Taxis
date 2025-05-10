package view;

import java.awt.Font;
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

		ImageIcon imagen = new ImageIcon("file/TaxiCarga.png");
		setIconImage(imagen.getImage());

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setBounds(155, 37, 112, 40);

		txtUsername = new JTextField(30);
		txtUsername.setBounds(155, 87, 112, 19);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setBounds(155, 116, 82, 40);

		passwd = new JPasswordField(30);
		passwd.setBounds(155, 156, 112, 19);

		login = new JButton("Login");
		login.setBounds(105, 215, 100, 34);

		registro = new JButton("Registro");
		registro.setBounds(253, 215, 100, 34);

		ImageIcon img = new ImageIcon("file/salir.png");
		exit = new JButton(img);
		exit.setBounds(415, 265, 40, 40);

		ManejadorEventos m = new ManejadorEventos();
		login.addActionListener(m);
		registro.addActionListener(m);
		exit.addActionListener(m);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(lblUsername);
		contentPane.add(txtUsername);
		contentPane.add(lblPassword);
		contentPane.add(passwd);
		contentPane.add(login);
		contentPane.add(registro);
		contentPane.add(exit);

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
							// new MecanicoView(u);
						}

					} catch (SQLException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Datos de inicio de sesión incorrectos", "Login fallido",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				// prueba
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
