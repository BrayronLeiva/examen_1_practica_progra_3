import Controller.Controller;
import org.jdom2.JDOMException;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, JDOMException {
        try {
            new Controller();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }
}
