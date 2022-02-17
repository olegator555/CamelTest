import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
@CsvRecord(separator=",")
public class Model {
    @DataField(pos=1)
    private String UniqueID;
    @DataField(pos=2)
    private String ProductCode;
    @DataField(pos=3)
    private String ProductName;
    @DataField(pos=4)
    private String PriceWholesale;
    @DataField(pos=5)
    private String PriceRetail;
    @DataField(pos=6)
    private String InStock;
}
