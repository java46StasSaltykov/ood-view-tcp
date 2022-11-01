package telran.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class InputOutputTest {
	InputOutput io = new ConsoleInputOutput();

	//@Test
	void readObjectTest() {
		Integer[] array = io.readObject("Enter numbers separated by space", "no number ", s -> {

			String strNumbers[] = s.split(" ");
			return Arrays.stream(strNumbers).map(str -> Integer.parseInt(str)).toArray(Integer[]::new);

		});
		io.writeLine(Arrays.stream(array).collect(Collectors.summarizingInt(x -> x)));

	}

	//@Test
	void readIntMinMax() {
		Integer res = io.readInt("Enter any number in range [1, 40]", "no number ", 1, 40);
		io.writeLine(res);
	}
	
	//@Test
	void readLongTest() {
		Long res = io.readLong("Enter number of type long", "no number ");
		io.writeLine(res);
	}
	
	//@Test
	void readOptionsTest() {
		String res = io.readOption("Enter options separated by space", "empty string", new ArrayList<String>());
		io.writeLine(res);
	}
	
	//@Test
	void readDateTest() {
		LocalDate res = io.readDate("Enter date in format YYYY-MM-DD", "not a date ");
		io.writeLine(res);
	}

	//@Test
	void readDateFormatTest() {
		LocalDate res = io.readDate("Enter date in format dd/MM/yyyy", "not a date ", "dd/MM/yyyy");
		io.writeLine(res);
	}

	@Test
	void readPredicateTest() {
		String res = io.readPredicate("Enter email address", "invalid email address ", Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").asMatchPredicate());
		io.writeLine(res);
	}
}