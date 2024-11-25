package bowling;

import java.util.ArrayList;

public class Tour {

	private int tour;
	private boolean strike;
	private boolean spare;
	private ArrayList<Lancer> lancers;
	private int numCoup;
	private boolean termine;

	public Tour(int tour) {
		this.tour = tour;
		this.strike = false;
		this.spare = false;
		this.lancers = new ArrayList<>();
		this.numCoup = 0;
		this.termine = false;
	}

	public boolean estTermine() {
		return termine;
	}

	public void enregistreLancer(Lancer lancer) {
		lancers.add(lancer);
		numCoup++;

		if (lancers.size() == 1 && lancer.getNbQuilles() == 10) {
			strike = true;
			termine = true;
		} else if (lancers.size() == 2) {
			if (lancers.get(0).getNbQuilles() + lancer.getNbQuilles() == 10) {
				spare = true;
			}
			termine = true;
		}
	}

	public int calculerScoreSansBonus() {
		int score = 0;
		for (Lancer lancer : lancers) {
			score += lancer.getNbQuilles();
		}
		return score;
	}

	public boolean estStrike() {
		return strike;
	}

	public boolean estSpare() {
		return spare;
	}

	public ArrayList<Lancer> getLancers() {
		return lancers;
	}
}
