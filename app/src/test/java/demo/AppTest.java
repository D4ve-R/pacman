/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo;

import demo.menus.FileEditor;
import demo.menus.MainMenu;
import demo.menus.OptionMenu;
import org.junit.Ignore;
import org.junit.Test;

public class AppTest {
    @Test public void appHasAGreeting() {
        App classUnderTest = new App();
    }

    @Ignore
    @Test( expected = IllegalArgumentException.class)
    public void  testConstructors(){
        MainMenu mM = new MainMenu(null);
        OptionMenu oM = new OptionMenu(null);
        FileEditor fe = new FileEditor(null);

    }

}
