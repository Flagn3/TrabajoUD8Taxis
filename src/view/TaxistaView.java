package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuario;

public class TaxistaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton gestionarVehiculos, gestionarViajes, exit;
	private Usuario usuarioActivo;

	public TaxistaView(Usuario u) {
		super("Taxista");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		gestionarVehiculos = new JButton("Gestionar Vehículos");
		gestionarVehiculos.setBounds(61, 128, 163, 42);
		
		gestionarViajes = new JButton("Gestionar Viajes");
		gestionarViajes.setBounds(61, 57, 163, 42);

		ImageIcon img = new ImageIcon("file/salir.png");
		exit = new JButton(img);
		exit.setBounds(246, 223, 40, 40);
		contentPane.add(exit);

		contentPane.setLayout(null);
		contentPane.add(gestionarVehiculos);
		contentPane.add(gestionarViajes);

		ManejadorEventos m = new ManejadorEventos();
		gestionarVehiculos.addActionListener(m);
		gestionarViajes.addActionListener(m);
		exit.addActionListener(m);

		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == exit) {
				dispose();
				new LoginView();
			} else if (o == gestionarVehiculos) {
				dispose();
				new GestionarVehiculosView(usuarioActivo);
			} else if (o == gestionarViajes) {
				dispose();
				new GestionarViajesView(usuarioActivo);
			}

		}

	}

}
