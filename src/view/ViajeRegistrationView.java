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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
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
		setTitle("Registro de Viajes");
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
		
		JLabel title = new JLabel("    Ficha de Viajes");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setBounds(100, 10, 250, 30);
		contentPane.add(title);

		lblfecha = new JLabel("Fecha");
		lblfecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblfecha.setBounds(30, 50, 80, 20);
		contentPane.add(lblfecha);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(120, 50, 200, 25);
		dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		contentPane.add(dateChooser);

		lblhora = new JLabel("Hora");
		lblhora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblhora.setBounds(30, 90, 80, 20);
		contentPane.add(lblhora);
		
		txthora = new JTextField(10);
		txthora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txthora.setBounds(120, 90, 200, 25);
		contentPane.add(txthora);

		lbldestino = new JLabel("Destino");
		lbldestino.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lbldestino.setBounds(30, 130, 80, 20);
		contentPane.add(lbldestino);
		
		txtdestino = new JTextField(10);
		txtdestino.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtdestino.setBounds(120, 130, 200, 25);
		contentPane.add(txtdestino);

		lblkm = new JLabel("Kilómetros");
		lblkm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblkm.setBounds(30, 170, 80, 20);
		contentPane.add(lblkm);
		
		txtkm = new JTextField(10);
		txtkm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtkm.setBounds(120, 170, 200, 25);
		contentPane.add(txtkm);

		lblprecio = new JLabel("Precio");
		lblprecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblprecio.setBounds(30, 210, 80, 20);
		contentPane.add(lblprecio);
		
		txtprecio = new JTextField(10);
		txtprecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtprecio.setBounds(120, 210, 200, 25);
		contentPane.add(txtprecio);

		
		ManejadorEventos m = new ManejadorEventos();
		txthora.addKeyListener(m);
		txtdestino.addKeyListener(m);
		txtkm.addKeyListener(m);
		txtprecio.addKeyListener(m);
		
		lblvehiculo = new JLabel("Vehículo");
		lblvehiculo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblvehiculo.setBounds(30, 250, 80, 20);
		contentPane.add(lblvehiculo);

		cbvehiculos = new JComboBox(cargarVehiculos());
		cbvehiculos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cbvehiculos.setBounds(120, 250, 200, 25);
		contentPane.add(cbvehiculos);

		btconfirmar = new JButton("Confirmar");
		btconfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btconfirmar.setForeground(Color.WHITE);
		btconfirmar.setBackground(new Color(40, 167, 69));
		btconfirmar.setFocusPainted(false);
		btconfirmar.setBorderPainted(false);
		btconfirmar.setBounds(60, 320, 120, 35);
		btconfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarViaje();
				dispose();
				JOptionPane.showMessageDialog(null, "Viaje registrado con éxito", "Viaje registrado",
						JOptionPane.INFORMATION_MESSAGE);
				Vehiculo vehiSeleccionado = vehiculos.get(cbvehiculos.getSelectedIndex());
				vehiSeleccionado.setEstado(vehiSeleccionado.getEstado() - 5);
				try {
					vehiCont.save(Conexion.obtener(), vehiSeleccionado);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new GestionarViajesView(userActivo);
			}
		});
		contentPane.add(btconfirmar);

		btcancelar = new JButton("Cancelar");
		btcancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btcancelar.setForeground(Color.WHITE);
		btcancelar.setBackground(new Color(220, 53, 69));
		btcancelar.setFocusPainted(false);
		btcancelar.setBorderPainted(false);
		btcancelar.setBounds(200, 320, 120, 35);
		btcancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestionarViajesView(userActivo);
			}
		});
		contentPane.add(btcancelar);

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
	
	private class ManejadorEventos implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {

			if(txthora.getText().length() >= 10) {
				e.consume();
			}
			if(txtdestino.getText().length() >= 30) {
				e.consume();
			}
			if(txtkm.getText().length() >= 12) {
				e.consume();
			}
			if(txtprecio.getText().length() >= 12) {
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
