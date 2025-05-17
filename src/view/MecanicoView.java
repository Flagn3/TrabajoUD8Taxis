package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MecanicoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelBotones;
	private JButton btnReparar, btnHistorial, btnExit;
	private Usuario usuarioActivo;

	/**
	 * Create the frame.
	 */
	public MecanicoView(Usuario u) {
		setTitle("Mecanico");
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

		btnReparar = new JButton("Reparar");
		btnReparar.setBounds(65, 168, 173, 45);
		btnReparar.setBackground(new Color(33, 150, 243));
		btnReparar.setForeground(Color.WHITE);
		btnReparar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnReparar.setFocusPainted(false);
		btnReparar.setBorderPainted(false);
		panelBotones.add(btnReparar);

		btnHistorial = new JButton("Historial");
		btnHistorial.setBounds(65, 69, 173, 45);
		btnHistorial.setBackground(new Color(33, 150, 243));
		btnHistorial.setForeground(Color.WHITE);
		btnHistorial.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnHistorial.setFocusPainted(false);
		btnHistorial.setBorderPainted(false);
		panelBotones.add(btnHistorial);

		ImageIcon imgVolver = new ImageIcon("file/back.jpg");
		btnExit = new JButton(imgVolver);
		btnExit.setPreferredSize(new Dimension(40, 40));
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(false);
		btnExit.setBounds(244, 290, 51, 45);
		panelBotones.add(btnExit);

		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

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
				new ReparacionView(usuarioActivo);
			}
		}

	}
}
