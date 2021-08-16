package gov.hhs.onc.leap.services.clients;


import gov.hhs.onc.leap.services.HapiFhirServer;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResearchSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FHIRResearchSubject {

    @Autowired
    private HapiFhirServer hapiFhirServer;

    public ResearchSubject createResearchSubject(ResearchSubject researchSubject) {
        Bundle bundle = hapiFhirServer.createAndExecuteBundle(researchSubject);
        ResearchSubject subject = (ResearchSubject)bundle.getEntry().get(0).getResource();
        return subject;
    }

    public List<Reference> getSubjectsWithSpecificCondition(String system, String code) {
        List<Reference> refList = hapiFhirServer.getSubjectsWithSpecificCondition(system, code);
        return refList;
    }

    public ResearchSubject getResearchSubjectByID(String url) {
        ResearchSubject res = new ResearchSubject();
        Bundle bundle = hapiFhirServer.getResearchSubjectById(url);
        Bundle.BundleEntryComponent b = bundle.getEntryFirstRep();
        res = (ResearchSubject) b.getResource();
        return res;
    }
}
