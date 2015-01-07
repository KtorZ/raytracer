/** Vector permet de modéliser un vecteur dans R3
 * @author  Clément Dolou, Maxime Hervé
 * @version 1.1
 */
package raytracing.scene.geometry;

public class Vector {

    private double x;		// coordonnée x
    private double y;		// coordonnée y
    private double z;		// coordonnée z
	
    /** Construire un vecteur à partir de sees coordonnées dans R3
     * @param vx
     * @param vy
     * @param vz
     */
    public Vector(double vx, double vy, double vz) {
	this.x = vx;
	this.y = vy;
	this.z = vz;
		
    }

    public Vector(Point vA, Point vB) {
	this.x = vB.getX()-vA.getX();
	this.y = vB.getY()-vA.getY();
	this.z = vB.getZ()-vA.getZ();
    }
	
    /** Obtenir coordonnée en x du vecteur
     * @return coordonnée en x du vecteur
     */
    public double getX() {
	return this.x;
    }	

    /** Obtenir coordonnée en y du vecteur
     * @return coordonnée en y du vecteur
     */
    public double getY() {
	return this.y;
    }

    /** Obtenir coordonnée en z du vecteur
     * @return coordonnée en z du vecteur
     */
    public double getZ() {
	return this.z;
    }


    /** Changer coordonnée en x du vecteur
     * @param coordonnée en x du vecteur
     */
    public void setX(double vx) {
	this.x = vx;
    }

    /** Changer coordonnée en y du vecteur
     * @param coordonnée en y du vecteur
     */
    public void setY(double vy) {
	this.y = vy;
    }

    /** Changer coordonnée en z du vecteur
     * @param coordonnée en z du vecteur
     */
    public void setZ(double vz) {
	this.z = vz;
    }

    /** Norme du vecteur
     * @return norme de this
     */
    public double getNorm() {
	return Math.sqrt(Math.pow(this.x, 2)
			 + Math.pow( this.y, 2)
			 + Math.pow( this.z, 2));
    }
	
    /** Savoir si le vecteur est le vecteur nul
     * @return Un boolean, true si le vecteur est nul, false sinon
     */
    public boolean isZero(){
	return(Math.abs(this.x) < Geometry.EPSILON && Math.abs(this.y) < Geometry.EPSILON && Math.abs(this.z) < Geometry.EPSILON);
    }
	
    public String toString(){
	return "("+x+", "+y+", "+z+")";
    }	

}





