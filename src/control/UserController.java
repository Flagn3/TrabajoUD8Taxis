package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.Vehiculo;

public class UserController {

	private final String tabla = "Usuarios";

	public void save(Connection conexion, Usuario usuario) {
		PreparedStatement consulta;
		try {
			if (usuario.getId() == null) {
				consulta = conexion.prepareStatement("INSERT INTO " + this.tabla
						+ " (nombre,apellido,password,username,rol,email) VALUES(?,?,?,?,?,?)");
				consulta.setString(1, usuario.getNombre());
				consulta.setString(2, usuario.getApellido());
				consulta.setString(3, usuario.getPassword());
				consulta.setString(4, usuario.getUsername());
				consulta.setString(5, usuario.getRol());
				consulta.setString(6, usuario.getEmail());
			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tabla
						+ " SET nombre = ?, apellido = ?, password = ?, username = ?, rol = ?, email = ? WHERE id = ?");
				consulta.setString(1, usuario.getNombre());
				consulta.setString(2, usuario.getApellido());
				consulta.setString(3, usuario.getPassword());
				consulta.setString(4, usuario.getUsername());
				consulta.setString(5, usuario.getRol());
				consulta.setString(6, usuario.getEmail());
				consulta.setInt(7, usuario.getId());
			}
			consulta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(Connection conexion, Usuario usuario) {
		try {
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, usuario.getId());
			consulta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Usuario> getAllUsers(Connection conexion) throws SQLException {
		List<Usuario> products = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT id,nombre,apellido, username, password, rol, email " + " FROM " + this.tabla);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				products.add(new Usuario(resultado.getInt("id"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("username"),
						resultado.getString("password"), resultado.getString("rol"), resultado.getString("email")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return products;
	}

	public Usuario getUser(Connection conexion, int id) {
		Usuario usuario = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT * FROM " + this.tabla + " WHERE id = ?");
			consulta.setInt(1, id);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				usuario = new Usuario();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuario;
	}
}
