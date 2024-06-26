# Spring Онлайн Приложение "Пиццерия"

## Введение
Это веб-проект, позволяющий пользователям просматривать, выбирать и заказывать блюда из пиццерии.
Для повышения удобства и безопасности пользователей система включает аутентификацию пользователей.

## Ключевые особенности
Данные о продуктах и пользователях уже добавлены в систему.

### ADMIN - Есть только 1 админ.
        Email: admin@gmail.com
        Password: !Admin123

####       У админа есть привилегии:
* Просмотр всех профилей пользователей с возможностью изменения их ролей (добавление или удаление роли «РАБОТНИК»).
* Доступ к полной истории заказов и возможность отмечать заказы как выполненные или отмененные.
* Управление меню путем добавления, редактирования или удаления продуктов.
* Просмотр личной информации, но без возможности редактирования.

### USER - 3 постоянных пользователя.

        Email: vlad@gmail.com
        Password: !Vlad123

        Email: alex@gmail.com
        Password: !Alex123

        Email: emilia@gmail.com
        Password: !Emilia123

####       У пользователей есть привилегии:
* Просмотр меню и добавление товаров в корзину.
* Удаление товаров из корзины.
* Размещение заказов.
* Просмотр истории заказов и деталей заказа.
* Доступ к собственной корзине.
* Просмотр и редактирование личной информации, включая имена.
* Доступ и отправка сообщений через контактную форму.

### WORKER - 2 постоянных работника.

        Email: sinan@gmail.com
        Password: !Sinan123

        Email: yulia@gmail.com
        Password: !Yulia123

####       Привилегии работника включают:
* Доступ к просмотру меню.
* Доступ к полной истории заказов и возможность отмечать заказы как выполненные или отмененные.
* Просмотр личной информации с возможностью редактирования имен.


### Поиск:
Система позволяет пользователям просматривать детали своих заказов,
предоставляя им возможность получить доступ к деталям заказа и просмотреть их.

### Обработка ошибок:
В приложении используется комплексный подход к обработке ошибок.
Во-первых, существует глобальный механизм рекомендаций контроллера для обработки ситуаций, когда объекты не найдены,
гарантируя, что пользователь получит соответствующий ответ.
Кроме того, для решения проблем, связанных с неправильными категориями меню, используются специальные исключения контроллера.
Кроме того, стандартная страница ошибок (Whitelabel error page) была заменена на адаптированную страницу «error.html»
для улучшения работы пользователей при возникновении ошибок.
