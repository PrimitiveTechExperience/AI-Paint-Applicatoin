package ca.utoronto.utm.paint;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PolylineManipulatorStrategy extends ShapeManipulatorStrategy {
    PolylineManipulatorStrategy(PaintModel paintModel) {
        super(paintModel);
    }
    private PolylineCommand polylineCommand;
    @Override
    public void mouseMoved(MouseEvent e) {
        if(this.polylineCommand != null) {
            this.polylineCommand.preview(new Point((int) e.getX(), (int) e.getY()));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if (this.polylineCommand == null) {
            this.polylineCommand = new PolylineCommand();
            this.addCommand(this.polylineCommand);
        }
        if (e.getButton() == MouseButton.PRIMARY){
            this.polylineCommand.add(new Point((int) e.getX(), (int) e.getY()));
        }
        else if (e.getButton() == MouseButton.SECONDARY) {
            this.polylineCommand.removePreview(new Point((int) e.getX(), (int) e.getY()));
            this.polylineCommand = null;
        }
        System.out.println(e.getButton());
    }
}
