package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Label;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;

import javax.swing.BorderFactory;
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
        setLocationRelativeTo(null);
		setResizable(false);
        setTitle("Cargando...");
        
        ImageIcon iconoVentana = new ImageIcon("file/TaxiCarga.png");
		setIconImage(iconoVentana.getImage());
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.white);

        setContentPane(contentPane);
        contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setBounds(0, 0, 500, 500);
        contentPane.add(panel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(38, 423, 400, 20);
        progressBar.setForeground(new Color(66, 135, 245));
        progressBar.setBackground(new Color(230, 230, 230));
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(66, 135, 245), 2));
        panel.add(progressBar);

		ImageIcon imagenOriginal = new ImageIcon("file/TaxiCarga.png");
		Image img = imagenOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(img);
		JLabel imagenlbl = new JLabel(imagenEscalada);
		imagenlbl.setBounds(38, 10, 400, 400);
		panel.add(imagenlbl);
		
				
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			int progreso = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				progreso += 10;
                if (progreso < 30) {
                    progressBar.setValue(progreso);
                progressBar.setString("Inicinado " + progreso + "%");
                }
                if (progreso >= 30 && progreso < 80) {
                    progressBar.setValue(progreso);
                    progressBar.setString("Cargando datos " + progreso + "%");
                }
                if (progreso >= 80 && progreso < 100) {
                    progressBar.setValue(progreso);
                    progressBar.setString("Listo para iniciar " + progreso + "%");
                }
                if (progreso == 100) {
                    timer.cancel();
                    dispose();
                    new LoginView();
                }
			}
		}, 0, 100);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}