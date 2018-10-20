import java.util.Random;

public class PointGenerator {
    public static final int SQUARE=0,DISK=1,SPHERE=2;
    public PointGenerator() {

    }

    public Vertex[] getVertices(int numberOfvertices, int topology){
        Vertex[] vertices = new Vertex[numberOfvertices];

        switch (topology){
            case SQUARE:{
                vertices = pointsOnASquare(vertices);
                break;
            }

            case DISK:{
                vertices =  pointsOnADisk(vertices);
                break;
            }
        }
        return vertices;
    }

    public Vertex[] pointsOnASquare(Vertex[] vertices){
        Random r=new Random();

        for(int i=0;i<vertices.length;i++){
            vertices[i]=new Vertex(i,r.nextDouble(),r.nextDouble());
        }
        return vertices;
    }

    public Vertex[] pointsOnADisk(Vertex[] vertices){
        Random r=new Random();
        double theta, radius, corrected_r;
        for(int i=0;i<vertices.length;i++){
            theta = Math.toRadians(r.nextDouble()*360);
            radius = r.nextDouble();
            corrected_r = Math.sqrt(radius);
            vertices[i]=new Vertex(i,corrected_r*Math.cos(theta),corrected_r*Math.sin(theta),theta);
            vertices[i].setRadius(radius);
        }
        return vertices;
    }
}
