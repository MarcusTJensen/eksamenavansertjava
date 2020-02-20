**Eksamen PGR200**
Kandidatnummer: 5003
___________________________________________

***KjøreInstruksjoner***
- Oppdater postgresql.properties, den ligger i eksamenavansertjava/DB/src/main/resources/postgresql.properties
- Naviger deg til prosjekt mappen og kjør kommandoen: "mvn clean install"
- Deretter kjører du kommandoen: "mvn package"
- Naviger deg så videre til http/target mappen
- Derfra kjører du kommandoen: "java -cp HTTP-1.0-SNAPSHOT-jar-with-dependencies.jar com.hk.exam.http.HttpServer" for å starte serveren
- Åpne en ny terminal og naviger deg til client/target mappen
- Kjør så kommandoen: "java -cp Client-1.0-SNAPSHOT-jar-with-dependencies.jar com.hk.exam.client.Client" for å starte klienten.
- Instruksjoner om hvordan man lister ut/oppdaterer/legger til kommer så fort man starter klienten
_____________________________________________

Programmet er bygget på Maven og har 3 under-moduler (HTTP, DB og Client).
Jeg har også benyttet meg av JUnit for å skrive tester til programmet og har tester for både HTTP-modulen og DB-modulen.
HTTP-modulen består av 2 test klasser med totalt 6 tester, mens DB-modulen består av en test klasse med totalt 4 tester.
Programmet anvender også TravisCI.
Klienten har kommandoene get all, update, get one og add for å oppnå funksjonaliteten spesifisert i oppgaveteksten.
Programmet  benytter PreparedStatement for å beskytte mot SQL injection.
Programmets database kode følger DAO-patternet som spesifisert i oppgaveteksten.
Jeg opplevde noen problemer da jeg skulle dele opp prosjektet i flere moduler.
Jeg fikk bare "noClassDefFoundError" om jeg kjørte .jar filene.
Dette klarte jeg å fikse ved hjelp av maven-assembly-plugin, som lager en jar med dependencies.
Oppgaven er ikke utført i Github classroom. Dette fordi jeg ikke har blitt tilsendt noen invitasjon eller lignende, 
som betyr at det ikke har vært en mulighet. Oppgaven er dog utført i et privat repository på min egen Github konto.
Oppgaveteksten sier også at vi skal legge ved en link til en screencast video hvor vi parprogrammerer på en del av oppgaven.
Ettersom jeg har utført eksamen alene(Eksamenskontoret spesifiserte også at det var en individuell hjemmeeksamen) har
jeg derfor ikke laget noen parprogrammeringsvideo.
I og med at jeg ikke har kunnet benyttet github classroom til denne oppgaven, kan man ikke se bruken av Travis CI.
Jeg har derfor valgt å legge med bilder for å dokumentere at jeg faktisk har brukt det:
![Travis 1](/travis/travis1.JPG)
![Travis 2](/travis/travis2.JPG)
![Travis 3](/travis/travis3.JPG)
![Travis 4](/travis/travis4.JPG)
![Travis 5](/travis/travis5.JPG)
![Travis 6](/travis/travis6.JPG)
______________________________________________________

![Diagram image](/prosjektdiagram.png)
Som tidligere nevnt, har programmet en hoved-modul (eksamenavansertjava) og 3 under-moduler(HTTP, DB og Client).
HTTP-modulen inneholder alt av kommunikasjon mellom Client og Server. Den består av 2 klasser HttpServer og HttpRequest.
HttpRequest klassen oppretter en Http request basert på parametere. Den vil så skrive denne requesten til OutputStream.
HttpRequest klassen har også logikk for å lese response som kommer fra serveren. HttpServer klassen starter en ny server på 
port 8380. Den leser requesten som kommer inn og legger til i eller henter fra databasen, alt ettersom hva requesten inneholder.
Den sender så en response, eventuelt med info fra databasen(dersom det var en get request).

DB-modulen(Database) består av 3 klasser (PostgresqlDataSource, Task og TaskDaoClass) og et interface(TaskDao).
PostgresqlDataSource klassen returnerer en DataSource som brukes i programmet for å koble seg til databasen.
Den leser fra postgresql.properties fila, for å finne url, bruker og passord til databasen.
Task klassen inneholder gettere og settere for alle fieldsa en task vil ha i databasen. Den blir også brukt i forhold 
til å hente fra/legge til i databasen.
TaskDao interfacet holder på alle metodene som omhandler å hente fra eller legge til i databasen. Disse blir implementert
i TaskDaoClass klassen.
TaskDaoClass klassen implementerer metodene spesifisert i TaskDao interfacet. Den tar seg også av oppretting av tabellen 
i databasen og tilkobling til databasen.
Metodene her benytter seg av PreparedStatement for å beskytte mot SQL injection.
Client klassen er klassen som leser og behandler bruker-input. Basert på bruker-input benytter den seg av HttpRequest
klassen for å sende requests til Serveren, og skriver ut svar i System.out.println.

_________________________________________________


