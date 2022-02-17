import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class FileNameProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        String headers = exchange.getIn().getHeader("CamelFileName").toString();
        final String newHeader = ("converted_" + headers.split("\\.")[0] + ".json");
        exchange.setProperty("newHeader", newHeader);
    }

}
