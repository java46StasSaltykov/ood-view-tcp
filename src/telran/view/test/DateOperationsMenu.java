package telran.view.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class DateOperationsMenu {
	
	public static void main(String[] args) {
		Menu menu = new Menu("Date operations", getItems());
		menu.perform(new ConsoleInputOutput());
	}

	private static Item[] getItems() {
		Item[] result = {
				Item.of("Date after a number of days", DateOperationsMenu::dateAfter),
				Item.of("Date before a number of days", DateOperationsMenu::dateBefore),
				Item.of("Number of days between two dates", DateOperationsMenu::daysBetween),
				Item.of("Age according to birthdate", DateOperationsMenu::ageByBirthdate),
				Item.exit()
			};
		return result;
	}

	public static void getDateOperationsMenu(InputOutput io) {
		DateOperationsMenu.main(null);
		
	}
	
	static void dateBefore(InputOutput io) {
		io.writeLine(LocalDate.now().minusDays(io.readInt("enter number", "no number")));
	}
	
	static void daysBetween(InputOutput io) {
		LocalDate[] dates = enterDates(io);
		ChronoUnit unit = ChronoUnit.DAYS;
		int daysBetween = (int) unit.between(dates[0], dates[1]);
		io.writeLine(daysBetween);
	}
	
	static void ageByBirthdate(InputOutput io) {
		ChronoUnit unit = ChronoUnit.YEARS;
		io.writeLine(unit.between(io.readDate("Enter your birth date in ISO format", "not a date"), LocalDate.now()));
	}
	
	static void dateAfter(InputOutput io) {
		io.writeLine(LocalDate.now().plusDays(io.readInt("enter number", "no number")));
	}

	private static LocalDate[] enterDates(InputOutput io) {
		LocalDate[] res = new LocalDate[2];
		res[0] = io.readDate("Enter first date in ISO format", "not a date");
		res[1] = io.readDate("Enter second date in ISO format", "not a date");
		return res;
	}
}
