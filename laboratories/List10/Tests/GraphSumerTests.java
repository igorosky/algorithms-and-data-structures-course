package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import GraphSumer.GraphSumer;
import GraphSumer.GraphSumerBuilder;

public class GraphSumerTests {
    private static Random rand;
    private GraphSumer graphSummer;
    private GraphSumerBuilder graphSummerBuilder;

    private final int NODE_COUNT = 2000;
    private final int TEST_COUNT = 30;
    private final int MAX_AMOUNT = 1000;
    private final int MAX_COUNT = 1000;

    private void build() {
        graphSummer = graphSummerBuilder.build();
    }

    private int getAmount() {
        return rand.nextInt(MAX_AMOUNT);
    }

    private int getCount() {
        return rand.nextInt(MAX_COUNT);
    }

    private void generateGraph() {
        graphSummerBuilder = new GraphSumerBuilder();
        graphSummerBuilder.addEdge(0, getAmount(), new ArrayList<>());
        for(int nodeId = 1; nodeId < NODE_COUNT; ++nodeId) {
            final ArrayList<GraphSumerBuilder.BuilderEdge> destinations = new ArrayList<>();
            final int destinationsCount = rand.nextInt(nodeId + 1);
            for(int i = 0; i < destinationsCount; i++) {
                destinations.add(graphSummerBuilder.new BuilderEdge(rand.nextInt(nodeId), getCount()));
            }
            graphSummerBuilder.addEdge(nodeId, getAmount(), destinations);
        }
    }

    @BeforeClass
    public static void BeforeClass() {
        rand = new Random(System.currentTimeMillis());
    }

    @Before
    public void BeforeEach() {
        graphSummerBuilder = new GraphSumerBuilder();
    }

    @Test
    public void randomTest() {
        generateGraph();
        build();
        graphSummer.getAllRootsValue();
    }

    @Test
    public void randomTests() {
        for(int i = 0; i < TEST_COUNT; ++i) {
            BeforeEach();
            randomTest();
        }
    }

    @Test
    public void randomTestSync() {
        generateGraph();
        build();
        graphSummer.getAllRootsValueParallel();
    }

    @Test
    public void randomTestsSync() {
        for(int i = 0; i < TEST_COUNT; ++i) {
            BeforeEach();
            randomTestSync();
        }
    }

    // @Test
    public void randomTestSync2() {
        generateGraph();
        build();
        graphSummer.getAllRootsValueParallel2();
    }

    // @Test
    public void randomTestsSync2() {
        for(int i = 0; i < TEST_COUNT; ++i) {
            BeforeEach();
            randomTestSync2();
        }
    }

    @Test
    public void randomTestThreads() {
        generateGraph();
        build();
        graphSummer.getAllRootsValueThreads();
    }

    @Test
    public void randomTestsThreads() {
        for(int i = 0; i < TEST_COUNT; ++i) {
            BeforeEach();
            randomTestThreads();
        }
    }

    @Test
    public void basicTest() {
        // Products
        graphSummerBuilder.addEdge(0, 3, null);
        graphSummerBuilder.addEdge(1, 10, null);

        // Semi-products
        graphSummerBuilder.addEdge(
            5,
            15,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(0, 1)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            3,
            0,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(0, 7)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            2,
            7,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(0, 2),
                graphSummerBuilder.new BuilderEdge(1, 1)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            4,
            8,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(1, 1)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            6,
            10,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(1, 6)
            ).collect(Collectors.toList()));

        // Ingredients
        graphSummerBuilder.addEdge(
            7,
            2,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(5, 2)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            8,
            0,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(5, 6),
                graphSummerBuilder.new BuilderEdge(3, 10),
                graphSummerBuilder.new BuilderEdge(2, 15)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            9,
            17,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(3, 2),
                graphSummerBuilder.new BuilderEdge(2, 1)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            10,
            13,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(2, 6),
                graphSummerBuilder.new BuilderEdge(4, 6)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            11,
            4,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(6, 12)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            12,
            1,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(6, 3)
            ).collect(Collectors.toList()));
        graphSummerBuilder.addEdge(
            13,
            4,
            Stream.of(
                graphSummerBuilder.new BuilderEdge(1, 10)
            ).collect(Collectors.toList()));
        
        build();
        final Collection<GraphSumer.RootValue> results = graphSummer.getAllRootsValue();
        final HashMap<Integer, Long> correctAnswer = new HashMap<>();

        correctAnswer.put(7, (long)38);
        correctAnswer.put(8, (long)663);
        correctAnswer.put(9, (long)82);
        correctAnswer.put(10, (long)259);
        correctAnswer.put(11, (long)844);
        correctAnswer.put(12, (long)211);
        correctAnswer.put(13, (long)104);

        for(final GraphSumer.RootValue val : results) {
            assertTrue(correctAnswer.containsKey(val.getNodeId()));
            assertEquals((long)correctAnswer.get(val.getNodeId()), val.getValue());
        }

        correctAnswer.put(0, (long)3);
        correctAnswer.put(1, (long)10);
        correctAnswer.put(5, (long)18);
        correctAnswer.put(3, (long)21);
        correctAnswer.put(2, (long)23);
        correctAnswer.put(4, (long)18);
        correctAnswer.put(6, (long)70);

        correctAnswer.forEach((final Integer key, final Long value) -> {
            final OptionalLong ans = graphSummer.getValue(key);
            assertTrue(ans.isPresent());
            assertEquals((long)value, ans.getAsLong());
        });
    }
}
