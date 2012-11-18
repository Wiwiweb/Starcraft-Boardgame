package presentation.text.menus;


public interface IPMenu<T> {

	public T askChoice();

	public T askChoiceWithCancel();

}
