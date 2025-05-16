package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.UserController;
import control.Conexion;
import model.Usuario;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JButton bteliminar, bteditar, btexit, btgrafica;
	private JTable table;
	private final UserController controller = new UserController();
	private List<Usuario> usuarios;
	private static Usuario usuarioActivo;

	/**
	 * Create the frame.
	 */
	public AdminView(Usuario u) {
		this.usuarioActivo = u;
		setTitle("Panel de Administrador");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 245, 245));
		setContentPane(contentPane);

		JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panelArriba.setBackground(new Color(245, 245, 245));
		panelArriba.setPreferredSize(new Dimension(130, 60));
		contentPane.add(panelArriba, BorderLayout.EAST);

		bteliminar = new JButton("Eliminar");
		bteliminar.setBackground(new Color(220, 53, 69));
		bteliminar.setForeground(Color.WHITE);
		bteliminar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		bteliminar.setFocusPainted(false);
		bteliminar.setBorderPainted(false);
		bteliminar.setPreferredSize(new Dimension(100, 35));
		panelArriba.add(bteliminar);

		bteditar = new JButton("Editar");
		bteditar.setBackground(new Color(255, 193, 7));
		bteditar.setForeground(Color.WHITE);
		bteditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		bteditar.setFocusPainted(false);
		bteditar.setBorderPainted(false);
		bteditar.setPreferredSize(new Dimension(100, 35));
		panelArriba.add(bteditar);

		btgrafica = new JButton("Grafica");
		btgrafica.setBackground(new Color(0, 153, 76));
		btgrafica.setForeground(Color.WHITE);
		btgrafica.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btgrafica.setFocusPainted(false);
		btgrafica.setBorderPainted(false);
		btgrafica.setPreferredSize(new Dimension(100, 35));
		panelArriba.add(btgrafica);

		ImageIcon img = new ImageIcon("file/salir.png");
		btexit = new JButton(img);
		btexit.setPreferredSize(new Dimension(40, 40));
		btexit.setFocusPainted(false);
		btexit.setContentAreaFilled(false);
		btexit.setBorderPainted(false);
		panelArriba.add(Box.createHorizontalStrut(30));
		panelArriba.add(btexit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		showUsuarios();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ManejadorEventos m = new ManejadorEventos();
		btexit.addActionListener(m);
		bteliminar.addActionListener(m);
		bteditar.addActionListener(m);
		btgrafica.addActionListener(m);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void showUsuarios() {
		try {
			this.usuarios = this.controller.getAllUsers(Conexion.obtener());
			table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

			}, new String[] { "Id", "Nombre", "Apellido", "Username", "Contraseña", "Rol", "Email" }));
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

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == btexit) {
				dispose();
				new LoginView();
			} else if (o == bteliminar) {
				int filaSeleccionada = table.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int decision = JOptionPane.showConfirmDialog(null,
							"Estas seguro que deseas elimininar este usuario", "Eliminar usuario",
							JOptionPane.YES_NO_OPTION);
					if (decision == 0) {
						try {
							controller.remove(Conexion.obtener(), usuarios.get(filaSeleccionada));
							showUsuarios();
							JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila", "Fila no seleccionada",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (o == bteditar) {
				int filaSeleccionada = table.getSelectedRow();
				if (filaSeleccionada >= 0) {
					new UserRegistrationView(usuarios.get(filaSeleccionada), usuarioActivo);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila", "Fila no seleccionada",
							JOptionPane.ERROR_MESSAGE);

				}

			} else if (o == btgrafica) {
				dispose();
				new EstadisticasView(usuarioActivo);
			}

		}
	}
}
