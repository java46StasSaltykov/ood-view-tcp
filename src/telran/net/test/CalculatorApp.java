package telran.net.test;

import java.io.*;
import java.util.*;
import telran.net.TcpHandler;
import telran.view.*;

public class CalculatorApp {

	private static final int PORT = 3000;

	public static void main(String[] args) {
		try {
			Calculator calculator = new NetCalculatorProxy(new TcpHandler("localhost", PORT));
			Item[] items = CalculatorMenu.getCalculatorItems(calculator);
			Item exit = Item.of("Exit", io -> {
				if (calculator instanceof Closeable) {
					try {
						((Closeable) calculator).close();
					} catch (IOException e) {
						throw new RuntimeException("can not be closed " + e.getMessage());
					}
				}
			}, true);
			ArrayList<Item> menuItems = new ArrayList<>(Arrays.asList(items));
			menuItems.add(exit);
			Menu menu = new Menu("Calculator", menuItems);
			menu.perform(new ConsoleInputOutput());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
