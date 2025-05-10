package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import control.ViajeController;
import model.Usuario;
import model.Viaje;

public class GestionarViajesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btadd, btupdate, btborrar;
	private static JTable tabla;
	private static Usuario userActivo;
	private static ViajeController viajeCont = new ViajeController();
	private static List<Viaje> viajes;

	/**
	 * Create the frame.
	 */
	public GestionarViajesView(Usuario u) {
		setTitle("Gestión de viajes");
		this.userActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btadd = new JButton("Añadir");
		btadd.setBounds(156, 10, 87, 31);
		btupdate = new JButton("Editar");
		btupdate.setBounds(291, 10, 87, 31);
		btborrar = new JButton("Eliminar");
		btborrar.setBounds(431, 10, 94, 31);
		contentPane.setLayout(null);

		contentPane.add(btadd);
		contentPane.add(btupdate);
		contentPane.add(btborrar);

		String[] columnNames = { "Fecha", "Hora", "Destino", "Kilómetros", "Precio", "Id Vehículo" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		// tabla = new JTable(model);
		showViajes(model);

		JScrollPane scrollpane = new JScrollPane(tabla);
		scrollpane.setBounds(10, 58, 618, 222);
		contentPane.add(scrollpane);

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

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido recuperar los viajes");
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Ha surgido un error y no se han podido recuperar los viajes");
		}
	}

}
