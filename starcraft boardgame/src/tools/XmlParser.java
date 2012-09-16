package tools;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import abstraction.Area;
import abstraction.Faction;
import abstraction.Galaxy;
import abstraction.Planet;
import abstraction.Planet.Cardinal;
import abstraction.Price;
import abstraction.Resource;
import abstraction.Resource.ResourceType;
import abstraction.creators.BaseCreator;
import abstraction.creators.BuildingCreator;
import abstraction.creators.UnitCreator;
import abstraction.patterns.BasePattern;
import abstraction.patterns.BuildingPattern;
import abstraction.patterns.UnitPattern;
import abstraction.patterns.UnitPattern.WalkType;

public class XmlParser {
	
	public static void getAll() {
		getUnits();
		getBuildings();
		getBases();
		getFactions();
		getPlanets();
	}

	public static void getPlanets() {
		Document doc = getXmlFile("./res/data/planets.xml");
		try {			
			NodeList allPlanets = doc.getElementsByTagName("planet");

			for (int i = 0; i < allPlanets.getLength(); i++) {
				Element planet = (Element) allPlanets.item(i);

				// Name
				String name = planet.getAttribute("name");

				// Entrances
				NodeList allEntrances = planet.getElementsByTagName("entrance");
				Cardinal[] entrances = new Cardinal[allEntrances.getLength()];
				for (int j = 0; j < allEntrances.getLength(); j++) {
					String entrance = allEntrances.item(j).getTextContent();
					entrances[j] = Cardinal.valueOf(entrance);
				}

				// Areas
				NodeList allAreas = planet.getElementsByTagName("area");
				Area[] areas = new Area[allAreas.getLength()];
				for (int j = 0; j < allAreas.getLength(); j++) {
					Element area = (Element) allAreas.item(j);
					
					Element resourceElement = (Element) area.getElementsByTagName("resource").item(0);
					Resource resource = parseResource(resourceElement, false);
					int unitLimit = parseInt("unitLimit", area);
					
					areas[j] = new Area(resource, unitLimit);
				}

				Galaxy.addPlanet(name, new Planet(name, entrances, areas));
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in planets.xml");
			e.printStackTrace();
		}
	}

	public static void getUnits() {
		Document doc = getXmlFile("./res/data/units.xml");

		try {
			NodeList allUnits = doc.getElementsByTagName("unit");

			for (int i = 0; i < allUnits.getLength(); i++) {
				Element unit = (Element) allUnits.item(i);

				// Name
				String name = unit.getAttribute("name");

				// Price
				Price price = parsePrice("price", unit);

				// Max number
				int maxNum = parseInt("maxNum", unit);

				// WalkType
				WalkType walkType = WalkType.valueOf(getTagValue("walkType", unit));

				// Ground and Air attacks
				boolean groundAttack = parseBoolean("groundAttack", unit);
				boolean airAttack = parseBoolean("airAttack", unit);

				// Support value
				int support =  parseInt("support", unit);

				// Is assist
				boolean isAssist = parseBoolean("isAssist", unit);

				UnitPattern pattern = new UnitPattern(name, price, maxNum, walkType, groundAttack, airAttack, support, isAssist);
				UnitCreator.addUnitPattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in units.xml");
			e.printStackTrace();
		}
	}

	public static void getBuildings() {
		Document doc = getXmlFile("./res/data/buildings.xml");

		try {
			NodeList allBuildings = doc.getElementsByTagName("building");

			for (int i = 0; i < allBuildings.getLength(); i++) {
				Element building = (Element) allBuildings.item(i);

				// Name
				String name = building.getAttribute("name");

				// Max level
				int maxLevel = Integer.parseInt(getTagValue("maxLevel", building));

				// Level prices
				Price[] levelPrices = new Price[maxLevel];
				Element priceList = (Element) building.getElementsByTagName("prices").item(0); 
				for (int j = 0; j < maxLevel; j++) {
					levelPrices[j] = parsePrice("level"+(j+1), priceList);
				}

				// Level units
				String[] levelUnits = new String[maxLevel];
				Element unitList = (Element) building.getElementsByTagName("units").item(0); 
				for (int j = 0; j < maxLevel; j++) {
					levelUnits[j] = getTagValue("level"+(j+1), unitList);
				}

				BuildingPattern pattern =  new BuildingPattern(name, maxLevel, levelPrices, levelUnits);
				BuildingCreator.addBuildingPattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in buildings.xml");
			e.printStackTrace();
		}
	}

	public static void getBases() {
		Document doc = getXmlFile("./res/data/bases.xml");

		try {			
			NodeList allBases = doc.getElementsByTagName("base");

			for (int i = 0; i < allBases.getLength(); i++) {
				Element base = (Element) allBases.item(i);

				// Name
				String name = base.getAttribute("name");

				// Buildings
				String[] buildingNames = new String[3];
				Element buildingList = (Element) base.getElementsByTagName("buildings").item(0); 
				for (int j = 0; j < buildingNames.length; j++) {
					buildingNames[j] = getTagValue("building"+(j+1), buildingList);
				}

				// Modules max number
				int modulesMaxNum = parseInt("modulesMaxNum", base);

				// Available modules
				Element moduleList = (Element) base.getElementsByTagName("availableModules").item(0); 
				NodeList allModules = moduleList.getElementsByTagName("module");
				String[] availableModules = new String[allModules.getLength()];	
				for (int j = 0; j < allModules.getLength(); j++) {
					availableModules[j] = allModules.item(j).getTextContent();
				}
				
				// Permanent resources
				Element resourceList = (Element) base.getElementsByTagName("permanentResources").item(0);
				NodeList allResources = resourceList.getElementsByTagName("resource");
				Resource[] permanentResources = new Resource[allResources.getLength()];
				for (int j = 0; j < allResources.getLength(); j++) {
					Element resource = (Element) allResources.item(j);
					permanentResources[j] = parseResource(resource, true);
				}

				// Workers max number
				int workersMaxNum = parseInt("workersMaxNum", base);

				// Worker price
				Price workerPrice = parsePrice("workerPrice", base);

				// Transports max number
				int transportsMaxNum = parseInt("transportsMaxNum", base);

				// Transport price
				Price transportPrice = parsePrice("transportPrice", base);

				// Bases max number
				int basesMaxNum = parseInt("basesMaxNum", base);

				// Base price
				Price basePrice = parsePrice("basePrice", base);


				BasePattern pattern = new BasePattern(name, buildingNames, modulesMaxNum, availableModules, permanentResources,
						workersMaxNum, workerPrice, transportsMaxNum, transportPrice, basesMaxNum, basePrice);
				BaseCreator.addBasePattern(name, pattern);
			}		
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in bases.xml");
			e.printStackTrace();
		}
	}

	public static void getFactions() {
		Document doc = getXmlFile("./res/data/factions.xml");

		try {			
			NodeList allFactions = doc.getElementsByTagName("faction");

			for (int i = 0; i < allFactions.getLength(); i++) {
				Element faction = (Element) allFactions.item(i);

				// Name
				String name = faction.getAttribute("name");
				
				// Base
				String baseName = getTagValue("base", faction);


				Element startingList = (Element) faction.getElementsByTagName("startingUnits").item(0); 

				// Starting workers
				int startingWorkers = parseInt("workers", startingList);

				// Starting transports
				int startingTransports = parseInt("transports", startingList);

				// Starting units type & number
				NodeList startingUnitsList = startingList.getElementsByTagName("unit");
				String[] startingUnitTypes = new String[startingUnitsList.getLength()];
				int[] startingUnitNumbers = new int[startingUnitsList.getLength()];
				for (int j = 0; j < startingUnitsList.getLength(); j++) {
					Element startingUnit = (Element) startingUnitsList.item(j);
					startingUnitTypes[j] = startingUnit.getAttribute("type");
					startingUnitNumbers[j] = Integer.parseInt(startingUnit.getTextContent());
				}

				Faction pattern = new Faction(name, baseName, startingWorkers, startingTransports, startingUnitTypes, startingUnitNumbers);
				Faction.addFaction(name, pattern);
			}		
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in faction.xml");
			e.printStackTrace();
		}
	}


	private static Document getXmlFile(String filename) {
		try {
			File xmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getTagValue(String tag, Element e) {
		return e.getElementsByTagName(tag).item(0).getTextContent();
	}

	private static int parseInt(String name, Element e) {
		return Integer.parseInt(getTagValue(name, e));
	}

	private static boolean parseBoolean(String name, Element e) {
		return Boolean.parseBoolean(getTagValue(name, e));
	}

	private static Price parsePrice(String name, Element e) {
		Element price = (Element) e.getElementsByTagName(name).item(0);
		int minerals =  parseInt("minerals", price);
		int gas =  parseInt("gas", price);
		return new Price(minerals, gas);
	}
	
	private static Resource parseResource(Element resource, boolean isPermanent) {
		ResourceType type =  ResourceType.valueOf(resource.getAttribute("type"));
		int num =  Integer.parseInt(resource.getTextContent());
		return new Resource(type, num, isPermanent);
	}
}
