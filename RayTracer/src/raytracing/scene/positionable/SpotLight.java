/** 
 * Representer une source de lumiere ponctuelle.
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.scene.positionable;

import raytracing.scene.Color;
import raytracing.scene.geometry.Point;

public class SpotLight extends Positionable{

    /** La couleur de la source de lumiere. */
    private Color color;

    /** La position de la source de lumiere. */
    private Point position;

    /** Initialiser une source de lumière depuis sa couleur et sa position
     * @param position La position de la lumiere
     * @param color La couleur de la lumiere
     */
    public SpotLight(Point position, Color color, String name){
	super(name);
	this.setPosition(position);
	this.setColor(color);
    }

    /** @see Positionable */
    public String toString(){
	return this.name+"(lumiere)";
    }

    /** Obtenir la couleur de la lumière.
     * @return La couleur
     */
    public Color getColor(){
	return this.color;
    }

    /** Obtenir la position de la source de lumière.
     * @return La position de la source
     */
    public Point getPosition(){
	return this.position;
    }

    /** Definir la couleur de la source de lumiere.
     * @param color La couleur de la lumiere.
     * @throw IllegalArgumentException Lorsque la couleur est null
     */
    public void setColor(Color color){
	if(color == null)
	    throw new IllegalArgumentException("Couleur invalide");
			
	this.color = color;
    }

    /** Definir la position de la source de lumiere.
     * @param position La position
     * @throws IllegalArgumentException Lorsque le point est null
     */
    public void setPosition(Point position){
	if(position == null)
	    throw new IllegalArgumentException("Position invalide");
			
	this.position = position;
    }
	
	


}
