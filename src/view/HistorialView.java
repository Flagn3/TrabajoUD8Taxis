package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
	private JButton btnFiltrar, btnPDF, btnPDFCompleto, btnVolver, btnDescripcion;
	private JTable tabla;
	private Usuario usuarioActivo;
	private ReparacionController reparacionController = new ReparacionController();
	private VehiculoController vehiculoController = new VehiculoController();
	private UserController userController = new UserController();
	private List<Reparacion> reparaciones;
	private static JTextField txtfiltro;
	private DefaultTableModel modeloFiltrado;

	/**
	 * Create the frame.
	 * 
	 * @param u
	 */
	public HistorialView(Usuario u) {
		this.usuarioActivo = u;
		
		setTitle("Panel de Historial de Reparaciones");
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
		contentPane.add(panelArriba, BorderLayout.EAST);

		btnPDF = new JButton("Crear PDF");
		btnPDF.setBackground(new Color(33, 150, 243));
		btnPDF.setForeground(Color.WHITE);
		btnPDF.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnPDF.setFocusPainted(false);
		btnPDF.setBorderPainted(false);
		btnPDF.setPreferredSize(new Dimension(150,35));
		panelArriba.add(btnPDF);

		btnPDFCompleto = new JButton("PDF Historial");
		btnPDFCompleto.setBackground(new Color(33, 150, 243));
		btnPDFCompleto.setForeground(Color.WHITE);
		btnPDFCompleto.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnPDFCompleto.setFocusPainted(false);
		btnPDFCompleto.setBorderPainted(false);
		btnPDFCompleto.setPreferredSize(new Dimension(150,35));
		panelArriba.add(btnPDFCompleto);

		btnDescripcion = new JButton("Descripcion");
		btnDescripcion.setBackground(new Color(33, 150, 243));
		btnDescripcion.setForeground(Color.WHITE);
		btnDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnDescripcion.setFocusPainted(false);
		btnDescripcion.setBorderPainted(false);
		btnDescripcion.setPreferredSize(new Dimension(150,35));
		panelArriba.add(btnDescripcion);

		JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0)); // Panel con diseño horizontal

		txtfiltro = new JTextField(12);
		txtfiltro.setPreferredSize(new Dimension(100, 30));
		panelFiltro.add(txtfiltro);

		btnFiltrar = new JButton(new ImageIcon("file/lupa.png"));
		btnFiltrar.setPreferredSize(new Dimension(30, 30));
		btnFiltrar.setContentAreaFilled(false);
		btnFiltrar.setBorderPainted(false);
		btnFiltrar.setFocusPainted(false);
		btnFiltrar.setOpaque(false);
		panelFiltro.add(btnFiltrar);

		panelArriba.add(panelFiltro);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		btnVolver = new JButton(imgVolver);
		btnVolver.setBounds(530, 78, 40, 40);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setFocusPainted(false);
		btnVolver.setOpaque(false);
		panelArriba.add(btnVolver);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		tabla = new JTable();
		filtrarHistorial();
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tabla.setRowHeight(25);
		tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		scrollPane.setViewportView(tabla);


		setVisible(true);
		setLocationRelativeTo(null);

		ManejadorEventos me = new ManejadorEventos();
		btnVolver.addActionListener(me);
		btnFiltrar.addActionListener(me);
		btnPDF.addActionListener(me);
		btnDescripcion.addActionListener(me);
		btnPDFCompleto.addActionListener(me);

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
			
			if (btnPDFCompleto == e.getSource()) {
				CrearPDF.PDFTabla(modeloFiltrado);
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

		modeloFiltrado = new DefaultTableModel();
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
