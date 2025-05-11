package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Reparacion;
import model.Usuario;

public class ReparacionController {

	private final String tabla = "Reparaciones";

	public void save(Connection conexion, Reparacion r) {
		PreparedStatement consulta;
		try {
			if (r.getId() == null) {
				consulta = conexion.prepareStatement("INSERT INTO " + this.tabla
						+ " VALUES (fecha,hora,descripcion,coste,id_usuario,id_vehiculo) VALUES(?,?,?,?,?,?)");
				consulta.setDate(1, Date.valueOf(r.getFecha()));
				consulta.setTime(2, Time.valueOf(r.getHora()));
				consulta.setString(3, r.getDescripcion());
				consulta.setFloat(4, r.getCoste());
				consulta.setInt(5, r.getIdUsuario());
				consulta.setInt(6, r.getIdVehiculo());
			} else {
				consulta = conexion.prepareStatement("UPADTE " + this.tabla
						+ " SET fecha = ?, hora = ?, descripcion = ?, coste = ?, id_usuario = ?, id_vehiculo = ? WHERE id=?");
				consulta.setDate(1, Date.valueOf(r.getFecha()));
				consulta.setTime(2, Time.valueOf(r.getHora()));
				consulta.setString(3, r.getDescripcion());
				consulta.setFloat(4, r.getCoste());
				consulta.setInt(5, r.getIdUsuario());
				consulta.setInt(6, r.getIdVehiculo());
				consulta.setInt(7, r.getId());
			}
			consulta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(Connection conexion, Reparacion r) {
		try {
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, r.getId());
			consulta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Reparacion> getAllReparaciones(Connection conexion,Usuario u) {
		List<Reparacion> reparaciones = new ArrayList<Reparacion>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT id,fecha,hora,descripcion,coste,id_usuario,id_vehiculo FROM " + this.tabla + " WHERE id_usuario = ?");
			consulta.setInt(1, u.getId());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				reparaciones.add(new Reparacion(resultado.getInt("id"), resultado.getDate("fecha").toLocalDate(),
						resultado.getTime("hora").toLocalTime(), resultado.getString("descripcion"),
						resultado.getFloat("coste"), resultado.getInt("id_usuario"), resultado.getInt("id_vehiculo")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reparaciones;
	}
}
