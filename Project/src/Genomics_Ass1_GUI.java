import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

public class Genomics_Ass1_GUI {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton_1;
	private String[] Seqs = new String[10000];
	private int stringCounter=0;
	private JTextField textField_1;
	private JTextArea txtrD;
	private String file="";
	private char[][] matrix = new char[24][3];
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Genomics_Ass1_GUI window = new Genomics_Ass1_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Genomics_Ass1_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Add Sequence");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Seqs[stringCounter]=textField.getText();
				txtrD.append(Seqs[stringCounter]+"\n");				
				CenterStar.sequence[stringCounter]=Seqs[stringCounter];
				stringCounter++;				
				
				textField.setText(">");
			}
		});
		btnNewButton.setBounds(24, 96, 122, 23);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setText(">");
		textField.setBounds(10, 52, 764, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterSequences = new JLabel("Enter Sequences :");
		lblEnterSequences.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnterSequences.setBounds(341, 26, 102, 15);
		frame.getContentPane().add(lblEnterSequences);
		
		btnNewButton_1 = new JButton("Cumpute");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file == "")
					JOptionPane.showMessageDialog(null,"File needs to be choose first.");
				else{
					txtrD.setText(CenterStar.cumpute(matrix,stringCounter));
					textField_1.setText(String.valueOf(CenterStar.totalScore));
				}
				
			}
		});
		btnNewButton_1.setBounds(637, 96, 137, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Subtitution Matrix File");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("Open a File");	
				fs.showOpenDialog(null);
				File fi =fs.getSelectedFile();
				try {
					BufferedReader br = new BufferedReader(new FileReader(fi.getPath()));					
					String line ="";					
					while((line = br.readLine())!= null){
						file+=line;
					}
					if(br !=null)
						br.close();	
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				initMatrix();
			}
		});
		btnNewButton_2.setBounds(156, 96, 167, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 130, 764, 418);
		
		frame.getContentPane().add(scrollPane);
		
		txtrD = new JTextArea();
		scrollPane.setViewportView(txtrD);
		txtrD.setEnabled(false);
		txtrD.setFont(new Font("Courier New", Font.BOLD, 12));
		txtrD.setEditable(false);
		txtrD.setForeground(Color.BLACK);		
		
		JLabel lblNewLabel = new JLabel("Score calculated by the file values :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 582, 227, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(231, 580, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}
	
	private void initMatrix(){
		file=file.replace(" ", "");
		for(int i=0;i<24;i++){
			matrix[i] = file.substring(i*3, i*3+3).toCharArray();		
		}
	}
	
}
