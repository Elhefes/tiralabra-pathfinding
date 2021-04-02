package tiralabra.pathfinding.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author henripal
 */
public class Logic {
    
    public File chooseFile() {
        JFileChooser chooser = new JFileChooser("./maps/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map files", "map");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

}
