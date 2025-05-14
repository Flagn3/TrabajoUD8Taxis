package view;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Conexion;
import control.CrearPDF;
import control.ReparacionController;
import control.UserController;
import control.VehiculoController;
import model.Reparacion;
import model.Usuario;
import model.Vehiculo;

public class HistorialView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnFiltrar, btnPDF, btnVolver, btnDescripcion;
	private JTable tabla;
	private Usuario usuarioActivo;
	private ReparacionController reparacionController = new ReparacionController();
	private VehiculoController vehiculoController = new VehiculoController();
	private UserController userController = new UserController();
	private List<Reparacion> reparaciones;
	private static JTextField txtfiltro;

	/**
	 * Create the frame.
	 * 
	 * @param u
	 */
	public HistorialView(Usuario u) {
		setTitle("Historial");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(462, 24, 85, 30);
		panel.add(btnFiltrar);

		btnPDF = new JButton("Crear PDF");
		btnPDF.setBounds(10, 24, 120, 30);
		panel.add(btnPDF);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		btnVolver = new JButton(imgVolver);
		btnVolver.setBounds(530, 78, 40, 40);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setFocusPainted(false);
		btnVolver.setOpaque(false);
		panel.add(btnVolver);

		txtfiltro = new JTextField(15);
		txtfiltro.setBounds(297, 24, 141, 30);
		panel.add(txtfiltro);

		tabla = new JTable();
		filtrarHistorial();
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(0, 64, 499, 189);
		panel.add(scrollPane);

		btnDescripcion = new JButton("Descripcion");
		btnDescripcion.setBounds(156, 24, 120, 30);
		panel.add(btnDescripcion);

		ManejadorEventos me = new ManejadorEventos();
		btnVolver.addActionListener(me);
		btnFiltrar.addActionListener(me);
		btnPDF.addActionListener(me);
		btnDescripcion.addActionListener(me);

		setVisible(true);
		setLocationRelativeTo(null);
	}

	public class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (btnVolver == e.getSource()) {
				dispose();
				new MecanicoView(usuarioActivo);
			}

			if (btnFiltrar == e.getSource()) {
				filtrarHistorial();
			}

			if (btnPDF == e.getSource()) {
				int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
					Reparacion r = reparaciones.get(filaSeleccionada);
					CrearPDF.PDFRow(r);
				} else {
					JOptionPane.showMessageDialog(null, "Fila no seleccionada");
				}
			}

			if (btnDescripcion == e.getSource()) {
				String descripcion = null;
				int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
					Reparacion r = reparaciones.get(filaSeleccionada);
					for (Reparacion reparacion : reparaciones) {
						if (reparacion.getId() == r.getId()) {
							descripcion = reparacion.getDescripcion();
						}
					}
					JOptionPane.showMessageDialog(null, descripcion);
				} else {
					JOptionPane.showMessageDialog(null, "Fila no seleccionada");
				}
			}
		}

	}

	private void filtrarHistorial() {
		String textoFiltro = txtfiltro.getText().trim().toLowerCase();
		try {
			reparaciones = reparacionController.getAllReparaciones(Conexion.obtener(), usuarioActivo);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DefaultTableModel modeloFiltrado = new DefaultTableModel();
		modeloFiltrado.setColumnIdentifiers(new String[] { "Fecha", "Hora", "Coste", "Vehiculo", "Taxista" });

		for (Reparacion r : reparaciones) {
			try {
				Vehiculo v = vehiculoController.getVehiculo(Conexion.obtener(), r.getIdVehiculo());
				Usuario u = userController.getUser(Conexion.obtener(), v.getIdUsuario());

				String matricula = v.getMatricula().toLowerCase();
				String nombreCompleto = (u.getNombre() + " " + u.getApellido()).toLowerCase();

				if (matricula.contains(textoFiltro) || nombreCompleto.contains(textoFiltro)) {
					modeloFiltrado.addRow(new Object[] { r.getFecha(), r.getHora(), r.getCoste(), v.getMatricula(),
							u.getNombre() + " " + u.getApellido() });
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		tabla.setModel(modeloFiltrado);
	}

}
