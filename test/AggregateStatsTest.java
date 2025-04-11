import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.dsl.directive.AggregateStats;
import io.cdap.wrangler.api.executor.DirectiveContext;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.parser.TokenGroup;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregateStats() throws Exception {
        Row row1 = new Row("size", "1MB").add("time", "1s");
        Row row2 = new Row("size", "512KB").add("time", "500ms");

        AggregateStats directive = new AggregateStats();
        directive.initialize(new DirectiveContext(), new TokenGroup(Arrays.asList(
            new Row("arg0", "size"),
            new Row("arg1", "time"),
            new Row("arg2", "total_size_mb"),
            new Row("arg3", "total_time_sec")
        )));

        List<Row> output = directive.execute(Arrays.asList(row1, row2), new ExecutorContext());
        Row result = output.get(0);

        Assert.assertEquals(1.5, (double) result.getValue("total_size_mb"), 0.001);
        Assert.assertEquals(1.5, (double) result.getValue("total_time_sec"), 0.001);
    }
}