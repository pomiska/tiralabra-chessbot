## Testausdokumentti

### Yksikkötestit

Testikattavuuden näkee [codecovista](https://app.codecov.io/gh/pomiska/tiralabra-chessbot).

Testit voi myös ajaa itse projektin juuressa komennolla `./gradlew run test` jolloin generoituu jacoco testiraportti, joka löytyy hakemistosta */tiralabra-chessbot/build/reports/tests/test/index.html*

### Suorituskykytestit

Suorituskykyä testattiin lähinnä empiirisesti asettamalla oma botti Stockfish 14 bottia vastaan Lichess alustalla. Suurimman osan ajasta botti päihitti tason 3 Stockfish botin, ja toisinaan pystyi päihittämään myös tason 4 Stockfish botin.

Laskentasyvyyden ollessa 4 pelin kesto oli keskimäärin noin 10 minuuttia, mutta laskentasyvyydellä 5 peli saattoi kestää jopa pari tuntia.

Tässä linkki muutamaan esimerkkipeliin:

https://lichess.org/sp0pMGaE  Laskentasyvyys 5, Stockfish taso 3, tasapeli aseman toiston takia. Pelin kesto noin tunti.

https://lichess.org/cVEJj2rX  Laskentasyvyys 4, Stockfish taso 3, voitto. Pelin kesto 14 minuuttia 54 sekuntia.

https://lichess.org/0STkLIbf Laskentasyvyys 4, Stockfish taso 4, häviö. Pelin kesto 16 minuuttia.

https://lichess.org/A5AZW2IL Laskentasyvyys 4, Stockfish taso 4, häviö. Pelin kesto 3 minuuttai 36 sekuntia.

https://lichess.org/7OfLctcL Laskentasyvyys 4, Stockfish taso 4, voitto. Pelin kesto 8 minuuttia 21 sekuntia






