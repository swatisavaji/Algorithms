import java.util.HashMap;

public class BipartiteTop2 {

    BipSubg[] biparts;
    HashMap<Integer, BipSubg> bipts4;

    BipartiteTop2(HashMap<Integer, Integer>[] adjacency, VertexColorer vc){
            int i = 0;
            HashMap<Integer,BipSubg> tempbs = new HashMap();
            biparts = new BipSubg[2];
            for (Integer color1: vc.colorMax.keySet()) {
                for (Integer color2: vc.colorMax.keySet()) {
                    if (color1 != color2){
                        BipSubg bipSubg = new BipSubg(adjacency,vc,color1,color2);
                        bipSubg.backboneCreate();
                        tempbs.put(i, bipSubg);
                        i++;
                    }
                }
            }

            for (int k = 0; k < 2; k++) {
                i = 0;
                for (Integer j : tempbs.keySet()) {
                    if (i == 0)
                        i = j;
                    else if (tempbs.get(i).bb.size() < tempbs.get(j).bb.size()) {
                        i = j;
                    }
                }
                biparts[k] = tempbs.remove(i);
            }
            bipts4 = tempbs;
    }
}

