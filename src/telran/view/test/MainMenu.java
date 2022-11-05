package telran.view.test;

import telran.view.ConsoleInputOutput;
import telran.view.Item;
import telran.view.Menu;

public class MainMenu {

	public static void main(String[] args) {
		Menu menu = new Menu("Main menu", getItems());
		menu.perform(new ConsoleInputOutput());
	}

	private static Item[] getItems() {
		Item[] result = {
			Item.of("Date operations", DateOperationsMenu::getDateOperationsMenu),
			Item.of("Number operations", CalculatorTest::getNumbersOperationsMenu),
			Item.exit()
		};
		return result;
	}
	
}
