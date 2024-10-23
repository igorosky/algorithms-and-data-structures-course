
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.AbstractSwappingSortingAlgorithm;
import testing.*;
import testing.comparators.*;
import testing.generation.*;
import testing.generation.conversion.*;

public class Main {
	public static void main(String[] args) {
		final List<Integer> testCases = new ArrayList<>();
		for(int i = 0; i < 1000; ++i) {
			testCases.add(i);
		}
		for(int i = 1000; i < 10000; i += 1000) {
			testCases.add(i);
		}
		for(int i = 10000; i <= 50000; i += 10000) {
			testCases.add(i);
		}
		try (final FileWriter writer = new FileWriter("statistics.csv")) {
			Test.writeListToCSV(writer, testCases);
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new OrderedIntegerArrayGenerator()))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new ReversedIntegerArrayGenerator()))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new ShuffledIntegerArrayGenerator()))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new RandomIntegerArrayGenerator(Integer.MAX_VALUE)))).test();
		}
		catch (final IOException e) {
			System.err.println("IO Error");
		}
	}
}

class Test {
	final List<Integer> testCases;
	final FileWriter writer;
	final Generator<MarkedValue<Integer>> generator;

	public Test(final List<Integer> testCases, final FileWriter writer, final Generator<MarkedValue<Integer>> generator) {
		this.testCases = testCases;
		this.writer = writer;
		this.generator = generator;
	}

	public static <T> void writeListToCSV(final FileWriter writer, final List<T> list) {
		try {
			boolean notFirst = false;
			for(T element : list) {
				if(notFirst) {
					writer.write(',');
				}
				notFirst = true;
				writer.write(element.toString());
			}
			writer.write('\n');
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private <T> void writeListToCSV(final List<T> list) {
		writeListToCSV(writer, list);
	}

	private void writeListOfResultsToCSV(final List<testing.results.swapping.Result> list) {
		final ArrayList<String> s = new ArrayList<>();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.averageTimeInMilliseconds()));
		}
		writeListToCSV(s);
		
		s.clear();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.timeStandardDeviation()));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.averageSwaps()));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.swapsStandardDeviation()));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.averageComparisons()));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.swapping.Result result : list) {
			s.add(String.valueOf(result.comparisonsStandardDeviation()));
		}
		writeListToCSV(s);
	}
	
	public void test() {
		
		
		final List<testing.results.swapping.Result> resultsBubble = getStatistics(new BubbleSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Bubble done");
		final List<testing.results.swapping.Result> resultsInsertion = getStatistics(new InsertionSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Insertion done");
		final List<testing.results.swapping.Result> resultsSelection = getStatistics(new SelectionSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Selection done");
		final List<testing.results.swapping.Result> resultsShaker = getStatistics(new ShakerSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Shaker done");

		writeListOfResultsToCSV(resultsBubble);
		writeListOfResultsToCSV(resultsInsertion);
		writeListOfResultsToCSV(resultsSelection);
		writeListOfResultsToCSV(resultsShaker);
	}
	
	private List<testing.results.swapping.Result> getStatistics(AbstractSwappingSortingAlgorithm<MarkedValue<Integer>> algorithm) {
		final List<testing.results.swapping.Result> ans = new ArrayList<>();
		Generator<MarkedValue<Integer>> generator = new MarkingGenerator<Integer>(new RandomIntegerArrayGenerator(Integer.MAX_VALUE));
		for(Integer testCase : testCases) {
			final var result = Tester.runNTimes(algorithm, generator, testCase, 20);
			assert result.sorted();
			ans.add(result);
			if(testCase >= 10000) {
				System.out.println(testCase);
			}
		}
		return ans;
	}
}
