package gov.hhs.onc.leap.services.clients;

import gov.hhs.onc.leap.services.HapiFhirServer;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FHIRQuestionnaire {
    //class utilized for loading various LEAP consent questionnaires

    @Autowired
    private HapiFhirServer hapiFhirServer;

    public Bundle createQuestionnaire(Questionnaire questionnaire) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(questionnaire);
        return bundle;
    }

    public Bundle createQuestionnaireFromBundle(Bundle bundle) {
        Bundle res = hapiFhirServer.createAndExecute(bundle);
        return res;
    }
}
