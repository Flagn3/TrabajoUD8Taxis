package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import control.Conexion;
import control.EstadisticasController;
import model.Usuario;

public class EstadisticasView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JComboBox<Integer> cbanyosViajes, cbanyosIngresos, cbanyosReparaciones;
	private static EstadisticasController estadisticasController = new EstadisticasController();
	private static ChartPanel chartViajesPanel, chartIngresosPanel, chartReparacionesPanel;
	private static JButton btvolver;
	private static Usuario usuarioActivo;

	/**
	 * Create the frame.
	 */
	public EstadisticasView(Usuario u) {
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Estadísticas");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		cbanyosViajes = new JComboBox();

		cbanyosIngresos = new JComboBox();

		cbanyosReparaciones = new JComboBox();

		btvolver = new JButton("Volver");
		btvolver.setBackground(new Color(33, 150, 243));
		btvolver.setForeground(Color.WHITE);
		btvolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
		contentPane.add(btvolver, BorderLayout.SOUTH);

		// panel viajes
		JPanel viajesPorMesPanel = new JPanel(new BorderLayout());
		viajesPorMesPanel.add(cbanyosViajes, BorderLayout.NORTH);

		chartViajesPanel = new ChartPanel(null);
		viajesPorMesPanel.add(chartViajesPanel, BorderLayout.CENTER);

		// panel ingresos
		JPanel ingresosPorMesPanel = new JPanel(new BorderLayout());
		ingresosPorMesPanel.add(cbanyosIngresos, BorderLayout.NORTH);

		chartIngresosPanel = new ChartPanel(null);
		ingresosPorMesPanel.add(chartIngresosPanel, BorderLayout.CENTER);

		// panel reparaciones
		JPanel reparacionesPorMesPanel = new JPanel(new BorderLayout());
		reparacionesPorMesPanel.add(cbanyosReparaciones, BorderLayout.NORTH);

		chartReparacionesPanel = new ChartPanel(null);
		reparacionesPorMesPanel.add(chartReparacionesPanel, BorderLayout.CENTER);

		ManejadorEventos manejador = new ManejadorEventos();

		cbanyosViajes.addActionListener(manejador);
		cbanyosIngresos.addActionListener(manejador);
		cbanyosReparaciones.addActionListener(manejador);
		btvolver.addActionListener(manejador);

		tabbedPane.addTab("Viajes por mes", viajesPorMesPanel);
		tabbedPane.addTab("Ingresos por mes", ingresosPorMesPanel);
		tabbedPane.addTab("Reparaciones por mes", reparacionesPorMesPanel);

		cargarAnyosViajes();
		cargarAnyosReparaciones();

		contentPane.add(tabbedPane, BorderLayout.CENTER);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void cargarAnyosViajes() {
		// TODO Auto-generated method stub
		try {
			Set<Integer> anyos = estadisticasController.getAnyosDisponiblesViajes(Conexion.obtener());
			for (Integer anyo : anyos) {
				cbanyosViajes.addItem(anyo);
				cbanyosIngresos.addItem(anyo);
			}
			if (!anyos.isEmpty()) {
				Integer anyoSeleccionado = (Integer) cbanyosViajes.getSelectedItem();
				actualizarGraficoViajesPorMes(anyoSeleccionado);
				actualizarGraficoIngresosPorMes(anyoSeleccionado);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		}
	}

	private void cargarAnyosReparaciones() {
		// TODO Auto-generated method stub
		try {
			Set<Integer> anyos = estadisticasController.getAnyosDisponiblesReparaciones(Conexion.obtener());
			for (Integer anyo : anyos) {
				cbanyosReparaciones.addItem(anyo);
			}
			if (!anyos.isEmpty()) {
				Integer anyoSeleccionado = (Integer) cbanyosReparaciones.getSelectedItem();
				actualizarGraficoReparacionesPorMes(anyoSeleccionado);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
			;
		}
	}

	private void actualizarGraficoReparacionesPorMes(Integer anyo) {
		// TODO Auto-generated method stub
		try {
			Map<Integer, Integer> datos = estadisticasController.getReparacionesPorMes(Conexion.obtener(), anyo);
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int mes = 1; mes <= 12; mes++) {
				dataset.addValue(datos.getOrDefault(mes, 0), "Reparaciones", getNombreMes(mes));
			}
			JFreeChart chart = ChartFactory.createBarChart("Reparaciones por mes - " + anyo, "Mes", "Cantidad",
					dataset);
			configurarGrafico(chart);
			chartReparacionesPanel.setChart(chart);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		}
	}

	private void actualizarGraficoIngresosPorMes(Integer anyo) {
		// TODO Auto-generated method stub
		try {
			Map<Integer, Double> datos = estadisticasController.getIngresosPorMes(Conexion.obtener(), anyo);
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int mes = 1; mes <= 12; mes++) {
				dataset.addValue(datos.getOrDefault(mes, 0.0), "Ingresos", getNombreMes(mes));
			}
			JFreeChart chart = ChartFactory.createAreaChart("Ingresos por mes - " + anyo, "Mes", "Ingresos (€)",
					dataset);
			configurarGrafico(chart);
			chartIngresosPanel.setChart(chart);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		}
	}

	private void actualizarGraficoViajesPorMes(Integer anyo) {
		// TODO Auto-generated method stub
		try {
			Map<Integer, Integer> datos = estadisticasController.getViajesPorMes(Conexion.obtener(), anyo);
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (int mes = 1; mes <= 12; mes++) {
				int viajes = datos.getOrDefault(mes, 0);
				dataset.addValue(viajes, "Viajes", getNombreMes(mes));
			}

			JFreeChart chart = ChartFactory.createBarChart("Viajes por mes - " + anyo, "Mes", "Número de viajes",
					dataset);

			configurarGrafico(chart);

			chartViajesPanel.setChart(chart);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Ha surgido un error al conectar con la base de datos");
		}
	}

	private void configurarGrafico(JFreeChart chart) {
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		CategoryPlot plot2 = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot2.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.toRadians(45)));

		chart.removeLegend();
	}

	private String getNombreMes(int mes) {
		return switch (mes) {
		case 1 -> "Enero";
		case 2 -> "Febrero";
		case 3 -> "Marzo";
		case 4 -> "Abril";
		case 5 -> "Mayo";
		case 6 -> "Junio";
		case 7 -> "Julio";
		case 8 -> "Agosto";
		case 9 -> "Septiembre";
		case 10 -> "Octubre";
		case 11 -> "Noviembre";
		case 12 -> "Diciembre";
		default -> "Mes inválido";
		};
	}

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {

			Object o = ev.getSource();

			if (o == cbanyosViajes) {
				Integer anyoSeleccionado = (Integer) cbanyosViajes.getSelectedItem();
				actualizarGraficoViajesPorMes(anyoSeleccionado);
			} else if (o == cbanyosIngresos) {
				Integer anyoSeleccionado = (Integer) cbanyosIngresos.getSelectedItem();
				actualizarGraficoIngresosPorMes(anyoSeleccionado);
			} else if (o == cbanyosReparaciones) {
				Integer anyoSeleccionado = (Integer) cbanyosReparaciones.getSelectedItem();
				actualizarGraficoReparacionesPorMes(anyoSeleccionado);
			} else if (o == btvolver) {
				dispose();
				new AdminView(usuarioActivo);
			}

		}

	}

}
