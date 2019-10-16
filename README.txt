Jocul este strcturat pe baza pattern-ului Singleton.
Se creeaza o instanta a clasei Application care creeaza la randul ei
o instanta a unei clase interne Matrix care reprezinta matricea jocului
populata cu 1 si 0.
Application contine o metoda "simulate" cu care se incepe simularea jocului,
el nu se opreste decat la inchiderea ferestrei.
Teste:
OverpopulationTest -> testeaza daca o celula moare cu mai mult de 3 vecini.
RevivingTest -> testeaza daca o celula invie cand are fix 3 vecini.
SurvivingTest -> testeaza daca o celula ramane in viata cand are 2 sau 3 vecini.
UnderpopulationTest -> testeaza daca o celula moara cand are mai putin de 2 vecini;
BorderlessTest -> testeaza daca dimensiunea matricei ce contine celulele se dubleaza
		atunci cand una din celule ajunge la margine.
Am implementat si o interfata grafica cu diferite meniuri.
Ceea ce nu am reusit sa fac este sa folosesc un JScrollPane pentru a putea vedea matricea mai clar.