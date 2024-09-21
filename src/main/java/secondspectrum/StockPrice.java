package secondspectrum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockPrice {

	static class StockData {
		private final String name;
		private Date firstDate;
		private Date lastDate;
		private double firstValue;
		private double lastValue;

		public StockData(String name, Date date, double value) {
			this.name = name;
			this.firstDate = date;
			this.firstValue = value;
			this.lastDate = date;
			this.lastValue = value;
		}

		public void addData(Date date, double value) {
			if (date.before(firstDate)) {
				firstDate = date;
				firstValue = value;
			}
			if (date.after(lastDate)) {
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

	static private Date parseDate(String dateStr) {
		Date result = null;

		// Without zero-padding, use SimpleDateFormat

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		try {
			result = dateFormat.parse(dateStr);

			return result;
		} catch (ParseException e) {
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

					Date date = parseDate(parts[1]);
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
		String csvFile = "C:\\Users\\Lenovo\\Downloads\\v.csv";

		Map<String, StockData> stockDataMap = readDataFromCSV(csvFile);

		StockData largestIncreaseStock = findLargestIncrease(stockDataMap);

		printResult(largestIncreaseStock);
	}

}
