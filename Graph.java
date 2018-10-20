import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    public static final int SQUARE = 0, DISK = 1;
    int numberOfVertices, avgDegree, totalDeg, act_avg, id, REGION;
    double radius;
    HashMap<Integer, Integer>[] adjacencyList;
    ArrayList<Integer>[] cells;
    Vertex[] vertices;

    Graph(Vertex[] vertices, int avgDegree, int REGION){
        this.numberOfVertices = vertices.length;
        this.avgDegree = avgDegree;
        this.vertices = vertices;
        this.REGION = REGION;
        createAdjacencyList(vertices, this.avgDegree, REGION);
    }
    Graph(double radius, Vertex[] vertices, int REGION){
        this.numberOfVertices = vertices.length;
        this.radius = radius;
        this.vertices = vertices;
        this.REGION = REGION;
        createAdjacencyList(vertices, this.avgDegree, REGION);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void createAdjacencyList(Vertex[] vertices, int avgDegree, int REGION){
        adjacencyList = new HashMap[numberOfVertices];

        distributeIntoCells(vertices, avgDegree, REGION);

        switch (REGION){
            case SQUARE:{
                //compare cells here
                int n = (int)Math.ceil(1/radius);
                for (int i = 0; i < cells.length-1; i++) {
                    compareCells(i,i);
                    if(i%(n-1) != 0 || i == 0)
                        compareCells(i,i+1);
                    if (i<(cells.length - n)) {
                        compareCells(i, i + n);
                        if (i%(n-1)!=0 || i == 0)
                            compareCells(i, i + n + 1);
                        if (i%(n)!=0)
                            compareCells(i, i + n - 1);
                    }
                }
            }

            case DISK:{
                //compare cells
                //int n = (2*4*(int)Math.ceil(1/radius));
                int i;
                for (i = 0; i < cells.length-1; i++) {
                    compareCells(i,i);
                    compareCells(i,i+1);
                    if (i%8 == 0 && i != 0)
                        compareCells(i, i-8);
                    if(i < cells.length-9) {
                        compareCells(i, i+8);
                        if(i%7 != 0 || i == 0)
                            compareCells(i, i+9);
                    }
                }
                compareCells(i,i);
                break;
            }

        }
    }
    public void getStats(){
        int totalDegree = 0;
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i] != null) {
                for (Integer j : adjacencyList[i].values()) {
                    totalDegree++;
                }
            }
        }

        this.totalDeg = totalDegree;
        this.act_avg = totalDegree / numberOfVertices;

    }

    public HashMap<Integer,Integer>[] getAdjacencyList(){
        return this.adjacencyList;
    }

    public void distributeIntoCells(Vertex[] vertices, int avgDegree, int REGION){
        switch(REGION){
            case SQUARE:{
 //1. calculate neighbourhood radius r = sqrt(avg/N)
                if (radius == 0)
                    radius = Math.sqrt(((double)(avgDegree)/(double)(vertices.length)))*0.6;

//2. number of cells along length(n)= 1/r
//total number of cells is n^2
                int n = (int)Math.ceil(1/radius);
                cells = new ArrayList[n*n+1];

//3. cell number V(x,y)= flr(y/r) * n + flr(x/r)
                for (Vertex v: vertices) {
                    int nextCell = ((int)Math.floor(v.y/radius))*n+(int)(Math.floor(v.x/radius));
                    if (cells[nextCell]==null)
                        cells[nextCell] = new ArrayList();
                    cells[nextCell].add(v.id);
                }
                break;
            }

            case DISK:{
                if (radius == 0)
                    radius = Math.sqrt(((double)(avgDegree)/(double)(vertices.length)))*1.09;
                int n = (2*4*(int)Math.ceil(1/radius));
                cells = new ArrayList[n+1];

                // 2 PI steps to cover one circle i.e 8 steps or PI/4
                for (int i = 0; i < vertices.length; i++) {
                    int nextCell = (int)(8*Math.floor(vertices[i].radius/radius))+(int)(Math.floor(4*vertices[i].theta/Math.PI));
                    if (cells[nextCell]==null)
                        cells[nextCell] = new ArrayList();
                    cells[nextCell].add(vertices[i].id);
                }
                break;
            }
        }

    }

    public void compareCells(int cell1, int cell2){
        double rsquare = radius*radius;
        if (cells[cell1] != null && cells[cell2] != null) {
            for (Integer v1 : cells[cell1]) {
                for (Integer v2 : cells[cell2]) {
                    if (v1 != v2) {
                        if ((Math.pow((vertices[v2].x - vertices[v1].x), 2) + Math.pow((vertices[v2].y - vertices[v1].y), 2)) < rsquare) {
                            if (adjacencyList[v1] == null)
                                adjacencyList[v1] = new HashMap();
                            if (!adjacencyList[v1].containsKey(v2))
                                adjacencyList[v1].put(v2,v2);

                            if (adjacencyList[v2] == null)
                                adjacencyList[v2] = new HashMap();
                            if (!adjacencyList[v2].containsKey(v1))
                                adjacencyList[v2].put(v1,v1);
                        }

                    }
                }
            }
        }
    }
}
