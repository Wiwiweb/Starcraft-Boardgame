package tools;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import abstraction.Factory;
import abstraction.Price;
import abstraction.Resource.ResourceType;
import abstraction.creators.BaseCreator;
import abstraction.creators.BuildingCreator;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;
import abstraction.patterns.AreaPattern;
import abstraction.patterns.BasePattern;
import abstraction.patterns.BuildingPattern;
import abstraction.patterns.PlanetPattern;
import abstraction.patterns.PlanetPattern.Cardinal;
import abstraction.patterns.UnitPattern;
import abstraction.patterns.UnitPattern.WalkType;

/**
 * @author William Gautier
 */
public class XmlParser {

	private static final String pathToXml = "./res/data/";

	private final Factory factory;

	public XmlParser(Factory factory) {
		this.factory = factory;
	}

	public void getAll() {
		getUnits();
		getBuildings();
		getBases();
		getRaces();
		getFactions();
		getPlanets();
	}

	public void getPlanets() {
		String filename = "planets.xml";
		Document doc = getXmlFile(pathToXml + filename);

		try {
			NodeList allPlanets = doc.getElementsByTagName("planet");

			for (int i = 0; i < allPlanets.getLength(); i++) {
				Element planet = (Element) allPlanets.item(i);

				// Name
				String name = planet.getAttribute("name");

				// Entrances
				NodeList allEntrances = planet.getElementsByTagName("entrance");
				Cardinal[] startingEntrances = new Cardinal[allEntrances.getLength()];
				for (int j = 0; j < allEntrances.getLength(); j++) {
					String entrance = allEntrances.item(j).getTextContent();
					startingEntrances[j] = Cardinal.valueOf(entrance);
				}

				// Areas
				NodeList allAreas = planet.getElementsByTagName("area");
				AreaPattern[] areas = new AreaPattern[allAreas.getLength()];
				for (int j = 0; j < allAreas.getLength(); j++) {
					Element area = (Element) allAreas.item(j);

					Element resourceElement = (Element) area.getElementsByTagName("resource").item(0);
					ResourceType resourceType = ResourceType.valueOf(resourceElement.getAttribute("type"));
					int resourceNum = Integer.parseInt(resourceElement.getTextContent());
					int unitLimit = parseInt("unitLimit", area);

					areas[j] = new AreaPattern(unitLimit, resourceType, resourceNum);
				}

				PlanetPattern pattern = new PlanetPattern(name, startingEntrances, areas);
				PlanetCreator.addPlanetPattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	public void getUnits() {
		String filename = "units.xml";
		Document doc = getXmlFile(pathToXml + filename);

		try {
			NodeList allUnits = doc.getElementsByTagName("unit");

			for (int i = 0; i < allUnits.getLength(); i++) {
				Element unit = (Element) allUnits.item(i);

				// Name
				String name = unit.getAttribute("name");

				// Price
				Price price = parsePrice("price", unit, factory);

				// Max number
				int maxNum = parseInt("maxNum", unit);

				// WalkType
				WalkType walkType = WalkType.valueOf(getTagValue("walkType", unit));

				// Ground and Air attacks
				boolean groundAttack = parseBoolean("groundAttack", unit);
				boolean airAttack = parseBoolean("airAttack", unit);

				// Support value
				int support = parseInt("support", unit);

				// Is assist
				boolean isAssist = parseBoolean("isAssist", unit);

				UnitPattern pattern = new UnitPattern(name, price, maxNum, walkType, groundAttack, airAttack, support, isAssist);
				UnitCreator.addUnitPattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	public void getBuildings() {
		String filename = "buildings.xml";
		Document doc = getXmlFile(pathToXml + filename);

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
				Element prices = (Element) building.getElementsByTagName("prices").item(0);
				NodeList priceList = prices.getElementsByTagName("price");
				for (int j = 0; j < maxLevel; j++) {

					Element price = null;
					for (int k = 0; k < maxLevel; k++) {
						Element e = (Element) priceList.item(k);
						if (Integer.parseInt(e.getAttribute("level")) == j + 1) {
							price = e;
							break;
						}
					}
					levelPrices[j] = parsePrice(price, factory);
				}

				// Level units
				String[] levelUnits = new String[maxLevel];
				Element units = (Element) building.getElementsByTagName("units").item(0);
				NodeList unitList = units.getElementsByTagName("unit");
				for (int j = 0; j < maxLevel; j++) {

					Element unit = null;
					for (int k = 0; k < maxLevel; k++) {
						Element e = (Element) unitList.item(k);
						if (Integer.parseInt(e.getAttribute("level")) == j + 1) {
							unit = e;
							break;
						}
					}
					levelUnits[j] = unit.getTextContent();
				}

				BuildingPattern pattern = new BuildingPattern(name, maxLevel, levelPrices, levelUnits);
				BuildingCreator.addBuildingPattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	public void getBases() {
		String filename = "bases.xml";
		Document doc = getXmlFile(pathToXml + filename);

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
					buildingNames[j] = buildingList.getElementsByTagName("building").item(j).getTextContent();
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
				ResourceType[] permanentResourcesType = new ResourceType[allResources.getLength()];
				int[] permanentResourcesNum = new int[allResources.getLength()];
				for (int j = 0; j < allResources.getLength(); j++) {
					Element resourceElement = (Element) allResources.item(j);
					ResourceType type = ResourceType.valueOf(resourceElement.getAttribute("type"));
					int num = Integer.parseInt(resourceElement.getTextContent());
					permanentResourcesType[j] = type;
					permanentResourcesNum[j] = num;
				}

				// Workers max number
				int workersMaxNum = parseInt("workersMaxNum", base);

				// Worker price
				Price workerPrice = parsePrice("workerPrice", base, factory);

				// Transports max number
				int transportsMaxNum = parseInt("transportsMaxNum", base);

				// Transport price
				Price transportPrice = parsePrice("transportPrice", base, factory);

				// Bases max number
				int basesMaxNum = parseInt("basesMaxNum", base);

				// Base price
				Price basePrice = parsePrice("basePrice", base, factory);

				BasePattern pattern = new BasePattern(name, buildingNames, modulesMaxNum, availableModules,
						permanentResourcesType, permanentResourcesNum,
						workersMaxNum, workerPrice, transportsMaxNum, transportPrice, basesMaxNum, basePrice);
				BaseCreator.addBasePattern(name, pattern);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	public void getFactions() {
		String filename = "factions.xml";
		Document doc = getXmlFile(pathToXml + filename);

		try {
			NodeList allFactions = doc.getElementsByTagName("faction");

			for (int i = 0; i < allFactions.getLength(); i++) {
				Element faction = (Element) allFactions.item(i);

				// Name
				String name = faction.getAttribute("name");

				// Race
				String raceName = getTagValue("race", faction);

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

				factory.newFaction(name, raceName, baseName, startingWorkers, startingTransports, startingUnitTypes,
						startingUnitNumbers);
			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	public void getRaces() {
		String filename = "races.xml";
		Document doc = getXmlFile(pathToXml + filename);

		try {
			NodeList allRaces = doc.getElementsByTagName("race");

			for (int i = 0; i < allRaces.getLength(); i++) {
				Element race = (Element) allRaces.item(i);

				// Name
				String name = race.getAttribute("name");

				// Abilities
				NodeList abilities = race.getElementsByTagName("ability");
				String[] abilityList = new String[abilities.getLength()];
				for (int j = 0; j < abilities.getLength(); j++) {
					Element ability = (Element) abilities.item(j);
					abilityList[j] = ability.getTextContent();
				}

				factory.newRace(name, abilityList);

			}
		} catch (NullPointerException e) {
			System.err.println("XML syntax error in " + filename);
			e.printStackTrace();
		}
	}

	private Document getXmlFile(String filename) {
		try {
			File xmlFile = new File(filename);
			if (!xmlFile.exists()) {
				throw new FileNotFoundException(filename + " is missing.");
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			dbFactory.setValidating(true);
			dbFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			dBuilder.setErrorHandler(new SimpleErrorHandler());

			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private class SimpleErrorHandler implements ErrorHandler {

		@Override
		public void warning(SAXParseException e) throws SAXException {
			System.out.println("Warning : " + e.getMessage());
		}

		@Override
		public void error(SAXParseException e) throws SAXException {
			System.err.println("Error : " + e.getMessage());
			throw e;
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			System.err.println("Fatal : " + e.getMessage());
			throw e;
		}
	}

	private String getTagValue(String tag, Element parent) {
		return parent.getElementsByTagName(tag).item(0).getTextContent();
	}

	private int parseInt(String name, Element parent) {
		return Integer.parseInt(getTagValue(name, parent));
	}

	private boolean parseBoolean(String name, Element parent) {
		return Boolean.parseBoolean(getTagValue(name, parent));
	}

	private Price parsePrice(String name, Element parent, Factory factory) {
		Element price = (Element) parent.getElementsByTagName(name).item(0);
		int minerals = parseInt("minerals", price);
		int gas = parseInt("gas", price);
		return factory.newPrice(minerals, gas);
	}

	private Price parsePrice(Element price, Factory factory) {
		int minerals = parseInt("minerals", price);
		int gas = parseInt("gas", price);
		return factory.newPrice(minerals, gas);
	}
}
