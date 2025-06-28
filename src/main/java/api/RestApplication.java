package api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AllCustomerBill.class);
        classes.add(BillingEventsAPI.class);
        classes.add(InsertInvoiceAPI.class);
        classes.add(InvoiceArchiveAPI.class);
        classes.add(ProductionRunAPI.class);
        classes.add(RenderInvoiceAPI.class);
        return classes;
    }
}