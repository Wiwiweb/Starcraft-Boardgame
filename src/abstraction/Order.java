package abstraction;

/**
 * @author William Gautier
 */
public class Order implements Comparable<Order> {

	public static enum OrderType {
		BUILD, MOBILIZE, RESEARCH,
		SPECIAL_BUILD, SPECIAL_MOBILIZE, SPECIAL_RESEARCH;

		@Override
		public String toString() {
			String result;
			switch (this) {
			case BUILD:
				result = "Build";
				break;
			case MOBILIZE:
				result = "Mobilize";
				break;
			case RESEARCH:
				result = "Research";
				break;
			case SPECIAL_BUILD:
				result = "Special Build";
				break;
			case SPECIAL_MOBILIZE:
				result = "Special Mobilize";
				break;
			case SPECIAL_RESEARCH:
				result = "Special Research";
				break;
			default:
				throw new IllegalStateException("Unknown order.");
			}
			return result;
		}
	}

	private final OrderType type;
	private final Player player;
	private final Planet planet;

	public Order(OrderType type, Player player, Planet planet) {
		this.type = type;
		this.player = player;
		this.planet = planet;
	}

	public OrderType getType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}

	public Planet getPlanet() {
		return planet;
	}

	@Override
	public String toString() {
		return "Order on " + planet;
	}

	@Override
	public int compareTo(Order o) {
		return this.getPlanet().compareTo(o.getPlanet());
	}
}
