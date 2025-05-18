package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.Conexion;
import control.VehiculoController;
import model.Usuario;
import model.Vehiculo;

public class VehiculoRegistratioView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioActivo;
	private JLabel lblMatricula, lblModelo, lblMarca;
	private JTextField txtMatricula, txtModelo, txtMarca;
	private JButton addVehiculo, cancelar;
	private static final VehiculoController controller = new VehiculoController();
	private static List<Vehiculo> vehiculos;

	public VehiculoRegistratioView(Usuario u) {
		this.usuarioActivo = u;

		setTitle("Registro de Vehiculos");
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

		JLabel title = new JLabel("Registro de vehiculos");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setBounds(100, 10, 250, 30);
		contentPane.add(title);

		lblMatricula = new JLabel("Matrícula");
		lblMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblMatricula.setBounds(30, 50, 80, 20);

		lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblModelo.setBounds(30, 90, 80, 20);

		lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblMarca.setBounds(30, 130, 80, 20);

		txtMatricula = new JTextField();
		txtMatricula.setBounds(120, 50, 200, 25);
		txtMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		txtModelo = new JTextField();
		txtModelo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtModelo.setBounds(120, 90, 200, 25);

		txtMarca = new JTextField();
		txtMarca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtMarca.setBounds(120, 130, 200, 25);

		addVehiculo = new JButton("Añadir");
		addVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		addVehiculo.setForeground(Color.WHITE);
		addVehiculo.setBackground(new Color(40, 167, 69));
		addVehiculo.setFocusPainted(false);
		addVehiculo.setBorderPainted(false);
		addVehiculo.setBounds(60, 320, 120, 35);

		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelar.setForeground(Color.WHITE);
		cancelar.setBackground(new Color(220, 53, 69));
		cancelar.setFocusPainted(false);
		cancelar.setBorderPainted(false);
		cancelar.setBounds(200, 320, 120, 35);

		ManejadorEventos m = new ManejadorEventos();
		addVehiculo.addActionListener(m);
		cancelar.addActionListener(m);

		txtMatricula.addKeyListener(m);
		txtMarca.addKeyListener(m);
		txtModelo.addKeyListener(m);

		contentPane.add(lblMatricula);
		contentPane.add(lblMarca);
		contentPane.add(lblModelo);
		contentPane.add(txtMatricula);
		contentPane.add(txtMarca);
		contentPane.add(txtModelo);
		contentPane.add(addVehiculo);
		contentPane.add(cancelar);

		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class ManejadorEventos implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();

			if (o == cancelar) {
				dispose();
				new GestionarVehiculosView(usuarioActivo);
			} else {
				if (!txtMatricula.getText().isEmpty() && !txtModelo.getText().isEmpty()
						&& !txtMarca.getText().isEmpty()) {

					if (txtMatricula.getText().matches("[0-9]{4}[-][^aeiouAEIOUa-z]{3}")) {
						if (!checkMatriculaExiste()) {
							Vehiculo v = new Vehiculo(usuarioActivo.getId(), txtMatricula.getText(),
									txtModelo.getText(), txtMarca.getText(), 100, false);

							try {
								controller.save(Conexion.obtener(), v);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "Ha surgido un error al registrar el vehiculo");
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "Ha surgido un error al registrar el vehiculo");
							}
							dispose();
							new GestionarVehiculosView(usuarioActivo);
							JOptionPane.showMessageDialog(null, "Vehículo registrado con éxito", "Vehículo registrado",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "La matrícula introducida ya existe", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Formato de la matricula incorrecto 1111-BBB", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Hay campos que están vacios", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}

		private boolean checkMatriculaExiste() {

			try {
				vehiculos = controller.getAllVehiculos(Conexion.obtener());

				for (Vehiculo v : vehiculos) {
					if (v.getMatricula().equalsIgnoreCase(txtMatricula.getText())) {
						return true;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
			}

			return false;
		}

		@Override
		public void keyTyped(KeyEvent e) {

			if (txtMatricula.getText().length() >= 15) {
				e.consume();
			}
			if (txtMarca.getText().length() >= 30) {
				e.consume();
			}
			if (txtModelo.getText().length() >= 30) {
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
