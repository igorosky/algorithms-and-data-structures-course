package GraphSumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import GraphSumer.GraphSumerBuilder.GraphSumerBuilderFriendship;

public class GraphSumer {
    private final Collection<? super Vertex> roots;
    private final HashMap<Integer, Vertex> vertices;
    
    private GraphSumer(
        final Collection<? super Vertex> roots,
        final HashMap<Integer, Vertex> vertices
    ) {
        this.roots = roots;
        this.vertices = vertices;
    }

    public static Optional<GraphSumer> newGraphSummer(
        final GraphSumerBuilderFriendship _x,
        final Collection<? super Vertex> roots,
        final HashMap<Integer, Vertex> vertices
    ) {
        return _x == null ? Optional.empty() : Optional.of(new GraphSumer(roots, vertices));
    }

    public OptionalLong getValue(final int nodeId) {
        if(vertices.containsKey(nodeId)) {
            return OptionalLong.of(vertices.get(nodeId).getSum());
        }
        return OptionalLong.empty();
    }

    public OptionalLong getValue(final Object node) {
        return getValue(node.hashCode());
    }

    public class RootValue {
        private final int nodeId;
        private final long value;

        private RootValue(final int nodeId, final long value) {
            this.nodeId = nodeId;
            this.value = value;
        }
        
        public int getNodeId() {
            return nodeId;
        }
        
        public long getValue() {
            return value;
        }
    }
    
    @SuppressWarnings("unchecked")
    public Collection<RootValue> getAllRootsValue() {
        final ArrayList<RootValue> ans = new ArrayList<>();
        ans.ensureCapacity(roots.size());
        for(final Vertex root : (Collection<Vertex>)roots) {
            ans.add(new RootValue(root.getNodeId(), root.getSum()));
        }
        return ans;
    }
    
    @SuppressWarnings("unchecked")
    public Collection<RootValue> getAllRootsValueParallel() {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final ArrayList<Callable<RootValue>> runnables = new ArrayList<>();
        runnables.ensureCapacity(roots.size());
        for(final Vertex root : (Collection<Vertex>)roots) {
            runnables.add(new Callable<RootValue>() {

                @Override
                public RootValue call() throws Exception {
                    return new RootValue(root.getNodeId(), root.getSumSync());
                }
                
            });
        }
        final ArrayList<RootValue> ans = new ArrayList<>();
        ans.ensureCapacity(roots.size());
        try {
            for(final Future<RootValue> val : executor.invokeAll(runnables)) {
                ans.add(val.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ans;
    }

    @SuppressWarnings("unchecked")
    public Collection<RootValue> getAllRootsValueParallel2() {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final ArrayList<Callable<RootValue>> runnables = new ArrayList<>();
        runnables.ensureCapacity(roots.size());
        for(final Vertex root : (Collection<Vertex>)roots) {
            runnables.add(new Callable<RootValue>() {

                @Override
                public RootValue call() throws Exception {
                    return new RootValue(root.getNodeId(), root.getSumSync(executor));
                }
                
            });
        }
        final ArrayList<RootValue> ans = new ArrayList<>();
        ans.ensureCapacity(roots.size());
        try {
            for(final Future<RootValue> val : executor.invokeAll(runnables)) {
                ans.add(val.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ans;
    }
    
    @SuppressWarnings("unchecked")
    public Collection<RootValue> getAllRootsValueThreads() {
        final ArrayList<RootValue> ans = new ArrayList<>();
        final ArrayList<Thread> threads = new ArrayList<>();
        ans.ensureCapacity(roots.size());
        threads.ensureCapacity(roots.size());
        for(final Vertex root : (Collection<Vertex>)roots) {
            ans.add(null);
            final int p = ans.size() - 1;
            final Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    ans.set(p, new RootValue(root.getNodeId(), root.getSumThreads()));
                }
                
            });
            thread.start();
            threads.add(thread);
        }
        for(final Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
}
