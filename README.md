# CultureTalk

CultureTalk to komunikator internetowy dedykowany do obsługi komunikacji pomiędzy osobami zainteresowanymi kulturą na temat wydarzeń organizowanych przez instytucje i ośrodki kultury. 

Dzięki CultureTalk możemy łatwo nawiązywać rozmowy i dyskusje dot. kultury i wydarzeń. Pozwala rejestrować instytucje i wydarzenia poprzez interfejs aplikacji webowej, jednocześnie umożliwiając - dzięki aplikacji na systemy Android - przeglądanie i wyszukiwanie wydarzeń przez użytkowników. 

Projekt oraz działający prototyp został zrealizowany przez młodego, początkującego programistę, studenta.

Stos technologiczny: Java, PostgreSQL, SpringBoot, JPA, Hibernate, 
REST, Thymeleaf, Bootstrap, Android.


# Info

Repozytorium aplikacji Android: https://github.com/filipolszewski/CultureTalkAndroid

Projekty całej aplikacji CultureTalk do programu yED (użyte w filmie):
https://www.dropbox.com/sh/doxs6px0leukrd6/AACcMry7ga3il-wwZSDEWz-ca?dl=0

# Uruchamianie

Aby oba projekty działały, trzeba pobrać kopię zapasową bazy danych Postgresql i stworzyć ją
np. w programie pgAdmin (Stworzyć bazę o nazwie "filip" (credentials: "postgres" / "") następnie użyć na niej opcji "Przywróć" i wybrać plik kopii)
Niestety nie umiem dostarczyć całej, spójnej aplikacji od razu gotowej do testów. Pierwszy raz pisałem aplikację rozproszoną. 
Postaram się pomóc i odpowiedzieć na pytania związane z uruchamianiem aplikacji do testów.

Aby przeprowadzić testy należy aplikację Android uruchomić na telefonie (Potrzebne będzie IDE Android Studio i kilka minut),
uruchomić projekt z repozytorium, w którym właśnie jesteśmy, aby uruchomić potrzebne do działania WebSerwisy, a także, zgodnie z powyższymi instrukcjami skonfigurować bazę danych na podstawie kopii zapasowej.

Aplikacja WEB:
xxxx@com.pl / x

Aplikacja Android:
test@test.com / test

# Kopia zapasowa Postgresql

https://www.dropbox.com/s/6irmbukbdsztuux/cTalk_database.backup?dl=0

# Kontakt

filipolszew@o2.pl
