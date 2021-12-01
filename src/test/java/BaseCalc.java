import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Constantine Mikhalev
 * 28/11/2021
 * YaTest
 */
public class BaseCalc {
    public static String pathToApp = "c:\\YaTest-1.0.jar";

    public static Response execDelivery(Response response) throws IOException {
        String command =  "java -jar " + pathToApp + " " + response.getCommands();
        Process proc = Runtime.getRuntime().exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        String s = null;
        StringBuilder out = new StringBuilder();
        StringBuilder error = new StringBuilder();
        if (stdError.readLine() != null) {
            while ((s = stdError.readLine()) != null)
                error.append(s);
            response.setError(error.toString());
        }
        while ((s = stdInput.readLine()) != null)
            out.append(s);
        response.setOutPut(out.toString());
        return response;
    }
    public static Response execDeliverCheckError(Response response) throws IOException {
        Response resp = execDelivery(response);
        Assert.assertNull(resp.getError(), "Error returned : " + resp.getOutPut());
        return  resp;
    }
}

class Response{
   private String error;
   private String outPut;
   private String commands;

    public Response(String commands) {
        this.commands = commands;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }
}