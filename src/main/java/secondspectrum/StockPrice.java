package secondspectrum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class StockPrice {

	static class StockData {
		private final String name;
		private LocalDate firstDate;
		private LocalDate lastDate;
		private double firstValue;
		private double lastValue;

		public StockData(String name, LocalDate date, double value) {
			this.name = name;
			this.firstDate = date;
			this.firstValue = value;
			this.lastDate = date;
			this.lastValue = value;
		}

		public void addData(LocalDate date, double value) {
			if (date.isBefore(firstDate)) {
				firstDate = date;
				firstValue = value;
			}
			if (date.isAfter(lastDate)) {
				lastDate = date;
				lastValue = value;
			}
		}

		public String getName() {
			return name;
		}

		public double getFirstValue() {
			return firstValue;
		}

		public double getLastValue() {
			return lastValue;
		}
	}

	static private LocalDate parseDate(String dateStr) {
		LocalDate result = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-M-d");
			result = LocalDate.parse(dateStr, formatter);
			return result;
		} catch (DateTimeParseException e) {
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-M-dd");
			result = LocalDate.parse(dateStr, formatter);
			return result;
		} catch (DateTimeParseException e) {
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-d");
			result = LocalDate.parse(dateStr, formatter);
			return result;
		} catch (DateTimeParseException e) {
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			result = LocalDate.parse(dateStr, formatter);
			return result;
		} catch (DateTimeParseException e) {
		}
		return result;
	}

	static Map<String, StockData> readDataFromCSV(String csvFile) {
		Map<String, StockData> stockDataMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 5) {
					String name = parts[0];

					LocalDate date = parseDate(parts[1]);
					if (date == null)
						continue;

					String valueStr = parts[3].trim();

					double value;
					try {
						value = Double.parseDouble(valueStr);
					} catch (NumberFormatException e) {
						continue;

					}

					if (!stockDataMap.containsKey(name)) {
						stockDataMap.put(name,
								new StockData(name, date, value));
					} else {
						StockData stockData = stockDataMap.get(name);
						stockData.addData(date, value);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockDataMap;
	}

	static StockData findLargestIncrease(Map<String, StockData> stockDataMap) {
		StockData largestIncreaseStock = null;
		double largestIncrease = 0.0;

		for (Map.Entry<String, StockData> entry : stockDataMap.entrySet()) {
			StockData stockData = entry.getValue();

			double increase = stockData.getLastValue()
					- stockData.getFirstValue();

			if (increase > largestIncrease) {
				largestIncrease = increase;
				largestIncreaseStock = stockData;
			}
		}

		return largestIncreaseStock;
	}

	static void printResult(StockData largestIncreaseStock) {
		if (largestIncreaseStock != null) {
			double largestIncrease = largestIncreaseStock.lastValue
					- largestIncreaseStock.firstValue;
			if (largestIncrease > 0.0) {
				System.out.printf("公司: %s, 股价增值: %.6f%n",
						largestIncreaseStock.getName(), largestIncrease);
				return;
			}
		}
		System.out.println("nil");
	}

	public static void main(String[] args) {
		String csvFile = "values.csv";

		Map<String, StockData> stockDataMap = readDataFromCSV(csvFile);

		StockData largestIncreaseStock = findLargestIncrease(stockDataMap);

		printResult(largestIncreaseStock);
	}

}
