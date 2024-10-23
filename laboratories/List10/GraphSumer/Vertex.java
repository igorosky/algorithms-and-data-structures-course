package GraphSumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.OptionalLong;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Vertex {
    private final int nodeId;
    private final Collection<Edge> destinations;
    private final int value;
    private OptionalLong sum;

    public Vertex(final Object node, final Collection<Edge> destinations, final int value) {
        this(node.hashCode(), destinations, value);
    }

    public Vertex(final int nodeId, final Collection<Edge> destinations, final int value) {
        this.nodeId = nodeId;
        this.destinations = destinations;
        this.value = value;
        this.sum = OptionalLong.empty();
    }

    public int getNodeId() {
        return nodeId;
    }

    public Collection<Edge> getDestinations() {
        return destinations;
    }

    public int getValue() {
        return value;
    }

    public long getSum() {
        if(sum.isEmpty()) {
            long ans = value;
            for(final Edge edge : destinations) {
                ans += edge.getWeight() * edge.getDestination().getSum();
            }
            sum = OptionalLong.of(ans);
        }
        return sum.getAsLong();
    }

    public synchronized long getSumSync() {
        if(sum.isEmpty()) {
            long ans = value;
            for(final Edge edge : destinations) {
                ans += edge.getWeight() * edge.getDestination().getSumSync();
            }
            sum = OptionalLong.of(ans);
        }
        return sum.getAsLong();
    }

    public synchronized long getSumSync(final ExecutorService executor) {
        if(sum.isEmpty()) {
            final ArrayList<Callable<Long>> runnables = new ArrayList<>();
            runnables.ensureCapacity(destinations.size());
            for(final Edge edge : destinations) {
                executor.submit(new Callable<Long>() {

                    @Override
                    public Long call() {
                        return edge.getWeight() * edge.getDestination().getSumSync(executor);
                    }
                    
                });
            }
            long ans = value;
            try {
                for(final Future<Long> val : executor.invokeAll(runnables)) {
                    ans += val.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }   
            sum = OptionalLong.of(ans);
        }
        return sum.getAsLong();
    }

    public long getSumThreads() {
        if(sum.isEmpty()) {
            final ArrayList<Thread> threads = new ArrayList<>();
            final long[] sums = new long[destinations.size()];
            int i = 0;
            for(final Edge edge : destinations) {
                final int p = i++;
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        sums[p] = edge.getWeight() * edge.getDestination().getSumSync();
                    }
                    
                });
                thread.start();
                threads.add(thread);
            }
            for(final Thread thread : threads) {
                try {
                    thread.join();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long ans = value;
            for(final long v : sums) {
                ans += v;
            }
            sum = OptionalLong.of(ans);
        }
        return sum.getAsLong();
    }
}
