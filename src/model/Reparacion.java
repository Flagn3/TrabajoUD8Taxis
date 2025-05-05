package model;

import java.time.LocalDateTime;

public class Reparacion {
	private int id;
	private LocalDateTime fechayhora_ini;
	private LocalDateTime fechayhora_fin;
	private String descripcion;
	private float coste;
	private int idUsuario, idVehiculo;

	public Reparacion() {
		super();
	}

	public Reparacion(LocalDateTime fechayhora_ini, LocalDateTime fechayhora_fin, String descripcion, float coste,
			int idUsuario, int idVehiculo) {
		super();
		this.fechayhora_ini = fechayhora_ini;
		this.fechayhora_fin = fechayhora_fin;
		this.descripcion = descripcion;
		this.coste = coste;
		this.idUsuario = idUsuario;
		this.idVehiculo = idVehiculo;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getFechayhora_ini() {
		return fechayhora_ini;
	}

	public void setFechayhora_ini(LocalDateTime fechayhora_ini) {
		this.fechayhora_ini = fechayhora_ini;
	}

	public LocalDateTime getFechayhora_fin() {
		return fechayhora_fin;
	}

	public void setFechayhora_fin(LocalDateTime fechayhora_fin) {
		this.fechayhora_fin = fechayhora_fin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	@Override
	public String toString() {
		return "Reparacion [id=" + id + ", fechayhora_ini=" + fechayhora_ini + ", fechayhora_fin=" + fechayhora_fin
				+ ", descripcion=" + descripcion + ", coste=" + coste + ", idUsuario=" + idUsuario + ", idVehiculo="
				+ idVehiculo + "]";
	}

}
