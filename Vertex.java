public class Vertex {
    int id;
    double x,y,radius,theta;

    Vertex(int id, double x, double y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    Vertex(int id, double x, double y, double theta){
        this.id=id;
        this.x=x;
        this.y=y;
        this.theta = theta;
    }


    public void setRadius(double radius) {
        this.radius = radius;
    }
}
