import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Constantine Mikhalev
 * 28/11/2021
 * YaTest
 */
public class DeliveryCalcTest extends BaseCalc {


    @DataProvider(name = "size")
    public Object[][] providerSize() {
        return new String[][]{
                {"distance=100 length=10 width=10 height=10 workload=Overload","800.0"},
                {"distance=100 length=1 width=1 height=1 workload=Overload","640.0"},
                {"distance=100 Length=10 wiDth=10 heighT=10 workload=Overload","800.0"},

        };
    }
    @DataProvider(name = "distance")
    public Object[][] providerDistance() {
        return new String[][]{
                {"distance=100 length=10 width=10 height=10","500.0"},
                {"distance=31 length=10 width=10 height=10","500.0"},
                {"distance=30 length=10 width=10 height=10 fragility=true","800.0"},
                {"distance=10 length=10 width=10 height=10 fragility=true","700.0"},
                {"distance=9 length=10 width=10 height=10 fragility=true","600.0"},
                {"distance=2 length=10 width=10 height=10 fragility=true","600.0"},
                {"distance=1 length=10 width=10 height=10 fragility=true","550.0"},
                {"DistancE=1 length=10 width=10 height=10 fragility=true","550.0"}
        };
    }
    @DataProvider(name = "workload")
    public Object[][] providerWorkload() {
        return new String[][]{
                {"distance=100 length=10 width=10 height=10 workload=Overload","800.0"},
                {"distance=100 length=10 width=10 height=10 workload=High","700.0"},
                {"distance=100 length=10 width=10 height=10 workload=Medium","600.0"},
                {"distance=100 length=10 width=10 height=10 workload=Normal","500.0"},
                {"distance=100 length=10 width=10 height=10","500.0"},
                {"distance=100 length=10 width=10 height=10 Workload=NORMAL","500.0"},
                {"distance=100 length=10 width=10 height=10 workload=Normal","500.0"},
                {"distance=100 length=10 width=10 height=10 WorkloAd=normal","500.0"},

        };
    }

    @Test(dataProvider = "size")
    public void sizePositiveTest(String command, String resultPrice) throws IOException {
          Response resp = execDeliverCheckError(new Response(command));
          Assert.assertEquals(resp.getOutPut(),resultPrice);

    }
    @Test(dataProvider = "distance")
    public void distancePositiveTest(String command, String resultPrice) throws IOException {
        Response commands = new Response(command);
        Response resp = execDeliverCheckError(commands);
        Assert.assertEquals(resp.getOutPut(),resultPrice);
    }

    @Test
    public void fragilePositiveTest() throws IOException {
        String fragileTrue = "distance=30 length=10 width=10 height=10 fragility=true";
        String fragileTrue2 = "distance=30 length=10 width=10 height=10 Fragility=True";
        String fragileFalse = "distance=30 length=10 width=10 height=10 fragility=false";
        String fragileNull = "distance=30 length=10 width=10 height=10";
        Response respFragileTrue = execDeliverCheckError(new Response(fragileTrue));
        Assert.assertEquals(respFragileTrue.getOutPut(),"800.0");
        Response respFragileTrue2 = execDeliverCheckError(new Response(fragileTrue2));
        Assert.assertEquals(respFragileTrue2.getOutPut(),"800.0");
        Response respFragileFalse = execDeliverCheckError(new Response(fragileFalse));
        Assert.assertEquals(respFragileFalse.getOutPut(),"500.0");
        Response respFragileNull = execDeliverCheckError(new Response(fragileNull));
        Assert.assertEquals(respFragileNull.getOutPut(),"500.0");
    }

    @Test(dataProvider = "workload")
    public void workLoadTest( String command, String resultPrice) throws IOException {
        Response commands = new Response(command);
        Response resp = execDeliverCheckError(commands);
        Assert.assertEquals(resp.getOutPut(),resultPrice);

    }
}
