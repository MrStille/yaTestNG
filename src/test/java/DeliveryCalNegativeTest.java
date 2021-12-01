import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Constantine Mikhalev
 * 30/11/2021
 * YaTest
 */
public class DeliveryCalNegativeTest extends BaseCalc {
    @DataProvider(name = "negativeSize")
    public Object[][] size() {
        return new String[][]{
                {"distance=100 length=10 width=10", "Error#1.2 Product height is not set"},
                {"distance=31 length=10 width=10 height=-10", "Error#1.2 Product height is not set"},
                {"distance=31 length=10 width=10 height=0", "Error#1.2 Product height is not set"},
                {"distance=30 length=10  height=10", "Error#1.3 Product width is not set"},
                {"distance=10 length=10 width=-1 height=10 fragility=true", "Error#1.3 Product width is not set"},
                {"distance=9  length=10 width=0 height=10 fragility=true", "Error#1.3 Product width is not set"},
                {"distance=2  width=10 height=10 fragility=true", "Error#1.4 Product length is not set"},
                {"distance=2  length=0   width=10 height=10 fragility=true", "Error#1.4 Product length is not set"},
                {"distance=1  length=-10 width=10 height=10 fragility=true", "Error#1.4 Product length is not set"}
        };
    }
    @DataProvider(name = "negativeDistance")
    public Object[][] distance() {
        return new String[][]{
                {" length=10 width=10 height=10", "Error#1.1 Distance is not set"},
                {"distance=0 length=10 width=10 height=10", "Error#1.1 Distance is not set"},
                {"distance=-3 length=10 width=10 height=10", "Error#1.1 Distance is not set"}

        };
    }
 
    @Test(dataProvider = "negativeDistance")
    public void distanceNegativeTest(String command, String errorMessage) throws IOException {
        Response commands = new Response(command);
        Response resp = execDelivery(commands);
        Assert.assertNotNull(resp.getError(), "Error is not returned, instead we got: " + resp.getOutPut());
        Assert.assertTrue(resp.getOutPut().contains(errorMessage),
                String.format("Wrong error message[%s] instead of [%s]",resp.getOutPut(),errorMessage));
    }

    @Test(dataProvider = "negativeSize")
    public void sizeNegativeTest(String command, String errorMessage) throws IOException {
        Response commands = new Response(command);
        Response resp = execDelivery(commands);
        Assert.assertNotNull(resp.getError(), "Error is not returned, instead we got: " + resp.getOutPut());
        Assert.assertTrue(resp.getOutPut().contains(errorMessage),
                String.format("Wrong error message[%s] instead of [%s]",resp.getOutPut(),errorMessage));
    }

    @Test
    public void fragileNegativeTest() throws IOException {
        String fragileTrue = "distance=100 length=10 width=10 height=10 fragility=true";
         Response respFragileTrue = execDelivery(new Response(fragileTrue));
        Assert.assertNotNull(respFragileTrue.getError(), "Error is not returned, instead we got: " + respFragileTrue.getOutPut());
        Assert.assertTrue(respFragileTrue.getOutPut().contains("Error#5.1 You cannot request a delivery of a fragile product to distance more then 30"),
                String.format("Wrong error message[%s] instead of [%s]",respFragileTrue.getOutPut(),"Error#5.1 You cannot request a delivery of a fragile product to distance more then 30 km"));

    }

}
