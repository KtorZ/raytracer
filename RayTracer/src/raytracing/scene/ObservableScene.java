/** 
 * Caracteriser une scene observable.
 * @author Groupe23
 * @version 1.0
 */
package raytracing.scene;

import raytracing.ihm.SceneObserver;

interface ObservableScene{
    /** Ajouter un observateur a la liste des observateurs.
     * @param obs L'observateur a ajouter.
     * @throws IllegalArgumentException Lorsque obs est null
     */
    public void addObserver(SceneObserver obs);

    /** Retirer un observateur de la liste des observateurs.
     * @param obs Observateur a retirer.
     * @throws IllegalArgumentException Lorsque obs est null ou lorsqu'il n'est pas dans la liste des observateurs.
     */
    public void removeObserver(SceneObserver obs);

    /** Notifier les observateurs d'un changement.
     */
    public void notifyObservers();
}
