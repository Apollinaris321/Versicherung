# Dokumentation

Als Datenbank habe ich MySql gewählt, da ich hier schon Erfahrungen sammeln konnte.
Ebenso passt MySQL als relationale Datenbank gut für die Umsetzung des Projektes, da das 
Schema sich nicht ändert. 
Sie können Ihre eigene MySQL Datenbank anbinden, ansonsten habe ich eine in Docker bereitgestellt.
Hierfür bitte im "src/main/resources/application.properties" die Zeile 8 verwenden anstatt 9:

# Wichtig
Wenn der Docker Container genutzt wird das Passwort nehmen:
spring.datasource.password=password

diese Zeile auskommentieren: 
spring.datasource.password=

Der Container kann gestartet werden über: 

docker-compose up --build -d

Mit 
- docker exec -it versicherung-mysqldb bash  

können Sie sich in den Container über windows einloggen, das Passwort ist "password".

Es wurden mehrere Services erstellt:
 - FahrzeugtypService
 - KilometerleistungService
 - PostleitzahlService
 - RegionService
 - VersicherungspraemienService

Dadurch, dass Fahrzeugtyp, Kilometerleistung und die Region Ihre eigene Serviceklassen besitzen, mit 
REST-Apis ist es für Drittanbieter später einfacher, den Service in Ihr Unternehmen einzubinden und anzupassen,
da alle Werte in einer Datenbank hinterlegt sind. 

Die Trennung zwischen Postleitzahl und Region erfolgt, weil der PostleitzahlService als allgemeiner Lookup-Service dient, um gültige Postleitzahlen zu prüfen.
Der Faktor für die Region wird hingegen im RegionService gespeichert. Dadurch ist der Faktor der Zulassungsstelle nicht direkt an eine Postleitzahl gebunden und kann einfacher geändert werden.

Der Versicherungspraemienservice dient zum einreichen der Anfragen. Alle Anfragen werden dabei verarbeitet
und die benötigten Fahrzeugtyp, Kilometerleistung und Regionobjekte aus der Datenbank entnommen und mit den 
jeweiligen Faktoren der Pramienfaktor ausgerechnet. Damit diese Werte später einsehbar sind, um zum Beispiel 
Statistiken über die Kunden zu erstellen, wird die Anfrage gespeichert mit dem Datum und dem errechneten Prämienfaktor.

Bespielhaft wurden für die Versicherungspraemie und für den Fahrzeugtyp Tests erstellt, um sicherzustellen 
das diese Module korrekt funktionieren. Für die Tests wird eine interne H2 In-Memory Datenbank verwendet.

Es wurde ebenso eine Webbasierte Oberfläche bereitgestellt, diese ist unter "http://localhost:8080/index.html" zu erreichen.
