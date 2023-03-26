### Perustiedot

- Opinto-ohjelma matemaattisten tieteiden kandiohjelma, tietojenkäsittelyteorian opintosuunta
- Ohjelmointikielenä Java, projektin kielenä suomi
    - Myös Pythonin vertaisarviointi onnistuu, tarvittaessa ehkä myös C++
- Käytetään minimax-algoritmia, jota tehostetaan alpha-beta karsinnalla.
- Jos aikaa jää, tutkitaan eri tapoja tehostaa algoritmiä, kuten esimerkiksi
    - [Null-move heuristic](https://en.wikipedia.org/wiki/Null-move_heuristic)
    - [Quiescence search](https://en.wikipedia.org/wiki/Quiescence_search)
    - [Arviointifunktiot](https://en.wikipedia.org/wiki/Evaluation_function) ja niiden [tehostaminen](https://en.wikipedia.org/wiki/Genetic_algorithm)
    - Tietokanta yleisimmistä aloituksista ja puolustuksista

### Ongelma ja aika-arviot

Pyritään luomaan mahdollisimman hyvä shakkitekoäly, joka tasapainottelee mahdollisimman hyvien siirtojen sekä ajankäytön kanssa. Liian hitaat siirrot voivat johtaa häviämiseen ajan loppumisen vuoksi, mutta hävitä voi myös huonojen siirtojen takia. Yritetään löytää tasapaino näiden kahden väliltä etsimällä optimaali hakusyvyys minimax algoritmille, sekä yritetään tehostaa minimax algoritmiä eri tavoin, jotta saadaan laadukkaampia siirtoja. Voisiko kenties hakusyvyyttä muuttaa kesken pelin jos aika meinaa loppua kesken?

Minimax algoritmin aikavaativuus on O(n^m) ja tilavaativuus O(nm), jossa n on mahdollisten siirtojen määrä ja m on hakusyvyys. Alpha-beta karsinnalla tehostettuna paras mahdollinen aikavaativuus olisi O(n^(m/2)).

### Toteutus

Käytetään hyödyksi valmista [shakkipohjaa](https://github.com/TiraLabra/chess), joka mahdollistaa tekoälyn käyttämisen xboard ja lichess alustoilla. Forkataan projekti, joten githubista näkee helposti, mitä tiedostoja on muutettu tekoälyä luodessa.