package model;

public class Vehiculo {

	private Integer id;
	private int idUsuario;
	private String matricula, modelo, marca;
	private int estado;
	private boolean enReparacion = false;

	public Vehiculo() {
		super();
	}

	public Vehiculo(int idUsuario, String matricula, String modelo, String marca, int estado) {
		super();
		this.idUsuario = idUsuario;
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		this.estado = estado;
	}

	public Vehiculo(Integer id, int idUsuario, String matricula, String modelo, String marca, int estado, boolean enReparacion) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		this.estado = estado;
		this.enReparacion = enReparacion;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEnReparacion() {
		return enReparacion;
	}

	public void setEnReparacion(boolean enReparacion) {
		this.enReparacion = enReparacion;
	}

	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", idUsuario=" + idUsuario + ", matricula=" + matricula + ", modelo=" + modelo
				+ ", marca=" + marca + ", estado=" + estado + ", enReparacion=" + enReparacion + "]";
	}

}
