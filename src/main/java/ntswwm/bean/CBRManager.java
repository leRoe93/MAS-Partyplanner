package ntswwm.bean;

import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;

/**
 * Some kind of singleton instance that gets initialized right away when then
 * application starts and serve as the single point of access to the underlying
 * MyCBR project and its related concept and case base.
 * 
 * @author leonr
 *
 */
public class CBRManager {

    public static Project PROJECT;
    public static Concept CONCEPT;
    public static ICaseBase CASE_BASE;

}
