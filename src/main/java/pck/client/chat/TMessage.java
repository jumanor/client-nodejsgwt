package pck.client.chat;

import com.google.gwt.core.client.JavaScriptObject;

public class TMessage extends JavaScriptObject{

	protected TMessage(){}
	
	 public final native String getMessage() /*-{ return this.message; }-*/;
	 public final native String getName() /*-{ return this.name; }-*/;
	 
}
