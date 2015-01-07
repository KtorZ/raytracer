/** 
 * Representer un objet positionnable dans la scene.
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.scene.positionable;

abstract public class Positionable{

    /** Nom du positionnable */
    protected String name;

    /** Initialiser un positionable a partir de son nom
     * @param name Nom du positionnable.
     * @throws IllegalArgumentException si le nom ne contient aucun caractere
     */
    public Positionable(String name){
	this.setName(name);
    }

    /** @see Object */
    public abstract String toString();

    /** Obtenir le nom d'un positionable
     * @return Le nom du positionnable
     */
    public String getName(){
        return this.name;
    }

    /** Definir le nom du positionnable
     * @param name Nom du positionnable.
     * @throws IllegalArgumentException si le nom ne contient aucun caractere
     */
    public void setName(String name){
	if(name.trim().equals(""))
	    throw new IllegalArgumentException("Nom de positionnable incorrect");
			
	this.name = name;
    }
}
