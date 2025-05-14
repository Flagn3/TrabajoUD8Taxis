package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import control.ReparacionController;
import model.Usuario;
import model.Vehiculo;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistorialRegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioActivo;
	private static final ReparacionController reparacionController = new ReparacionController();
	private Vehiculo vehiculoSeleccionado;
	private JLabel lblFecha, lblHora, lblDescripcion;
	private JTextField txthora;
	private JTextArea areaDescripcion;
	private static JDateChooser dateChooser;
	private JButton aceptar, cancelar; 
	
	public HistorialRegistrationView(Usuario u, Vehiculo v) {
		super("Crear reparación");
		this.usuarioActivo = u;
		this.vehiculoSeleccionado = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 333, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		
		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFecha.setBounds(10, 25, 66, 19);
		contentPane.add(lblFecha);
		dateChooser = new JDateChooser();
		dateChooser.setBounds(176, 25, 88, 19);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		contentPane.add(dateChooser);
		lblHora = new JLabel("Hora (hh:mm)");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHora.setBounds(10, 65, 118, 30);
		contentPane.add(lblHora);
		txthora = new JTextField();
		txthora.setBounds(176, 74, 88, 19);
		contentPane.add(txthora);
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescripcion.setBounds(10, 133, 95, 30);
		contentPane.add(lblDescripcion);
		areaDescripcion = new JTextArea(30, 30);
		areaDescripcion.setBounds(147, 139, 149, 117);
		contentPane.add(areaDescripcion);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBounds(41, 306, 103, 44);
		contentPane.add(aceptar);
		cancelar = new JButton("Cancelar");
		cancelar.setBounds(176, 306, 88, 44);
		contentPane.add(cancelar);
		
		
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new ReparacionView(usuarioActivo);
				
			}
		});
		
		setContentPane(contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
