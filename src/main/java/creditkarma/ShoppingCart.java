package creditkarma;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	/*
	 * A very strangely designed e-commerce platform represents the user's
	 * shopping cart as a set of strings. When a customer completes a purchase,
	 * they receive a receipt which lists the name of all the items and their
	 * price (including tax), finishing with the total cost of the items, and
	 * the total amount of taxes paid. Unfortunately, the previous checkout
	 * system stopped working, and you've been tasked with implementing a
	 * replacement but there's no budget or scope to fix the shopping cart
	 * system so you need to conform to the contract of the existing system.
	 *
	 * Basic sales tax is applicable at a rate of 10% on all goods except books,
	 * food, and medical products, which are exempt. Import tax is applicable on
	 * all imported goods at a rate of 5% with no exemptions. Taxes are rounded
	 * up to the nearest 0.05.
	 *
	 * For the purposes of this exercise, ignore I/O and provide a function of
	 * form `List<string> -> string`
	 *
	 * Example inputs:
	 *
	 * Input 1: 1 book at 12.49 1 music CD at 14.99 1 chocolate bar at 0.85
	 *
	 * Output 1: 1 book: 12.49 1 music CD: 16.49 1 chocolate bar: 0.85 Sales
	 * Taxes: 1.50 Total: 29.83
	 *
	 * Input 2: 1 imported box of chocolates at 10.00 1 imported bottle of
	 * perfume at 47.50
	 *
	 * Output 2: 1 imported box of chocolates: 10.50 1 imported bottle of
	 * perfume: 54.65 Sales Taxes: 7.65 Total: 65.15
	 *
	 * Input 3: 1 imported bottle of perfume at 27.99 1 bottle of perfume at
	 * 18.99 1 packet of headache pills at 9.75 1 box of imported chocolates at
	 * 11.25
	 *
	 * Output 3: 1 imported bottle of perfume: 32.19 1 bottle of perfume: 20.89
	 * 1 packet of headache pills: 9.75 1 imported box of chocolates: 11.85
	 * Sales Taxes: 6.70 Total: 74.68
	 *
	 * tax rounded to 0.05 cent
	 *
	 */

	/*
	 * To execute Java, please define "static void main" on a class named
	 * Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */

	public String getCategory(String s) {
		if (s.contains("chocolate")) {
			return "food";
		}
		if (s.contains("book")) {
			return "books";
		}
		if (s.contains("headache pills")) {
			return "medical products";
		}
		return "else";
	}

	private double getPrice(String s) {
		int index = s.lastIndexOf("at");
		String p = s.substring(index + 3);
		return Double.valueOf(p);
	}

	private double round(double tax) {
		System.out.println(tax);
		double res = Math.ceil(tax * 20) / 20.0;
		System.out.println(res);
		return res;
	}

	private String parse(double price) {
		String s = String.valueOf(price);
		int index = s.lastIndexOf(".");
		if (index + 2 >= s.length())
			s = s + "0";
		return s.substring(0, index + 3);
	}

	public String shoppingCart(List<String> input) {
		StringBuilder sb = new StringBuilder();
		double tax = 0.0;
		double sum = 0.0;

		for (String s : input) {
			double price = getPrice(s);
			double t = 0.0;
			if (getCategory(s).equals("else")) {
				t += round(price * 0.1);
			}

			if (s.contains("imported")) {
				t += round(price * 0.05);

			}

			tax += t;
			sum += t;
			sum += price;

			int index = s.lastIndexOf("at");
			sb.append(s.substring(0, index - 1));
			sb.append(": ").append(parse(price + t)).append("\n");
		}

		sb.append("Sales Taxes: ").append(parse(tax)).append("\n");
		sb.append("Total: ").append(parse(sum)).append("\n");
		return sb.toString();

	}

	public static void main(String[] args) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("1 book at 12.49");
		strings.add("1 music CD at 14.99");
		strings.add("1 chocolate bar at 0.85");
		ShoppingCart s = new ShoppingCart();
		System.out.println(s.shoppingCart(strings));

		ArrayList<String> strings2 = new ArrayList<String>();
		strings2.add("1 imported box of chocolates at 10.00");
		strings2.add("1 imported bottle of perfume at 47.50");
		System.out.println(s.shoppingCart(strings2));

		ArrayList<String> strings3 = new ArrayList<String>();
		strings3.add("1 imported bottle of perfume at 27.99");
		strings3.add("1 bottle of perfume at 18.99");
		strings3.add("1 packet of headache pills at 9.75");
		strings3.add("1 box of imported chocolates at 11.25");

		System.out.println(s.shoppingCart(strings3));
	}

}
