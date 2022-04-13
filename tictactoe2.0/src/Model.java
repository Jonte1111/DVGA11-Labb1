import java.util.Observable;

public class Model extends Observable{
	private int index;
	private double p;
	private String player;
	private int[] winList;
	public Model() {
		winList = new int[9];
		p = Math.random();
		player = "O";
	}
	public void firstPlayer() {
		if(p <= 0.5)
			this.player = "X";
		else
			this.player = "O";
		System.out.println(player);
	}
	public String getPlayer() {
		return this.player;
	}
	public void changePlayer() {
		if(this.player == "X")
			this.player = "O";
		else
			this.player = "X";
	}
	public int getIndex() {
		return this.index;
	}
	public void setIndex(int index) {
		this.index = index;
		setChanged();
		notifyObservers();
	}
	//Lägger till 1 eller 2 i listan på samma plats som knappen som trycktes på, 1 och 2 är spelarna
	public void addToArr(int index, int player) {
		winList[index] = player;
	}
	public void clearLista() {
		for(int i = 0; i < winList.length; i++)
			winList[i] = 0;
	}
	public int checkWin() {
		//Spelare 1
		if(winList[0] == 1 && winList[1] == 1 && winList[2] == 1) 
			return 1;
		else if(winList[3] == 1 && winList[4] == 1 && winList[5] == 1)
			return 1;
		else if(winList[6] == 1 && winList[7] == 1 && winList[8] == 1)
			return 1;
		
		else if(winList[0] == 1 && winList[3] == 1 && winList[6] == 1)
			return 1;
		else if(winList[1] == 1 && winList[4] == 1 && winList[7] == 1)
			return 1;
		else if(winList[2] == 1 && winList[5] == 1 && winList[8] == 1)
			return 1;
		
		else if(winList[0] == 1 && winList[4] == 1 && winList[8] == 1)
			return 1;
		else if(winList[2] == 1 && winList[4] == 1 && winList[6] == 1)
			return 1;
		//Spelare 2
		else if(winList[0] == 2 && winList[1] == 2 && winList[2] == 2) 
			return 2;
		else if(winList[3] == 2 && winList[4] == 2 && winList[5] == 2)
			return 2;
		else if(winList[6] == 2 && winList[7] == 2 && winList[8] == 2)
			return 2;
		
		else if(winList[0] == 2 && winList[3] == 2 && winList[6] == 2)
			return 2;
		else if(winList[1] == 2 && winList[4] == 2 && winList[7] == 2)
			return 2;
		else if(winList[2] == 2 && winList[5] == 2 && winList[8] == 2)
			return 2;
		
		else if(winList[0] == 2 && winList[4] == 2 && winList[8] == 2)
			return 2;
		else if(winList[2] == 2 && winList[4] == 2 && winList[6] == 2)
			return 2;
		else if(checkDraw())
			return 3;
		else
			return 0;
	}
	//Returnerar true endast om det inte finns några 0:or i arrayen
	public boolean checkDraw() {
		for(int i = 0; i < winList.length;) {
			//Om det finns en nolla, returnera false
			if(winList[i] == 0) {
				return false;
			}
			//Är i inte en nolla, fortsätt
			else
				i++;
		}
		return true;
	}
	public boolean arrIsEmpty() {
		for(int i = 0; i < winList.length;) {
			if(winList[i] == 0)
			i++;
			else 
				return false;
		}
		return true;
	}
}
