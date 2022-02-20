import com.cameltest.ContextConfigurator;
import com.cameltest.QueueRoute;
import com.cameltest.RouteParameters;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.*;

import javax.jms.ConnectionFactory;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ProjectTest {
    private static final String RECEIVED_FILE_PATH = "file://src/test/received_files";
    private final RouteParameters routeParameters = new RouteParameters("file://src/test/csv_test_files","activeMQ", true,
            "test_queue", "test_message");

    @BeforeClass
    public static void before() {
        System.out.println("Starting tests");
    }
    @AfterClass
    public static void after() {
        System.out.println("All ran tests were successfully passed");
    }

    @Test
    public void isTheRouteCorrect() {
        String correctFrom = "file://src/test/csv_test_files?noop=true";
        String correctTo = "activeMQ:test_queue?destinationName=test_message";
        assertThat(routeParameters.getFrom(), is(correctFrom));
        assertThat(routeParameters.getTo(), is(correctTo));
    }

    @Test
    public void isMessageSentToQueue() throws Exception {
        final String QUEUE_NAME = "activeMQ";
        final String BROKER_URL = "tcp://0.0.0.0:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        QueueRoute queueRoute = new QueueRoute(routeParameters);
        AtomicBoolean wasCaught = new AtomicBoolean(false);
        String correct_json_string = "{\"UniqueID\":\"6c8k7a37c\",\"ProductCode\":\"04321\",\"ProductName\":\"SOME_KIT\",\"PriceWholesale\":\"190.80\",\"PriceRetail\":\"210.40\",\"InStock\":\"300\"}";
        new ContextConfigurator(QUEUE_NAME,BROKER_URL).configure(queueRoute);
        new ContextConfigurator(QUEUE_NAME, BROKER_URL).configure(new TestRoute(new RouteParameters
                ("activeMQ:test_message", "file://src/test/received_files?filename=test.json", false,
                        null, null), exchange -> {
                                String received_message = exchange.getIn().getBody(String.class);
                                System.out.println("received_message = " + received_message);
                                System.out.println("coorect Stringgg = " + correct_json_string);
                                try {
                                    assertEquals(correct_json_string, received_message);
                                } catch (AssertionError error) {
                                    wasCaught.set(true);
                                }
                            }));

        assertFalse(wasCaught.get());
    }

    @Test
    public void isJsonValid() throws Exception {
        AtomicBoolean isValid = new AtomicBoolean(true);
        new ContextConfigurator().configure(new TestRoute(new RouteParameters("file://src/received_files",
                null, true, null, null), new Processor() {
            @Override
            public void process(Exchange exchange) {
                 {
                     String json_string = exchange.getIn().getBody(String.class);
                     try {
                         Gson gson = new Gson();
                         gson.fromJson(json_string, Object.class);

                     } catch (JsonSyntaxException e) {
                         isValid.set(false);
                     }
                 }
                }
        }));
        assertTrue(isValid.get());

    }

}
