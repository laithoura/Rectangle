package Model;

public class Rectangle {
	private float width;
	private float heigth;
	
	public float getArea() {
		return width*heigth;
	}
	
	public Rectangle(float w,float h) {
		width = w;
		heigth = h;
	}

	public Rectangle() {}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeigth() {
		return heigth;
	}

	public void setHeigth(float heigth) {
		this.heigth = heigth;
	}

	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", heigth=" + heigth + "]";
	}
			
	
}
