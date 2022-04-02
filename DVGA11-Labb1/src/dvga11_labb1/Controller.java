package dvga11_labb1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
	private Model model;
	public Controller(Model model) {
		this.model = model;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*
		int i = buttonList.indexOf(e.getSource()); 	//i �r indexet f�r den knappen som tryckts p� 
		if(p.getP() == 1 && buttonList.get(i).getText() == "") {
			p.changePlayer();
			turnPanel.setForeground(Color.red);
			turnPanel.setText("Aktuell spelare �r: " + p.getP());
			buttonList.get(i).setForeground(Color.red);
			buttonList.get(i).setText("X");//L�gger till text p� den knapp som tryckts p�
			popUp(wl.showWinner(wl.checkGameOver(buttonList)));
		}
		else if (buttonList.get(i).getText() == "") {
			p.changePlayer();
			turnPanel.setText("Aktuell spelare �r: " + p.getP());
			buttonList.get(i).setForeground(Color.blue);
			buttonList.get(i).setText("O");
			popUp(wl.showWinner(wl.checkGameOver(buttonList)));
			
		}
		//Om n�gon vinner, k�r removeAction som tar bort lyssnarna p� knapparna
		if((wl.checkGameOver(buttonList)) == 1 || wl.checkGameOver(buttonList) == 2 || wl.checkGameOver(buttonList) == 3) 
			removeAction();
		*/
		//G�r om Den tryckta knappens ActionCommand till en int f�r att anv�nda den som index
		if(e.getActionCommand() == "start")
			model.startGame();
		else if(e.getActionCommand() != "start") {
			int i = Integer.parseInt(e.getActionCommand());
			model.setButtonPressed(i);
			model.changePlayer();
		}
	}
	public void setView(View view) {

	}

}
