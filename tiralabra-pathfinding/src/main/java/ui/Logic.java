package ui;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
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
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

}
