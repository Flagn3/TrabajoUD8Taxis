package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import control.Conexion;
import control.ReparacionController;
import control.VehiculoController;
import model.Reparacion;
import model.Usuario;
import model.Vehiculo;

public class HistorialRegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioActivo;
	private static final ReparacionController reparacionController = new ReparacionController();
	private static final VehiculoController vehiculoController = new VehiculoController();
	private Vehiculo vehiculoSeleccionado;
	private JLabel lblFecha, lblHora, lblPrecio, lblDescripcion;
	private JTextField txthora, txtPrecio;
	private JTextArea areaDescripcion;
	private static JDateChooser dateChooser;
	private JButton aceptar, cancelar;

	public HistorialRegistrationView(Usuario u, Vehiculo v) {
		this.usuarioActivo = u;
		this.vehiculoSeleccionado = v;

		setTitle("Registro de Reparaciones");
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

		JLabel title = new JLabel("Ficha de Reparacion");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setBounds(100, 10, 250, 30);
		contentPane.add(title);

		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblFecha.setBounds(30, 50, 80, 20);
		contentPane.add(lblFecha);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(120, 50, 200, 25);
		dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		contentPane.add(dateChooser);

		lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblHora.setBounds(30, 90, 80, 20);
		contentPane.add(lblHora);

		txthora = new JTextField();
		txthora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txthora.setBounds(120, 90, 200, 25);
		contentPane.add(txthora);

		lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblPrecio.setBounds(30, 130, 80, 20);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtPrecio.setBounds(120, 130, 200, 25);
		contentPane.add(txtPrecio);

		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblDescripcion.setBounds(30, 170, 80, 20);
		contentPane.add(lblDescripcion);

		areaDescripcion = new JTextArea(30, 30);
		areaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		areaDescripcion.setBounds(120, 170, 200, 120);
		contentPane.add(areaDescripcion);

		aceptar = new JButton("Aceptar");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.setForeground(Color.WHITE);
		aceptar.setBackground(new Color(40, 167, 69));
		aceptar.setFocusPainted(false);
		aceptar.setBorderPainted(false);
		aceptar.setBounds(60, 320, 120, 35);
		contentPane.add(aceptar);

		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelar.setForeground(Color.WHITE);
		cancelar.setBackground(new Color(220, 53, 69));
		cancelar.setFocusPainted(false);
		cancelar.setBorderPainted(false);
		cancelar.setBounds(200, 320, 120, 35);
		contentPane.add(cancelar);

		ManejadorEventos m = new ManejadorEventos();
		txthora.addKeyListener(m);
		txtPrecio.addKeyListener(m);
		areaDescripcion.addKeyListener(m);

		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (dateChooser.getDate() != null && !txthora.getText().isEmpty() && !txtPrecio.getText().isEmpty()
						&& !areaDescripcion.getText().isEmpty()) {

					LocalDate fecha = dateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault())
							.toLocalDate();
					LocalTime hora = LocalTime.parse(txthora.getText());
					float coste = Float.parseFloat(txtPrecio.getText());
					String descripcion = areaDescripcion.getText();

					Reparacion repa = new Reparacion(fecha, hora, descripcion, coste, usuarioActivo.getId(),
							vehiculoSeleccionado.getId());
					try {
						reparacionController.save(Conexion.obtener(), repa);

						vehiculoSeleccionado.setEnReparacion(false);
						vehiculoSeleccionado.setEstado(100);

						vehiculoController.save(Conexion.obtener(), vehiculoSeleccionado);

						JOptionPane.showMessageDialog(null, "Coche reparado");
						dispose();
						new ReparacionView(usuarioActivo);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Faltan campos por rellenar", "Campos incompletos",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class ManejadorEventos implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

			if (txthora.getText().length() >= 10) {
				e.consume();
			}
			if (areaDescripcion.getText().length() >= 200) {
				e.consume();
			}
			if (txtPrecio.getText().length() >= 12) {
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
