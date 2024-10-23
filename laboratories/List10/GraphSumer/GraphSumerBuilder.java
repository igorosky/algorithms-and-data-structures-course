package GraphSumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class GraphSumerBuilder {
    public class BuilderEdge {
        private final int destination;
        private final int weight;

        public BuilderEdge(final int destination, final int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public BuilderEdge(final Object destination, final int weight) {
            this(destination.hashCode(), weight);
        }

        public int getDestination() {
            return destination;
        }
        
        public int getWeight() {
            return weight;
        }
    }
    
    public class GraphSumerBuilderFriendship {
        private GraphSumerBuilderFriendship() { }
    }

    private final HashSet<Integer> roots;
    private final HashMap<Integer, Vertex> vertices;
    
    public GraphSumerBuilder() {
        roots = new HashSet<>();
        vertices = new HashMap<>();
    }

    public boolean addEdge(final Object node, final int amount, final Collection<BuilderEdge> edges) {
        return addEdge(node.hashCode(), amount, edges);
    }
    
    public boolean addEdge(final int nodeId, final int amount, final Collection<BuilderEdge> edges) {
        if(vertices.containsKey(nodeId)) {
            return false;
        }
        final ArrayList<Edge> destinations = new ArrayList<>();
        if(edges != null) {
            for(final BuilderEdge edge : edges) {
                if(!vertices.containsKey(edge.getDestination())) {
                    return false;
                }
            }
            for(final BuilderEdge edge : edges) {
                roots.remove(edge.getDestination());
            }
            for(final BuilderEdge edge : edges) {
                destinations.add(new Edge(vertices.get(edge.destination), edge.weight));
            }
        }
        roots.add(nodeId);
        vertices.put(nodeId, new Vertex(nodeId, destinations, amount));
        return true;
    }

    public GraphSumer build() {
        final ArrayList<Vertex> roots = new ArrayList<>();
        for(final Integer nodeId : this.roots) {
            roots.add(vertices.get(nodeId));
        }
        // It's ok that objects in final summer are the same ones as in Builder because those are immutable
        return GraphSumer.newGraphSummer(new GraphSumerBuilderFriendship(), roots, vertices).get();
    }
}
