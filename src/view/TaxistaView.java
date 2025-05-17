package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Usuario;

public class TaxistaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelBotones;
	private JButton gestionarVehiculos, gestionarViajes, exit;
	private Usuario usuarioActivo;

	public TaxistaView(Usuario u) {
		super("Taxista");
		setResizable(false);
		this.usuarioActivo = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1234, 754);
		
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon fondo = new ImageIcon("file/taxiview.jpeg");
				g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelBotones = new JPanel();
		panelBotones.setBounds(473, 161, 305, 345);
		panelBotones.setBackground(new Color(250, 250, 250));
		panelBotones.setBorder(new LineBorder(Color.BLACK, 1));
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		
		gestionarVehiculos = new JButton("Gestionar Vehículos");
		gestionarVehiculos.setBounds(65, 168, 173, 45);
		gestionarVehiculos.setBackground(new Color(33, 150, 243));
		gestionarVehiculos.setForeground(Color.WHITE);
		gestionarVehiculos.setFont(new Font("Segoe UI", Font.BOLD, 14));
		gestionarVehiculos.setFocusPainted(false);
		gestionarVehiculos.setBorderPainted(false);

		gestionarViajes = new JButton("Gestionar Viajes");
		gestionarViajes.setBounds(65, 69, 173, 45);
		gestionarViajes.setBackground(new Color(33, 150, 243));
		gestionarViajes.setForeground(Color.WHITE);
		gestionarViajes.setFont(new Font("Segoe UI", Font.BOLD, 14));
		gestionarViajes.setFocusPainted(false);
		gestionarViajes.setBorderPainted(false);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		exit = new JButton(imgVolver);
		exit.setPreferredSize(new Dimension(40, 40));
		exit.setFocusPainted(false);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setBounds(244, 290, 51, 45);
		panelBotones.add(exit);

		contentPane.setLayout(null);
		panelBotones.add(gestionarVehiculos);
		panelBotones.add(gestionarViajes);

		ManejadorEventos m = new ManejadorEventos();
		gestionarVehiculos.addActionListener(m);
		gestionarViajes.addActionListener(m);
		exit.addActionListener(m);

		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
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
