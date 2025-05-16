package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

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
	private static final VehiculoController services = new VehiculoController();

	public VehiculoRegistratioView(Usuario u) {
		super("Registro de vehículo");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblMatricula = new JLabel("Matrícula");
		lblMatricula.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMatricula.setBounds(26, 8, 96, 25);
		lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModelo.setBounds(26, 78, 63, 30);
		lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMarca.setBounds(26, 46, 63, 19);

		txtMatricula = new JTextField();
		txtMatricula.setBounds(132, 11, 82, 19);
		txtModelo = new JTextField();
		txtModelo.setBounds(132, 87, 82, 19);
		txtMarca = new JTextField();
		txtMarca.setBounds(132, 49, 82, 19);

		addVehiculo = new JButton("Añadir");
		addVehiculo.setBounds(26, 153, 96, 30);
		cancelar = new JButton("Cancelar");
		cancelar.setBounds(132, 153, 82, 30);

		ManejadorEventos m = new ManejadorEventos();
		addVehiculo.addActionListener(m);
		cancelar.addActionListener(m);

		txtMatricula.addKeyListener(m);
		txtMarca.addKeyListener(m);
		txtModelo.addKeyListener(m);

		contentPane.setLayout(null);

		contentPane.add(lblMatricula);
		contentPane.add(lblMarca);
		contentPane.add(lblModelo);
		contentPane.add(txtMatricula);
		contentPane.add(txtMarca);
		contentPane.add(txtModelo);
		contentPane.add(addVehiculo);
		contentPane.add(cancelar);

		setContentPane(contentPane);
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
						Vehiculo v = new Vehiculo(usuarioActivo.getId(), txtMatricula.getText(), txtModelo.getText(),
								txtMarca.getText(), 100, false);

						try {
							services.save(Conexion.obtener(), v);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
						new GestionarVehiculosView(usuarioActivo);
						JOptionPane.showMessageDialog(null, "Vehículo registrado con éxito", "Vehículo registrado",
								JOptionPane.INFORMATION_MESSAGE);
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
