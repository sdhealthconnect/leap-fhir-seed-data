package gov.hhs.onc.leap.fhir.seed;

import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResearchSubject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import gov.hhs.onc.leap.services.clients.*;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:config/application-test.yaml")
public class FHIRLoadSeedData2ResearchSubjectTest {

    @Autowired
    private FHIRResearchSubject fhirResearchSubject;

    private List<Reference> referenceList;

    @BeforeEach
    void setup() {
        try {
            referenceList = fhirResearchSubject.getSubjectsWithSpecificCondition("http://snomed.info/sct", "15777000");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void createStudySubjects() {
        Iterator iter = referenceList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            Reference ref = (Reference) iter.next();
            ResearchSubject subject = new ResearchSubject();
            subject.setId("asu-prediabetes-"+i);
            subject.setStatus(ResearchSubject.ResearchSubjectStatus.POTENTIALCANDIDATE);
            subject.setIndividual(ref);
            Reference studyReference = new Reference();
            studyReference.setReference("ResearchStudy/NCT04269070");
            studyReference.setDisplay("WorkWell: A Pre-clinical Pilot Study of Increased Standing and Light-intensity Physical Activity in Prediabetic Sedentary Office Workers");
            subject.setStudy(studyReference);
            ResearchSubject subjectResult = fhirResearchSubject.createResearchSubject(subject);
            i++;
        }
        Assert.assertTrue(i > 0);
    }
}
