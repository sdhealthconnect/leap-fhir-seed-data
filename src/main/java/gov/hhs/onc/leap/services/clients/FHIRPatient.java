package gov.hhs.onc.leap.services.clients;

import gov.hhs.onc.leap.services.HapiFhirServer;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.AuditEvent;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class FHIRPatient {
    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Bundle createPatient(Patient patient) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(patient);
        return bundle;
    }

    public Bundle createPatientFromBundle(Bundle bundle) {
        Bundle res = hapiFhirServer.createAndExecute(bundle);
        return res;
    }

    public Collection<Patient> getAllPatients() {
        List<IBaseResource> patientList = hapiFhirServer.getAllPatients();
        Collection<Patient> patientCollection = new ArrayList<>();
        if (patientList != null) {
            Iterator iter = patientList.iterator();
            while (iter.hasNext()) {
                Patient patient = (Patient)iter.next();
                patientCollection.add(patient);
            }
        }
        return patientCollection;
    }
}
