package model;

public class Vehiculo {

	private int id, idUsuario;
	private String matricula, modelo, marca;
	private int estado;
	public Vehiculo() {
		super();
	}
	
	public Vehiculo(int idUsuario, String matricula, String modelo, String marca) {
		super();
		this.idUsuario = idUsuario;
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		this.estado = 100;
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
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", idUsuario=" + idUsuario + ", matricula=" + matricula + ", modelo=" + modelo
				+ ", marca=" + marca + ", estado=" + estado + "]";
	}
	
	
	
}
