package gov.hhs.onc.leap.services.clients;


import gov.hhs.onc.leap.services.HapiFhirServer;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class FHIROrganization {
    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Bundle createOrganization(Organization organization) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(organization);
        return bundle;
    }

    public Bundle createOrganizationsFromBundle(Bundle bundle) {
        Bundle res = hapiFhirServer.createAndExecute(bundle);
        return res;
    }

}
