
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.AbstractSortingAlgorithm;
import core.AbstractSwappingSortingAlgorithm;
import testing.*;
import testing.comparators.*;
import testing.generation.*;
import testing.generation.conversion.*;

public class Main {

	public static void main(String[] args) {
		final List<Integer> testCases = new ArrayList<>();
		for(int i = 14; i < 1000; ++i) {
			testCases.add(i);
		}
		for(int i = 1000; i < 10000; i += 1000) {
			testCases.add(i);
		}
		for(int i = 10000; i < 100000; i += 10000) {
			testCases.add(i);
		}
		for(int i = 100000; i <= 1000000; i += 100000) {
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
		try (final FileWriter writer = new FileWriter("statistics2.csv")) {
			Test.writeListToCSV(writer, testCases);
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new LinkedListGenerator<>(new OrderedIntegerArrayGenerator())))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new LinkedListGenerator<>(new ReversedIntegerArrayGenerator())))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new LinkedListGenerator<>(new ShuffledIntegerArrayGenerator())))).test();
			(new Test(testCases, writer, new MarkingGenerator<Integer>(new LinkedListGenerator<>(new RandomIntegerArrayGenerator(Integer.MAX_VALUE))))).test();
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

	private void writeListOfSwappingResultsToCSV(final List<testing.results.swapping.Result> list) {
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

	private void writeListOfResultsToCSV(final List<testing.results.Result> list) {
		final ArrayList<String> s = new ArrayList<>();
		for(testing.results.Result result : list) {
			s.add(String.valueOf(result.averageTimeInMilliseconds()));
		}
		writeListToCSV(s);
		
		s.clear();
		for(testing.results.Result result : list) {
			s.add(String.valueOf(result.timeStandardDeviation()));
		}
		writeListToCSV(s);

		s.clear();
		for(int i = 0; i < list.size(); ++i) {
			s.add(String.valueOf(-1));
		}
		writeListToCSV(s);

		s.clear();
		for(int i = 0; i < list.size(); ++i) {
			s.add(String.valueOf(-1));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.Result result : list) {
			s.add(String.valueOf(result.averageComparisons()));
		}
		writeListToCSV(s);

		s.clear();
		for(testing.results.Result result : list) {
			s.add(String.valueOf(result.comparisonsStandardDeviation()));
		}
		writeListToCSV(s);
	}
	
	public void test() {
		final List<testing.results.Result> resultsMerge = getStatistics(new MergeSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Merge done");
		QuickSort.randomPivot = false;
		final List<testing.results.swapping.Result> resultsQuick = getStatistics(new QuickSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Quick done");
		QuickSort.randomPivot = true;
		final List<testing.results.swapping.Result> resultsQuickRandom = getStatistics(new QuickSort<MarkedValue<Integer>>(new MarkedValueComparator<Integer>(new IntegerComparator())));
		System.out.println("Quick (random) done");

		writeListOfResultsToCSV(resultsMerge);
		writeListOfSwappingResultsToCSV(resultsQuick);
		writeListOfSwappingResultsToCSV(resultsQuickRandom);
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
	
	private List<testing.results.Result> getStatistics(AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm) {
		final List<testing.results.Result> ans = new ArrayList<>();
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
