package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

public class EditVehiculoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Vehiculo vehiculoSeleccionado;
	private JLabel lblMatricula, lblModelo, lblMarca;
	private JTextField txtMatricula, txtModelo, txtMarca;
	private JButton guardar, volver;
	private Usuario usuarioActivo;
	private final VehiculoController controller = new VehiculoController();

	public EditVehiculoView(Vehiculo v, Usuario u) {
		this.vehiculoSeleccionado = v;
		this.usuarioActivo = u;

		setTitle("Edicion de vehiculos");
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

		JLabel title = new JLabel("Edicion de vehiculos");
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

		txtMatricula.setText(vehiculoSeleccionado.getMatricula());
		txtMarca.setText(vehiculoSeleccionado.getMarca());
		txtModelo.setText(vehiculoSeleccionado.getModelo());

		guardar = new JButton("Guardar");
		guardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		guardar.setForeground(Color.WHITE);
		guardar.setBackground(new Color(40, 167, 69));
		guardar.setFocusPainted(false);
		guardar.setBorderPainted(false);
		guardar.setBounds(60, 320, 120, 35);

		volver = new JButton("Volver");
		volver.setFont(new Font("Segoe UI", Font.BOLD, 14));
		volver.setForeground(Color.WHITE);
		volver.setBackground(new Color(220, 53, 69));
		volver.setFocusPainted(false);
		volver.setBorderPainted(false);
		volver.setBounds(200, 320, 120, 35);

		contentPane.add(lblMatricula);
		contentPane.add(lblMarca);
		contentPane.add(lblModelo);
		contentPane.add(txtMatricula);
		contentPane.add(txtMarca);
		contentPane.add(txtModelo);
		contentPane.add(guardar);
		contentPane.add(volver);

		ManejadorEventos m = new ManejadorEventos();
		guardar.addActionListener(m);
		volver.addActionListener(m);

		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == guardar) {
				if (!txtMatricula.getText().isEmpty() && !txtModelo.getText().isEmpty()
						&& !txtMarca.getText().isEmpty()) {

					if (txtMatricula.getText().matches("[0-9]{4}[-][^aeiouAEIOUa-z]{3}")) {
						Vehiculo v = new Vehiculo(vehiculoSeleccionado.getId(), usuarioActivo.getId(),
								txtMatricula.getText(), txtModelo.getText(), txtMarca.getText(),
								vehiculoSeleccionado.getEstado(), vehiculoSeleccionado.isEnReparacion());

						try {
							controller.save(Conexion.obtener(), v);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido guardar el registro");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido guardar el registro");
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

			if (o == volver) {
				dispose();
				new GestionarVehiculosView(usuarioActivo);
			}

		}
	}
}