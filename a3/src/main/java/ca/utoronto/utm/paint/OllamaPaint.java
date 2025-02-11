package ca.utoronto.utm.paint;
public class OllamaPaint extends Ollama{
    public OllamaPaint(String host){
        super(host);
    }
    /**
     * removes llama3 banter at the top and bottom of the file.  Returns "Error" if there is an error.
     * @param input Ollama response
     */
    public String RemoveBanter(String input){
        String[] strings = input.split("\n");
        StringBuilder parsedString = new StringBuilder();
        boolean addFlag = false;
        boolean beginFlag = false;
        for (String s: strings){
            if(s.strip().replaceAll(" ", "").equals("PaintSaveFileVersion1.0")) {
                addFlag = true;
                beginFlag = true;
            }
            if(s.strip().replaceAll(" ", "").equals("EndPaintSaveFile")) {
                addFlag = false;
                parsedString.append(s + "\n");
                break;
            };
            if(addFlag) parsedString.append(s + "\n");
        }
        if(!beginFlag || addFlag) return "Error";
        return parsedString.toString();
    }
    /**
     * checks llama3 response for oddities. Runs until there are no oddities.
     */
    private void checkResponse(String outFileName, StringBuilder system, StringBuilder prompt) {
        String response = this.call(system.toString(), prompt.toString());
        String cleanedResponse = RemoveBanter(response);
        while (cleanedResponse.equals("Error")){
            response = this.call(system.toString(), prompt.toString());
            cleanedResponse = RemoveBanter(response);
//            System.out.println(response);
        }
        FileIO.writeHomeFile(cleanedResponse, outFileName);
    }
    /**
     * i hate ollama so much.
     */
    private String moonScrubber(String response){
        return response.replaceAll("Moon", "Circle");
    }

    /**
     * Ask llama3 to generate a new Paint File based on the given prompt
     * @param prompt user prompt
     * @param outFileName name of new file to be created in users home directory
     */
    public void newFile(String prompt, String outFileName){
        // YOUR CODE GOES HERE
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");
        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        String response = this.call(system.toString(), prompt);
        String cleanedResponse = RemoveBanter(response);
        while (cleanedResponse.equals("Error")){
            response = this.call(system.toString(), prompt);
            cleanedResponse = RemoveBanter(response);
        }
//        System.out.println(RemoveBanter(response));
        FileIO.writeHomeFile(cleanedResponse, outFileName);
    }

    /**
     * Ask llama3 to generate a new Paint File based on a modification of inFileName and the prompt
     * @param prompt the user supplied prompt
     * @param inFileName the Paint File Format file to be read and modified to outFileName
     * @param outFileName name of new file to be created in users home directory
     */
    public void modifyFile(String prompt, String inFileName, String outFileName){
        // YOUR CODE GOES HERE
        // Your job is to create the right system and prompt.
        // then call Ollama and write the new file in the home directory
        // HINT: You should have a collection of resources, examples, prompt wrapper etc. available
        // in the resources directory. See OllamaNumberedFile as an example.
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");
        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        String f = FileIO.readHomeFile(inFileName);
        String fullPrompt = "Produce a New Paint Save File, resulting from the following OPERATION being performed on the following Paint Save File. OPERATION START"+prompt+ " OPERATION END "+ f;
        String response = this.call(system.toString(), fullPrompt);
        String cleanedResponse = RemoveBanter(response);
        while (cleanedResponse.equals("Error")){
            response = this.call(system.toString(), fullPrompt);
            cleanedResponse = RemoveBanter(response);
        }

//        System.out.println(RemoveBanter(response));
        FileIO.writeHomeFile(cleanedResponse, outFileName);
        FileIO.writeHomeFile(response, outFileName);
    }

    /**
     * newFile1: Given a prompt about space, it can draw a nice solar system.
     * Note the following.
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile1(String outFileName) {
        // YOUR CODE GOES HERE
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");
        String spaceBackground = FileIO.readResourceFile("spaceBackgroundExample.txt");
        String spacePlanet = FileIO.readResourceFile("spacePlanetExample.txt");
        String spaceMoon = FileIO.readResourceFile("spaceMoonExample.txt");
        String spaceMoonExample1 = FileIO.readResourceFile("EXAMPLEMOON1.txt");
        String spaceMoonExample2 = FileIO.readResourceFile("EXAMPLEMOON2.txt");
        String spaceMoonExample3 = FileIO.readResourceFile("EXAMPLEMOON3.txt");
        String spaceMoonExample4 = FileIO.readResourceFile("EXAMPLEMOON4.txt");
        String spaceMoonExample5 = FileIO.readResourceFile("EXAMPLEMOON5.txt");
        String spaceMoonExample6 = FileIO.readResourceFile("EXAMPLEMOON6.txt");
        String spaceMoonExample7 = FileIO.readResourceFile("EXAMPLEMOON7.txt");
        String spaceMoonExample8 = FileIO.readResourceFile("EXAMPLEMOON8.txt");

        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY."
                + "To draw outer space, use the following format to draw the background: " + spaceBackground
                + "To draw a planet, use the following format to draw a planet: " + spacePlanet
                + "To draw a moon, use the following format to draw a moon: " + spaceMoon
                + "Here are example on how to draw a moon. Use these are arbitrary examples; Use these as reference:" +
                "1. " + spaceMoonExample1 +
                "2. " + spaceMoonExample2 +
                "3. " + spaceMoonExample3 +
                "4. " + spaceMoonExample4 +
                "5. " + spaceMoonExample5 +
                "6. " + spaceMoonExample6 +
                "7. " + spaceMoonExample7 +
                "8. " + spaceMoonExample8);
        StringBuilder prompt = new StringBuilder("Draw outer space (represented with a black background)." +
                " Draw 3 planets. Draw a moon for each planet.");
        String response = this.call(system.toString(), prompt.toString());
        String cleanedResponse = RemoveBanter(response);
        while (cleanedResponse.equals("Error")){
            response = this.call(system.toString(), prompt.toString());
            cleanedResponse = RemoveBanter(response);
        }
        cleanedResponse = moonScrubber(cleanedResponse);

//        System.out.println(RemoveBanter(response));
        FileIO.writeHomeFile(cleanedResponse, outFileName);
    }

    /**
     * newFile2: Draws some trees on a pathway.
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile2(String outFileName) {
        // Sometimes this draws bushes.
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");
        String groundExample1 = FileIO.readResourceFile("EXAMPLEGROUND1.txt");
        String groundExample2 = FileIO.readResourceFile("EXAMPLEGROUND2.txt");
        String skyExample1 = FileIO.readResourceFile("EXAMPLESKY1.txt");
        String treeExample1 = FileIO.readResourceFile("EXAMPLETREE1.txt");
        String treeExample2 = FileIO.readResourceFile("EXAMPLETREE2.txt");
        String treeExample3 = FileIO.readResourceFile("EXAMPLETREE3.txt");
        String treeExample4 = FileIO.readResourceFile("EXAMPLETREE4.txt");
        String treeExample5 = FileIO.readResourceFile("EXAMPLETREE5.txt");
        String treeExample6 = FileIO.readResourceFile("EXAMPLETREE6.txt");
        String treeExample7 = FileIO.readResourceFile("EXAMPLETREE7.txt");
        String treeExample8 = FileIO.readResourceFile("EXAMPLETREE8.txt");
        String treeExample9 = FileIO.readResourceFile("EXAMPLETREE9.txt");
        String treeExample10 = FileIO.readResourceFile("EXAMPLETREE10.txt");
        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        StringBuilder prompt = new StringBuilder("Draw the sky and the ground. Draw 2-5 trees." +
                "1. The sky is a rectangle where p1 is in the form p1:(x,y), where x,y are non-positive integers" +
                "2. The sky is a rectangle where p2 is in the form p2:(500,500)" +
                "3. The sky is a rectangle where the color is a sky blue and is filled." +
                "Here are a few examples of what the ground should look like: " +
                "1." + skyExample1 +
                "The ground is a rectangle where the color is a dark green and is filled. Furthermore:" +
                "1. The ground is a rectangle where p1 is in the form p1:(0,y), where y can range from 150 to 250." +
                "2. The ground is a rectangle where p2 is in the form p2:(500,500)" +
                "3. The ground is drawn after the sky." +
                "3. The ground MUST be drawn." +
                "Here are a few examples of what the ground should look like: " +
                "1." + groundExample1 +
                "2." + groundExample2 +
                "The Trees in the forest are composed of a rectangle and a circle, where:" +
                "1. The Tree log is a rectangle where p1:(x,y) and p2:(x,y), x,y are both non-negative numbers." +
                "2. The Tree log is a rectangle where its color is strictly brown and filled." +
                "3. The Tree log MUST ALWAYS BE DRAWN. In other words, there should always be a rectangle for each tree." +
                "4. The Tree head is a circle whose center is of the form center:(x,y), where y matches the y value for " +
                "the y value of the Tree log's p1 coordinate." +
                "5. The tree head is a circle whose color is of the form color:r,g,b, where each value can range between" +
                " 0 to 255"+
                "6. The tree head is a circle whose is always filled (in other words, filled is always filled:true)." +
                "Here are a few examples of what the tree should look like: " +
                "1." + treeExample1 +
                "2." + treeExample2 +
                "3." + treeExample3 +
                "4." + treeExample4 +
                "5." + treeExample5 +
                "6." + treeExample6 +
                "7." + treeExample7 +
                "8." + treeExample8 +
                "9." + treeExample9 +
                "10." + treeExample10);
        checkResponse(outFileName, system, prompt);
    }

    /**
     * newFile3: Draws a face.
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile3(String outFileName) {
        // YOUR CODE GOES HERE
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");
        String faceExample1 = FileIO.readResourceFile("EXAMPLEFACE1.txt");
        String faceExample2 = FileIO.readResourceFile("EXAMPLEFACE2.txt");
        String faceExample3 = FileIO.readResourceFile("EXAMPLEFACE3.txt");
        String faceExample4 = FileIO.readResourceFile("EXAMPLEFACE4.txt");
        String faceExample5 = FileIO.readResourceFile("EXAMPLEFACE5.txt");
        String faceExample6 = FileIO.readResourceFile("EXAMPLEFACE6.txt");
        String faceExample7 = FileIO.readResourceFile("EXAMPLEFACE7.txt");
        String faceExample8 = FileIO.readResourceFile("EXAMPLEFACE8.txt");
        String faceExample9 = FileIO.readResourceFile("EXAMPLEFACE9.txt");
        String faceExample10 = FileIO.readResourceFile("EXAMPLEFACE10.txt");
        String faceExample11 = FileIO.readResourceFile("EXAMPLEFACE11.txt");
        String faceExample12 = FileIO.readResourceFile("EXAMPLEFACE12.txt");

        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY."
                + "To Draw a face, there are a couple of guidelines that you should be following: "
                + "Head: Can be either filled or not filled."+
                "Head: Must be a circle shape."+
                "Head: Color can vary, but it should encompass all other parts of the face (eyes, nose, and smile)."+
                "Head: Radius must be sufficient to ensure all other parts fit within it."+
                "Eyes: Must be circles."+
                "Eyes: Must be filled."+
                "Eyes: Colors can vary and do not have to match."+
                "Eyes: Can be different sizes."+
                "Eyes: Both eyes must fit entirely within the head."+
                "Nose: Must be a rectangle."+
                "Nose: Must be filled."+
                "Nose: Color can vary but should remain neutral or complementary to other face elements."+
                "Nose: The rectangle must fit entirely within the head and be positioned vertically aligned to suggest a nose."+
                "Smile: Must be represented using a polyline."+
                "Smile: Polyline must have at least three points."+
                "Smile: Polyline must have a shape where y-values decrease then increase as x-values increase (i.e., a smile curve)."+
                "Smile: The polyline can be filled or not, but must clearly represent a smiling curve."+
                "Smile: Must fit entirely within the head."+
                "Smile: Must be below nose."+
                "Here are several examples of faces:"+
                "1." + faceExample1 +
                "2." + faceExample2 +
                "3." + faceExample3 +
                "4." + faceExample4 +
                "5." + faceExample5 +
                "6." + faceExample6 +
                "7." + faceExample7 +
                "8." + faceExample8 +
                "9." + faceExample9 +
                "10." + faceExample10 +
                "11." + faceExample11 +
                "12." + faceExample12);
        StringBuilder prompt = new StringBuilder("Draw smiling face.");
        checkResponse(outFileName, system, prompt);
    }

    /**
     * modifyFile1: MODIFY inFileName TO PRODUCE outFileName BY changing the color of an object
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile1(String inFileName, String outFileName) {
        // YOUR CODE GOES HERE
        String file = FileIO.readHomeFile(inFileName);
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");

        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        String prompt = "Change All circles to the color red. You should not be adding anything," +
                " as you only need to change the colors of shapes";
        StringBuilder fullPrompt = new StringBuilder("Produce a new Numbered Document, resulting from the following" +
                " OPERATION being performed on the following Numbered Document. OPERATION START"+ prompt +
                " OPERATION END " + file);
        checkResponse(outFileName, system, fullPrompt);
    }

    /**
     * modifyFile2: MODIFY inFileName TO PRODUCE outFileName BY Transforming a rectangle into a circle.
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile2(String inFileName, String outFileName) {
        // YOUR CODE GOES HERE
        String file = FileIO.readHomeFile(inFileName);
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");

        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        String prompt = "Change one rectangle into a circle";
        StringBuilder fullPrompt = new StringBuilder("Produce a new Numbered Document, resulting from the following" +
                " OPERATION being performed on the following Numbered Document. OPERATION START"+ prompt +
                " OPERATION END " + file);
        checkResponse(outFileName, system, fullPrompt);
    }
    /**
     * modifyFile3: MODIFY inFileName TO PRODUCE outFileName BY adding a circle.
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile3(String inFileName, String outFileName) {
        // YOUR CODE GOES HERE
        String file = FileIO.readHomeFile(inFileName);
        String format = FileIO.readResourceFile("PaintSaveFileFormat.txt");
        String RectFormat = FileIO.readResourceFile("PaintSaveFileRectFormat.txt");
        String CircleFormat = FileIO.readResourceFile("PaintSaveFileCircleFormat.txt");
        String PolylineFormat = FileIO.readResourceFile("PaintSaveFilePolylineFormat.txt");

        StringBuilder system = new StringBuilder("The answer to this question should be a Paint Save File." +
                " Respond only with a Paint Save File and nothing else. Do not include a title, nor any fancy headings;"
                +
                " Strictly follow the format given. Generally speaking, the format should follow the following guidelines:"
                +
                "1. The Title should be exactly \"Paint Save File Version 1.0\"." +
                "2. The End should be exactly \"End Paint Save File\"." +
                " Here is a more detailed version of the"
                +
                " formatting guide you should be following:" + format +
                ". Here is the format for what Rectangle should be: "
                + RectFormat + ". Here is the format for what Circle should be: "
                + CircleFormat + ". Here is the format for what Polyline should be: "
                + PolylineFormat + ". You should also be following the user's prompt EXACTLY.");
        String prompt = "Add a red circle with radius 50 somewhere on the canvas.";
        StringBuilder fullPrompt = new StringBuilder("Produce a new Numbered Document, resulting from the following" +
                " OPERATION being performed on the following Numbered Document. OPERATION START"+ prompt +
                " OPERATION END " + file);
        checkResponse(outFileName, system, fullPrompt);
    }

    public static void main(String [] args){
        String prompt = null;

//        prompt="Draw a 100 by 120 rectangle with 4 radius 5 circles at each rectangle corner.";
        OllamaPaint op = new OllamaPaint("dh2010pc10.utm.utoronto.ca"); // Replace this with your assigned Ollama server.

        prompt="Draw a 100 by 120 rectangle with 4 radius 5 circles at each rectangle corner.";
        op.newFile(prompt, "OllamaPaintFile1.txt");
        op.modifyFile("Remove all shapes except for the circles.","OllamaPaintFile1.txt", "OllamaPaintFile2.txt" );

        prompt="Draw 5 concentric circles with different colors.";
        op.newFile(prompt, "OllamaPaintFile3.txt");
        op.modifyFile("Change all circles into rectangles.", "OllamaPaintFile3.txt", "OllamaPaintFile4.txt" );

        prompt="Draw a polyline then two circles then a rectangle then 3 polylines all with different colors.";
        op.newFile(prompt, "OllamaPaintFile4.txt");

//        prompt="Modify the following Paint Save File so that each circle is surrounded by a non-filled rectangle. ";
        op.modifyFile("Change all circles into rectangles.", "OllamaPaintFile4.txt", "OllamaPaintFile5.txt" );

        for(int i=1;i<=3;i++){
            op.newFile1("PaintFile1_"+i+".txt");
            op.newFile2("PaintFile2_"+i+".txt");
            op.newFile3("PaintFile3_"+i+".txt");
        }
        for(int i=1;i<=3;i++){
            for(int j=1;j<=3;j++) {
                op.modifyFile1("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_1.txt");
                op.modifyFile2("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_2.txt");
                op.modifyFile3("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_3.txt");
            }
        }
    }
}
