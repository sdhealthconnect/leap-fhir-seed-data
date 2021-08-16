package gov.hhs.onc.leap.services.clients;

import gov.hhs.onc.leap.services.HapiFhirServer;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class FHIRPractitioner {

    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Bundle createPractitioner(Practitioner practitioner) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(practitioner);
        return bundle;
    }

    public Bundle createPractitionerFromBundle(Bundle bundle) {
        Bundle res = hapiFhirServer.createAndExecute(bundle);
        return res;
    }


}
