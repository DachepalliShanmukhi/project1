package io.cdap.wrangler.dsl.directive;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.executor.Directive;
import io.cdap.wrangler.api.executor.DirectiveContext;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.parser.TokenGroup;

import java.util.List;
import java.util.ArrayList;

public class AggregateStats implements Directive {
    private String sizeInputCol, timeInputCol, sizeOutputCol, timeOutputCol;
    private long totalBytes = 0;
    private long totalMillis = 0;
    private int count = 0;

    @Override
    public void initialize(DirectiveContext ctx, TokenGroup args) {
        sizeInputCol = args.asString(0);
        timeInputCol = args.asString(1);
        sizeOutputCol = args.asString(2);
        timeOutputCol = args.asString(3);
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext ctx) {
        for (Row row : rows) {
            Object sizeVal = row.getValue(sizeInputCol);
            Object timeVal = row.getValue(timeInputCol);

            if (sizeVal != null && timeVal != null) {
                ByteSize bs = new ByteSize(sizeVal.toString());
                TimeDuration td = new TimeDuration(timeVal.toString());
                totalBytes += bs.getBytes();
                totalMillis += td.getMilliseconds();
                count++;
            }
        }

        double totalMB = totalBytes / (1024.0 * 1024.0);
        double totalSec = totalMillis / 1000.0;
        Row result = new Row();
        result.add(sizeOutputCol, totalMB);
        result.add(timeOutputCol, totalSec);

        List<Row> output = new ArrayList<>();
        output.add(result);
        return output;
    }

    @Override
    public void destroy() {}
}