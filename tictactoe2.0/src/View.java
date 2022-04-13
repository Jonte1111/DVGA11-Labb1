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
	private JLabel score1, score2, ikonLabel, colorScore1, colorScore2, currPlayerText, currPlayerColor; //score1/2 texten innan poäng, colorScore1/2 färgade siffror
	private JOptionPane popUp;
	private BorderLayout b;
	private Model model;
	private ImageIcon ikon;
	private Image resize;
	private Image resize2;
	private int xScore;
	private int oScore;
	private FlowLayout turnLayout;
	private Color orange;
	private Color green;
	public View(Controller controller) {
		model = new Model();
		this.controller = controller;
		xScore = 0;
		oScore = 0;
		spelPlan = new JPanel();
		turnPanel = new JPanel();
		buttonList = new ArrayList<JButton>();
		turnLayout = new FlowLayout();
		orange = new Color(252, 107, 3);
		green = new Color(3, 252, 69);
		turnPanel.setBackground(Color.DARK_GRAY);
		//Skapar en ny ikon
		ikon = new ImageIcon(this.getClass().getResource("/tttlogo.png"), "En vacker syn");
		//Hittade en tråd om hur man ändrar storleken på en ImageIcon
		//https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
		resize = ikon.getImage();
		resize2 = resize.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); 
		ikon = new ImageIcon(resize2);
		ikonLabel = new JLabel(ikon, JLabel.CENTER);
		//score1.setText("<html>Poäng <font color=#fc6b03> X: </font>" + " " + xScore + "</html>");
		//score2.setText("<html>Poäng <font color=#03fc45> O: </font>" + " " + oScore + "</html>");
		score1 = new JLabel("<html>Poäng <font color=#fc6b03> X: </font>" + " " + xScore + "</html>");//<font color=xxxxxx></font> ändrar färgen på det innanför taggen
		score2 = new JLabel("<html>Poäng <font color=#03fc45> O: </font>" + " " + oScore + "</html>");
		score1.setForeground(Color.white);
		score2.setForeground(Color.white);
		currPlayerText = new JLabel("<html><font color=#03fc45>O</font>'s tur" + "</html>");
		currPlayerText.setForeground(Color.white);
		//("<html><font color=#fc6b03>X</font>'s tur" + "</html>");
		//currPlayerColor = new JLabel(model.getPlayer());
		//colorScore1 = new JLabel(xScore + ""); //JLabel behöver en string så kör en fullösning med + ""
		//colorScore2 = new JLabel(oScore + "");
		popUp = new JOptionPane();
		//if(model.getPlayer() == "X")
			//currPlayerColor.setForeground(orange);
		//else
			//currPlayerColor.setForeground(green);
		//colorScore1.setForeground(new Color(252, 107, 3));
		//colorScore2.setForeground(new Color(3, 252, 69));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setLayout(b = new BorderLayout());
		turnLayout.setHgap(20);
		turnPanel.setLayout(turnLayout);
		spelPlan.setLayout(new GridLayout(3,3));
		turnPanel.add(ikonLabel);
		turnPanel.add(score1);
		//turnPanel.add(colorScore1);
		//turnPanel.add(currPlayerColor);
		turnPanel.add(currPlayerText); //Sätt currentPlayer här
		turnPanel.add(score2);
		//turnPanel.add(colorScore2);
		for(int i = 0; i < 9; i++) {
			buttonList.add(new JButton(""));
		}
		for(JButton button : buttonList) {
			String index = Integer.toString(buttonList.indexOf(button));
			button.setFont(new Font("Roboto",Font.BOLD, 75));
			button.setBackground(Color.DARK_GRAY);
			button.setFocusPainted(false); //Tar bort rutorna kring texten i knapparna
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
			/*if(model.arrIsEmpty()) {
				if(model.getPlayer() == "X") {
					this.currP = "X";
					currPlayerText.setText(currP);
				}
				else
					this.currP = "O";
				System.out.println(currP);
			} */
			if(buttonList.get(model.getIndex()).getText() == "" && model.getPlayer() == "X") {
				model.changePlayer();
				//currPlayerColor.setForeground(green);
				currPlayerText.setText("<html><font color=#03fc45>O</font>'s tur" + "</html>");
				buttonList.get(model.getIndex()).setForeground(orange);
				buttonList.get(model.getIndex()).setText("X");
				model.addToArr(model.getIndex(), 1);
			}
			else if(buttonList.get(model.getIndex()).getText() == "" && model.getPlayer() == "O"){
				model.changePlayer();
				//currPlayerColor.setForeground(orange);
				currPlayerText.setText("<html><font color=#fc6b03>X</font>'s tur" + "</html>");
				buttonList.get(model.getIndex()).setForeground(green);
				buttonList.get(model.getIndex()).setText("O");
				model.addToArr(model.getIndex(), 2);
			}
			if(model.checkWin() == 1) {
				System.out.println("Spelare 1 vinner");
				xScore++;
				score1.setText("<html>Poäng <font color=#fc6b03> X: </font>" + " " + xScore + "</html>");
				winPopUp("<html>Vinnaren är <font color=#fc6b03>X</font></html>");
				model.clearLista();
				tomLista();
				model.firstPlayer();
				if(model.getPlayer() == "X") {
					//currPlayerColor.setForeground(orange);
					currPlayerText.setText("<html><font color=#fc6b03>X</font>'s tur" + "</html>");
				}
				else {
					//currPlayerColor.setForeground(green);
					currPlayerText.setText("<html><font color=#03fc45>O</font>'s tur" + "</html>");
				}
				
			}
			else if(model.checkWin() == 2) {
				System.out.println("Spelare 2 vinner");
				oScore++;
				score2.setText("<html>Poäng <font color=#03fc45> O: </font>" + " " + oScore + "</html>");
				winPopUp("<html>Vinnaren är <font color=#03fc45>O</font>");
				model.clearLista();
				tomLista();
				model.firstPlayer();
				if(model.getPlayer() == "X") {
					//currPlayerColor.setForeground(orange);
					currPlayerText.setText("<html><font color=#fc6b03>X</font>'s tur" + "</html>");
				}
				else {
					//currPlayerColor.setForeground(green);
					currPlayerText.setText("<html><font color=#03fc45>O</font>'s tur" + "</html>");
				}
			}
			else if(model.checkWin() == 3) {
				System.out.println("Oavgjort");
				winPopUp("Oavgjort");
				model.clearLista();
				tomLista();
				model.firstPlayer();
				if(model.getPlayer() == "X") {
					//currPlayerColor.setForeground(orange);
					currPlayerText.setText("<html><font color=#fc6b03>X</font>'s tur" + "</html>");
				}
				else {
					//currPlayerColor.setForeground(green);
					currPlayerText.setText("<html><font color=#03fc45>O</font>'s tur" + "</html>");
				}
			}
			
	}
	private void tomLista() {
		for(JButton button : buttonList) 
			button.setText("");
	}
	private void winPopUp(String vinnare) {
		popUp.setBackground(Color.DARK_GRAY);
		popUp.showMessageDialog(this, vinnare);
	}

}
