## Златна топка (35 поени)
Да се имплементира класа `GoldenBall` во која обработуваат податоците од гласањето за најдобар светски фудбалер. Во класата треба да се имплементираат следните методи:

+ `void addVote(String[] players)` - се додаваат поените од едно гласање. Во низата players се наоѓаат 3 имиња на фудбалери за кои се гласа, при што првиот во низата (освоено прво место во гласањето) добива 5 поени, вториот (освено второ место) 3 поени и третиот (освоено трето место) 1 поен.

+ `void listTopN(int n)` - методот ги печати имињата (20 места порамнето во лево), освоените поени и процентот од вкупно можно поени (со две децимални места) на првите `n` фудбалери во опаѓачки редослед според поените (ако имаат ист број на поени, според името). Комплексноста на методот не треба да надминува **O(N∗log2(N))** за вкупно N фудбалери.

+ `void maxPlace(int x)` - метод кој го печати името на фудбалерот на најмногу освоени (x = 1 прво, x = 2 второ, x = 3 трето) места во гласањето. Комплексноста на методот не треба да надминува **O(N)** за вкупно N фудбалери.
+ `void count()` - метод кој печати колку различни фудбалери освоиле барем едно прво место, едно второ место и едно трето место (сите разеделени со едно празно место). Комплексноста на методот не треба да надминува **O(1)**.
