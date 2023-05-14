### Käyttöohje

Projekti on tehty toimimaan Lichess-alustan kanssa ja se käynnistetään Gradlen avulla. Pelataksesi Lichess-alustalla tarvitset Lichess käyttäjän ja Lichess API-tokenin.
Voit myös halutessasi pelata itse bottia vastaan käyttäen XBoard alustaa.
Oletuksena botti pelaa aina valkoisella puolella, mutta jos haluat vaihtaa puolta joudut vaihtamaan *MinunBot.java* tiedostosta riveiltä *69* ja *95* `Side.WHITE` kohdan tilalle `Side.BLACK`, jotta botin arviointi toimii oikein.
Ohjeet Lichessin ja XBoardin käyttöönottoon löytyy sivulta https://github.com/TiraLabra/chess/blob/master/documentation/Beginners_guide.md

Tämän jälkeen aja projektin juuressa komento `./gradlew build`
Ja käynnistääksesi botti Lichess sivulla aja komento `./gradlew run --args="--lichess --token=OMA_API_TOKEN_TÄHÄN" `