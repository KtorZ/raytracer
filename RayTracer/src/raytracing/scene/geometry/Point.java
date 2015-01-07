/** Point permet de modéliser un point dans un espace en 3 dimensions
 * @author  Clément Dolou, Maxime Hervé
 * @version 1.1
 */
package raytracing.scene.geometry;

public class Point {
  	
    private double x;		// abscisse
    private double y;		// ordonnée
    private double z;		// cote

    /** Construire un point à partir de son abscisse de son ordonnée et de s
     * @param vx abscisse
     * @param vy ordonnée
     * @param vz cote
     */
    public Point(double vx, double vy , double vz) {
	// System.out.println("CONSTRUCTEUR Point(" + vx + ", " + vy + ", " + vz +")");
	this.x = vx;
	this.y = vy;
	this.z = vz;
		
    }

    /** Obtenir l'abscisse du point.
     * @return abscisse du point
     */
    public double getX() {
	return this.x;
    }

    /** Obtenir l'ordonnée du point.
     * @return ordonnée du point
     */
    public double getY() {
	return this.y;
    }

    /** Obtenir la cote du point.
     * @return cote du point
     */
    public double getZ() {
	return this.z;
    }
	

    /** Changer l'abscisse du point.
     * @param vx nouvelle abscisse
     lic	  */
    public void setX(double vx) {
	this.x = vx;
    }

    /** Changer l'ordonnée du point.
     * @param vy nouvelle ordonnée
     */
    public void setY(double vy) {
	this.y = vy;
    }

    /** Changer la cote du point.
     * @param vz nouvelle cote
     */
    public void setZ(double vz) {
	this.z = vz;
    }

    public String toString(){
	return "("+x+", "+y+", "+z+")";
    }	
	
    public boolean isEqual(Point anotherPoint){
	double xGap = Math.abs(anotherPoint.getX() - this.x);
	double yGap = Math.abs(anotherPoint.getY() - this.y);
	double zGap = Math.abs(anotherPoint.getZ() - this.z);
		
	return (xGap < Geometry.EPSILON && yGap < Geometry.EPSILON && zGap < Geometry.EPSILON);
    }

}
