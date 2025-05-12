package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Label;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JProgressBar;

public class LoadingScreenView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LoadingScreenView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setTitle("Cargando...");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);
		

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		panel.setBackground(Color.white);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(38, 423, 400, 20);
		progressBar.setStringPainted(true);
		panel.add(progressBar);

		ImageIcon imagen = new ImageIcon("file/TaxiCarga.png");
		setIconImage(imagen.getImage());
		JLabel imagenlbl = new JLabel(imagen);
		imagenlbl.setBounds(38, 10, 400, 400);
		panel.add(imagenlbl);
		
				
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			int progreso = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				progreso += 10;
				progressBar.setValue(progreso);

				if (progreso == 100) {
					dispose();
					new LoginView();
				}
			}
		}, 0, 100);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
