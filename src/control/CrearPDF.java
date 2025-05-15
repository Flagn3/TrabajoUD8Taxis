package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Reparacion;
import model.Usuario;
import model.Vehiculo;

public class CrearPDF {
	private static UserController userController = new UserController();
	private static VehiculoController vehiculoController = new VehiculoController();
	private static Vehiculo vehiculo = new Vehiculo();
	private static Usuario usuario = new Usuario();
	private static Usuario mecanico = new Usuario();
	private static LocalDate fecha = LocalDate.now();

	private static Font FuenteTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
	private static Font FuenteSubTitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
	private static Font FuenteDeNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	private static Font FuenteEncabezado = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	private static Font FuentePequena = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.DARK_GRAY);
	private static Font FuenteTabla = new Font(Font.FontFamily.HELVETICA, 11);

	public static void PDFRow(Reparacion reparacion) {
		Document documento = new Document();
		try {
			PdfWriter.getInstance(documento,
					new FileOutputStream("PDFreparaciones/factura_" + reparacion.getId() + ".pdf"));
			documento.open();

			Paragraph titulo = new Paragraph("T-MOVE REPARATIONS - FACTURA", FuenteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(20f);
			documento.add(titulo);

			vehiculo = vehiculoController.getVehiculo(Conexion.obtener(), reparacion.getIdVehiculo());
			usuario = userController.getUser(Conexion.obtener(), vehiculo.getIdUsuario());

			documento.add(
					new Paragraph("Cliente : " + usuario.getNombre() + " " + usuario.getApellido(), FuenteSubTitulo));
			documento.add(new Paragraph("Email : " + usuario.getEmail(), FuenteSubTitulo));
			documento.add(new Paragraph("Fecha Factura : " + fecha, FuenteSubTitulo));
			documento.add(Chunk.NEWLINE);
			documento.add(new Paragraph("Vehiculo : " + vehiculo.getMarca() + " " + vehiculo.getModelo()
					+ " Matricula : " + vehiculo.getMatricula(), FuenteSubTitulo));
			documento.add(new Paragraph("Fecha de entrada : " + reparacion.getFecha() + " " + reparacion.getHora(),
					FuenteSubTitulo));
			documento.add(Chunk.NEWLINE);

			PdfPTable tabla = new PdfPTable(3);
			tabla.setWidthPercentage(100);
			tabla.setSpacingBefore(10f);
			tabla.setSpacingAfter(10f);

			String[] cabeceras = { "Descripcion", "Mecanico Responsable", "Coste de la reparacion" };
			for (String string : cabeceras) {
				PdfPCell celda = new PdfPCell(new Phrase(string, FuenteEncabezado));
				celda.setBackgroundColor(new BaseColor(70, 130, 180));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setPadding(8);
				tabla.addCell(celda);
			}

			mecanico = userController.getUser(Conexion.obtener(), reparacion.getIdUsuario());

			tabla.addCell(new PdfPCell(new Phrase(reparacion.getDescripcion(), FuenteTabla)));
			tabla.addCell(new PdfPCell(new Phrase(mecanico.getNombre() + " " + mecanico.getApellido(), FuenteTabla)));
			tabla.addCell(new PdfPCell(new Phrase(String.valueOf(reparacion.getCoste()), FuenteTabla)));

			documento.add(tabla);

			Paragraph firmas = new Paragraph();
			firmas.add(new Chunk("Firma Mecánico", FuenteSubTitulo));
			firmas.add(new Chunk("                         "));
			firmas.add(new Chunk("Firma Cliente", FuenteSubTitulo));
			firmas.add(new Chunk("                         "));
			firmas.add(new Chunk("Sello", FuenteSubTitulo));

			documento.add(firmas);

			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);

			Paragraph PieDePagina = new Paragraph();

			PieDePagina.add(new Paragraph("T-MOVE Taller S.L. - CIF: 54345688X", FuentePequena));
			PieDePagina.add(new Paragraph("Calle del Diablo 1, Madrid, Moralzarzal", FuentePequena));
			PieDePagina.add(new Paragraph("Tel: 945 233 874 - Email: TmoverTalleres@gmail.com", FuentePequena));

			Image logo = Image.getInstance("file/MarcaDeAgua.png");
			logo.scaleToFit(50, 50);
			logo.setAlignment(Element.ALIGN_RIGHT);

			PdfPTable tablaPieDePagina = new PdfPTable(2);
			tablaPieDePagina.setWidthPercentage(100);

			PdfPCell textoCelda = new PdfPCell(PieDePagina);
			textoCelda.setBorder(Rectangle.NO_BORDER);
			textoCelda.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaPieDePagina.addCell(textoCelda);

			PdfPCell imagenCelda = new PdfPCell(logo);
			imagenCelda.setBorder(Rectangle.NO_BORDER);
			imagenCelda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablaPieDePagina.addCell(imagenCelda);

			documento.add(tablaPieDePagina);

			Paragraph gracias = new Paragraph("Gracias por confiar en nuestro taller.", FuenteDeNegrita);
			gracias.setAlignment(Element.ALIGN_CENTER);
			documento.add(gracias);

		} catch (IOException | DocumentException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (documento.isOpen()) {
				JOptionPane.showMessageDialog(null, "PDF creado con exito");
				documento.close();
			}
		}
	}

	public static void PDFTabla(DefaultTableModel modelo) {
		Document documento = new Document();
		try {
			File carpeta = new File("PDFHistorial");
			if (carpeta.isDirectory() && carpeta.exists()) {
				File[] archivos = carpeta.listFiles();
				if (archivos != null && archivos.length == 0) {
					PdfWriter.getInstance(documento, new FileOutputStream("PDFHistorial/Informe1.pdf"));
					documento.open();
				} else {
					Arrays.sort(archivos, Comparator.comparingLong(File::lastModified));
					File ultimoArchivo = archivos[archivos.length - 1];
					String nombreArchivo = ultimoArchivo.getName();
					int numero = 1;
					if (nombreArchivo.startsWith("Informe") && nombreArchivo.endsWith(".pdf")) {
						String numeroStr = nombreArchivo.replaceAll("[^0-9]", "");
						if (!numeroStr.isEmpty()) {
							numero = Integer.parseInt(numeroStr) + 1;
						}
					}
					PdfWriter.getInstance(documento, new FileOutputStream("PDFHistorial/Informe" + numero + ".pdf"));
					documento.open();
				}
			}

			Paragraph titulo = new Paragraph("T-MOVE REPARATIONS - INFORME", FuenteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(20f);
			documento.add(titulo);

			PdfPTable tabla = new PdfPTable(5);
			tabla.setWidthPercentage(100);
			tabla.setSpacingBefore(10f);
			tabla.setSpacingAfter(10f);
			
			String[] cabeceras = { "Fecha", "Hora", "Coste de la reparacion", "Vehiculo", "Taxista" };
			for (String string : cabeceras) {
				PdfPCell celda = new PdfPCell(new Phrase(string, FuenteEncabezado));
				celda.setBackgroundColor(new BaseColor(70, 130, 180));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setPadding(8);
				tabla.addCell(celda);
			}

			for (int i = 0; i < modelo.getRowCount(); i++) {
			    for (int j = 0; j < modelo.getColumnCount(); j++) {
			        Object valor = modelo.getValueAt(i, j);
			        tabla.addCell(new PdfPCell(new Phrase(valor != null ? valor.toString() : "", FuenteTabla)));
			    }
			}

			documento.add(tabla);
			
			Paragraph firmas = new Paragraph();
			firmas.add(new Chunk("Firma Mecánico", FuenteSubTitulo));
			firmas.add(new Chunk("                         "));
			firmas.add(new Chunk("Firma Jefe", FuenteSubTitulo));
			firmas.add(new Chunk("                         "));
			firmas.add(new Chunk("Sello", FuenteSubTitulo));

			documento.add(firmas);

			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);

			Paragraph PieDePagina = new Paragraph();

			PieDePagina.add(new Paragraph("T-MOVE Taller S.L. - CIF: 54345688X", FuentePequena));
			PieDePagina.add(new Paragraph("Calle del Diablo 1, Madrid, Moralzarzal", FuentePequena));
			PieDePagina.add(new Paragraph("Tel: 945 233 874 - Email: TmoverTalleres@gmail.com", FuentePequena));

			Image logo = Image.getInstance("file/MarcaDeAgua.png");
			logo.scaleToFit(50, 50);
			logo.setAlignment(Element.ALIGN_RIGHT);

			PdfPTable tablaPieDePagina = new PdfPTable(2);
			tablaPieDePagina.setWidthPercentage(100);

			PdfPCell textoCelda = new PdfPCell(PieDePagina);
			textoCelda.setBorder(Rectangle.NO_BORDER);
			textoCelda.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaPieDePagina.addCell(textoCelda);

			PdfPCell imagenCelda = new PdfPCell(logo);
			imagenCelda.setBorder(Rectangle.NO_BORDER);
			imagenCelda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablaPieDePagina.addCell(imagenCelda);

			documento.add(tablaPieDePagina);

			Paragraph gracias = new Paragraph("Gracias por confiar en nuestro taller.", FuenteDeNegrita);
			gracias.setAlignment(Element.ALIGN_CENTER);
			documento.add(gracias);
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (documento.isOpen()) {
				JOptionPane.showMessageDialog(null, "PDF creado con exito");
				documento.close();
			}
		}
	}
}
