package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.Vehiculo;

public class VehiculoController {

	private final String tabla = "Vehiculos";

	public void save(Connection conexion, Vehiculo vehiculo) throws SQLException {
		try {
			PreparedStatement consulta;
			if (vehiculo.getId() == null) {
				consulta = conexion.prepareStatement("INSERT INTO " + this.tabla
						+ "(id_usuario, matricula, modelo, marca, estado, reparacion) VALUES(?, ?, ?, ?, ?, ?)");
				consulta.setInt(1, vehiculo.getIdUsuario());
				consulta.setString(2, vehiculo.getMatricula());
				consulta.setString(3, vehiculo.getModelo());
				consulta.setString(4, vehiculo.getMarca());
				consulta.setInt(5, vehiculo.getEstado());
				consulta.setBoolean(6, false);

			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tabla
						+ " SET id_usuario = ?, matricula = ?, modelo = ?, marca = ? , estado = ? ,reparacion = ? WHERE id = ?");
				consulta.setInt(1, vehiculo.getIdUsuario());
				consulta.setString(2, vehiculo.getMatricula());
				consulta.setString(3, vehiculo.getModelo());
				consulta.setString(4, vehiculo.getMarca());
				consulta.setInt(5, vehiculo.getEstado());
				consulta.setBoolean(6, vehiculo.isEnReparacion());
				consulta.setInt(7, vehiculo.getId());
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Vehiculo> getAllVehiculos(Connection conexion, Usuario u) throws SQLException {
		List<Vehiculo> vehiculos = new ArrayList<>();

		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT id, id_usuario, matricula, modelo, marca, estado, reparacion FROM " + this.tabla
							+ " WHERE id_usuario = ? AND reparacion = 0");
			consulta.setInt(1, u.getId());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				Vehiculo v = new Vehiculo(resultado.getInt("Id"), resultado.getInt("id_usuario"),
						resultado.getString("matricula"), resultado.getString("modelo"), resultado.getString("marca"), resultado.getInt("estado"), resultado.getBoolean("reparacion"));
				vehiculos.add(v);
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return vehiculos;
	}
	
	public List<Vehiculo> getAllVehiculosTaller(Connection conexion) throws SQLException {
		List<Vehiculo> vehiculos = new ArrayList<>();

		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT id, id_usuario, matricula, modelo, marca, estado, reparacion FROM " + this.tabla + " WHERE reparacion != 0");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				Vehiculo v = new Vehiculo(resultado.getInt("Id"), resultado.getInt("id_usuario"),
						resultado.getString("matricula"), resultado.getString("modelo"), resultado.getString("marca"), resultado.getInt("estado"), resultado.getBoolean("reparacion"));
				vehiculos.add(v);
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return vehiculos;
	}

	public void remove(Connection conexion, Vehiculo vehiculo) throws SQLException {
		try {
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, vehiculo.getId());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Vehiculo getVehiculo(Connection conexion, int id) {
		Vehiculo vehiculo = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT * FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, id);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				vehiculo = new Vehiculo(id, resultado.getInt("id_usuario"), resultado.getString("matricula"),
						resultado.getString("modelo"), resultado.getString("marca"), 100, resultado.getBoolean("reparacion"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vehiculo;
	}

}
