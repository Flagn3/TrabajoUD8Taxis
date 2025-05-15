package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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
		super("Crear reparación");
		this.usuarioActivo = u;
		this.vehiculoSeleccionado = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFecha.setBounds(10, 25, 66, 19);
		contentPane.add(lblFecha);
		dateChooser = new JDateChooser();
		dateChooser.setBounds(138, 25, 88, 19);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		contentPane.add(dateChooser);
		lblHora = new JLabel("Hora (hh:mm)");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHora.setBounds(10, 65, 118, 30);
		contentPane.add(lblHora);
		txthora = new JTextField();
		txthora.setBounds(138, 73, 88, 19);
		contentPane.add(txthora);
		
		
		lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrecio.setBounds(10, 123, 76, 27);
		contentPane.add(lblPrecio);
		txtPrecio = new JTextField();
		txtPrecio.setBounds(138, 129, 88, 19);
		contentPane.add(txtPrecio);
		
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescripcion.setBounds(10, 180, 95, 30);
		contentPane.add(lblDescripcion);
		areaDescripcion = new JTextArea(30, 30);
		areaDescripcion.setBounds(115, 180, 149, 117);
		contentPane.add(areaDescripcion);

		aceptar = new JButton("Aceptar");
		aceptar.setBounds(22, 341, 103, 44);
		contentPane.add(aceptar);
		cancelar = new JButton("Cancelar");
		cancelar.setBounds(167, 341, 88, 44);
		contentPane.add(cancelar);
		
		
		ManejadorEventos m = new ManejadorEventos();
		txthora.addKeyListener(m);
		txtPrecio.addKeyListener(m);
		areaDescripcion.addKeyListener(m);
		
		
		
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new ReparacionView(usuarioActivo);

			}
		});

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(dateChooser.getDate() != null && !txthora.getText().isEmpty()
						&& !txtPrecio.getText().isEmpty() && !areaDescripcion.getText().isEmpty()) {
					
					LocalDate fecha = dateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					LocalTime hora = LocalTime.parse(txthora.getText());
					float coste = Float.parseFloat(txtPrecio.getText());
					String descripcion = areaDescripcion.getText();
					
					Reparacion repa = new Reparacion(fecha, hora, descripcion, coste, usuarioActivo.getId(), vehiculoSeleccionado.getId());
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
				}else {
					JOptionPane.showMessageDialog(null, "Faltan campos por rellenar", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

		setContentPane(contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private class ManejadorEventos implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {

			if(txthora.getText().length() >= 10) {
				e.consume();
			}
			if(areaDescripcion.getText().length()>=200) {
				e.consume();
			}
			if(txtPrecio.getText().length() >= 12) {
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
