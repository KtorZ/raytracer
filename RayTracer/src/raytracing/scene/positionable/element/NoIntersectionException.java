/** Exception levee lorsqu'un element n'est pas intersecte par le rayon fourni */
package raytracing.scene.positionable.element;

public class NoIntersectionException extends RuntimeException{
    
    public NoIntersectionException(){
	super();
    }

    public NoIntersectionException(String msg){
	super(msg);
    }
}
