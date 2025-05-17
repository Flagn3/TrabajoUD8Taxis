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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Conexion;
import control.ViajeController;
import model.Usuario;
import model.Viaje;

public class GestionarViajesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btadd, btupdate, btborrar, volver;
	private static JTable tabla;
	private static Usuario userActivo;
	private static ViajeController viajeCont = new ViajeController();
	private static List<Viaje> viajes;

	/**
	 * Create the frame.
	 */
	public GestionarViajesView(Usuario u) {
		this.userActivo = u;
		
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

		btadd = new JButton("Añadir");
		btadd.setBackground(new Color(33, 150, 243));
		btadd.setForeground(Color.WHITE);
		btadd.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btadd.setFocusPainted(false);
		btadd.setBorderPainted(false);
		btadd.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(btadd);
		
		btupdate = new JButton("Editar");
		btupdate.setBackground(new Color(33, 150, 243));
		btupdate.setForeground(Color.WHITE);
		btupdate.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btupdate.setFocusPainted(false);
		btupdate.setBorderPainted(false);
		btupdate.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(btupdate);
		
		btborrar = new JButton("Eliminar");
		btborrar.setBackground(new Color(33, 150, 243));
		btborrar.setForeground(Color.WHITE);
		btborrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btborrar.setFocusPainted(false);
		btborrar.setBorderPainted(false);
		btborrar.setPreferredSize(new Dimension(150, 35));
		panelArriba.add(btborrar);

		ImageIcon img = new ImageIcon("file/back.jpg");
		volver = new JButton(img);
		volver.setBounds(530, 78, 40, 40);
		volver.setContentAreaFilled(false);
		volver.setBorderPainted(false);
		volver.setFocusPainted(false);
		volver.setOpaque(false);
		panelArriba.add(volver);
		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new TaxistaView(userActivo);

			}
		});

		String[] columnNames = { "Fecha", "Hora", "Destino", "Kilómetros", "Precio", "Id Vehículo" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		// tabla = new JTable(model);
		showViajes(model);

		JScrollPane scrollpane = new JScrollPane(tabla);
		scrollpane.setBounds(25, 86, 518, 167);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		contentPane.add(scrollpane,BorderLayout.CENTER);

		btadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ViajeRegistrationView(userActivo);
			}
		});

		btupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
					dispose();
					new ViajeRegistrationView(userActivo, viajes.get(filaSeleccionada));
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila", "Ninguna fila seleccionada",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btborrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int decision = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que deseas eliminar este viaje?", "Eliminar viaje",
							JOptionPane.YES_NO_OPTION);
					if (decision == 0) {
						try {
							viajeCont.remove(Conexion.obtener(), viajes.get(filaSeleccionada));
							showViajes(model);
							JOptionPane.showMessageDialog(null, "Viaje eliminado con éxito", "Viaje eliminado",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,
									"Ha surgido un error y no se ha podido eliminar el viaje.");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,
									"Ha surgido un error y no se ha podido eliminar el viaje.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila", "Ninguna fila seleccionada",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private static void showViajes(DefaultTableModel model) {
		try {
			viajes = viajeCont.getAllViajes(Conexion.obtener(), userActivo);

			model.setRowCount(0);
			for (Viaje v : viajes) {
				model.addRow(new Object[] { v.getFecha(), v.getHora(), v.getDestino(), v.getKm(), v.getPrecio(),
						v.getIdVehiculo() });
			}
			tabla = new JTable(model);
			tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			tabla.setRowHeight(25);
			tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido recuperar los viajes");
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido recuperar los viajes");
		}
	}

}
