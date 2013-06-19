package presentation.text.menus;

/**
 * @author William Gautier
 */
public interface IPMenu<T> {

	public T askChoice(boolean cancel);

}
