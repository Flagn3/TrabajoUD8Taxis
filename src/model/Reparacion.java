package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reparacion {
	private Integer id;
	private LocalDate fecha;
	private LocalTime hora;
	private String descripcion;
	private float coste;
	private int idUsuario, idVehiculo;

	public Reparacion() {
		super();
	}

	public Reparacion(LocalDate fecha, LocalTime hora, String descripcion, float coste, int idUsuario, int idVehiculo) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.descripcion = descripcion;
		this.coste = coste;
		this.idUsuario = idUsuario;
		this.idVehiculo = idVehiculo;
	}

	public Reparacion(Integer id, LocalDate fecha, LocalTime hora, String descripcion, float coste, int idUsuario,
			int idVehiculo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.descripcion = descripcion;
		this.coste = coste;
		this.idUsuario = idUsuario;
		this.idVehiculo = idVehiculo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "Reparacion [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", descripcion=" + descripcion
				+ ", coste=" + coste + ", idUsuario=" + idUsuario + ", idVehiculo=" + idVehiculo + "]";
	}

}
