import java.util.HashMap;

public class VertexColorer {
    int[] order_vertex;
    int[] vertex_degreeR;
    int[] colors;
    int max_degree = 0, min_degree = 0;
    HashMap<Integer, Integer> colorMax, colorCount;

    VertexColorer(HashMap<Integer,Integer>[] adjacency_list){
        getVertexOrder(adjacency_list);
        getColors(adjacency_list);
    }

    void getVertexOrder(HashMap<Integer,Integer>[] adjacency_list){
        //1. build adjacency degree-structure
        IntegerLinkedList[] degreeStructure = new IntegerLinkedList[adjacency_list.length];
        VertexSub[] vertexSubs = new VertexSub[degreeStructure.length];
        for (int i = 0; i < adjacency_list.length; i++){
            int degree;
            //pick each vertex and find its degree
            if (adjacency_list[i] == null){
                degree = 0;
            }
            else {
                degree = adjacency_list[i].keySet().size();
            }

            //insert vertex into its corresponding degree - list
            if (degreeStructure[degree] == null){
                degreeStructure[degree] = new IntegerLinkedList();
            }
            vertexSubs[i] = new VertexSub(degree, i);
            degreeStructure[degree].addLast(vertexSubs[i]);
            max_degree = max_degree >= degree? max_degree : degree;
            if (i == 0)
                min_degree = degree;
            else
                min_degree = min_degree <= degree ? min_degree : degree;
        }


        //2. Create vertex order
        order_vertex = new int[adjacency_list.length];
        vertex_degreeR = new int[order_vertex.length];
        for (int i = 0; i < order_vertex.length; i++) {
            //find vertex with smallest order
            for (int j = 0; j < degreeStructure.length; j++) {
                if (degreeStructure[j] != null) {
                    if (!degreeStructure[j].isEmpty()) {
                        VertexSub lowestDegreeVertex = degreeStructure[j].removeFirst();
                        //record degree when removed
                        vertex_degreeR[lowestDegreeVertex.id] = lowestDegreeVertex.degree;
                        lowestDegreeVertex.degree = 0;
                        //add the vertex to the order
                        order_vertex[i] = lowestDegreeVertex.id;

                        //shift neighbors of the vertex from n to n-1 degree list
                        if (adjacency_list[lowestDegreeVertex.id] != null) {
                            for (Integer neighbor : adjacency_list[lowestDegreeVertex.id].keySet()) {
                                if (vertexSubs[neighbor].degree != 0) {
                                    VertexSub nVertex = vertexSubs[neighbor];
                                    degreeStructure[nVertex.degree].remove(nVertex);
                                    if (degreeStructure[nVertex.degree - 1] == null)
                                        degreeStructure[nVertex.degree - 1] = new IntegerLinkedList();
                                    degreeStructure[nVertex.degree - 1].addLast(nVertex);
                                    nVertex.degree--;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

    }

    public void getColors(HashMap<Integer,Integer>[] adjacency_list){
        colors = new int[order_vertex.length];
        for (int i = order_vertex.length-1; i > -1 ; i--) {
            int current = order_vertex[i];
            int[] colortemp = new int[max_degree];

            //record colors of its neighbours
            if (adjacency_list[current] != null) {
                for (Integer neighbour : adjacency_list[current].keySet()) {
                    colortemp[colors[neighbour]] = 1;
                }
            }
            int j;
            //iterate the temp array to find least color
            for (j = 1; j < colortemp.length; j++) {
                if (colortemp[j] == 0)
                    break;
            }

            colors[current] = j;
        }
    }

    public void getMaxColors(){
        HashMap<Integer,Integer> colorCount = new HashMap();
        for (int i = 0; i < colors.length; i++) {
            int c = 0;
            if (colorCount.containsKey(colors[i]))
                c = colorCount.get(colors[i]);
            colorCount.put(colors[i],++c);
        }

        HashMap<Integer, Integer> tempColors = new HashMap<>(colorCount);
        HashMap<Integer, Integer> colorMax = new HashMap();

        for (int i = 0; i < 4; i++) {;
            int max = 0;
            for (Integer color : tempColors.keySet()) {
                if (max == 0)
                    max = color;
                else
                    max = tempColors.get(max) >= tempColors.get(color) ? max : color;
            }
            colorMax.put(max, tempColors.get(max));
            tempColors.remove(max);
        }
        this.colorCount = colorCount;
        this.colorMax = colorMax;
    }
}


