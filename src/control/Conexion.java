package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static Connection cnx = null;

	public static Connection obtener() throws SQLException, ClassNotFoundException {
		if (cnx == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				cnx = DriverManager.getConnection(
						"jdbc:mysql://bktehwk17s5a3dadtynd-mysql.services.clever-cloud.com:3306/bktehwk17s5a3dadtynd?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Madrid",
						"uxkm0jdm45e03qjl", "6dB2qsiPExXuCzbJzxlh");
			} catch (SQLException ex) {
				throw new SQLException(ex);
			} catch (ClassNotFoundException ex) {
				throw new ClassCastException(ex.getMessage());
			}
		}
		return cnx;
	}

	public static void cerrar() throws SQLException {
		if (cnx != null) {
			cnx.close();
		}
	}
}
