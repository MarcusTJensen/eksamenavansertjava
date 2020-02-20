**Eksamen PGR200**
Kandidatnummer: 5003
___________________________________________

###KjøreInstruksjoner
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

_________________________________________________


