package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import control.Conexion;
import control.CrearPDF;
import control.ReparacionController;
import model.Reparacion;
import model.Usuario;

public class HistorialView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnFiltrar, btnPDF, btnVolver, btnDescripcion;
	private JTable tabla;
	private Usuario usuarioActivo;
	private ReparacionController reparacionController = new ReparacionController();
	private List<Reparacion> reparaciones;

	/**
	 * Create the frame.
	 * 
	 * @param u
	 */
	public HistorialView(Usuario u) {
		setTitle("Historial");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(10, 24, 85, 30);
		panel.add(btnFiltrar);

		btnPDF = new JButton("Crear PDF");
		btnPDF.setBounds(105, 24, 120, 30);
		panel.add(btnPDF);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		btnVolver = new JButton(imgVolver);
		btnVolver.setBounds(376, 10, 40, 40);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setFocusPainted(false);
		btnVolver.setOpaque(false);
		panel.add(btnVolver);

		tabla = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		VerReparaciones(model);
		tabla.setModel(model);
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(0, 64, 426, 189);
		panel.add(scrollPane);

		btnDescripcion = new JButton("Descripcion");
		btnDescripcion.setBounds(235, 24, 120, 30);
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

	private void VerReparaciones(DefaultTableModel model) {
		// TODO Auto-generated method stub
		String[] columna = { "fecha", "hora", "coste", "id vehiculo" };
		model.setColumnIdentifiers(columna);
		try {
			reparaciones = reparacionController.getAllReparaciones(Conexion.obtener(), usuarioActivo);
			for (Reparacion r : reparaciones) {
				model.addRow(new Object[] { r.getFecha(), r.getHora(), r.getCoste(), r.getIdVehiculo() });
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
