import com.cameltest.QueueRoute;
import com.cameltest.RouteParameters;
import org.apache.camel.Processor;


public class TestRoute extends QueueRoute {
    private RouteParameters routeParameters;
    Processor processor;
    public TestRoute(RouteParameters routeParameters) {
        super(routeParameters);
    }
    public TestRoute(RouteParameters routeParameters, Processor processor) {
        this.routeParameters = routeParameters;
        this.processor = processor;
    }

    @Override
    public void configure() {
        boolean isToNull = false;
        try {
            routeParameters.getTo();
        } catch (NullPointerException e) {
            isToNull = true;
        }
        if(isToNull)
            from(routeParameters.getFrom()).process(processor);
        else
            from(routeParameters.getFrom()).process(processor).to(routeParameters.getTo());

    }
}
