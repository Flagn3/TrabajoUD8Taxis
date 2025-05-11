package view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuario;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MecanicoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnReparar, btnHistorial, btnExit;
	private Usuario usuarioActivo;

	/**
	 * Create the frame.
	 */
	public MecanicoView(Usuario u) {
		setTitle("Mecanico");
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnReparar = new JButton("Reparar");
		btnReparar.setBounds(85, 100, 100, 60);
		panel.add(btnReparar);

		btnHistorial = new JButton("Historial");
		btnHistorial.setBounds(230, 100, 100, 60);
		panel.add(btnHistorial);

		ImageIcon img = new ImageIcon("file/salir.png");
		btnExit = new JButton(img);
		btnExit.setBounds(376, 203, 40, 40);
		panel.add(btnExit);

		setVisible(true);
		setLocationRelativeTo(null);

		ManejadorEventos me = new ManejadorEventos();
		btnExit.addActionListener(me);
		btnHistorial.addActionListener(me);
		btnReparar.addActionListener(me);

	}

	public class ManejadorEventos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (btnExit == e.getSource()) {
				dispose();
				new LoginView();
			}

			if (btnHistorial == e.getSource()) {
				dispose();
				new HistorialView(usuarioActivo);
			}

			if (btnReparar == e.getSource()) {
				dispose();
				// new RepararView();
			}
		}

	}
}
