package bowling;

import java.util.ArrayList;

public class PartieMonoJoueur {

	private ArrayList<Tour> tours;
	private boolean termine;

	public PartieMonoJoueur() {
		this.tours = new ArrayList<>();
		this.termine = false;
		this.tours.add(new Tour(1)); // Le premier tour est déjà créé
	}

	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee()) {
			throw new IllegalStateException("La partie est déjà terminée");
		}

		Tour dernierTour = getDernierTour();

		Lancer lancer = new Lancer(nombreDeQuillesAbattues);
		dernierTour.enregistreLancer(lancer);

		if (dernierTour.estTermine() && tours.size() < 10) {
			tours.add(new Tour(tours.size() + 1));
		}

		if (tours.size() == 10 && dernierTour.estTermine()) {
			termine = true; // Partie terminée après 10 tours
		}

		return !dernierTour.estTermine();
	}


	public int score() {
		int scoreTotal = 0;
		for (int i = 0; i < tours.size(); i++) {
			Tour tour = tours.get(i);
			scoreTotal += tour.calculerScoreSansBonus();

			// Si le tour est un strike ou un spare, on ajoute les bonus correspondants
			if (tour.estStrike() && i < 9) {
				scoreTotal += calculerBonusStrike(i);
			} else if (tour.estSpare() && i < 9) {
				scoreTotal += calculerBonusSpare(i);
			}
		}
		return scoreTotal;
	}

	private int calculerBonusStrike(int indexTour) {
		int bonus = 0;
		if (indexTour + 1 < tours.size()) {
			Tour prochainTour = tours.get(indexTour + 1);
			bonus += prochainTour.calculerScoreSansBonus();
			if (prochainTour.estStrike() && indexTour + 2 < tours.size()) {
				bonus += tours.get(indexTour + 2).calculerScoreSansBonus();
			}
		}
		return bonus;
	}

	private int calculerBonusSpare(int indexTour) {
		if (indexTour + 1 < tours.size()) {
			return tours.get(indexTour + 1).getLancers().get(0).getNbQuilles();
		}
		return 0;
	}

	public boolean estTerminee() {
		return termine || tours.size() == 10 && getDernierTour().estTermine();
	}

	public int numeroTourCourant() {
		if (estTerminee()) {
			return 0; // Partie terminée, pas de tour courant
		}
		return tours.size();
	}

	public int numeroProchainLancer() {
		if (estTerminee()) {
			return 0; // Partie terminée, pas de prochain lancer
		}

		Tour dernierTour = getDernierTour();
		if (dernierTour.getLancers().size() < 2 && !dernierTour.estStrike()) {
			return 1; // Premier lancer du tour
		} else if (dernierTour.getLancers().size() == 2 || dernierTour.estStrike()) {
			return 1; // Lancer suivant dans le tour
		}
		return 0;
	}

	private Tour getDernierTour() {
		return tours.get(tours.size() - 1); // Retourner le dernier tour
	}
}
