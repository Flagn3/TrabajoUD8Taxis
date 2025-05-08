package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TaxistaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton gestionarVehiculos, gestionarViajes, exit;
	
	
	
	/**
	 * Create the frame.
	 */
	public TaxistaView() {
		super("Taxista");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		gestionarVehiculos = new JButton("Gestionar Vehículos");
		gestionarVehiculos.setBounds(61, 128, 163, 42);
		gestionarViajes = new JButton("Gestionar Viajes");
		gestionarViajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gestionarViajes.setBounds(61, 57, 163, 42);
		contentPane.setLayout(null);
		
		contentPane.add(gestionarVehiculos);
		contentPane.add(gestionarViajes);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new TaxistaView();
	}
}
