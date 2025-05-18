package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.Conexion;
import control.UserController;
import model.Usuario;

public class UserRegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsername;
	private JTextField textPassword;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textEmail;
	private JRadioButton rdbtnMecanico, rdbtnTaxista;
	private JButton btnConfirmar, btnVolver;
	private final UserController controller = new UserController();
	private static Usuario nuevoUsuario;
	private static Usuario usuarioActivo;
	private static List<Usuario> usuarios;

	/**
	 * Create the frame.
	 */
	public UserRegistrationView() {

		nuevoUsuario = new Usuario();
		iniciarComponentes();

	}

	public UserRegistrationView(Usuario u, Usuario activo) {

		this.usuarioActivo = activo;
		this.nuevoUsuario = u;
		iniciarComponentes();
		textNombre.setText(u.getNombre());
		textApellido.setText(u.getApellido());
		textPassword.setText(u.getPassword());
		textUsername.setText(u.getUsername());
		textEmail.setText(u.getEmail());
		if (u.getRol().equalsIgnoreCase("TAXISTA")) {
			rdbtnTaxista.setSelected(true);
		} else if (u.getRol().equalsIgnoreCase("MECANICO")) {
			rdbtnMecanico.setSelected(true);
		}

	}

	private void iniciarComponentes() {
		setTitle("Registro de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 450);
		setLocationRelativeTo(null);
		setResizable(false);

		ImageIcon iconoVentana = new ImageIcon("file/TaxiCarga.png");
		setIconImage(iconoVentana.getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 245, 245));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("Registro de Usuario");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setBounds(100, 10, 250, 30);
		contentPane.add(title);

		JLabel lblnombre = new JLabel("Nombre:");
		lblnombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblnombre.setBounds(30, 50, 80, 20);
		contentPane.add(lblnombre);

		textNombre = new JTextField();
		textNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textNombre.setBounds(120, 50, 200, 25);
		contentPane.add(textNombre);

		JLabel lblapellido = new JLabel("Apellido:");
		lblapellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblapellido.setBounds(30, 90, 80, 20);
		contentPane.add(lblapellido);

		textApellido = new JTextField();
		textApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textApellido.setBounds(120, 90, 200, 25);
		contentPane.add(textApellido);

		JLabel lblusername = new JLabel("Usuario:");
		lblusername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblusername.setBounds(30, 130, 80, 20);
		contentPane.add(lblusername);

		textUsername = new JTextField();
		textUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textUsername.setBounds(120, 130, 200, 25);
		contentPane.add(textUsername);

		JLabel lblpassword = new JLabel("Contraseña:");
		lblpassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblpassword.setBounds(30, 170, 80, 20);
		contentPane.add(lblpassword);

		textPassword = new JTextField();
		textPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textPassword.setBounds(120, 170, 200, 25);
		contentPane.add(textPassword);

		JLabel lblemail = new JLabel("Email:");
		lblemail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblemail.setBounds(30, 210, 80, 20);
		contentPane.add(lblemail);

		textEmail = new JTextField();
		textEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textEmail.setBounds(120, 210, 200, 25);
		contentPane.add(textEmail);

		JLabel lblRol = new JLabel("Rol:");
		lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblRol.setBounds(30, 250, 80, 20);
		contentPane.add(lblRol);

		rdbtnTaxista = new JRadioButton("Taxista");
		rdbtnTaxista.setBounds(120, 250, 90, 20);
		rdbtnTaxista.setBackground(new Color(245, 245, 245));
		contentPane.add(rdbtnTaxista);

		rdbtnMecanico = new JRadioButton("Mecánico");
		rdbtnMecanico.setBounds(210, 250, 100, 20);
		rdbtnMecanico.setBackground(new Color(245, 245, 245));
		contentPane.add(rdbtnMecanico);

		ButtonGroup grupoRol = new ButtonGroup();
		grupoRol.add(rdbtnMecanico);
		grupoRol.add(rdbtnTaxista);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setBackground(new Color(40, 167, 69));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.setBorderPainted(false);
		btnConfirmar.setBounds(60, 300, 120, 35);
		contentPane.add(btnConfirmar);

		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(220, 53, 69));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setBounds(200, 300, 120, 35);
		contentPane.add(btnVolver);

		ManejadorEventos me = new ManejadorEventos();
		btnConfirmar.addActionListener(me);
		btnVolver.addActionListener(me);

		textNombre.addKeyListener(me);
		textApellido.addKeyListener(me);
		textUsername.addKeyListener(me);
		textPassword.addKeyListener(me);
		textEmail.addKeyListener(me);

		setVisible(true);
	}

	public class ManejadorEventos implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object o = e.getSource();

			if (o == btnVolver) {

				if (nuevoUsuario.getId() == null) {
					dispose();
					new LoginView();
				} else {
					dispose();
					new AdminView(usuarioActivo);
				}
			}

			if (o == btnConfirmar) {
				if (!textUsername.getText().isEmpty() && !textPassword.getText().isEmpty()
						&& !textNombre.getText().isEmpty() && !textApellido.getText().isEmpty()
						&& !textEmail.getText().isEmpty()) {
					if (!checkUsuarioExiste()) {
						if (textEmail.getText().matches("[a-zA-Z]+@(mecanico|taxista)\\.com")) {
							if (rdbtnMecanico.isSelected() || rdbtnTaxista.isSelected()) {
								try {

									nuevoUsuario.setNombre(textNombre.getText());
									nuevoUsuario.setApellido(textApellido.getText());
									nuevoUsuario.setPassword(textPassword.getText());
									nuevoUsuario.setUsername(textUsername.getText());
									nuevoUsuario.setEmail(textEmail.getText());
									nuevoUsuario.setRol(rdbtnMecanico.isSelected() ? "mecanico" : "taxista");
									controller.save(Conexion.obtener(), nuevoUsuario);
									if (nuevoUsuario.getId() == null) {
										dispose();
										new LoginView();
										JOptionPane.showMessageDialog(null, "Usuario añadido con exito");
									} else {
										dispose();
										new AdminView(usuarioActivo);
										JOptionPane.showMessageDialog(null, "Usuario editado con exito");
									}
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Ha surgido un error al registrar el usuario");
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Ha surgido un error al registrar el usuario");
								}
							} else {
								JOptionPane.showMessageDialog(null, "No esta seleccionado el campo ROL");
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Email incorrecto, nombre@mecanico.com o nombre@taxista.com", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "El username elegido ya existe", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Faltan campos por rellenar");
				}
			}
		}

		private boolean checkUsuarioExiste() {

			try {
				usuarios = controller.getAllUsers(Conexion.obtener());

				for (Usuario u : usuarios) {
					if (u.getUsername().equalsIgnoreCase(textUsername.getText())) {
						return true;
					}
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"Ha surgido un error y no se ha podido conectar con la base de datos");
			}

			return false;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			if (textNombre.getText().length() >= 30) {
				e.consume();
			}
			if (textApellido.getText().length() >= 30) {
				e.consume();
			}
			if (textUsername.getText().length() >= 30) {
				e.consume();
			}
			if (textPassword.getText().length() >= 30) {
				e.consume();
			}
			if (textEmail.getText().length() >= 50) {
				e.consume();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
