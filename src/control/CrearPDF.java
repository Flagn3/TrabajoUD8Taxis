package control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import model.Reparacion;
import model.Usuario;
import model.Vehiculo;

public class CrearPDF {
	private static Document documento = new Document();
	private static UserController userController = new UserController();
	private static VehiculoController vehiculoController = new VehiculoController();
	private static Vehiculo vehiculo = new Vehiculo();
	private static Usuario usuario = new Usuario();

	public static void PDFRow(Reparacion r) {
		try {
			PdfWriter.getInstance(documento, new FileOutputStream("PDFreparaciones/reparacion_" + r.getId() + ".pdf"));
			documento.open();

			Font FuenteTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
			Font FuenteSubTitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
			Font FuenteDeNegrita = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
			Font FuenteEncabezado = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.WHITE);

			Paragraph titulo = new Paragraph("T-MOVE REPARATIONS - FACTURA", FuenteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(20f);
			documento.add(titulo);

			try {
				vehiculo = vehiculoController.getVehiculo(Conexion.obtener(), r.getIdVehiculo());
				usuario = userController.getUser(Conexion.obtener(), vehiculo.getIdUsuario());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			documento.add(new Paragraph("Cliente "));

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void PDFTabla(List<Reparacion> r) {

	}
}
