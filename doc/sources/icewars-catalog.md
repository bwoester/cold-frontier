---
status: in-bearbeitung
type: source-catalog
---
# IceWars-Unterlagen – Quellenkatalog und Fortschritt

**Inventar zuletzt geprüft:** 2026-11-01. Ausgangspunkt: [Google-Drive-Ordner IceWars](https://drive.google.com/drive/folders/0B_fvsE93Ci4dTTlvWEFnYkZXYTQ).

Ziel ist, frühere Arbeit nachvollziehbar im Repository zu bewahren und fachlich auszuwerten. Eine Erwähnung im Gespräch oder ein Drive-Link ist noch keine abgeschlossene Übernahme. Historische IceWars-Daten, Community-Inhalte und frühere eigene Redesign-Entwürfe behalten jeweils ihre Herkunft; daraus folgt keine heutige Spielregel.

## Fortschritt lesen und pflegen

Analyse, Dokumentation und Archivierung werden unabhängig erfasst:

| Achse | Status und Bedeutung |
| --- | --- |
| Analyse | **Offen:** Inhalt nicht geprüft. **Gesichtet:** Überblick, keine systematische Auswertung. **Teilweise:** einzelne Aspekte untersucht. **Gelesen:** Inhalt bzw. Datenstruktur gelesen, fachliche Fragen können offenbleiben. **Abgeschlossen:** alle relevanten Inhalte ausgewertet oder begründet ausgeschlossen. |
| Dokumentation | **Inventar:** nur erfasst. **Kurzbefund:** erste Erkenntnisse hier festgehalten. **Teilübernahme:** ausgewählte Inhalte zusätzlich in Fachseiten übernommen. **Abgeschlossen:** relevante Inhalte mit Herkunft und offenen Fragen im Repo dokumentiert. |
| Archiv im Repo | **Offen:** keine eigenständige Quellenkopie im Repo. **Teilweise:** nur Teile gesichert. **Gesichert:** Original oder geeigneter Export mit Herkunft und Integritätsprüfung im Repo. **Entfällt:** begründeter Ausschluss, gemeinsam festgehalten. |

**Aktueller Stand:** 23 Dateien inventarisiert; noch keine Quelle als vollständig ausgewertet, vollständig dokumentiert oder im Repo archiviert abgeschlossen. Ausgewählte Inhalte aus Tech Tree und Abbau-Tabelle sind bereits fachlich dokumentiert. Die Ressourcen-Zuordnung und alle 64 Einträge der Schiffkosten-XML sind im [Einheitenkatalog](icewars-ships.md) rekonstruiert; die [Forschungsmatrix](icewars-ship-research.md) dokumentiert 38 namensbasierte Grafikbezüge und 26 offene Zuordnungen. Ein gemeinsamer Versionsstand bleibt ungeklärt. Die nachfolgende Tabelle ist die maßgebliche Einzelübersicht; keine pauschale Prozentzahl aus der Anzahl geöffneter Dateien ableiten.

Bei jeder weiteren Bearbeitung die betroffenen Zeilen, Ergebnislinks und nächsten Schritte aktualisieren. Neue Dateien ergänzen, bestehende IDs nicht neu vergeben. Eine Entscheidung, eine Quelle auszuschließen, ebenfalls begründen; nicht stillschweigend aus dem Inventar entfernen.

## Inventar

„Drive geändert“ ist das Metadatum der Ablage, **kein Beleg für Entstehungsdatum, IceWars-Runde oder Spielversion**. IDs IW/RD/UNK dienen der Quellenzuordnung, nicht als Requirement-IDs.

| ID | Quelle | Drive geändert | Herkunft / Einordnung | Analyse | Dokumentation | Archiv im Repo | Befund / Restarbeit |
| --- | --- | --- | --- | --- | --- | --- | --- |
| IW-01 | [ressourcen.xml](https://drive.google.com/file/d/0B_fvsE93Ci4daTh6cGsxanI1WkE/view?usp=drivesdk&resourcekey=0-S3HkE-OC7qvI07N4srsFrw) | 2014-05-04 | Offiziell laut Projektinhaber | Gelesen | Teilübernahme | Offen | Alle zehn Ressourcen im [Einheitenkatalog](icewars-ships.md) zugeordnet; Semantik und Spielversion offen. |
| IW-02 | [handelskurse.xml](https://drive.google.com/file/d/0B_fvsE93Ci4deVZ5VS13S2sySFE/view?usp=drivesdk&resourcekey=0-swFV1uninjMyWosCxJ9Waw) | 2014-05-04 | Offiziell laut Projektinhaber | Gelesen | Kurzbefund | Offen | Preise, Freigaben und Gebühren gelesen; dynamische Preisbildung und Exportkontext offen. |
| IW-03 | [schiffkosten.xml](https://drive.google.com/file/d/0B_fvsE93Ci4dNXJwbzZhTzRZTkk/view?usp=drivesdk&resourcekey=0--Tbgj_pN3P0ugV2nN92V-w) | 2014-05-04 | Offiziell laut Projektinhaber | Gelesen | Teilübernahme | Offen | [Alle 64 Einträge mit Kosten](icewars-ships.md); [Forschungsmatrix](icewars-ship-research.md) für alle IDs, davon 38 mit Grafikbezug. Feldsemantik und gemeinsamer Versionsstand offen. |
| IW-04 | [evo0.png](https://drive.google.com/file/d/0B_fvsE93Ci4dT2VjMVd6MlVRT0k/view?usp=drivesdk&resourcekey=0-Dxq6yH-vU9HLRohWXEvArA) | 2025-04-04 | Community-Referenz | Gesichtet | Kurzbefund | Offen | Starttechnologien sichtbar; Zuordnung zu einer der Serien und Spielversion offen. |
| IW-05 | [evo1.png](https://drive.google.com/file/d/0B_fvsE93Ci4dUmNOTkxwc29xSlE/view?usp=drivesdk&resourcekey=0-ITGyOHLZgh_DzJeyJltNew) | 2014-05-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie A1: lesbare lokale Einheitenbezüge und Terraforming-Gebäudefeld separat in der [Forschungszuordnung](icewars-ship-research.md). Übrige Mechaniken offen. |
| IW-06 | [evo2.png](https://drive.google.com/file/d/0B_fvsE93Ci4dRzFsTm0xbWNEcDQ/view?usp=drivesdk&resourcekey=0-hfGxGwfNyOfGAVKs9zaJkg) | 2014-05-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie A2: zusätzliche Zuordnung Artefaktsammelbasis Alpha samt vorgelagerter Forschung/Gebäudefeldern; siehe [Forschungszuordnung](icewars-ship-research.md). |
| IW-07 | [evo3.png](https://drive.google.com/file/d/0B_fvsE93Ci4dY3JJNFRFX2xoZWc/view?usp=drivesdk&resourcekey=0-sOXrVuud9bXeHwKrecNJHA) | 2014-05-04 | Community-Referenz | Gesichtet | Inventar | Offen | Nur Übersichtssichtung; niedrige Auflösung (312 × 229). |
| IW-08 | [evo4.png](https://drive.google.com/file/d/0B_fvsE93Ci4dWFMzcWh1bXlDTDA/view?usp=drivesdk&resourcekey=0-1kEbHJt01bbODLLEy21Ucw) | 2014-05-04 | Community-Referenz | Gesichtet | Inventar | Offen | Nur Übersichtssichtung; niedrige Auflösung (350 × 194). |
| IW-09 | [evo5.png](https://drive.google.com/file/d/0B_fvsE93Ci4dV29RcmJqaFRNajA/view?usp=drivesdk&resourcekey=0-Px8-FqBb5FC1xM527GqxhQ) | 2014-05-04 | Community-Referenz | Gesichtet | Inventar | Offen | Nur Übersichtssichtung; niedrige Auflösung (388 × 232). |
| IW-10 | [evo7.png](https://drive.google.com/file/d/0B_fvsE93Ci4dR1ptSnpONHBGbFE/view?usp=drivesdk&resourcekey=0-v9PUKhrXQL3adwwkq4xWeg) | 2014-05-04 | Community-Referenz | Gesichtet | Inventar | Offen | Nur Übersichtssichtung; niedrige Auflösung (372 × 298). |
| IW-11 | [IceWars-Techtree-Evo1.png](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view?usp=drivesdk) | 2025-04-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie B1: Einheiten-Knoten und Voraussetzungen in der [Forschungsmatrix](icewars-ship-research.md). Große Eismine bereits dokumentiert; übrige Mechaniken nicht vollständig ausgewertet. |
| IW-12 | [IceWars-Techtree-Evo2.png](https://drive.google.com/file/d/10j0oA_e1vozl-W2JMRedfr8BU6yID_Bh/view?usp=drivesdk) | 2025-04-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie B2: Einheiten-Knoten, Forschungs-/Zusatzfelder und ausgewählte Gebäudewege in der [Forschungsmatrix](icewars-ship-research.md). Atombomber/SDI Gatling nicht mit XML-Namen gleichgesetzt. |
| IW-13 | [IceWars-Techtree-Evo3.png](https://drive.google.com/file/d/1S-C77ux_m7YuEm88GJ6I2x7VAx5zSrgV/view?usp=drivesdk) | 2025-04-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie B3: Einheiten-Knoten, Voraussetzungen und Terraforming-Abweichung in der [Forschungsmatrix](icewars-ship-research.md). |
| IW-14 | [IceWars-Techtree-Evo4.png](https://drive.google.com/file/d/1Tzu_CmrF-Bi_YUVVNtj2ZATInYKacSHr/view?usp=drivesdk) | 2025-04-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie B4: Einheiten-Knoten einschließlich evoübergreifender Zusatzfelder in der [Forschungsmatrix](icewars-ship-research.md). Übrige Mechaniken offen. |
| IW-15 | [IceWars-Techtree-Evo5.png](https://drive.google.com/file/d/1tQWofJMO0Z-jJ2WgiDTjqYixuLYhlkeQ/view?usp=drivesdk) | 2025-04-04 | Community-Referenz | Teilweise | Teilübernahme | Offen | Serie B5: Einheiten-Knoten und Tarn-/Dreadnought-Forschung in der [Forschungsmatrix](icewars-ship-research.md). Übrige Mechaniken offen. |
| RD-01 | [Konzepte](https://docs.google.com/document/d/1XXbbTBkpdvmSU3to5JmIk_GzDSzgKtdaphDi_HYdyhc/edit?usp=drivesdk) | 2014-05-04 | Früheres eigenes Redesign | Gelesen | Kurzbefund | Offen | Aktionen, Bedingungen, Jobs und Produktions-Snapshots; siehe Entwurfsbefunde unten. |
| RD-02 | [Resourcenabbau](https://docs.google.com/spreadsheets/d/1Wng7Wi-kAfF1JS_VECUQVXRTWIYlHXlCo7YKTTP_vnA/edit?usp=drivesdk) | 2019-05-12 | Früheres eigenes Redesign | Teilweise | Teilübernahme | Offen | Werte und Textnotizen gelesen; Zusammenhang in [IceWars-Referenzen](../game-design/icewars-references.md) dokumentiert. Originalformeln und Diagramme noch nicht vollständig geprüft. |
| RD-03 | [playground.tgz](https://drive.google.com/file/d/1BGT6Cq-pUFM8UY9cia4jy66uxmA8gx6h/view?usp=drivesdk) | 2025-03-27 | Früheres eigenes Redesign | Teilweise | Kurzbefund | Offen | Archiv inventarisiert, Java-Quellen/Tests und Notizen gelesen; nicht ausgeführt oder vollständig mit heutigem Code verglichen. |
| RD-04 | [ResourceTicker.html](https://drive.google.com/file/d/0B_fvsE93Ci4dMHpZTTZCcDNuREk/view?usp=drivesdk&resourcekey=0-aCErT4THs21_y2WUeYJi_w) | 2016-02-15 | Früheres eigenes Redesign | Gelesen | Kurzbefund | Offen | Eingebettetes Diagramm dekodiert: Resource und Stock, noch kein ausgearbeitetes Tick-Modell. |
| RD-05 | [conceptual data model](https://drive.google.com/file/d/0B_fvsE93Ci4dWDBlbEVJdWlCOE0/view?usp=drivesdk&resourcekey=0-K_u1VRuU_DWa1SOkguML_Q) | 2016-02-11 | Früheres eigenes Redesign | Gelesen | Kurzbefund | Offen | Diagramm dekodiert: Universe, CelestialBody, Outpost, Ressourcen- und Gebäude-Beziehungen. |
| UNK-01 | [Untitled Diagram.html](https://drive.google.com/file/d/0B_fvsE93Ci4db0V6aWQxQzdGZG8/view?usp=drivesdk&resourcekey=0-XlrNEArQl1ZLYW22yfk4Kg) | 2016-02-11 | Zuordnung offen | Gelesen | Kurzbefund | Offen | Diagramm dekodiert: Lager, Personal, Wartung, Vertrieb; Zusammenhang mit dem Redesign unklar. |
| UNK-02 | [Unbenanntes Diagramm.html](https://drive.google.com/file/d/0B_fvsE93Ci4dUU5jVVpmdGxFZkk/view?usp=drivesdk&resourcekey=0-r27WaWVat1kELFDExBZHwQ) | 2016-01-26 | Zuordnung offen | Gelesen | Kurzbefund | Offen | Diagramm dekodiert: lediglich „Button Text“; möglicher Platzhalter, noch nicht ausgeschlossen. |
| UNK-03 | [paroScan-open-compare](https://drive.google.com/file/d/0B_fvsE93Ci4dZERzNGQtbkllck0/view?usp=drivesdk&resourcekey=0-oXNzNLpneJK2M69juFQJyQ) | 2015-11-19 | Zuordnung offen | Offen | Inventar | Offen | Nur Dateimetadaten bekannt; Inhalt und Projektbezug noch nicht geprüft. |

### Ordner und Lücken

- [draw.io](https://drive.google.com/drive/folders/0B_fvsE93Ci4dfi1IWWM3MzRsMVhkS0ZtSTZ1cHBlUzEzRUpuVmxDSGcydl9kRGpOQVl1SXc): fünf Dateien, einzeln oben erfasst.
- [gemini](https://drive.google.com/drive/folders/1xyivG5bBuNi5ibefhrOucLlELtWfMyXm): Connector lieferte am Prüfdatum keine Dateien; deshalb keine inhaltliche Auswertung möglich.
- Ältere benannte Tech-Tree-Reihe: Evo 1–5 und 7 vorhanden; Evo 6 fehlt. Die andere Reihe umfasst Evo 1–5; die Zuordnung der separat benannten Evo-0-Datei bleibt offen.
- Metadaten belegen weder die Autorenschaft der Community-Grafiken noch die ursprünglichen offiziellen XML-Endpunkte. Diese Herkunft ist weiter zu präzisieren.

## Bisherige Befunde

### Offizielle XMLs laut Projektinhaber (IW-01 bis IW-03)

Originale sind XML mit deklarierter ISO-8859-1-Kodierung. Die Dateiausgabe des Connectors ergänzte teilweise irreführend „.xsl“; Inhalt und ursprünglichen Dateinamen bei einer Archivierung getrennt prüfen.

- **IW-01:** Ressourcen-IDs: 1 Eisen, 2 Stahl, 3 VV4A, 4 Eis, 5 chem. Elemente, 6 Wasser, 7 Energie, 8 Pinguine, 10 Credits, 11 Bevölkerung. Die gemeinsame Struktur belegt keine gemeinsame Lager-, Verbrauchs- oder Besitzsemantik. Produktionsrezepte fehlen.
- **IW-02:** Zehn Ressourcen-Einträge mit An-/Verkaufspreisen und Handelsfreigabe. Aktiv markiert sind IDs 1–7; nicht aktiv sind 8, 10 und 11. Gebührenfelder: 4 Prozent, einmalig 100, Mindestmenge 100. Anwendung und Perspektive der Gebühren-/Preisfelder sind noch zu klären. Interner Aktualisierungszeitpunkt: 2014-05-04 10:00:01 UTC. Keine belegte Preisbildungsformel.
- **IW-03:** 64 Einträge, darunter Lebensformen; Kostenvektoren, Dauer, Kategorie und baubar. 56 Einträge mit baubar=1, acht mit baubar=0. Kategorien: Lebensformen (2), Sonden (3), Zivile Schiffe (22), Jäger (6), Bomber (4), Korvetten (5), Zerstörer (5), Kreuzer (6), Schlachtschiffe (4), Dreadnoughts (5), Spezielle Schiffe (2). Interner Aktualisierungszeitpunkt: 2014-05-03 22:00:01 UTC. Bedeutung von baubar (global oder accountabhängig) und Einheit der Dauer nicht gesichert. Die [vollständige Einheitenliste](icewars-ships.md) ist jetzt mit allen Nichtnull-Kosten, Dauer und baubar in Markdown übertragen. Die ID-Hypothese zeigt Gegenbeispiele; [Forschungszuordnungen](icewars-ship-research.md) liegen jetzt für 38 Einträge vor, bei 26 fehlen belastbare Belege.

### Community-Tech-Trees (IW-04 bis IW-15)

Die beiden Darstellungsreihen nicht zu einem vermeintlich einheitlichen Regelwerk verschmelzen. „Grundlagen Terraformen“ steht in IW-05 (evo1.png) mit 30.000 FP, in IW-13 (IceWars-Techtree-Evo3.png) mit 500.000 FP. Unterschiede sind belegt; genaue Versionszuordnung offen.

IW-11 zeigt die große Eismine unter Robotik, aber keine Förderwerte. IW-13 unterscheidet in der Legende Forschungspunkte, Ressourcen-, Forschungs- und Gebäudevoraussetzungen sowie Evolution und evoübergreifende Freischaltungen. Die [Forschungszuordnung](icewars-ship-research.md) dokumentiert die Einheiten-Knoten der Serie B1–B5, ausgewählte Befunde der älteren A1/A2 und die Auflösungs-/Versionsgrenzen der übrigen Grafiken. Die restlichen Mechaniken der Tech Trees sind weiterhin nicht vollständig ausgewertet.

### Frühere eigene Entwürfe (RD-01 bis RD-05)

- **RD-01, Konzepte:** Aktionen mit Start-, fortlaufenden und Abschlussbedingungen; Jobs mit positivem/negativem Abschluss und Client-Benachrichtigung; Ressourcenberechnung über Abschnitte konstanter Produktionsraten. Zeit als Ressource und Produktions-Snapshots sind damalige Lösungsideen, keine akzeptierten Architekturentscheidungen. Zeitpunkt von Kostenabzug und erneuter Bedingungsprüfung bleibt unklar.
- **RD-02, Resourcenabbau:** Zahlenmodell mit 50.000 Startvorkommen, Grundförderung 20 je Mine/Tick und wachsender Minenzahl. Gelesene Werte entsprechen Minenzahl × Grundförderung × relativem Restvorkommen. Notiz: mehrere Vorkommensarten (u. a. tiefes Eis, Permafrost) und passende Minentypen; Raubbau-Effekt wiederholt sich über Entwicklungsstufen. Die anfängliche relative Dichte 1 ist nicht gleichbedeutend mit einer absoluten planetaren Eisdichte von 100 Prozent. Originalformeln, vollständige Arbeitsmappe und Verhalten an einer Mindestdichte noch zu prüfen.
- **RD-03, playground:** Java-Prototyp mit Stock, Production, Buildings und Construction sowie tickbezogenen Historien und Tests. Quellen rechnen noch schrittweise über Ticks; eine Historie belegt keine ereignisdiskrete Simulation. Bestände werden bei null begrenzt, während Energieengpässe und Erschöpfung noch TODOs sind. Enthaltene Build-Artefakte und Testberichte sind keine von uns ausgeführte Validierung. Notizen überschneiden sich mit [heutigen technischen Notizen](../notes.md); vollständiger Herkunfts-/Versionsvergleich steht aus.
- **RD-04, ResourceTicker:** Diagrammfragment mit Resource (id, amount, field) und Stock (resources). Der Dateiname allein belegt keinen ausgearbeiteten Ressourcen-Ticker.
- **RD-05, conceptual data model:** Universe, User, CelestialBody und Outpost; Himmelskörper- und Außenpostentypen; Beziehungen für Bestand, Produktion, Lagerkapazität, Gebäudekosten und Unterhalt. Enthält auch Zeit als Ressourcenkandidat. Begriffe und Kardinalitäten nicht ungeprüft als heutiges Domain-Modell übernehmen.

### Noch unklar zugeordnete Dateien (UNK-01 bis UNK-03)

UNK-01 enthält Abteilungen und Aufgaben zu Lager, Personal, Wartung und Vertrieb; der Projektbezug ist offen. UNK-02 enthält nur „Button Text“. UNK-03 wurde noch nicht inhaltlich geöffnet. Kein Ausschluss ist damit bereits entschieden.

## Ergebnisse und heutige Entscheidungen

- [Forschungszuordnung aller XML-Einheiten](icewars-ship-research.md): direkte Forschung, FP, Zusatzfelder, ausgewählte indirekte Gebäudeanforderungen und explizite Unsicherheiten. 37 Zuordnungen in B1–B5, eine zusätzliche in A2; keine Cold-Frontier-Regeln.

- [Rekonstruierter Einheitenkatalog](icewars-ships.md): vollständige Extraktion von IW-03 mit Ressourcen aus IW-01, Quellen-Prüfsummen und Prüfung der ID-/Tier-Hypothese. Technische Extraktion abgeschlossen; historische Einordnung und Quellenarchivierung noch offen.

- [IceWars-Referenzen](../game-design/icewars-references.md): ausgewählte Quellenbefunde und Benjamins ausdrücklich gekennzeichneter Erfahrungsbericht vom 2026-11-01. Der Erfahrungsbericht ist eine zusätzliche Quelle außerhalb des Drive-Dateiinventars.
- [Planetare Wirtschaft](../game-design/economy.md): bestätigte Designrichtung zu sinkender Ergiebigkeit, begrenzter Baukapazität, Quellenwechsel und Überleben bei Abwesenheit; konkrete Regeln und Balancing bleiben offen.

**Nicht gleichsetzen:** Quelle archiviert ≠ Quelle verstanden ≠ Idee übernommen. Auch verworfene frühere Ideen können als historische Arbeit aufbewahrt werden.

## Nächste Arbeitsschritte

1. **Quellen sichern:** Originaldateien beziehungsweise geeignete Exporte im Repo archivieren. Als Zielstruktur ist `doc/sources/icewars/` vorgesehen: getrennte Unterordner für offizielle Daten, Community-Material und eigene frühere Entwürfe. Diese Ordner und Quellenkopien sind noch nicht angelegt.
2. **Herkunft erhalten:** Pro Quellenkopie ID, Drive-Datei-ID/Link, Abrufdatum, ursprünglichen Namen, Version soweit bekannt und SHA-256 festhalten. Konvertierte Fassungen als solche kennzeichnen. Eigene Texte zusätzlich als Markdown lesbar machen; Tabellenformeln und Diagrammstruktur erhalten.
3. **Gemeinsam auswerten:** Zunächst RD-01 (Aktionen/Bedingungen) und RD-02 (Abbau-Modell) vertiefen; danach Datenmodell und Tech Trees. Pro Thema Befund, alte Idee, heutige Entscheidung und offene Frage unterscheiden.
4. **Abschluss prüfen:** Relevante Inhalte dokumentiert oder begründet ausgeschlossen, Ergebnislinks gesetzt und Quellenarchivierung separat nachgewiesen. Erst dann die jeweilige Achse auf abgeschlossen beziehungsweise gesichert setzen.

Die Reihenfolge ist ein Arbeitsvorschlag, keine neue Game-Design- oder Architekturentscheidung.
