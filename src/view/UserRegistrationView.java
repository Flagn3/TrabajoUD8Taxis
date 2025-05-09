package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Conexion;
import control.UserController;
import model.Usuario;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

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
	private final UserController services = new UserController();

	/**
	 * Create the frame.
	 */
	public UserRegistrationView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 365);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(50, 255, 90, 30);
		panel.add(btnConfirmar);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(185, 255, 90, 30);
		panel.add(btnVolver);

		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setBounds(50, 52, 80, 13);
		panel.add(lblUsername);

		textUsername = new JTextField();
		textUsername.setBounds(147, 49, 128, 19);
		panel.add(textUsername);
		textUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(50, 81, 80, 13);
		panel.add(lblPassword);

		textPassword = new JTextField();
		textPassword.setBounds(147, 78, 128, 19);
		panel.add(textPassword);
		textPassword.setColumns(10);

		JLabel lblRol = new JLabel("Rol : ");
		lblRol.setBounds(50, 111, 45, 13);
		panel.add(lblRol);

		rdbtnMecanico = new JRadioButton("Mecanico");
		rdbtnMecanico.setBounds(147, 126, 103, 21);
		panel.add(rdbtnMecanico);

		rdbtnTaxista = new JRadioButton("Taxista");
		rdbtnTaxista.setBounds(147, 103, 103, 21);
		panel.add(rdbtnTaxista);

		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(rdbtnMecanico);
		grupo1.add(rdbtnTaxista);

		JLabel lblNombre = new JLabel("Nombre : ");
		lblNombre.setBounds(50, 156, 80, 13);
		panel.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setBounds(147, 153, 128, 19);
		panel.add(textNombre);
		textNombre.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido :");
		lblApellido.setBounds(50, 185, 80, 13);
		panel.add(lblApellido);

		textApellido = new JTextField();
		textApellido.setBounds(147, 182, 128, 19);
		panel.add(textApellido);
		textApellido.setColumns(10);

		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setBounds(50, 214, 80, 13);
		panel.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setBounds(147, 211, 128, 19);
		panel.add(textEmail);
		textEmail.setColumns(10);

		ManejadorEventos me = new ManejadorEventos();
		btnConfirmar.addActionListener(me);
		btnVolver.addActionListener(me);
		textNombre.addKeyListener(me);
		textApellido.addKeyListener(me);
		textUsername.addKeyListener(me);
		textPassword.addKeyListener(me);
		textEmail.addKeyListener(me);

	}

	public class ManejadorEventos implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object o = e.getSource();

			if (o == btnVolver) {
				dispose();
				new LoginView();
			}

			if (o == btnConfirmar) {
				if (!textUsername.getText().isEmpty() && !textPassword.getText().isEmpty()
						&& !textNombre.getText().isEmpty() && !textApellido.getText().isEmpty()
						&& !textEmail.getText().isEmpty()) {
					if (rdbtnMecanico.isSelected() || rdbtnTaxista.isSelected()) {
						try {
							Usuario user = new Usuario(textNombre.getText(), textApellido.getText(),
									textPassword.getText(), textUsername.getText(),
									rdbtnMecanico.isSelected() ? "mecanico" : "taxista", textEmail.getText());
							services.save(Conexion.obtener(), user);
							dispose();
							new LoginView();
							JOptionPane.showMessageDialog(null, "Usuario añadido con exito");
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showConfirmDialog(null, "No esta seleccionado el campo ROL");
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Faltan campos por rellenar");
				}
			}
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
			if (textEmail.getText().length() >= 30) {
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
