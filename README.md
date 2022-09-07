# Cinema

В этом проекте разработан сайт для покупки билетов в кинотеатр.
> **Используемы технологии**: Spring boot, Thymeleaf, Bootstrap, Hibernate, PostgreSql

***
**Требуемые элементы:**
* PostgreSql 14
* JDK 17
* Maven 3.8.1
***
**Перед запуском проекта:**
* создать базу данных с именем ***cinema***
* поменять login/password в файле src/main/resources/db.properties

***
**Запуск приложения:**
* выполнить команду *maven install*
* выполнить команду *java -jar target/job4j_cinema-1.0.jar*
* после запуска сервера перейти на адрес: *http://localhost:8080/index*
***

С главной страницы пользователь может перейти в раздел со списком фильмов

![1.index.png](image/1.index.png)

![2.movies_quest.png](image/2.movies_quest.png)

Для перехода в раздел с выбором места требуется произвести регистрацию

![3.registration.png](image/3.registration.png)

Либо выполнить авторизацию

![4.login.png](image/4.login.png)

Теперь можно перети к выбору мест

![5.raw.png](image/5.raw.png)

Сначала ряда

![6.raw_choose.png](image/6.raw_choose.png)

Затем места

![7.seat.png](image/7.seat.png)
![8.seat_choose.png](image/8.seat_choose.png)

Далее пользователь попадает на страницу с купленными билетами

![9.tickets.png](image/9.tickets.png)

При следующей покупке билеты (верно и для другого пользователя)
выбор купленного места будет не доступен