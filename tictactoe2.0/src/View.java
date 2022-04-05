import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Använd indexet från controller för att skapa en ny lista i Model, Använd sedan den listan för att kontrollera winstates.

public class View extends JFrame implements Observer{
	private Controller controller;
	private JPanel spelPlan, turnPanel;
	private ArrayList<JButton> buttonList;
	private JLabel score1, score2, ikonLabel, colorScore1, colorScore2; //score1/2 texten innan poäng, colorScore1/2 färgade siffror
	private JOptionPane popUp;
	private BorderLayout b;
	private Model model;
	private ImageIcon ikon;
	private Image resize;
	private Image resize2;
	private int xScore;
	private int oScore;
	public View(Controller controller) {
		model = new Model();
		this.controller = controller;
		xScore = 0;
		oScore = 0;
		spelPlan = new JPanel();
		turnPanel = new JPanel();
		buttonList = new ArrayList<JButton>();
		//Skapar en ny ikon
		ikon = new ImageIcon(this.getClass().getResource("/pagman2.png"), "En vacker syn");
		//Hittade en tråd om hur man ändrar storleken på en ImageIcon
		//https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
		resize = ikon.getImage();
		resize2 = resize.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); 
		ikon = new ImageIcon(resize2);
		ikonLabel = new JLabel(ikon, JLabel.CENTER);
		score1 = new JLabel("Poäng X: ");
		score2 = new JLabel("Poäng O: ");
		colorScore1 = new JLabel(xScore + ""); //JLabel behöver en string så kör en fullösning med + ""
		colorScore2 = new JLabel(oScore + "");
		popUp = new JOptionPane();
		colorScore1.setForeground(Color.magenta);
		colorScore2.setForeground(Color.GREEN);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setLayout(b = new BorderLayout());
		turnPanel.setLayout(new GridLayout(0,5));
		spelPlan.setLayout(new GridLayout(3,3));
		turnPanel.add(ikonLabel);
		turnPanel.add(score1);
		turnPanel.add(colorScore1);
		turnPanel.add(score2);
		turnPanel.add(colorScore2);
		for(int i = 0; i < 9; i++) {
			buttonList.add(new JButton(""));
		}
		for(JButton button : buttonList) {
			String index = Integer.toString(buttonList.indexOf(button));
			button.setFont(new Font("MV Boli", Font.BOLD, 75));
			button.setBackground(Color.white);
			button.setFocusPainted(false);
			spelPlan.add(button);
			button.addActionListener(controller);
			//index = knappens index som en string för att sätta som knappens actioncommand
			button.setActionCommand(index);
		}
		add(spelPlan,b.CENTER);
		add(turnPanel, b.NORTH);
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
			Model model = (Model)o; //Utan denna funkar inte ActionListeners
			if(buttonList.get(model.getIndex()).getText() == "" && model.getPlayer() == 1) {
				model.changePlayer();
				buttonList.get(model.getIndex()).setForeground(Color.MAGENTA);
				buttonList.get(model.getIndex()).setText("X");
				model.addToArr(model.getIndex(), 1);
			}
			else if(buttonList.get(model.getIndex()).getText() == "" && model.getPlayer() == 2){
				model.changePlayer();
				buttonList.get(model.getIndex()).setForeground(Color.GREEN);
				buttonList.get(model.getIndex()).setText("O");
				model.addToArr(model.getIndex(), 2);
			}
			if(model.checkWin() == 1) {
				System.out.println("Spelare 1 vinner");
				xScore++;
				colorScore1.setText(xScore + "");
				winPopUp("Vinnaren är X");
				model.clearLista();
				tomLista();
			}
			else if(model.checkWin() == 2) {
				System.out.println("Spelare 2 vinner");
				oScore++;
				colorScore2.setText(oScore + "");
				winPopUp("Vinnaren är O");
				model.clearLista();
				tomLista();
			}
			else if(model.checkWin() == 3) {
				System.out.println("Oavgjort");
				winPopUp("Oavgjort");
				model.clearLista();
				tomLista();
			}
			
	}
	private void tomLista() {
		for(JButton button : buttonList) 
			button.setText("");
	}
	private void winPopUp(String vinnare) {
		popUp.showMessageDialog(this, vinnare);
	}

}
