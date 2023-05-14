## Käyttöohje

Projekti on tehty toimimaan Lichess-alustan kanssa ja se käynnistetään Gradlen avulla. Pelataksesi Lichess-alustalla tarvitset Lichess käyttäjän ja Lichess API-tokenin.
Voit myös halutessasi pelata itse bottia vastaan käyttäen XBoard alustaa.

Oletuksena botti pelaa aina valkoisella puolella, mutta jos haluat vaihtaa puolta joudut vaihtamaan *MinunBot.java* tiedostosta riveiltä *69* ja *95* `Side.WHITE` kohdan tilalle `Side.BLACK`, jotta botin arviointi toimii oikein.

Ohjeet Lichessin ja XBoardin käyttöönottoon löytyy sivulta https://github.com/TiraLabra/chess/blob/master/documentation/Beginners_guide.md

### Botin käynnistäminen Lichess sivulla
Lichessiä käyttäessä aja ensin projektin juuressa komento `./gradlew build`

Jonka jälkeen valitse *Play against a computer* / *Pelaa tietokonetta vastaan*, valitse vastustajan vaikeustaso valitse kummalla puolella haluat botin pelaavan.

Lopuksi käynnistääksesi botti aja projektin juurihakemistossa komento `./gradlew run --args="--lichess --token=OMA_API_TOKEN_TÄHÄN" `

### Botin käynnistäminen XBoard alustalla
Käynnistä XBoard sovellus. Valitse *Engine* valikon alta *Edit Engline list*. Lisää listan loppuun 

`"tira-chess" -fcp "java -jar HAKEMISTON SIJAINTI/tiralabra-chessbot/build/libs/chess-all.jar"`

ja paina *commit changes* sekä *OK*. Tämän jälkeen valitse *Engine* valikon alta *Load New 1st Engine* ja valitse *tira-chess* ja paina *OK*.

Jos botti pelaa valkoisella puolella, valitse vielä *Mode* valikon alta *Machine White*.
