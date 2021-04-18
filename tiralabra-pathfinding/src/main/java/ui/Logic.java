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
    
    public boolean askForMapChoosingConfirmation(int height, int width) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Warning");
        alert.setHeaderText("Map size large");
        alert.setContentText(
                "The map you are trying to open is very large (" + height + "*" + width + "). "
                + "The opening of this map might take a long time (several minutes depending on the "
                + "speed of your computer) due to JavaFX's performance. Are you sure you want to open this map?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

}
