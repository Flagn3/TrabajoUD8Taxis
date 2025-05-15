package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Conexion;
import control.VehiculoController;
import model.Usuario;
import model.Vehiculo;

public class ReparacionView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaVehiculos;
	private JButton reparar, volver;
	private List<Vehiculo> vehiculos;
	private static final VehiculoController controller = new VehiculoController();
	private Usuario usuarioActivo;

	public ReparacionView(Usuario u) {
		super("Vehiculos en reparación");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		tablaVehiculos = new JTable();
		tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		showVehiculos();
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 568, 204);
		scrollPane.add(tablaVehiculos);
		scrollPane.setViewportView(tablaVehiculos);
		contentPane.add(scrollPane);
		

		reparar = new JButton("Reparar Vehículo");
		reparar.setBounds(219, 10, 157, 47);
		reparar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int filaseleccionada = tablaVehiculos.getSelectedRow();
				if (filaseleccionada >= 0) {
					dispose();
					new HistorialRegistrationView(usuarioActivo, vehiculos.get(filaseleccionada));
					showVehiculos();
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un vehículo", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		ImageIcon volverImage = new ImageIcon("file/back.jpg");
		volver = new JButton(volverImage);
		volver.setBounds(499, 10, 56, 53);

		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new MecanicoView(usuarioActivo);

			}
		});

		contentPane.add(reparar);
		contentPane.add(volver);

		setContentPane(contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void showVehiculos() {
		try {
			this.vehiculos = this.controller.getAllVehiculosTaller(Conexion.obtener());
			tablaVehiculos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

			}, new String[] { "Matricula", "Modelo", "Marca", "Estado" }));
			DefaultTableModel dtm = (DefaultTableModel) tablaVehiculos.getModel();
			dtm.setRowCount(0);
			for (int i = 0; i < this.vehiculos.size(); i++) {
				dtm.addRow(new Object[] { this.vehiculos.get(i).getMatricula(), this.vehiculos.get(i).getModelo(),
						this.vehiculos.get(i).getMarca(), this.vehiculos.get(i).getEstado() });
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
