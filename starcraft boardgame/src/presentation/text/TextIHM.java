package presentation.text;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import presentation.IHM;
import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Faction;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Resource;
import abstraction.Resource.ResourceType;

public class TextIHM implements IHM {

	private <T> T selectFromList(List<T> list) {
		int choice = -1;
		while (choice < 1 || choice > list.size()) {

			Iterator<T> it = list.iterator();
			int i = 1;
			while (it.hasNext()) {
				T t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		return list.get(choice - 1);
	}

	private <T> T selectFromListWithCancel(List<T> list) {
		T result;
		int choice = -1;
		while (choice < 1 || choice > list.size() + 1) {

			Iterator<T> it = list.iterator();
			int i = 1;
			while (it.hasNext()) {
				T t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}
			System.out.println(i + " : Cancel");

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		if (choice == list.size() + 1) {
			result = null;
		} else {
			result = list.get(choice - 1);
		}

		return result;
	}

	@Override
	public Faction selectStartingFaction(Player player, List<Faction> list) {
		System.out.println(player.getName() + ", choose your faction :");
		return selectFromList(list);
	}

	@Override
	public Planet selectPlanetToPlace(Player player, List<Planet> list) {
		System.out.println(player.getName() + ", choose which planet to place :");
		return selectFromList(list);
	}

	@Override
	public int choosePlanetRotation(Player player, Planet planet) {
		System.out.println(player.getName() + ", do you want to rotate " + planet.getName() + "?");

		int choice = -1;
		while (choice < 1 || choice > 4) {
			System.out.println("1 : Rotate clockwise");
			System.out.println("2 : Rotate counterclockwise");
			System.out.println("3 : Place planet");
			System.out.println("4 : Cancel");

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		return choice;
	}

	@Override
	public PlanetEntrance selectSpotToPlacePlanet(Player player, List<PlanetEntrance> list, Planet planet) {
		System.out.println(player.getName() + ", choose where to place " + planet.getName() + " :");
		return selectFromListWithCancel(list);
	}

	@Override
	public Area selectAreaToPlaceBase(Player player, List<Area> list) {
		System.out.println(player.getName() + ", choose where to place your base :");
		return selectFromList(list);
	}

	@Override
	public Resource selectResourceToSendWorker(Player player, List<Resource> list, int workerNumber, ResourceType resourceType) {
		System.out.print(player.getName() + ", select where to send a worker for ");

		if (resourceType == ResourceType.MINERALS) {
			System.out.print("mineral");
		} else if (resourceType == ResourceType.GAS) {
			System.out.print("gas");
		} else {
			throw new IllegalArgumentException("Resource asked is neither mineral nor gas.");
		}
		System.out.println(" #" + workerNumber + ":");

		return selectFromList(list);
	}

	@Override
	public Area selectAreaToPlaceUnit(Player player, List<Area> list) {
		System.out.println(player.getName() + ", choose where to place your unit :");
		return selectFromList(list);
	}

	@Override
	public boolean askToPlaceBase(Player player, Planet chosenPlanet) {
		boolean result;
		int choice = -1;
		while (choice < 1 || choice > 2) {
			System.out.println(player.getName() + ", do you want to place your base on " + chosenPlanet.getName());
			System.out.println("1 : Yes");
			System.out.println("2 : No");

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		if (choice == 1) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
