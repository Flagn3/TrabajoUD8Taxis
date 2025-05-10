package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Viaje {
	private Integer id;
	private LocalDate fecha;
	private LocalTime hora;
	private String destino;
	private float km, precio;
	private int idUsuario, idVehiculo;

	public Viaje() {
		super();
	}

	public Viaje(LocalDate fecha, LocalTime hora, String destino, float km, float precio, int idUsuario,
			int idVehiculo) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.destino = destino;
		this.km = km;
		this.precio = precio;
		this.idUsuario = idUsuario;
		this.idVehiculo = idVehiculo;
	}

	public Viaje(Integer id, LocalDate fecha, LocalTime hora, String destino, float km, float precio, int idUsuario,
			int idVehiculo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.destino = destino;
		this.km = km;
		this.precio = precio;
		this.idUsuario = idUsuario;
		this.idVehiculo = idVehiculo;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
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
		return "Viaje [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", destino=" + destino + ", km=" + km
				+ ", precio=" + precio + ", idUsuario=" + idUsuario + ", idVehiculo=" + idVehiculo + "]";
	}

}
