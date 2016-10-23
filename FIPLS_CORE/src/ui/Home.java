package ui;

import inconsistency.Inconsistency;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import problemsolver.ProblemSolver;

import knowledge.KnowledgeBean;

import dataload.DataLoader;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1106, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCandidateTypePredictor = new JLabel("Candidate Type Predictor ");
		lblCandidateTypePredictor.setFont(new Font("Stencil", Font.BOLD, 20));
		lblCandidateTypePredictor.setBounds(384, 33, 349, 32);
		contentPane.add(lblCandidateTypePredictor);
		
		JButton btnRefreshTheList = new JButton("REFRESH THE LIST");
		btnRefreshTheList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataLoader data = new DataLoader();
				String[] column = {"AttribueName","Value"} ;
				try {
					table_1.setModel( new DefaultTableModel( data.getAttributes(), column) );
					//table_1.getModel().getColumnClass(0).
					System.out.println(table_1.getModel().getValueAt(0, 1));
					table_1.repaint();
					contentPane.repaint();
					System.out.println("Repainted");
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefreshTheList.setBounds(458, 94, 162, 23);
		contentPane.add(btnRefreshTheList);

		
		
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(423, 581, 46, 14);
		contentPane.add(lblResult);
		
		 final TextArea textArea = new TextArea();
		textArea.setBounds(496, 512, 456, 160);
		contentPane.add(textArea);
		
		JButton btnPredict = new JButton("Predict");
		btnPredict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataLoader load = new DataLoader();
				KnowledgeBean bean = null;
				try {
					 bean = load.loadKnowledgeBean();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProblemSolver sol = new ProblemSolver();
				java.util.List values = new ArrayList();
				for(int i=0; i<table_1.getModel().getRowCount();i++){
					values.add(table_1.getModel().getValueAt(i, 0)+","+table_1.getModel().getValueAt(i, 1));
				}
				try {
					textArea.setText(sol.predictor(bean, values));
					textArea.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textArea.setText("Data entered has some problem. Please chack with the input and try again...");
					textArea.repaint();
				}
				
			}
		});
		btnPredict.setBounds(180, 555, 187, 67);
		contentPane.add(btnPredict);
		table_1 = new JTable();
		table_1.setBounds(180, 128, 740, 351);
		
		
		contentPane.add(table_1);
		
		
	}
}
