package ca.utoronto.utm.paint;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel; 
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");

	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");

	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");

	private Pattern pPolylineStart=Pattern.compile("^Polyline$");
	private Pattern pPolylineEnd=Pattern.compile("^EndPolyline$");
	// ADD MORE!!
	private Pattern pFilled=Pattern.compile("^filled:(true|false)$");
	private Pattern pColor=Pattern.compile("^color:(0*(2)?(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]\\d|\\d)," +
			"(0*(2)?(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]\\d|\\d),(0*(2)?(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]\\d|\\d)$");
	private	Pattern pP1=Pattern.compile("^p1:\\(-?\\d+,-?\\d+\\)$");
	private	Pattern pP2=Pattern.compile("^p2:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pCenter = Pattern.compile("^center:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pRadius=Pattern.compile("^radius:([1-9]|[1-9]\\d+)$");
	private Pattern pPoints=Pattern.compile("^points$");
	private Pattern pEndPoints=Pattern.compile("^endpoints$");
	private	Pattern pPoint=Pattern.compile("^point:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pAnything = Pattern.compile("^.+$");
	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}

	/**
	 * Parse the specified file
	 * @param fileName
	 * @return
	 */
	public boolean parse(String fileName){
		boolean retVal = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			PaintModel pm = new PaintModel();
			retVal = this.parse(br, pm);
		} catch (FileNotFoundException e) {
			error("File Not Found: "+fileName);
		} finally {
			try { br.close(); } catch (Exception e){};
		}
		return retVal;
	}

	/**
	 * Parse the specified inputStream as a Paint Save File Format file.
	 * @param inputStream
	 * @return
	 */
	public boolean parse(BufferedReader inputStream){
		PaintModel pm = new PaintModel();
		return this.parse(inputStream, pm);
	}

	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following commands. As we parse the file, we modify 
		// the appropriate command.
		
		CircleCommand circleCommand = null; 
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
		PolylineCommand polylineCommand = null;
		boolean filled = false;
		Color rgbColor = null;
		int[] rgbVals = new int[3];
		ArrayList<Point> points = new ArrayList<Point>();
		Point center = null, p1 = null, p2 = null;
		int radius = 0;
		String[] temp;


		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				l = l.strip().replaceAll(" ", "");
				if (l.isEmpty()) continue;
				this.lineNumber++;
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
					case 0:
//						init case
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Expected Start of Paint Save File");
						return false;
					case 1: // Looking for the start of a new object or end of the save file
						m=pCircleStart.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=2;
							break;
						}
						m=pRectangleStart.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=3;
							break;
						}
						m=pSquiggleStart.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=4;
							break;
						}
						m=pPolylineStart.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=5;
							break;
						}m=pFileEnd.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=6;
							break;
						}
						error("Expected Start of Shape or End Paint Save File");
						return false;
						// ADD CODE
					case 2: // Circle start
						m = pColor.matcher(l);
						if(m.matches()){
							for(int i =0; i < 3; i++){
								rgbVals[i] = Integer.parseInt(l.split(":")[1].split(",")[i]);
							}
							rgbColor = Color.rgb(rgbVals[0], rgbVals[1], rgbVals[2]);
							state=7;
							break;
						}
						error("Expected Circle color");
						return false;
					case 3: // rect
						m = pColor.matcher(l);
						if(m.matches()){
							for(int i =0; i < 3; i++){
								rgbVals[i] = Integer.parseInt(l.split(":")[1].split(",")[i]);
							}
							rgbColor = Color.rgb(rgbVals[0], rgbVals[1], rgbVals[2]);
							state=8;
							break;
						}
						error("Expected Rectangle color");
						return false;
					case 4: // squig
						m = pColor.matcher(l);
						if(m.matches()){
							for(int i =0; i < 3; i++){
								rgbVals[i] = Integer.parseInt(l.split(":")[1].split(",")[i]);
							}
							rgbColor = Color.rgb(rgbVals[0], rgbVals[1], rgbVals[2]);
							state=9;
							break;
						}
						error("Expected Squiggle color");
						return false;
					case 5: // poly
						m = pColor.matcher(l);
						if(m.matches()){
							for(int i =0; i < 3; i++){
								rgbVals[i] = Integer.parseInt(l.split(":")[1].split(",")[i]);
							}
							rgbColor = Color.rgb(rgbVals[0], rgbVals[1], rgbVals[2]);
							state=10;
							break;
						}
						error("Expected Polyline color");
						return false;
					case 6: // end of file
						m = pFileEnd.matcher(l);
						if(m.matches()){
							state=23;
							break;
						}
						error("Expected End of Paint Save File");
						return false;
					case 7: // circ col
						m = pFilled.matcher(l);
						if(m.matches()){
							filled = Boolean.parseBoolean(l.split(":")[1]);
							state=11;
							break;
						}
						error("Expected Circle filled");
						return false;
					case 8: // rect col
						m = pFilled.matcher(l);
						if(m.matches()){
							filled = Boolean.parseBoolean(l.split(":")[1]);
							state=12;
							break;
						}
						error("Expected Rectangle filled");
						return false;
					case 9: // squiggle col
						m = pFilled.matcher(l);
						if(m.matches()){
							filled = Boolean.parseBoolean(l.split(":")[1]);
							state=13;
							break;
						}
						error("Expected Squiggle filled");
						return false;
					case 10: // poly fil
						m = pFilled.matcher(l);
						if(m.matches()){
							filled = Boolean.parseBoolean(l.split(":")[1]);
							state=14;
							break;
						}
						error("Expected Polyline filled");
						return false;
					case 11: // circ cent
						m = pCenter.matcher(l);
						if(m.matches()){
							temp = l.replace("(", "")
									.replace(")", "").split(":")[1].split(",");
							center = new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
							state=15;
							break;
						}
						error("Expected Circle center");
						return false;
					case 12: // rect p1
						m = pP1.matcher(l);
						if(m.matches()){
							temp = l.replace("(", "")
									.replace(")", "").split(":")[1].split(",");
							p1 = new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
							state=16;
							break;
						}
						error("Expected Rectangle p1");
						return false;
					case 13: // start points squig
						m = pPoints.matcher(l);
						if(m.matches()){
							state=17;
							break;
						}
						error("Expected Squiggle points");
						return false;
					case 14: // start points poly
						m = pPoints.matcher(l);
						if(m.matches()){
							state=18;
							break;
						}
						error("Expected Polyline points");
						return false;
					case 15: //cirl radius
						m = pRadius.matcher(l);
						if(m.matches()){
							temp = l.split(":");
							radius = Integer.parseInt(temp[1]);
							state=19;
							break;
						}
						error("Expected Circle radius");
						return false;
					case 16: // rect p2
						m = pP2.matcher(l);
						if(m.matches()){
							temp = l.replace("(", "")
									.replace(")", "").split(":")[1].split(",");
							p2 = new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
							state=20;
							break;
						}
						error("Expected Rectangle p2");
						return false;
					case 17: //suig point
						m = pEndPoints.matcher(l);
						if(m.matches()){
							state=21;
							break;
						}
						m = pPoint.matcher(l);
						if(m.matches()){
							temp = l.replace("(", "")
									.replace(")", "").split(":")[1].split(",");
							points.add(new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
							break;
						}
						error("Expected Squiggle point or end points");
						return false;
					case 18: // poly point
						m = pEndPoints.matcher(l);
						if(m.matches()){
							state=22;
							break;
						}
						m = pPoint.matcher(l);
						if(m.matches()){
							temp = l.replace("(", "")
									.replace(")", "").split(":")[1].split(",");
							points.add(new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
							break;
						}
						error("Expected Polyline point or end points");
						return false;
					case 19: //end cir
						m = pCircleEnd.matcher(l);
						if(m.matches()){
							if (center == null) error("Created Circle without setting Center");
							circleCommand = new CircleCommand(center, radius);
							circleCommand.setFill(filled);
							circleCommand.setColor(rgbColor);
							paintModel.addCommand(circleCommand);
                            state=1;
							break;
						}
						error("Expected End Circle");
						return false;
					case 20:
						m = pRectangleEnd.matcher(l);
						if(m.matches()){
							if (p1 == null || p2 == null) error("Created Rectangle without setting P1 or P2");
							rectangleCommand = new RectangleCommand(p1, p2);
							rectangleCommand.setFill(filled);
							rectangleCommand.setColor(rgbColor);
							paintModel.addCommand(rectangleCommand);
							state=1;
							break;
						}
						error("Expected End Rectangle");
						return false;
					case 21:
						m = pSquiggleEnd.matcher(l);
						if(m.matches()){
							squiggleCommand = new SquiggleCommand();
							for (Point p : points) {
								squiggleCommand.add(p);
							}
							squiggleCommand.setFill(filled);
							squiggleCommand.setColor(rgbColor);
							paintModel.addCommand(squiggleCommand);
							points.clear();
							state=1;
							break;
						}
						error("Expected End Squiggle");
						return false;
					case 22:
						m = pPolylineEnd.matcher(l);
						if(m.matches()){
							polylineCommand = new PolylineCommand();
							for (Point p : points) {
								polylineCommand.add(p);
							}
							polylineCommand.setFill(filled);
							polylineCommand.setColor(rgbColor);
							paintModel.addCommand(polylineCommand);
							points.clear();
							state=1;
							break;
						}
						error("Expected End Polyline");
						return false;
					case 23:
						m = pAnything.matcher(l);
						if(m.matches()){
							error("Extra content after End of File");
							return false;
						}
						state = 24;
						break;
					// ...
					/**
					 * I have around 20+/-5 cases in my FSM. If you have too many
					 * more or less, you are doing something wrong. Too few, and I bet I can find
					 * a bad file that you will say is good. Too many and you are not capturing the right concepts.
					 *
					 * Here are the errors I catch. All of these should be in your code.
					 *
					 	error("Expected Start of Paint Save File"); done
						error("Expected Start of Shape or End Paint Save File"); done
						error("Expected Circle color"); done
						error("Expected Circle filled"); done
						error("Expected Circle center"); done
						error("Expected Circle Radius"); done
						error("Expected End Circle"); done
						error("Expected Rectangle color");done
						error("Expected Rectangle filled"); done
						error("Expected Rectangle p1"); done
						error("Expected Rectangle p2"); done
						error("Expected End Rectangle"); done
						error("Expected Squiggle color"); done
						error("Expected Squiggle filled");done
						error("Expected Squiggle points"); done
						error("Expected Squiggle point or end points"); done
						error("Expected End Squiggle"); done
						error("Expected Polyline color"); done
						error("Expected Polyline filled");done
						error("Expected Polyline points");done
						error("Expected Polyline point or end points"); done
						error("Expected End Polyline"); done
						error("Extra content after End of File"); done
						error("Unexpected end of file"); done
					 */
				}
			}
			if (state != 24 && state != 6){
				if(this.errorMessage.isEmpty()) {
					error("Unexpected end of file");
					Exception exception = new Exception("Unexpected end of file");
					throw exception;
				}
			}
		}  catch (Exception e){
			System.out.println("Error in line "+lineNumber+" "+e.getMessage());
			return false;
		}
		return true;
	}
}
