package abstraction;

import java.util.ArrayList;
import java.util.List;

import presentation.IHM;
import presentation.text.TextIHM;

public class Game {

	public static IHM ihm = new TextIHM();

	private List<Player> playerList = new ArrayList<Player>();

	private Player firstPlayer = null;

	public void setupGame() {

		final int numberOfPlayers = getPlayerList().size();

		// 1. Choose the First Player
		final int firstPlayerIndex = (int) (Math.random() * numberOfPlayers);
		setFirstPlayer(getPlayerList().get(firstPlayerIndex));

		// 2. Choose Factions
		for (Player p : getPlayerListByOrder()) {
			//TODO
		}

	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public List<Player> getPlayerListByOrder() {
		@SuppressWarnings("unchecked")
		List<Player> orderedPlayerList = (ArrayList<Player>) ((ArrayList<Player>) playerList).clone();

		Player nextPlayer = null;
		while (nextPlayer != firstPlayer) {
			nextPlayer = orderedPlayerList.get(0);
			if (nextPlayer != firstPlayer) {
				orderedPlayerList.remove(nextPlayer);
				orderedPlayerList.add(nextPlayer);
			}
		}
		return orderedPlayerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
}
