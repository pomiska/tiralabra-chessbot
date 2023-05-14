## Toteutus

### Yleisrakenne

Kansiosta [*src/main/java/datastructureproject*](https://github.com/pomiska/tiralabra-chessbot/tree/master/src/main/java/datastructureproject) sekä tiedostosta [*src/main/java/chess/bot/MinunBot.java*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/chess/bot/MinunBot.java) löytyy kaikki itse toteutettu koodi.

[*MinunBot.java*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/chess/bot/MinunBot.java)sta löytyy itse botin koodi. Botti käyttää siirtojen arvioimiseen minimax-algoritmiä, jota on tehostettu alfa-beta karsinnalla.

[*Arvioija.java*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/datastructureproject/Arvioija.java) tiedostosta löytyy eri nappuloiden ja lautapositioiden arviointiin käytetty koodi, jonka avulla botti päättää, mitkä siirrot ovat parhaita.

[*NappulaTyyppi*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/datastructureproject/NappulaTyyppi.java) luokkaa käytettiin apuna tiedon tallentamisessa.

[*Pelilauta.java*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/datastructureproject/Pelilauta.java) sisältää itse pelilaudan logiikan, eli se tallentaa kummankin pelaajan nappuloiden sijainnin, sekä vastaa siirtojen tekemisen logiikasta.

[*SiirtoGeneraattori.java*](https://github.com/pomiska/tiralabra-chessbot/blob/master/src/main/java/datastructureproject/SiirtoGeneraattori.java) vastaa siirtolistan luomisesta botille.

Ohjelman toimintajärjestys on siis seuraava:
1. Botti luo pelin alussa itselleen pelilauta-olion
2. Botti tekee peli-lauta oliolle kutsun *etsiLaillisetSiirrot*
3. Pelilauta luo SiirtoGeneraattori-olion, jolle annetaan tieto nykyisistä nappuloiden sijainneista
4. Pelilauta tekee SiirtoGeneraattorille kutsun *valkoisenSiirrot* tai *mustanSiirrot*, riippuen kummalla värillä botti pelaa
5. SiirtoGeneraattori palauttaa listan kaikista teoriassa mahdollisista siirroista
6. Pelilauta tekee SiirtoGeneraattorille kutsun *poistaLaittomatSiirrot* ja antaa SiirtoGeneraattorille kyseisen listan.
7. Nappuloiden sijaintien perusteella SiirtoGeneraattori:
- Poistaa kuninkaan laittomat siirrot, eli käytännössä tarkistaa mihin ruutuihin vastustajan nappuloilla on näköyhteys
- Tarkistaa onko kuningas shakissa. Jos on, palauttaa listan siirroista, joilla kuninkaan saa pois shakista
- Tarkistaa, mitkä omat nappulat on kiinnitetty kuninkaaseen
- Poistaa kiinnitetyiltä nappuloilta ne siirrot, joiden siirtämisen myötä kuningas joutuisi shakkiin
- Palauttaa listan siirroista Pelilaudalle
8. Pelilauta palauttaa kyseisen listan botille
9. Botti alkaa käymään siirtojen pisteytystä läpi ja valittuaan parhaan siirron, kommunikoi ensin Pelilaudalle tieton tehdystä siirrosta, jonka jälkeen kommunikoi siirron Lichess- tai XBoard alustalle
10. Seuraavan kerran, kun botille tehdään kutsu *nextMove*, pyydetään *gamestate* oliolta tieto viimeisimmästä siirrosta (eli vastustajan siirrosta), joka kommunikoidaan meidän Pelilaudalle, jonka jälkeen aloitetaan taas kohdasta 1.

### Suorituskyky

Minimax-algoritmin aikavaativuus on O(n^m), jossa n on mahdollisten siirtojen määrä ja m on laskentasyvyys, eli kuinka monta siirtoa eteenpäin lasketaan. Alfa-beta karsinnalla tehostettuna
aikavaativuus parhaassa tapauksessa on O(n^(m/2)) ja pahimmassa tapauksessa, eli tilanteessa jossa koko hakupuu käydään läpi, on sama kuin minimaxilla. Alfa-beta karsintaa voisi tehostaa siirtolistan
järjestämisellä ennen kuin se annetaan algoritmin käsittelyyn, mutta tätä ei keretty onnistuneesti toteuttaa. Laskentasyvyyden ollessa 4 botti onnistui välillä voittamaan Stockfish 14-botin 
tason 4, mutta näille eri tasoille ei löydy varmistettua ELO-ratingia, joten on hankalaa sanoa minkä ELO-ratingin oma bottini saisi.

[Teustausdokumentissa](https://github.com/pomiska/tiralabra-chessbot/blob/master/documentation/tiralabra_dokumentaatio/testausdokumentti.md) on linkkejä esimerkkipeleihin.

### Puutteet ja parannusehdotukset

Lisäämällä siirtojen järjestäminen alfa-beta karsintaa voitaisin tehostaa ja mahdollisesti laskentasyvyyttä voitaisin kasvattaa. Lisäksi siirtojen peruminen jäi bugittamaan lukuisten yritysten jälkeenkin,
joten joka tarkisteltavalle siirrolle joudutaan luomaan oma Pelilauta-olio. Alunperin, kun tieto nappuloista tallennettiin HashMappeina, tämä hidasti botin suoritusta huomattavasti. Kun Pelilauta-
olio refaktoroitiin käyttämään taulukoita, botin laskentanopeus parani huomattavasti. Mysteeriksi jää, olisiko laskentanopeus edelleen nopeampi jos pärjäisi vain yhdellä Pelilauta-oliolla.

### Lähteet
- Tirakirja
- https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
- https://www.chessprogramming.org/Evaluation
- https://www.chessprogramming.org/Simplified_Evaluation_Function
- https://www.chessprogramming.org/Move_Ordering (näistä ei tosin kereytetty toteuttaa mitään)
