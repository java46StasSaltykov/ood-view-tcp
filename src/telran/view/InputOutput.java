package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.*;

public interface InputOutput {
	
	String readString(String prompt);

	void writeObject(Object obj);

	default void close() {
	}

	default void writeLine(Object obj) {
		String str = obj + "\n";
		writeObject(str);
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		R result = null;
		while (true) {
			String str = readString(prompt);
			try {
				result = mapper.apply(str);
				break;
			} catch (Exception e) {
				writeLine(errorPrompt + e.getMessage());
			}
		}
		return result;

	}

	default Integer readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	default Integer readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(prompt, errorPrompt, s -> {
			int num = Integer.parseInt(s);
			if (num < min) {
				throw new RuntimeException("less than " + min);
			}
			if (num > max) {
				throw new RuntimeException("greater than " + max);
			}
			return num;

		});
	}
	
	default Long readLong(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Long::parseLong);
	}
	
	default String readOption(String prompt, String errorPrompt, List<String> options) {
		return readObject(prompt, errorPrompt, s -> {
			String result = "";
			if (s.isEmpty()) {
				writeLine(errorPrompt); 
			}
			String[] strings = s.split(" "); 
			for (int i = 0; i < strings.length; i++) {
				options.add(strings[i]);
			}
			for (String option: options) {
				result = result.concat(option) + " ";
			}
			return result;
		});
	}
	
	default LocalDate readDate(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, LocalDate::parse);
	}
	
	default LocalDate readDate(String prompt, String errorPrompt, String format) {
		return readObject(prompt, errorPrompt, d -> {
			return LocalDate.parse(d, DateTimeFormatter.ofPattern(format));
		});
	}
	
	default String readPredicate(String prompt, String errorPrompt, Predicate<String> predicate) {
		return readObject(prompt, errorPrompt, s -> {
			if (Pattern.compile(predicate.toString()).matcher(s).matches()) {
				return s;
			} else {
				throw new RuntimeException("");
			}
		});
	}

}