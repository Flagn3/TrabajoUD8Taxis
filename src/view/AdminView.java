package view;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.AdminController;
import control.Conexion;
import model.Usuario;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JButton bteliminar, bteditar, btexit;
	private JTable table;
	private final AdminController services = new AdminController();
	private List<Usuario> usuarios;

	/**
	 * Create the frame.
	 */
	public AdminView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Admin");

		setContentPane(contentPane);
		contentPane.setLayout(null);

		bteliminar = new JButton("Eliminar");
		bteliminar.setBounds(105, 23, 92, 23);
		contentPane.add(bteliminar);

		bteditar = new JButton("Editar");
		bteditar.setBounds(235, 23, 75, 23);
		contentPane.add(bteditar);

		ImageIcon img = new ImageIcon("file/salir.png");
		btexit = new JButton(img);
		btexit.setBounds(384, 10, 40, 40);
		contentPane.add(btexit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 97, 399, 146);
		contentPane.add(scrollPane);

		table = new JTable();
		showUsuarios();
		scrollPane.setViewportView(table);

		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new AdminView();
	}

	private void showUsuarios() {
		try {
			this.usuarios = this.services.getAllUsers(Conexion.obtener());
			table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

			}, new String[] { "Id", "Nombre", "Apellido", "Contraseña", "Username", "Rol", "Email" }));
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			for (int i = 0; i < this.usuarios.size(); i++) {
				dtm.addRow(new Object[] { this.usuarios.get(i).getId(), this.usuarios.get(i).getNombre(),
						this.usuarios.get(i).getApellido(), this.usuarios.get(i).getUsername(),
						this.usuarios.get(i).getPassword(), this.usuarios.get(i).getRol(),
						this.usuarios.get(i).getEmail() });
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
		}
	}

}
