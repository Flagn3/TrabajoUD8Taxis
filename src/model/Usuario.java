package model;

public class Usuario {

	private int id;
	private String nombre, apellido, password, username, rol, email;

	public Usuario() {
		super();
	}

	public Usuario(String nombre, String apellido, String password, String username, String rol, String email) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.username = username;
		this.rol = rol;
		this.email = email;
	}

	public Usuario(int id, String nombre, String apellido, String password, String username, String rol, String email) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.username = username;
		this.rol = rol;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", password=" + password
				+ ", username=" + username + ", rol=" + rol + ", email=" + email + "]";
	}

}
