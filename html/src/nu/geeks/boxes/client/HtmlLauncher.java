package nu.geeks.boxes.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import nu.geeks.boxes.TheBoxes;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration conf = new GwtApplicationConfiguration(960,540);
                conf.antialiasing = true;

                return conf;
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new TheBoxes();
        }
}