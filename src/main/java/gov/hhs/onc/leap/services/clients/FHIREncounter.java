package gov.hhs.onc.leap.services.clients;


import gov.hhs.onc.leap.services.HapiFhirServer;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class FHIREncounter {
    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Collection<Encounter> getPatientEncounters(final String patientId) {
        List<IBaseResource> encounterList = hapiFhirServer.getAllPatientEncounters(patientId);
        Collection<Encounter> encounterCollection = new ArrayList<>();
        if (encounterList != null) {
            Iterator iter = encounterList.iterator();
            while (iter.hasNext()) {
                Encounter encounter = (Encounter)iter.next();
                encounterCollection.add(encounter);
            }
        }
        return encounterCollection;
    }
}
