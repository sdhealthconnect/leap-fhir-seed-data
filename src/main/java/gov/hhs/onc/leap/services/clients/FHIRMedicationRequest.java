package gov.hhs.onc.leap.services.clients;

import gov.hhs.onc.leap.services.HapiFhirServer;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FHIRMedicationRequest {
    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Bundle createMedicationRequest(MedicationRequest medicationRequest) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(medicationRequest);
        return bundle;
    }

    public Bundle createMedicationRequestFromBundle(Bundle bundle) {
        Bundle res = hapiFhirServer.createAndExecute(bundle);
        return res;
    }
}
