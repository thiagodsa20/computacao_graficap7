package slide3;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CvDefPoly extends Canvas {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<Point2d> v = new Vector<Point2d>();
	 float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize;
	 boolean ready = true;
	 int centerX, centerY;
	 
	 CvDefPoly() {
		 addMouseListener(new MouseAdapter() { // Ativando o mouse
			 public void mousePressed(MouseEvent evt) { 
				float xA = fx(evt.getX()), yA = fy(evt.getY()); // Coordenadas de x e y no plano cartesiano baseado no ponteiro do mouse

				if (ready) {
					v.removeAllElements(); // Esvaziando o setor de vértices
					x0 = xA;
					y0 = yA;
					ready = false;
				}
				float dx = xA - x0, dy = yA - y0;
				if (v.size() > 0 && dx * dx + dy * dy < 4 * pixelSize * pixelSize) // Vértice no pequeno retângulo
					ready = true;
				else
					v.addElement(new Point2d(xA, yA)); // Armazenando o vértice no vetor
				repaint();

			 }
		 });
	 }

	void initgr() { // Dimensões do retângulo
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
		centerX = maxX/2;
		centerY = maxY/2;
	}
	
	int iX(float x) {
		return Math.round(centerX + x/pixelSize);
	}
	
	int iY(float y) {
		return Math.round(centerY - y/pixelSize);
	}
	
	float fx(int x) {
		return (x - centerX) * pixelSize;
	}
	
	float fy(int y) {
		return (centerY - y) * pixelSize;
	}
	

	public void paint(Graphics g) {
		initgr();
		int left = iX(-rWidth/2), right = iX(rWidth/2), bottom = iY(-rHeight/2), top = iY(rHeight/2);
		g.drawRect(left, top, right - left, bottom - top); // Função que faz o desenho do retângulo
		int n = v.size();
		if (n == 0) return;
	
		Point2d a = (Point2d)(v.elementAt (0)); // Primeiro Vértice
		// Desenha um retângulo menor ao redor do primeiro vértice
		g.drawRect(iX(a.x)-2, iY(a.y)-2, 4, 4); // Desenha o retângulo menor
		for (int i=1; i<=n; i++) {
			if (i == n && !ready) break;
			Point2d b = (Point2d)(v.elementAt(i % n));
			g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y)); // Desenha a reta entre 2 vértices
			a = b;
		}	
		
	}

}
