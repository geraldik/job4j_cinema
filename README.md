# Cinema
 
В этом учебном проекте разработан сайт для покупки билетов в кинотеатр.
В проекте использованы Spring boot, Thymeleaf, Bootstrap, JDBC.
Для запуска проекта требуются наличие следущих компонентов: 
* JDK17
* Maven 3.6
* PostgreSQL14

С главной страницы пользователь может перейти в раздел со списком фильмов

![1.index.png](image/1.index.png)

![2.movies_quest.png](image/2.movies_quest.png)

Для перехода в раздел с выбором места требуется произвести регистрацию

![3.registration.png](image/3.registration.png)

Либо выполнить авторизацию

![4.login.png](image/4.login.png)

Теперь можно перейти к выбору мест

![5.raw.png](image/5.raw.png)

Сначала ряда

![6.raw_choose.png](image/6.raw_choose.png)

Затем места

![7.seat.png](image/7.seat.png)
![8.seat_choose.png](image/8.seat_choose.png)

Далее пользователь попадает на страницу с купленными билетами

![9.tickets.png](image/9.tickets.png)

При следующей покупке билета (верно и для другого пользователя)
выбор купленного места будет не доступен