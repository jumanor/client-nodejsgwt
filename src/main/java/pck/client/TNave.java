package pck.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import com.google.gwt.user.client.ui.Image;

public class TNave {

	private Context2d context;
	private Canvas canvas;
	private int x,y,w,h;
	private Image imgNave;
	private String name;
	private String titulo;
	private String color;
	
	public void setColor(String color) {
		this.color = color;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public Canvas getCanvas(){
		return canvas;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	///////////////////////////////////////METODOS PUBLICOS//////////////////////////////////////
	public TNave(){
		 canvas=Canvas.createIfSupported();
		 canvas.setWidth(600 + "px");
	     canvas.setHeight(200 + "px");
	     canvas.setCoordinateSpaceWidth(600);
	     canvas.setCoordinateSpaceHeight(200);
	     context = canvas.getContext2d();
		
		 imgNave=new Image("imgNaveMarciano.gif");
		 x=0;
		 y=0;
		 
		 w=50;
	 	 h=50;
	 	 color="black";
	 	 
	}//end function
	public void DibujarNave(){
		
		ImagePreloader.load(imgNave.getUrl(), new ImageLoadHandler() {
			
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				// TODO Auto-generated method stub
				titulo=name+" ("+x+","+y+")";
				context.beginPath();
				ImageElement imageElement = ImageElement.as(imgNave.getElement());
				context.drawImage(imageElement, x, y,w,h);
				context.setFillStyle(color);
				context.fillText  (titulo, x, y);
				context.closePath();				
			}
		});
		
				
	}//end function
	public void DibujarNaveArriba(){
				// TODO Auto-generated method stub
				BorrarNave();
				y=y-20;
				titulo=name+" ("+x+","+y+")";
				context.beginPath();
				ImageElement imageElement = ImageElement.as(imgNave.getElement());
				context.drawImage(imageElement, x, y,w,h);
				context.setFillStyle(color);
				context.fillText  (titulo, x, y);
				context.closePath();		
			
	}
	//////////////////////////////
   public void DibujarNaveAbajo(){
	   	BorrarNave();
		y=y+20;
		titulo=name+" ("+x+","+y+")";
		   
		context.beginPath();
		ImageElement imageElement = ImageElement.as(imgNave.getElement());
		context.drawImage(imageElement, x, y,w,h);
		context.setFillStyle(color);
		context.fillText  (titulo, x, y);
		context.closePath();
		
	}
	///////////////////////////////
	public void DibujarNaveIzquierda(){
		BorrarNave();
		x=x-20;
		titulo=name+" ("+x+","+y+")";   
		context.beginPath();
		ImageElement imageElement = ImageElement.as(imgNave.getElement());
		context.drawImage(imageElement, x, y,w,h);
		context.setFillStyle(color);
		context.fillText  (titulo, x, y);
		context.closePath();
		
	}
	//////////////////////////////
   public void DibujarNaveDerecha(){
	   BorrarNave();
		x=x+20;
		titulo=name+" ("+x+","+y+")";
		context.beginPath();
		ImageElement imageElement = ImageElement.as(imgNave.getElement());
		context.drawImage(imageElement, x, y,w,h);
		context.setFillStyle(color);
		context.fillText  (titulo, x, y);
		context.closePath();
		
	}
   protected void BorrarNave(){
 		context.clearRect(x, y-10, w+100, h+10);	
     }
}
