import java.io.IOException;
import java.util.HashMap;

public class WSM {
    public static int SQUARE = 0 , DISK = 1;
    public static void main(String[] args) throws IOException {
        //int numberOfVertices = 1000 , avgDegree = 32, gid = 1, REGION = SQUARE;

        int[] numberOfVertices = {1000, 4000, 16000, 64000, 64000, 4000 ,4000, 20};
        int[] avgDegree = {32, 64, 64, 64, 128, 64, 128};
        int[] REGION = {SQUARE,SQUARE,SQUARE,SQUARE,SQUARE,DISK,DISK, SQUARE};
        int[] gid = {1, 2, 3, 4, 5, 6, 7, 8};
            int i = 7;
            double radius = 0.4;
        //for (int i = 0; i < numberOfVertices.length; i++) {
            PointGenerator pointGenerator = new PointGenerator();
            Vertex[] vertices = pointGenerator.getVertices(numberOfVertices[i], REGION[i]);
            //Graph graph = new Graph(vertices, avgDegree[i], REGION[i]);
            Graph graph = new Graph(radius, vertices, REGION[i]);
            graph.setId(gid[i]);
            graph.getStats();
            HashMap<Integer, Integer>[] adjacencyList = graph.getAdjacencyList();
            VertexColorer vertexColorer = new VertexColorer(adjacencyList);
            vertexColorer.getMaxColors();
            BipartiteTop2 t2 = new BipartiteTop2(adjacencyList, vertexColorer);
            WriteCSV w = new WriteCSV();
            w.pointWriter(graph, vertices, vertexColorer.colors, vertexColorer.order_vertex);
            w.adjacencyWriter(graph);
            w.part1op(graph, vertexColorer, t2);
            w.part2op(vertexColorer, graph);
            w.part3op(graph, t2);
        //}

    }
}
