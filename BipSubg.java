import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BipSubg {
    HashMap<Integer, ArrayList<Integer>> bipSub;
    int edges, vertices;    
    LinkedList<Integer>[] comps;
    LinkedList<Integer> bb;
    int col1, col2;
    BipSubg(HashMap<Integer, Integer>[] adjacency, VertexColorer vc, int c1, int c2){
        //create backbone
        this.col1 = c1;
        this.col2 = c2;

        constructB(adjacency, vc.colors, c1, c2);
        backboneCreate();
    }

    public void constructB(HashMap<Integer, Integer>[] adjacency, int[] color, int c1, int c2){
        bipSub = new HashMap();
        for (int i = 0; i < adjacency.length; i++) {
            if (color[i] == c1){
                bipSub.put(i , new ArrayList());
                if (adjacency[i] != null) {
                    for (Integer neighbour : adjacency[i].keySet()) {
                        if (color[neighbour] == c2)
                            bipSub.get(i).add(neighbour);
                    }
                }
            }

            if (color[i] == c2){
                bipSub.put(i , new ArrayList());
                if (adjacency[i] != null){
                    for (Integer neighbour: adjacency[i].keySet()) {
                        if (color[neighbour] == c1)
                            bipSub.get(i).add(neighbour);
                    }
                }
            }
        }
        setValues();
    }

    public void backboneCreate(){
        comps = new LinkedList[bipSub.size()];
        bb = new LinkedList();
        HashMap<Integer, Integer> visited = new HashMap();
        int i = 0;
        for (Integer v: bipSub.keySet()) {
            if (!visited.containsKey(v)){
                LinkedList<Integer> component = traverse(v, visited, new LinkedList());
                if (bb == null)
                    bb = component;
                else{
                    if (bb.size() < component.size())
                        bb = component;
                }

                comps[i] = component;
                i++;
            }
        }
    }

    private LinkedList<Integer> traverse(Integer vertex, HashMap<Integer, Integer> visited, LinkedList<Integer> comp) {
        comp.addLast(vertex);
        visited.put(vertex, vertex);
        if (bipSub.get(vertex) != null){
            for (Integer neighbour: bipSub.get(vertex)) {
                if (!visited.containsKey(neighbour))
                    comp = traverse(neighbour, visited, comp);

            }
        }
        return comp;
    }


    public void setValues(){
        int vertices = 0, edges = 0;
        for (Integer vertex: bipSub.keySet()) {
            vertices++;
            edges += bipSub.get(vertex).size();
        }
        this.edges = edges;
        this.vertices = vertices;
    }
}
