package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(20, 20, 1888, 1020);

		cbanyosViajes = new JComboBox();
		cbanyosViajes.setBounds(30, 20, 100, 25);

		cbanyosIngresos = new JComboBox();
		cbanyosIngresos.setBounds(30, 20, 100, 25);

		cbanyosReparaciones = new JComboBox();
		cbanyosReparaciones.setBounds(30, 20, 100, 25);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		btvolver = new JButton(imgVolver);
		btvolver.setBounds(1850, 50, 40, 40);
		btvolver.setContentAreaFilled(false);
		btvolver.setBorderPainted(false);
		btvolver.setFocusPainted(false);
		btvolver.setOpaque(false);
		contentPane.add(btvolver);

		// panel viajes
		JPanel viajesPorMesPanel = new JPanel(null);
		viajesPorMesPanel.add(cbanyosViajes);

		chartViajesPanel = new ChartPanel(null);
		chartViajesPanel.setBounds(30, 60, 1800, 900);
		viajesPorMesPanel.add(chartViajesPanel);

		// panel ingresos
		JPanel ingresosPorMesPanel = new JPanel(null);
		ingresosPorMesPanel.add(cbanyosIngresos);

		chartIngresosPanel = new ChartPanel(null);
		chartIngresosPanel.setBounds(30, 60, 1800, 900);
		ingresosPorMesPanel.add(chartIngresosPanel);

		// panel reparaciones
		JPanel reparacionesPorMesPanel = new JPanel(null);
		reparacionesPorMesPanel.add(cbanyosReparaciones);

		chartReparacionesPanel = new ChartPanel(null);
		chartReparacionesPanel.setBounds(30, 60, 1800, 900);
		reparacionesPorMesPanel.add(chartReparacionesPanel);

		ManejadorEventos manejador = new ManejadorEventos();

		cbanyosViajes.addActionListener(manejador);
		cbanyosIngresos.addActionListener(manejador);
		cbanyosReparaciones.addActionListener(manejador);
		btvolver.addActionListener(manejador);

		tabbedPane.addTab("Viajes por mes", viajesPorMesPanel);
		tabbedPane.addTab("Ingresos por mes", ingresosPorMesPanel);
		tabbedPane.addTab("Reparaciones por mes", reparacionesPorMesPanel);
		// tabbedPane.addTab("Top 5 taxistas", new JPanel());

		cargarAnyosViajes();
		cargarAnyosReparaciones();

		contentPane.add(tabbedPane);
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
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
