/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author nbp184
 */
public class MyFileChooser extends JFileChooser {
    
    public MyFileChooser(String description, String... extensions) {
        super();
        setFileFilter(new FileNameExtensionFilter(description, extensions));
    }
    
    @Override
    public void approveSelection() {
        FileNameExtensionFilter filter = (FileNameExtensionFilter)getFileFilter();
        File file = getSelectedFile();
        if(getDialogType() == OPEN_DIALOG) {
            if(!file.exists()) {
                JOptionPane.showMessageDialog(this, file.getName() +"\nFile not found.\nCheck the file name and try again.", "Open", JOptionPane.WARNING_MESSAGE);
            } else if(!filter.accept(file)) {
                JOptionPane.showMessageDialog(this, file.getName() +"\nFile incorrect type.\nCheck the file and try again.", "Open", JOptionPane.WARNING_MESSAGE);
            } else {
                super.approveSelection();
            }
        } else if(getDialogType() == SAVE_DIALOG) {
            if(!filter.accept(file)) {
                file = new File(file.getAbsolutePath() +"." +filter.getExtensions()[0]);
                setSelectedFile(file);
            }
            if(file.exists()) {
                int result = JOptionPane.showConfirmDialog(this, file.getName() +" already exists.\nDo you want to replace it?", "Confirm Save", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(result == JOptionPane.YES_OPTION) {
                    super.approveSelection();
                }
            } else {
                super.approveSelection();
            }
        }
    }
    
    public void clearSelected() {
        setSelectedFile(new File(""));
    }
    
}
