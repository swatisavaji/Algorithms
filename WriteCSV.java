import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteCSV {
        public String[] region = {"SQUARE", "DISK"};

        public void pointWriter(Graph g,Vertex[] points,int[] color,int[] vertexOrder) throws IOException{
            FileWriter out = new FileWriter(new String(g.id + "pts.csv"));
            StringBuilder builder= new StringBuilder();
            int i,numberOfPoints=points.length;

            builder.append("id").append(",")
                    .append("x").append(",")
                    .append("y").append(",")
                    .append("color");
            builder.append("\n");
            for (i=0;i<numberOfPoints;i++){
                builder.append(vertexOrder[i]).append(",");
                builder.append(points[vertexOrder[i]].x).append(",");
                builder.append(points[vertexOrder[i]].y).append(",");
                builder.append(color[vertexOrder[i]]);
                if (i!=numberOfPoints-1)
                    builder.append("\n");
            }

            out.append(builder.toString());
            out.flush();
            out.close();
        }

        public void adjacencyWriter(Graph g) throws IOException {
            HashMap<Integer, Integer>[] adjacencies = g.adjacencyList;
            FileWriter out = new FileWriter(new String(g.id + "adj.csv"));
            StringBuilder builder= new StringBuilder();

            for (int i = 0; i < adjacencies.length; i++) {
                builder.append(i);
                builder.append(",");
                if(adjacencies[i]!=null) {
                    for (Integer vertex : adjacencies[i].keySet()) {
                        builder.append(vertex).append(",");
                    }
                }
                builder.replace(builder.length()-1,builder.length(),"\n");
            }
            out.append(builder.toString());
            out.flush();
            out.close();
        }

        public void part1op(Graph g, VertexColorer sm, BipartiteTop2 bp) throws IOException {
            FileWriter out = new FileWriter(new String(g.id+"statpart1.csv"));
            StringBuilder str = new StringBuilder();
            //write columns
            str.append("id").append(",")
                    .append("R").append(",")
                    .append("N").append(",")
                    .append("Min degree").append(",")
                    .append("Average Degree").append(",")
                    .append("Maximum Degree").append(",")
                    .append("Distribution").append(",")
                    .append("Realized average deg").append(",")
                    .append("Edges in largest subg");

            str.append("\n");

            str.append(g.id).append(",");
            str.append(g.radius).append(",");
            str.append(g.numberOfVertices).append(",");
            str.append(sm.min_degree).append(",");
            str.append(g.avgDegree).append(",");
            str.append(sm.max_degree).append(",");
            str.append(region[g.REGION]).append(",");
            str.append(g.act_avg).append(",");
            str.append(bp.biparts[0].bb.size()-1);
            out.append(str.toString());
            out.flush();
            out.close();
        }

        public void part2op(VertexColorer sm, Graph g) throws IOException {
            FileWriter out = new FileWriter(new String(g.id+"statpart2.csv"));
            StringBuilder str = new StringBuilder();
            //write columns
            str.append("Original Degree").append(",").append("Degree when deleted").append("\n");

            HashMap<Integer, Integer>[] adj = g.adjacencyList;
            int[] sldeg = sm.vertex_degreeR;
            for (int i = 0; i < adj.length; i++) {
                int deg = 0;
                if (adj[i] != null)
                    deg = adj[i].keySet().size();

                str.append(deg).append(",").append(sldeg[i]);
                if (i != adj.length-1)
                    str.append("\n");
            }

            out.append(str.toString());
            out.flush();
            out.close();

            //colorweights
            FileWriter out2 = new FileWriter(new String(g.id + "colcount.csv"));
            StringBuilder bld = new StringBuilder();
            bld.append("Color").append(",").append("Number of vertices").append("\n");

            for (Integer color: sm.colorCount.keySet()) {
                bld.append(color).append(",").append(sm.colorCount.get(color)).append("\n");
            }
            bld.delete(bld.length()-1,bld.length());
            out2.append(bld.toString());
            out2.flush();
            out2.close();

            ////largest colors
            out2 = new FileWriter(new String(g.id + "maxcol.csv"));
            bld = new StringBuilder();
            bld.append("Color").append(",").append("Number of vertices").append("\n");

            for (Integer color: sm.colorMax.keySet()) {
                bld.append(color).append(",").append(sm.colorMax.get(color)).append("\n");
            }
            bld.delete(bld.length()-1,bld.length());
            out2.append(bld.toString());
            out2.flush();
            out2.close();
        }

        public void part3op(Graph g, BipartiteTop2 b) throws IOException {
            FileWriter out = new FileWriter(new String(g.id+"bip.csv"));
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < b.biparts.length; i++) {
                str.append(b.biparts[i].col1).append(",").append(b.biparts[i].col2).append("\n");

                BipSubg bip = b.biparts[i];

                for (Integer vertex: bip.bb) {
                    str.append(vertex).append(",");
                }
                str.replace(str.length()-1,str.length(),"\n");
            }
            str.delete(str.length()-1,str.length());
            out.append(str.toString());
            out.flush();
            out.close();
        }

}

