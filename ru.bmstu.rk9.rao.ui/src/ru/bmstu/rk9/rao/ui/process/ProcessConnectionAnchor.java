package ru.bmstu.rk9.rao.ui.process;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public class ProcessConnectionAnchor extends AbstractConnectionAnchor {

	public boolean topDown = true;
	public boolean leftToRight = true;
	public int offsetVertical;
	public int offsetHorizontal;

	public ProcessConnectionAnchor(IFigure owner) {
		super(owner);
	}

	@Override
	public void ancestorMoved(IFigure figure) {
		if (figure instanceof ScalableFigure)
			return;
		super.ancestorMoved(figure);
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle rectangle = getOwner().getBounds();
		int x, y;
		if (topDown)
			y = rectangle.y + offsetVertical;
		else
			y = rectangle.bottom() - 1 - offsetVertical;

		if (leftToRight)
			x = rectangle.x + offsetHorizontal;
		else
			x = rectangle.right() - 1 - offsetHorizontal;

		Point point = new PrecisionPoint(x, y);
		getOwner().translateToAbsolute(point);
		return point;
	}

	@Override
	public Point getReferencePoint() {
		return getLocation(null);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ProcessConnectionAnchor) {
			ProcessConnectionAnchor processAnchor = (ProcessConnectionAnchor) object;

			if (processAnchor.leftToRight == this.leftToRight && processAnchor.topDown == this.topDown
					&& processAnchor.offsetHorizontal == this.offsetHorizontal
					&& processAnchor.offsetVertical == this.offsetVertical
					&& processAnchor.getOwner() == this.getOwner()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return ((this.leftToRight ? 31 : 0) + (this.topDown ? 37 : 0) + this.offsetHorizontal * 43
				+ this.offsetVertical * 47) ^ this.getOwner().hashCode();
	}
}
