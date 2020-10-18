package labor02.num4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MinimalNetwork {
	private int nodeCount;
	private List<Set<Integer>> territories;
	private final TreeSet<Edge> edges;
	private final int initialWeight;
	
	public static void main (String[] args) {
		try {
			MinimalNetwork minimalNetworkSmall = new MinimalNetwork("smallNetwork.csv", ",");
			MinimalNetwork minimalNetworkBig = new MinimalNetwork("network.txt", ",");
			
			System.out.println("Maximum Saving of the small minimal network: " + minimalNetworkSmall.getMaximumSaving());
			System.out.println("Maximum Saving of the big minimal network: " + minimalNetworkBig.getMaximumSaving());
			System.out.println(getTotalWeightOf(minimalNetworkBig.edges));
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public MinimalNetwork (String filename, String regex) throws IOException {
		TreeSet<Edge> initialEdges = readEdgesFromCsv(filename, regex);
		edges = new TreeSet<>();
		initialWeight = getTotalWeightOf(initialEdges);
		createTerritories();
		
		while (territories.size() > 1) {
			Edge edge = initialEdges.pollFirst();
			
			Set<Integer> node1Territory = getTerritoryOfNode(Objects.requireNonNull(edge).getNode1());
			Set<Integer> node2Territory = getTerritoryOfNode(edge.getNode2());
			
			if (!node1Territory.contains(edge.getNode2())) {
				edges.add(edge);
				node1Territory.addAll(node2Territory);
				territories.remove(node2Territory);
			}
		}
	}
	
	private Set<Integer> getTerritoryOfNode (int node) {
		for (Set<Integer> territory : territories) {
			if (territory.contains(node))
				return territory;
		}
		throw new IllegalArgumentException("The given node does not exist!");
	}
	
	private void createTerritories () {
		territories = new ArrayList<>();
		for (int i = 0; i < nodeCount; i++) {
			Set<Integer> territory = new HashSet<>();
			territory.add(i);
			territories.add(territory);
		}
	}
	
	private TreeSet<Edge> readEdgesFromCsv (String filename, String regex) throws IOException {
		try (Stream<String> reader = Files.lines(Paths.get("src", "main", "resources", "labor02", filename))) {
			List<String> lines = reader.collect(Collectors.toList());
			TreeSet<Edge> readEdges = new TreeSet<>();
			List<Integer> nodes = new ArrayList<>();
			
			for (int i = 0; i < lines.size(); i++) {
				List<String> weights = Arrays.asList(lines.get(i).split(regex).clone());
				if (nodes.isEmpty()) {
					nodes = IntStream.range(0, weights.size()).boxed().collect(Collectors.toList());
					nodeCount = nodes.size();
				}
				if (weights.size() != nodeCount || lines.size() != nodeCount)
					throw new IllegalArgumentException("The given file is illegal due to unequal row and column count.");
				readEdges.addAll(createEdge(nodes, i, weights));
			}
			return readEdges;
		}
	}
	
	private Set<Edge> createEdge (List<Integer> nodes, int i, List<String> weights) {
		Set<Edge> currentEdges = new TreeSet<>();
		for (int j = i; j < weights.size(); j++) {
			try {
				int weight = Integer.parseInt(weights.get(j));
				currentEdges.add(new Edge(nodes.get(i), nodes.get(j), weight));
			} catch (NumberFormatException ignored) {}
		}
		return currentEdges;
	}
	
	private static int getTotalWeightOf (Set<Edge> edges) {
		int weight = 0;
		for (Edge e : edges)
			weight += e.getWeight();
		return weight;
	}
	
	private int getMaximumSaving () {
		return initialWeight - getTotalWeightOf(edges);
	}
}

class Edge implements Comparable<Edge> {
	private final int node1;
	private final int node2;
	private final int weight;
	
	public Edge (int node1, int node2, int weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	
	public int getWeight () {
		return weight;
	}
	
	public int getNode1 () {
		return node1;
	}
	
	public int getNode2 () {
		return node2;
	}
	
	@Override
	public int compareTo (Edge o) {
		return Comparator.comparing(Edge::getWeight).thenComparing(Edge::getNode1).thenComparing(Edge::getNode2).compare(this, o);
	}
	
	@Override
	public String toString () {
		return "Edge{" +
				"node1=" + node1 +
				", node2=" + node2 +
				", weight=" + weight +
				'}';
	}
}