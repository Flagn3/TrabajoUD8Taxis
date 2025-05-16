package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EstadisticasController {

	private final String tablaViajes = "Viajes";
	private final String tablaReparaciones = "Reparaciones";

	/**
	 * Años que tienen viajes registrados
	 * 
	 * @param conexion
	 * @return
	 * @throws SQLException
	 */
	public Set<Integer> getAnyosDisponiblesViajes(Connection conexion) throws SQLException {

		Set<Integer> anios = new TreeSet<>();
		try {
			PreparedStatement consulta;

			consulta = conexion.prepareStatement("SELECT DISTINCT YEAR(fecha) anyo FROM " + tablaViajes);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				anios.add(resultado.getInt("anyo"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}

		return anios;
	}
	
	public Set<Integer> getAnyosDisponiblesReparaciones(Connection conexion) throws SQLException {

		Set<Integer> anios = new TreeSet<>();
		try {
			PreparedStatement consulta;

			consulta = conexion.prepareStatement("SELECT DISTINCT YEAR(fecha) anyo FROM " + tablaReparaciones);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				anios.add(resultado.getInt("anyo"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}

		return anios;
	}

	/**
	 * Viajes por mes del año que le pasemos
	 * 
	 * @param conexion
	 * @param anio
	 * @return
	 * @throws SQLException
	 */
	public Map<Integer, Integer> getViajesPorMes(Connection conexion, int anyo) throws SQLException {
		Map<Integer, Integer> viajesPorMes = new TreeMap<>();
		try {
			PreparedStatement consulta = conexion.prepareStatement("SELECT MONTH(fecha) mes, COUNT(*) total FROM "
					+ tablaViajes + " WHERE YEAR(fecha) = ? GROUP BY MONTH(fecha)");
			consulta.setInt(1, anyo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				viajesPorMes.put(resultado.getInt("mes"), resultado.getInt("total"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}

		return viajesPorMes;
	}

	/**
	 * Ingresos por mes del año que le pasemos
	 * 
	 * @param conexion
	 * @param anyo
	 * @return
	 * @throws SQLException
	 */
	public Map<Integer, Double> getIngresosPorMes(Connection conexion, int anyo) throws SQLException {
		Map<Integer, Double> ingresosPorMes = new TreeMap<>();

		try {
			PreparedStatement consulta = conexion.prepareStatement("SELECT MONTH(fecha) mes, SUM(precio) total FROM "
					+ tablaViajes + " WHERE YEAR(fecha) = ? GROUP BY MONTH(fecha) ORDER BY mes");
			consulta.setInt(1, anyo);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				int mes = resultado.getInt("mes");
				double total = resultado.getDouble("total");
				ingresosPorMes.put(mes, total);
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ingresosPorMes;
	}

	/**
	 * Reparaciones por mes del año que le pasemos
	 * 
	 * @param conexion
	 * @param anyo
	 * @return
	 * @throws SQLException
	 */
	public Map<Integer, Integer> getReparacionesPorMes(Connection conexion, int anyo) throws SQLException {
		Map<Integer, Integer> reparacionesPorMes = new TreeMap<>();

		try {
			PreparedStatement consulta = conexion.prepareStatement("SELECT MONTH(fecha) mes, COUNT(*) total FROM "
					+ tablaReparaciones + " WHERE YEAR(fecha) = ? GROUP BY MONTH(fecha) ORDER BY mes");
			consulta.setInt(1, anyo);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				int mes = resultado.getInt("mes");
				int total = resultado.getInt("total");
				reparacionesPorMes.put(mes, total);
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}

		return reparacionesPorMes;
	}

}
