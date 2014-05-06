package pck.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Nodejspru implements EntryPoint {
	
	
	
	public void onModuleLoad() {
	
		TEngine engine=new TEngine();
		engine.ejecutar();
	}
}
