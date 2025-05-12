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
						+ "(id_usuario, matricula, modelo, marca, estado) VALUES(?, ?, ?, ?, ?)");
				consulta.setInt(1, vehiculo.getIdUsuario());
				consulta.setString(2, vehiculo.getMatricula());
				consulta.setString(3, vehiculo.getModelo());
				consulta.setString(4, vehiculo.getMarca());
				consulta.setInt(5, vehiculo.getEstado());

			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tabla
						+ " SET id_usuario = ?, matricula = ?, modelo = ?, marca = ? WHERE id = ?");
				consulta.setInt(1, vehiculo.getIdUsuario());
				consulta.setString(2, vehiculo.getMatricula());
				consulta.setString(3, vehiculo.getModelo());
				consulta.setString(4, vehiculo.getMarca());
				consulta.setInt(5, vehiculo.getId());
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
					.prepareStatement("SELECT id, id_usuario, matricula, modelo, marca, estado FROM " + this.tabla
							+ " WHERE id_usuario = ?");
			consulta.setInt(1, u.getId());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				Vehiculo v = new Vehiculo(resultado.getInt("Id"), resultado.getInt("id_usuario"),
						resultado.getString("matricula"), resultado.getString("modelo"), resultado.getString("marca"));
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
					"SELECT id, id_usuario, matricula, modelo, marca FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, id);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				vehiculo = new Vehiculo(id, resultado.getInt("id_usuario"), resultado.getString("matricula"),
						resultado.getString("modelo"), resultado.getString("marca"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vehiculo;
	}

}
