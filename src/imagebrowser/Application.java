package imagebrowser;

import imagebrowser.control.Command;
import imagebrowser.control.NextImageCommand;
import imagebrowser.control.PrevImageCommand;
import imagebrowser.model.Image;
import imagebrowser.persistence.ImageListLoader;
import imagebrowser.persistence.file.FileImageListLoader;
import imagebrowser.ui.swing.ActionListenerFactory;
import imagebrowser.ui.swing.ApplicationFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        new Application().execute();
    }
    
    private static final String PATH = "C:\\Users\\Darwin\\Pictures\\Mi album fotografico\\Varios";
    private Map<String, Command> commandMap;
    private ApplicationFrame frame;

    private void execute() {
        ImageListLoader loader = createImageListLoader(PATH);
        List<Image> list = loader.load();
        frame = new ApplicationFrame(createActionListenerFactory());
        frame.getImageViewer().setImage(list.get(0));
        createCommands();
        frame.setVisible(true);
    }

    private void createCommands() {
        commandMap = new HashMap<>();
        commandMap.put("Next", new NextImageCommand(frame.getImageViewer()));
        commandMap.put("Prev", new PrevImageCommand(frame.getImageViewer()));
    }

    private ImageListLoader createImageListLoader(String path) {
        return new FileImageListLoader(path);
    }

    private ActionListenerFactory createActionListenerFactory() {
        return new ActionListenerFactory() {

            @Override
            public ActionListener create(final String action) {
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Command command = commandMap.get(action);
                        if (command == null) return;
                        command.execute();
                    }
                };
            }
        };
    }

    
}
