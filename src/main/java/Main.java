import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ExpressionNode;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;


public class Main {
    public static void main(String[] args) throws Exception {
        DataFormat bindy = new BindyCsvDataFormat(Model.class);
        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file://csv_files?noop=true")
                            .routeId("CsvFileRoute")
                            .split(body().tokenize("\n")).streaming()
                            .unmarshal(bindy)
                            .marshal()
                            .json(JsonLibrary.Gson)
                            .process(new FileNameProcessor())
                            .log("Unmarshalled model: ${body}")
                            .to("file://json_files?fileExist=Append&filename=${headers.newHeader}");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.start();
        Thread.sleep(4_000);
        context.close();
    }
}
