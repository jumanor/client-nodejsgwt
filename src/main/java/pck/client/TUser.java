package pck.client;

import com.google.gwt.core.client.JavaScriptObject;

public class TUser extends JavaScriptObject {

	protected TUser(){};
	
    public final native int getX() /*-{ return this.x; }-*/;
    public final native int getY() /*-{ return this.y; }-*/;
    public final native String getName() /*-{ return this.name; }-*/;
}
