package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import control.Conexion;
import control.VehiculoController;
import control.ViajeController;
import model.Usuario;
import model.Vehiculo;
import model.Viaje;

public class ViajeRegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Usuario userActivo;
	private static ViajeController viajeCont = new ViajeController();
	private static VehiculoController vehiCont = new VehiculoController();
	private static JLabel lblfecha, lblhora, lbldestino, lblkm, lblprecio, lblvehiculo;
	private static JDateChooser dateChooser;
	private static JTextField txthora, txtdestino, txtkm, txtprecio;
	private static JButton btconfirmar, btcancelar;
	private static JComboBox cbvehiculos;
	private static Viaje viaje;
	private static List<Vehiculo> vehiculos;

	public ViajeRegistrationView(Usuario u) {
		viaje = new Viaje();
		this.userActivo = u;
		iniciarComponentes();
	}

	public ViajeRegistrationView(Usuario u, Viaje v) {
		this.viaje = v;
		this.userActivo = u;
		iniciarComponentes();

		dateChooser.setDate(java.sql.Date.valueOf(v.getFecha()));
		txthora.setText(v.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
		txtdestino.setText(v.getDestino());
		txtkm.setText(Float.toString(v.getKm()));
		txtprecio.setText(Float.toString(v.getPrecio()));
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getId() == v.getIdVehiculo()) {
				cbvehiculos.setSelectedIndex(i);
				break;
			}
		}

	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registrar viaje");
		setBounds(100, 100, 330, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		lblfecha = new JLabel("Fecha");
		lblfecha.setBounds(10, 14, 110, 14);
		contentPane.add(lblfecha);

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(118, 11, 125, 20);
		contentPane.add(dateChooser);

		lblhora = new JLabel("Hora (HH:mm)");
		lblhora.setBounds(10, 53, 98, 14);
		contentPane.add(lblhora);
		txthora = new JTextField(10);
		txthora.setBounds(118, 50, 125, 20);
		contentPane.add(txthora);

		lbldestino = new JLabel("Destino");
		lbldestino.setBounds(10, 88, 79, 14);
		contentPane.add(lbldestino);
		txtdestino = new JTextField(10);
		txtdestino.setBounds(118, 85, 125, 20);
		contentPane.add(txtdestino);

		lblkm = new JLabel("Kilómetros");
		lblkm.setBounds(10, 128, 79, 14);
		contentPane.add(lblkm);
		txtkm = new JTextField(10);
		txtkm.setBounds(118, 125, 125, 20);
		contentPane.add(txtkm);

		lblprecio = new JLabel("Precio");
		lblprecio.setBounds(10, 166, 79, 14);
		contentPane.add(lblprecio);
		txtprecio = new JTextField(10);
		txtprecio.setBounds(118, 163, 125, 20);
		contentPane.add(txtprecio);

		lblvehiculo = new JLabel("Vehículo");
		lblvehiculo.setBounds(10, 204, 79, 14);
		contentPane.add(lblvehiculo);

		cbvehiculos = new JComboBox(cargarVehiculos());
		cbvehiculos.setBounds(118, 201, 186, 20);
		contentPane.add(cbvehiculos);

		btconfirmar = new JButton("Confirmar");
		btconfirmar.setBounds(21, 247, 99, 23);

		btconfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarViaje();
				dispose();
				JOptionPane.showMessageDialog(null, "Viaje registrado con éxito", "Viaje registrado",
						JOptionPane.INFORMATION_MESSAGE);
				new GestionarViajesView(userActivo);
			}
		});
		contentPane.add(btconfirmar);

		btcancelar = new JButton("Cancelar");
		btcancelar.setBounds(157, 247, 110, 23);
		btcancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestionarViajesView(userActivo);
			}
		});
		contentPane.add(btcancelar);

		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private static String[] cargarVehiculos() {
		try {
			vehiculos = vehiCont.getAllVehiculos(Conexion.obtener(), userActivo);
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido recuperar los vehículos");
		}
		List<String> datosVehiculos = new ArrayList<>();
		for (Vehiculo v : vehiculos) {
			String datos = v.getMarca() + " " + v.getModelo() + " (" + v.getMatricula() + ")";
			datosVehiculos.add(datos);
		}
		return datosVehiculos.toArray(new String[0]);
	}

	private void registrarViaje() {
		try {
			Date selectedDate = dateChooser.getDate();
			if (selectedDate != null) {
				LocalDate localDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				viaje.setFecha(localDate);
			}

			viaje.setHora(LocalTime.parse(txthora.getText()));
			viaje.setDestino(txtdestino.getText());
			viaje.setKm(Float.parseFloat(txtkm.getText()));
			viaje.setPrecio(Float.parseFloat(txtprecio.getText()));
			viaje.setIdUsuario(userActivo.getId());
			viaje.setIdVehiculo(vehiculos.get(cbvehiculos.getSelectedIndex()).getId());

			viajeCont.save(Conexion.obtener(), viaje);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
