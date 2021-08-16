# leap-fhir-seed-data
This repository contains data load mechanisms for populating a new instance of LEAP HAPI-FHIR JPAServer version 4.1.0.
It utilizes Postman version 8.10.0 to simplify the bulk loading of Organization, Practitioners, Questionnaires, and our test Patients clinical records.
There are other freely available tools for performing this task.  This is just one example we have setup for you.

## Steps For Bulk Data Load

**Step #1** Check out this repository:

````
> git clone https://github.com/sdhealthconnect/leap-fhir-seed-data.git
````

**Step #2** Install Postman
- Download and install Postman desktop for your OS platform at postman.com.   Postman.com provides a "Free" community addition plan for up to 3 team members. 

**Step #3** Setup Postman
- Create a workspace for this activity.  Name it LEAP.
![Create Workspace](docs/assets/createworkspace.png?raw=true)

- Import Enviroment File {clone directory}/leap-fhir-seed-data/src/main/resources/fhir-seed-data-environment.postman_environment.json
![Import Environment](docs/assets/importenvironment.png?raw=true)

- Modify Enviroment Variables for your working enviroment.  
You will need to change the values for you SEED_DATA_DIR and HAPI_FHIR_URL they are currently set as.

SEED_DATA_DIR=/Users/duanedecouteau/leap-fhir-seed-data/seed-data

HAPI_FHIR_URL=http://34.94.253.50:8080/hapi-fhir-jpaserver/fhir/

![Modify Environment](docs/assets/modifyenvironment.png?raw=true)

- Set Environment
![No Environment](docs/assets/noenvironment.png?raw=true) 

To

![Environment Set](docs/assets/environmentset.png?raw=true)

- Import FHIR Seed Data Postman Collection File {clone directory}/leap-fhir-seed-data/src/main/resources/postman-collection/fhir-seed-data-collection.postman_collection.json
![Import Collection](docs/assets/importcollection.png?raw=true)

**Step #4** Run Postman Collection
- This will load base LEAP FHIR Resources and the generated clincial history of 10 test patients created using Synthea at https://synthea.mitre.org/downloads.  This will take some time.
![run Collection](docs/assets/runcollection.png?raw=true)


##Steps for Loading Informed Consent Experimental Use Cases

**Step #1** - Modify application-test.yaml with your HAPI FHIR endpoint.  This file is located in the folder {clone directory}/src/test/resources/config.

````
hapi-fhir:
  url: ${HAPI_FHIR_URL:http://34.94.253.50:8080/hapi-fhir-jpaserver/fhir/}
````

**Step #2** - Run Load Tests
- These tests will load ResearchStudy for Pre-Diabetics, identify and create ResearchSubject as potential candiates, and load a MedicationRequest for all patients that requires informed consent.
````
> cd {clone directory}/leap-fhir-seed-data
> mvn test
````

## Post Load Patient-User Provisioning
- Patient Users of the LEAP Consent UI require their accounts to be mapped to their FHIR patient resource on the consent repositiory.  This is done for you as an automated function 
provided to admin user on our demonstration platform.  For more info on this refer to https://github.com/sdhealthconnect/leap-consent-ui#readme.


## Refreshing Data
If you wish to reload your hapi-fhir-jpaserver instance.  Perform the following;
- 1 Shutdown your hapi fhir instance, if using docker the command would be similar to
````
> docker stop hapi-fhir-jpaserver
````

- 2 Log into your mysql instance, and drop hapi database, again if using docker and mysql the command would be similar to 
````
>  docker exec -it {container id} bash
>  mysql -u root -ppassword

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| hapi               |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.00 sec)

mysql> drop database hapi;
mysql> exit
> exit
````

- 3 Restart hapi-fhir-jpaserver.  Again if using docker the command would be similar to;
````
> docker start hapi-fhir-jpaserver
````

If your hapi-fhir-jpaserver is configured correctly it will automatically recreate the database and its tables
on first request to base URL.














