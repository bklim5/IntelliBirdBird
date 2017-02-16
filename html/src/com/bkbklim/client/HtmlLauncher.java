package com.bkbklim.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.bkbklim.Helpers.DummyController;
import com.bkbklim.IntelliBirdBird;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
//                return new GwtApplicationConfiguration(480, 320);
                return new GwtApplicationConfiguration(272, 408);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new IntelliBirdBird(new DummyController());
        }
}