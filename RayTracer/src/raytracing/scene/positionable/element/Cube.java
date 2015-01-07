/** 
 * Representer un cube positionnable.
 * @author Clement Dolou
 * @version 1.1
 */
package raytracing.scene.positionable.element;

import raytracing.scene.Ray;
import raytracing.scene.geometry.*;

public class Cube extends Element{

    /** Triplet de sommets servant a la construction du cube. 
     * Le cube est extrudé dans le sens direct de passage des points
     */
    private Point vertexs[] = new Point[3];

    /** Centre du cube.
     * Le centre du cube
     */
    private Point center;

    /** Facettes composant le cube, 2 facettes par face du cube. */
    private Triangle triangles[] = new Triangle[12];

    /** Initialiser un cube depuis 3 sommets.
     * @param vertexs Sommets du cube.
     */
    public Cube(Point p_vertexs[], String name, Material material){
	super(name, material);
	this.setVertexs(p_vertexs);
    }

	
    private void buildTriangles(){	
	Point p_0 = this.vertexs[0];
	Point p_1 = this.vertexs[1];
	Point p_2 = this.vertexs[2];
	
	Vector x = new Vector(p_0,p_1);
	Vector y = new Vector(p_0,p_2);
	Vector z = Geometry.realProduct(Geometry.normalize(Geometry.vectorialProduct(x,y)), x.getNorm());

	Point p_3 = Geometry.translate(p_1,y);
	Point p_4 = Geometry.translate(p_0,z);
	Point p_5 = Geometry.translate(p_1,z);
	Point p_6 = Geometry.translate(p_2,z);
	Point p_7 = Geometry.translate(p_3,z);

	String name = this.toString();
	Material material = this.getMaterial();

	//Centre
	this.center = Geometry.translate(this.vertexs[0],
					 Geometry.realProduct(Geometry.sum(Geometry.sum(x, y), z),
							      0.5)); 
						 
		
	//Faces du fond
	this.triangles[0] = new Triangle(new Point[]{p_0,p_2,p_1}, name, material);
	this.triangles[1] = new Triangle(new Point[]{p_3,p_1,p_2}, name, material);

	//Faces frontales
	this.triangles[2] = new Triangle(new Point[]{p_4,p_5,p_6}, name, material);
	this.triangles[3] = new Triangle(new Point[]{p_7,p_6,p_5}, name, material);

	//Faces du dessus
	this.triangles[4] = new Triangle(new Point[]{p_7,p_3,p_6}, name, material);
	this.triangles[5] = new Triangle(new Point[]{p_2,p_6,p_3}, name, material);

	//Faces du dessous
	this.triangles[6] = new Triangle(new Point[]{p_5,p_4,p_1}, name, material);
	this.triangles[7] = new Triangle(new Point[]{p_0,p_1,p_4}, name, material);

	//Faces de gauches
	this.triangles[8] = new Triangle(new Point[]{p_0,p_4,p_2}, name, material);
	this.triangles[9] = new Triangle(new Point[]{p_6,p_2,p_4}, name, material);

	//Faces de droites
	this.triangles[10] = new Triangle(new Point[]{p_1,p_3,p_5}, name, material);
	this.triangles[11] = new Triangle(new Point[]{p_7,p_5,p_3}, name, material);

	this.triangles = triangles;	
    }

    /** @see Element
     */
    public Point getIntersection(Ray ray){
	Point closestIntersection = null;
	Point intersection; 
	Point source = ray.getSource();
	double distance;
	double distMin = Double.POSITIVE_INFINITY;
	double cote = Geometry.distance(this.vertexs[0],this.vertexs[1]);
	Vector SC =  new Vector(ray.getSource(), this.center);
	double dScalSC = Geometry.scalarProduct(ray.getSupport(),SC);
		
	// optimisation de l'intersection, on teste d'abord l'intersection avec la sphere de diagonale la grande diagonale du cube
	if ((Math.sqrt(3)*cote/2 - (Geometry.sum(Geometry.realProduct(ray.getSupport(),-dScalSC),SC).getNorm()) < Geometry.EPSILON))
	    throw new NoIntersectionException();

	try{
	    for(int i = 0; i < 12; i++){
		try{
		    intersection = this.triangles[i].getIntersection(ray);
		    distance = Geometry.distance(intersection, source);
					
		    //Lorsqu'on est sur une arrete.
		    if(Math.abs(distance - distMin) < Geometry.EPSILON)
			throw new RuntimeException("Arête");
					
		    if(distance < distMin){
			distMin = distance;
			closestIntersection = intersection;
		    }
		}catch(NoIntersectionException e){}	
	    }
	}catch(RuntimeException e){
	    closestIntersection = null;
	}
		
	if(closestIntersection == null)
	    throw new NoIntersectionException();

	return closestIntersection;
    }

    /** @see Element 
     */
    public Vector getNormal(Point intersection){
	int i =0;	
	Point points[];
	boolean triangFound = false;
		
	do{
	    points = this.triangles[i].getPoints();
	    triangFound = Geometry.belong(points[0],points[1],points[2], intersection); // Attention, belong indique l'appartenance à un plan, pas une facette.
	    i++;
	}while(i < 12 && !triangFound);
		
	if(!triangFound)
	    throw new RuntimeException("Normale en un point hors du cube.");
		
	points = this.triangles[i-1].getPoints();
	Vector resultat = Geometry.vectorialProduct(new Vector(points[0],points[1]), new Vector(points[0],points[2]));
		
	//System.out.println("Triangle "+(i-1)+" : "+Geometry.normalize(resultat));
    	return Geometry.normalize(resultat);
    }

    /** Definir les sommets du cube.
     * @param vextexs Sommets a definir.
     * @throws IllegalArgumentException lorsque vertexs n'est pas un triplet de point*
     * @throws IllegalArgumentException lorsque l'un des points est null
     * @throws IllegalArgumentException lorsque les la demi-face créée a partir des trois sommets n'est pas isocele rectangle en le 2nd point de vertexs 
     */
    public void setVertexs(Point vertexs[]){
    	if(vertexs == null || vertexs.length != 3)
	    throw new IllegalArgumentException("Sommets indefinis");

	if(vertexs[0] == null || vertexs[1] == null || vertexs[2] == null)
	    throw new IllegalArgumentException("Un des sommets est indefini");

	Vector x = new Vector(vertexs[0],vertexs[1]);
	Vector y = new Vector(vertexs[0],vertexs[2]);

	if (Math.abs(Geometry.scalarProduct(x, y)) > Geometry.EPSILON || Math.abs(x.getNorm() - y.getNorm()) > Geometry.EPSILON)
	    throw new IllegalArgumentException("Les sommets ne definissent pas un triangle isocele rectangle"); 
		
	this.vertexs = vertexs; 
	this.buildTriangles();
    }

    /** Obtenir les sommets du cubes
     * @return les sommets du cube.
     */
    public Point[] getVertexs(){
	return this.vertexs;
    }
}
