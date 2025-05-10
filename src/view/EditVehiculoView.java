package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		super("Editar vehículo");
		this.vehiculoSeleccionado = v;
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		lblMatricula = new JLabel("Matrícula");
		lblMatricula.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMatricula.setBounds(10, 10, 70, 22);
		lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModelo.setBounds(10, 99, 57, 22);
		lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMarca.setBounds(10, 56, 46, 22);

		txtMatricula = new JTextField();
		txtMatricula.setText(vehiculoSeleccionado.getMatricula());
		txtMatricula.setBounds(110, 14, 62, 20);
		txtModelo = new JTextField();
		txtModelo.setText(vehiculoSeleccionado.getModelo());
		txtModelo.setBounds(110, 103, 62, 20);
		txtMarca = new JTextField();
		txtMarca.setText(vehiculoSeleccionado.getMarca());
		txtMarca.setBounds(110, 60, 62, 20);
		
		guardar = new JButton("Guardar");
		guardar.setBounds(10, 164, 101, 23);
		volver = new JButton("Volver");
		volver.setBounds(121, 164, 78, 23);
		contentPane.setLayout(null);
		
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
		
		setContentPane(contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class ManejadorEventos implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object o = e.getSource();
			
			if(o == guardar) {
				vehiculoSeleccionado.setMatricula(txtMatricula.getText());
				vehiculoSeleccionado.setMarca(txtMarca.getText());
				vehiculoSeleccionado.setModelo(txtModelo.getText());
				
				try {
					controller.save(Conexion.obtener(), vehiculoSeleccionado);
					dispose();
					new GestionarVehiculosView(usuarioActivo);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(o == volver) {
				dispose();
				new GestionarVehiculosView(usuarioActivo);
			}
			
		}
		
	}
	
}
