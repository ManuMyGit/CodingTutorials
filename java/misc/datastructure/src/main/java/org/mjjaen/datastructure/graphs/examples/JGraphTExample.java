package org.mjjaen.datastructure.graphs.examples;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JGraphTExample extends GraphExample {
    private static final String FRIEND = "friend";
    private static final String ENEMY = "enemy";

    @Override
    public void run() {
        Graph<String, DefaultEdge> stringGraph = createStringGraph();
        log.info(stringGraph.toString());

        Graph<URI, DefaultEdge> hrefGraph = createHrefGraph();
        log.info(hrefGraph.toString());
        URI start = hrefGraph.vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny().get();
        log.info(start.toString());

        Graph<String, RelationshipEdge> graph = new DefaultDirectedGraph<>(RelationshipEdge.class);
        List<String> people = new ArrayList<>();
        people.add("John");
        people.add("James");
        people.add("Sarah");
        people.add("Jessica");

        // John is everyone's friend
        for (String person : people) {
            graph.addVertex(person);
            if (!person.equals("John")) {
                graph.addEdge("John", person, new RelationshipEdge(FRIEND));
            }
        }

        // Apparently James doesn't really like John
        graph.addEdge("James", "John", new RelationshipEdge(ENEMY));

        // Jessica is Sarah and James's friend
        graph.addEdge("Jessica", "Sarah", new RelationshipEdge(FRIEND));
        graph.addEdge("Jessica", "James", new RelationshipEdge(FRIEND));

        // But Sarah doesn't really like James
        graph.addEdge("Sarah", "James", new RelationshipEdge(ENEMY));

        assert graph.getEdge("James", "John").getLabel().equals(ENEMY);
    }

    private Graph<String, DefaultEdge> createStringGraph() {
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);

        return g;
    }

    private Graph<URI, DefaultEdge> createHrefGraph() {
        Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        URI google = null, wikipedia = null, jgrapht = null;
        try {
            google = new URI("http://www.google.com");
            wikipedia = new URI("http://www.wikipedia.org");
            jgrapht = new URI("http://www.jgrapht.org");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);

        return g;
    }
}

class RelationshipEdge extends DefaultEdge {
    private String label;

    public RelationshipEdge(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
    }
}