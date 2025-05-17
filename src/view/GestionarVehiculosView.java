package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
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
		this.usuarioActivo = u;
		
		setTitle("Panel de Gestion de Vehiculos");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 245, 245));
		setContentPane(contentPane);

		ImageIcon iconoVentana = new ImageIcon("file/TaxiCarga.png");
		setIconImage(iconoVentana.getImage());

		JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panelArriba.setBackground(new Color(245, 245, 245));
		panelArriba.setPreferredSize(new Dimension(180, 60));
		contentPane.add(panelArriba, BorderLayout.EAST);

		editarVehiculo = new JButton("Editar");
		editarVehiculo.setBackground(new Color(33, 150, 243));
		editarVehiculo.setForeground(Color.WHITE);
		editarVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		editarVehiculo.setFocusPainted(false);
		editarVehiculo.setBorderPainted(false);
		editarVehiculo.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(editarVehiculo);

		eliminarVehiculo = new JButton("Eliminar");
		eliminarVehiculo.setBackground(new Color(33, 150, 243));
		eliminarVehiculo.setForeground(Color.WHITE);
		eliminarVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		eliminarVehiculo.setFocusPainted(false);
		eliminarVehiculo.setBorderPainted(false);
		eliminarVehiculo.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(eliminarVehiculo);

		addVehiculo = new JButton("Añadir");
		addVehiculo.setBackground(new Color(33, 150, 243));
		addVehiculo.setForeground(Color.WHITE);
		addVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		addVehiculo.setFocusPainted(false);
		addVehiculo.setBorderPainted(false);
		addVehiculo.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(addVehiculo);

		repararVehiculo = new JButton("Reparar vehículo");
		repararVehiculo.setBackground(new Color(33, 150, 243));
		repararVehiculo.setForeground(Color.WHITE);
		repararVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		repararVehiculo.setFocusPainted(false);
		repararVehiculo.setBorderPainted(false);
		repararVehiculo.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(repararVehiculo);

		
		ImageIcon img = new ImageIcon("file/back.jpg");
		inicio = new JButton(img);
		inicio.setBounds(530, 78, 40, 40);
		inicio.setContentAreaFilled(false);
		inicio.setBorderPainted(false);
		inicio.setFocusPainted(false);
		inicio.setOpaque(false);
		panelArriba.add(inicio);

		ManejadorEventos m = new ManejadorEventos();
		addVehiculo.addActionListener(m);
		eliminarVehiculo.addActionListener(m);
		editarVehiculo.addActionListener(m);
		inicio.addActionListener(m);
		repararVehiculo.addActionListener(m);

		tablaVehiculos = new JTable();
		tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaVehiculos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaVehiculos.setRowHeight(25);
		tablaVehiculos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		showVehiculos();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 86, 518, 167);
		scrollPane.add(tablaVehiculos);
		scrollPane.setViewportView(tablaVehiculos);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		contentPane.add(scrollPane, BorderLayout.CENTER);

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
