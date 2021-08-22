package gov.hhs.onc.leap.fhir.seed;


import gov.hhs.onc.leap.services.clients.FHIREncounter;
import gov.hhs.onc.leap.services.clients.FHIRMedicationRequest;
import gov.hhs.onc.leap.services.clients.FHIRPatient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@SpringBootTest
@Slf4j
@TestPropertySource("classpath:config/application-test.yaml")
public class FHIRLoadSeedData3MedicationRequestsTest {

    @Autowired
    private FHIRMedicationRequest fhirMedicationRequest;

    @Autowired
    private FHIRPatient fhirPatient;

    @Autowired
    private FHIREncounter fhirEncounter;

    private Collection<Patient> patientList;

    private int patientProcessed = 0;
    private int medRequestsCreated = 0;

    @BeforeEach
    void loadPatientList() {
        patientList = fhirPatient.getAllPatients();
    }

    @Test
    void processPatientMedicationRequests() {
        if (patientList != null) {
            Iterator patientIter = patientList.iterator();
            while (patientIter.hasNext()) {
                Patient patient = (Patient) patientIter.next();
                String patientId = patient.getIdElement().getIdPart();
                System.out.println("Processing patientID: "+patientId);
                Collection<Encounter>  patientEncounters = fhirEncounter.getPatientEncounters(patientId);
                System.out.println("Number of Patient Encounters Returned: "+patientEncounters.size());
                Iterator encounterIter = patientEncounters.iterator();
                //get first if sorted correctly
                Encounter mostRecent = (Encounter) encounterIter.next();
                Encounter.EncounterParticipantComponent participantComponent = mostRecent.getParticipant().get(0);
                Reference participantReference = participantComponent.getIndividual();
                Reference conditionReference = mostRecent.getDiagnosisFirstRep().getCondition();
                String encounterRef = "Encounter/"+mostRecent.getIdElement().getIdPart();

                MedicationRequest newRequest = new MedicationRequest();
                newRequest.setId("leap-medicationrequest-"+patientId);
                newRequest.setStatus(MedicationRequest.MedicationRequestStatus.ONHOLD);
                newRequest.setAuthoredOn(new Date());

                Reference encounterReference = new Reference();
                encounterReference.setReference(encounterRef);
                newRequest.setEncounter(encounterReference);

                newRequest.setIntent(MedicationRequest.MedicationRequestIntent.ORDER);

                Reference patientReference = new Reference();
                patientReference.setReference("Patient/"+patientId);
                newRequest.setSubject(patientReference);

                Coding coding = new Coding();
                coding.setDisplay("Sertraline 25 MG Oral Tablet [Zoloft]");
                coding.setSystem("http://www.nlm.nih.gov/research/umls/rxnorm");
                coding.setCode("212233");

                newRequest.getMedicationCodeableConcept().addCoding(coding);
                newRequest.getMedicationCodeableConcept().setText("Sertraline 25 MG Oral Tablet [Zoloft]");

                newRequest.setRequester(participantReference);

                newRequest.setPerformer(participantReference);

                newRequest.getReasonReference().add(conditionReference);

                try {
                    fhirMedicationRequest.createMedicationRequest(newRequest);
                    medRequestsCreated++;
                }
                catch (Exception ex) {

                    ex.printStackTrace();
                }
                patientProcessed++;
            }
        }
        else {
            assert(false);
        }

        assert(patientProcessed == 10);
        assert(medRequestsCreated == 10);
    }
}
