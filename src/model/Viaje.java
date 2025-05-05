package model;

import java.time.LocalDateTime;

public class Viaje {
	private int id;
	private LocalDateTime fechayhora_ini;
	private LocalDateTime fechayhora_fin;
	private String destino;
	private float km, precio;
	private int idUsuario, idVehiculo;

	public Viaje() {
		super();
	}

	public Viaje(LocalDateTime fechayhora_ini, LocalDateTime fechayhora_fin, String destino, float km, float precio,
			int idUsuario, int idVehiculo) {
		super();
		this.fechayhora_ini = fechayhora_ini;
		this.fechayhora_fin = fechayhora_fin;
		this.destino = destino;
		this.km = km;
		this.precio = precio;
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

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
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
		return "Viaje [id=" + id + ", fechayhora_ini=" + fechayhora_ini + ", fechayhora_fin=" + fechayhora_fin
				+ ", destino=" + destino + ", km=" + km + ", precio=" + precio + ", idUsuario=" + idUsuario
				+ ", idVehiculo=" + idVehiculo + "]";
	}

}
