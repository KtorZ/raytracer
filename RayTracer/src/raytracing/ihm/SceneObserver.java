/** 
 * 
 */
package raytracing.ihm;

import raytracing.scene.positionable.ViewPoint;
import java.awt.Image;

public interface SceneObserver{
    public void updateImage(Image image, ViewPoint vp);
}
