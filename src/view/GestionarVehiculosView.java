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

public class GestionarVehiculosView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton editarVehiculo, eliminarVehiculo, addVehiculo, repararVehiculo, inicio;
	private final VehiculoController controller = new VehiculoController();
	private List<Vehiculo> vehiculos;
	private JTable tablaVehiculos;
	private Usuario usuarioActivo;

	public GestionarVehiculosView(Usuario u) {
		super("Gestionar Vehiculos");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		editarVehiculo = new JButton("Editar");
		editarVehiculo.setBounds(112, 24, 77, 31);
		eliminarVehiculo = new JButton("Eliminar");
		eliminarVehiculo.setBounds(199, 24, 93, 31);
		addVehiculo = new JButton("Añadir");
		addVehiculo.setBounds(25, 24, 77, 31);

		repararVehiculo = new JButton("Reparar vehículo");
		repararVehiculo.setBounds(302, 24, 154, 31);
		contentPane.add(repararVehiculo);

		ImageIcon img = new ImageIcon("file/salir.png");
		inicio = new JButton(img);
		inicio.setBounds(473, 11, 57, 44);
		contentPane.add(inicio);

		contentPane.setLayout(null);

		contentPane.add(addVehiculo);
		contentPane.add(editarVehiculo);
		contentPane.add(eliminarVehiculo);

		setContentPane(contentPane);

		ManejadorEventos m = new ManejadorEventos();
		addVehiculo.addActionListener(m);
		eliminarVehiculo.addActionListener(m);
		editarVehiculo.addActionListener(m);
		inicio.addActionListener(m);
		repararVehiculo.addActionListener(m);

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
			this.vehiculos = this.controller.getAllVehiculos(Conexion.obtener(), usuarioActivo);
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

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == addVehiculo) {
				dispose();
				new VehiculoRegistratioView(usuarioActivo);
			} else if (o == eliminarVehiculo) {
				int filaSeleccionada = tablaVehiculos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int decision = JOptionPane.showConfirmDialog(null,
							"Estas seguro que deseas elimininar este vehículo", "Eliminar vehículo",
							JOptionPane.YES_NO_OPTION);
					if (decision == 0) {
						try {
							controller.remove(Conexion.obtener(), vehiculos.get(filaSeleccionada));
							showVehiculos();
							JOptionPane.showMessageDialog(null, "Vehículo Eliminado con éxito");
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione una fila", "Seleccionar fila",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (o == editarVehiculo) {
				int filaSeleccionada = tablaVehiculos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					new EditVehiculoView(vehiculos.get(filaSeleccionada), usuarioActivo);
					dispose();
				}
			} else if (o == inicio) {
				dispose();
				new TaxistaView(usuarioActivo);
			} else if (o == repararVehiculo) {
				int filaseleccionada = tablaVehiculos.getSelectedRow();
				if (filaseleccionada >= 0) {
					try {
						vehiculos.get(filaseleccionada).setEnReparacion(true);
						controller.save(Conexion.obtener(), vehiculos.get(filaseleccionada));
						showVehiculos();
						JOptionPane.showMessageDialog(null, "Vehículo mandado a reparar");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un vehículo", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}
}
