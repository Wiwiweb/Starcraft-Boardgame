package presentation.text;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import presentation.IHM;
import abstraction.Area;
import abstraction.Resource;
import abstraction.Resource.ResourceType;

public class TextIHM implements IHM {

	public TextIHM() {

	}

	@Override
	public Resource selectResourceFromList(List<Resource> list) {
		int choice = -1;
		while(choice < 0 || choice > list.size()) {
			
			Iterator<Resource> it = list.iterator();
			int i = 1;
			System.out.println("Choose a resource :");
			while (it.hasNext()) {
				Resource r = it.next();
				System.out.println(i + " : " + r);
				i++;
			}

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		return list.get(choice - 1);
	}
	
	@Override
	public Area selectAreaFromList(List<Area> list) {
		int choice = -1;
		while(choice < 0 || choice > list.size()) {
			
			Iterator<Area> it = list.iterator();
			int i = 1;
			System.out.println("Choose an area :");
			while (it.hasNext()) {
				Area r = it.next();
				System.out.println(i + " : " + r);
				i++;
			}

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}

		return list.get(choice - 1);
	}

	@Override
	public void warnSendWorker(int number, ResourceType resourceType) {
		System.out.print("Select where to send a worker for ");
		if(resourceType == ResourceType.MINERALS) {
			System.out.print("mineral");
		} else if(resourceType == ResourceType.GAS) {
			System.out.print("gas");
		} else {
			throw new IllegalArgumentException("Resource asked is neither mineral nor gas.");
		}
		System.out.println(" #" + number + ":");
	}
}
