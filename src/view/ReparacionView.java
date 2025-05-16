package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
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
		this.usuarioActivo = u;
		setTitle("Panel de Reparacion de Vehículos");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 245, 245));
		setContentPane(contentPane);

		ImageIcon iconoVentana = new ImageIcon("file/TaxiCarga.png");
		setIconImage(iconoVentana.getImage());
		
		JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        panelArriba.setBackground(new Color(245, 245, 245));
		panelArriba.setPreferredSize(new Dimension(180,60));
		contentPane.add(panelArriba, BorderLayout.SOUTH);
		
		tablaVehiculos = new JTable();
		tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaVehiculos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaVehiculos.setRowHeight(25);
		tablaVehiculos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		showVehiculos();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 568, 204);
		scrollPane.add(tablaVehiculos);
		scrollPane.setViewportView(tablaVehiculos);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		

		reparar = new JButton("Reparar Vehículo");
		reparar.setBounds(219, 10, 157, 47);
		reparar.setBackground(new Color(33, 150, 243));
		reparar.setForeground(Color.WHITE);
		reparar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		reparar.setFocusPainted(false);
		reparar.setBorderPainted(false);
		reparar.setPreferredSize(new Dimension(1350,35));
		reparar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int filaseleccionada = tablaVehiculos.getSelectedRow();
				if (filaseleccionada >= 0) {
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
        volver.setContentAreaFilled(false);
		volver.setBorderPainted(false);
		volver.setFocusPainted(false);
		volver.setOpaque(false);
		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new MecanicoView(usuarioActivo);

			}
		});

		panelArriba.add(reparar);
		panelArriba.add(volver);

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
