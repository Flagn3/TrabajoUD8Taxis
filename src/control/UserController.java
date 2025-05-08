package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UserController {

	private final String tabla = "Usuarios";

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

}
