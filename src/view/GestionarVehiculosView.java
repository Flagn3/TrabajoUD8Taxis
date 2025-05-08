package view;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Conexion;
import control.VehiculoController;
import model.Vehiculo;

public class GestionarVehiculosView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton editarVehiculo, eliminarVehiculo, addVehiculo;
	private final VehiculoController controller = new VehiculoController();
	private List<Vehiculo> vehiculos;
	private JTable tablaVehiculos;
	
	public GestionarVehiculosView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		editarVehiculo = new JButton("Editar");
		editarVehiculo.setBounds(228, 10, 77, 31);
		eliminarVehiculo = new JButton("Eliminar");
		eliminarVehiculo.setBounds(357, 10, 77, 31);
		addVehiculo = new JButton("Añadir");
		addVehiculo.setBounds(109, 10, 77, 31);
		
		
		contentPane.setLayout(null);
		
		contentPane.add(addVehiculo);
		contentPane.add(editarVehiculo);
		contentPane.add(eliminarVehiculo);
		
	
		setContentPane(contentPane);
		
		
		
		tablaVehiculos = new JTable();
		showVehiculos();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 86, 518, 167);
		scrollPane.add(tablaVehiculos);
		scrollPane.setViewportView(tablaVehiculos);
		contentPane.add(scrollPane);
		setVisible(true);
		setLocationRelativeTo(null);
	}

		private void showVehiculos() {
		try {
			this.vehiculos = this.controller.getAllVehiculos(Conexion.obtener());
			tablaVehiculos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

			}, new String[] { "id", "idUsuario", "Matricula", "Modelo", "Marca", "Estado" }));
			DefaultTableModel dtm=(DefaultTableModel) tablaVehiculos.getModel();
			dtm.setRowCount(0);
			for (int i = 0; i < this.vehiculos.size(); i++) {
				dtm.addRow(new Object[] { this.vehiculos.get(i).getId(), this.vehiculos.get(i).getIdUsuario(),
						this.vehiculos.get(i).getMatricula(), this.vehiculos.get(i).getModelo(),
						this.vehiculos.get(i).getMarca(), this.vehiculos.get(i).getEstado()});
			}
	

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
		}
	}
}
