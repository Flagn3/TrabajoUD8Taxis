package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.Viaje;

public class ViajeController {

	private final String tabla = "Viajes";

	public void save(Connection conexion, Viaje viaje) throws SQLException {

		try {
			PreparedStatement consulta;
			if (viaje.getId() == null) {
				consulta = conexion.prepareStatement("INSERT INTO " + this.tabla
						+ "(fecha, hora, destino, km, precio, id_usuario, id_vehiculo) VALUES(?, ?, ?, ?, ?, ?, ?)");

				consulta.setDate(1, Date.valueOf(viaje.getFecha()));
				consulta.setTime(2, Time.valueOf(viaje.getHora()));
				consulta.setString(3, viaje.getDestino());
				consulta.setFloat(4, viaje.getKm());
				consulta.setFloat(5, viaje.getPrecio());
				consulta.setInt(6, viaje.getIdUsuario());
				consulta.setInt(7, viaje.getIdVehiculo());
			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tabla
						+ " SET fecha = ?, hora = ?, destino = ?, km = ?, precio = ?, id_usuario = ?, id_vehiculo = ? WHERE id = ?");

				consulta.setDate(1, Date.valueOf(viaje.getFecha()));
				consulta.setTime(2, Time.valueOf(viaje.getHora()));
				consulta.setString(3, viaje.getDestino());
				consulta.setFloat(4, viaje.getKm());
				consulta.setFloat(5, viaje.getPrecio());
				consulta.setInt(6, viaje.getIdUsuario());
				consulta.setInt(7, viaje.getIdVehiculo());

				consulta.setInt(8, viaje.getId());
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}

	}

	public void remove(Connection conexion, Viaje viaje) throws SQLException {
		try {
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, viaje.getId());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Viaje> getAllViajes(Connection conexion, Usuario u) throws SQLException {
		List<Viaje> products = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT id, fecha, hora, destino,km, precio, id_vehiculo " + " FROM " + this.tabla
							+ " WHERE id_usuario= ?");
			consulta.setInt(1, u.getId());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				products.add(new Viaje(resultado.getInt("id"), resultado.getDate("fecha").toLocalDate(),
						resultado.getTime("hora").toLocalTime(), resultado.getString("destino"),
						resultado.getFloat("km"), resultado.getFloat("precio"), u.getId(),
						resultado.getInt("id_vehiculo")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return products;
	}

}
