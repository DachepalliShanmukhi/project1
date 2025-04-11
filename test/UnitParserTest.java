import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class UnitParserTest {

    @Test
    public void testByteSizeParsing() {
        Assert.assertEquals(1024, new ByteSize("1KB").getBytes());
        Assert.assertEquals(1048576, new ByteSize("1MB").getBytes());
    }

    @Test
    public void testTimeDurationParsing() {
        Assert.assertEquals(1000, new TimeDuration("1s").getMilliseconds());
        Assert.assertEquals(60000, new TimeDuration("1m").getMilliseconds());
    }
}