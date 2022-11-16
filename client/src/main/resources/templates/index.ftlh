<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/index.css">
    <link rel="stylesheet" href="/splash.css">
    <#if !skipsplash??>
        <link rel="stylesheet" href="/animation.css">
    </#if>
    <title>ФРОНТ</title>
</head>
<body>
<div class="splash-1">
    <div>
        <div style="display: inline;">
            <span class="s1">НЕВЕРОЯТНО</span>
            <span class="s2">красивый</span>
        </div>
        <span class="s3">и главное</span>
    </div>
    <span class="s4">очень нужный</span>
    <span class="s5">в этом проекте</span>
    <div class="d6">
        <span class="s6-1">Ф</span>
        <span class="s6-2">РО</span>
        <span class="s6-3">Н</span>
        <span class="s6-4">Т</span>
    </div>
</div>

<div class="splash-2">
    <span class="s1">НА</span>
    <span class="s2">который</span>
    <span class="s3">потрачена</span>
    <span class="s4">К У Ч А</span>
    <span class="s5">времени</span>
</div>

<div class="splash-3">
    <object data="/hourglass.svg"></object>
</div>

<#if splashCanBeSkipped??>
    <div class="skipsplash">
        <a href="/?skipsplash">Я хочу пропустить эту бесподобную анимацию</a>
    </div>
</#if>

<div class="background">
    <div id="page-main" class="page current">
        <h1>Выберите действие</h1>
        <hr/>
        <div class="wide-button noselect" id="btn-add-group">Добавить группу</div>
        <div class="wide-button noselect" id="btn-put-group">Изменить группу с определённым ID</div>
        <div class="wide-button noselect" id="btn-delete-group">Удалить группу с определённым ID</div>
        <div class="wide-button noselect" id="btn-get-group">Найти группу по ID</div>
        <div class="wide-button noselect" id="btn-get-all-groups">Все группы</div>
        <h1>Дополнительные функции</h1>
        <hr/>
        <div class="wide-button noselect" id="btn-count-higher-semester">Посчитать кол-во групп с более высоким семестром</div>
        <div class="wide-button noselect" id="btn-prefix">Найти группы с именами, начинающимися с префикса</div>
        <form method="post" action="/get_unique_forms">
            <div class="wide-button noselect submit">Отобразить уникальные формы обучения</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
        <div class="wide-button noselect" id="btn-change-edu-form">Сменить форму обучения группы</div>
        <form method="post" action="/count_expelled">
            <div class="wide-button noselect submit">Посчитать количество отчисленных студентов</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-add-group" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Добавить группу
        </h1>
        <hr/>
        <form method="post" action="/add">
            <div class="field">
                <label>Имя</label><input type="text" name="name" />
            </div>
            <div class="header">
                <span class="label">Координаты</span>
            </div>
            <div class="indent">
                <div class="field">
                    <label>X (int)</label><input type="text" name="coordinates.x" />
                </div>
                <div class="field">
                    <label>Y (float)</label><input type="text" name="coordinates.y" />
                </div>
            </div>
            <div class="field">
                <label>Число студентов</label><input type="text" name="studentsCount" />
            </div>
            <div class="field">
                <label>Форма обучения</label>
                <select name="formOfEducation">
                    <option value="DISTANCE_EDUCATION">Дистанционное обучение</option>
                    <option value="FULL_TIME_EDUCATION">Полный день</option>
                    <option value="EVENING_CLASSES">Вечернее обучение</option>
                </select>
            </div>
            <div class="field">
                <label>Семестр</label>
                <select name="semesterEnum">
                    <option value="SECOND">Второй</option>
                    <option value="THIRD">Третий</option>
                    <option value="FOURTH">Четвёртый</option>
                    <option value="FIFTH">Пятый</option>
                    <option value="SEVENTH">Седьмой</option>
                </select>
            </div>
            <div class="header">
                <span class="label">Староста (необязательно)</span>
            </div>
            <div class="indent">
                <div class="field">
                    <label>Имя</label><input type="text" name="groupAdmin.name" />
                </div>
                <div class="field">
                    <label>Номер паспорта</label><input type="text" name="groupAdmin.passportID" />
                </div>
                <div class="field">
                    <label>Цвет глаз</label>
                    <select name="groupAdmin.eyeColor">
                        <option value="GREEN">Зелёный</option>
                        <option value="BLACK">Чёрный</option>
                        <option value="YELLOW">Жёлтый</option>
                        <option value="ORANGE">Оранжевый</option>
                        <option value="WHITE">Белый</option>
                    </select>
                </div>
                <div class="field">
                    <label>Цвет волос</label>
                    <select name="groupAdmin.hairColor">
                        <option value="BLUE">Синий</option>
                        <option value="BLACK">Чёрный</option>
                        <option value="BROWN">Коричневый</option>
                        <option value="ORANGE">Оранжевый</option>
                        <option value="WHITE">Белый</option>
                    </select>
                </div>
                <div class="field">
                    <label>Национальность</label>
                    <select name="groupAdmin.nationality">
                        <option value="CHINA">Китаец</option>
                        <option value="INDIA">Индус</option>
                        <option value="JAPAN">Японец</option>
                        <option value="ITALY">Итальянец</option>
                    </select>
                </div>
            </div>

            <div class="wide-button noselect center-text submit">Добавить</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>

    </div>

    <div id="page-put-group" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Изменить группу с определённым ID
        </h1>
        <hr/>
        <form method="post" action="/put">
            <div class="field" style="margin-bottom: 10px;">
                <label><b>ID</b></label><input type="text" name="id" />
            </div>
            <div class="field">
                <label>Имя</label><input type="text" name="name" />
            </div>
            <div class="header">
                <span class="label">Координаты</span>
            </div>
            <div class="indent">
                <div class="field">
                    <label>X (int)</label><input type="text" name="coordinates.x" />
                </div>
                <div class="field">
                    <label>Y (float)</label><input type="text" name="coordinates.y" />
                </div>
            </div>
            <div class="field">
                <label>Число студентов</label><input type="text" name="studentsCount" />
            </div>
            <div class="field">
                <label>Форма обучения</label>
                <select name="formOfEducation">
                    <option value="DISTANCE_EDUCATION">Дистанционное обучение</option>
                    <option value="FULL_TIME_EDUCATION">Полный день</option>
                    <option value="EVENING_CLASSES">Вечернее обучение</option>
                </select>
            </div>
            <div class="field">
                <label>Семестр</label>
                <select name="semesterEnum">
                    <option value="SECOND">Второй</option>
                    <option value="THIRD">Третий</option>
                    <option value="FOURTH">Четвёртый</option>
                    <option value="FIFTH">Пятый</option>
                    <option value="SEVENTH">Седьмой</option>
                </select>
            </div>
            <div class="header">
                <span class="label">Староста (необязательно)</span>
            </div>
            <div class="indent">
                <div class="field">
                    <label>Имя</label><input type="text" name="groupAdmin.name" />
                </div>
                <div class="field">
                    <label>Номер паспорта</label><input type="text" name="groupAdmin.passportID" />
                </div>
                <div class="field">
                    <label>Цвет глаз</label>
                    <select name="groupAdmin.eyeColor">
                        <option value="GREEN">Зелёный</option>
                        <option value="BLACK">Чёрный</option>
                        <option value="YELLOW">Жёлтый</option>
                        <option value="ORANGE">Оранжевый</option>
                        <option value="WHITE">Белый</option>
                    </select>
                </div>
                <div class="field">
                    <label>Цвет волос</label>
                    <select name="groupAdmin.hairColor">
                        <option value="BLUE">Синий</option>
                        <option value="BLACK">Чёрный</option>
                        <option value="BROWN">Коричневый</option>
                        <option value="ORANGE">Оранжевый</option>
                        <option value="WHITE">Белый</option>
                    </select>
                </div>
                <div class="field">
                    <label>Национальность</label>
                    <select name="groupAdmin.nationality">
                        <option value="CHINA">Китаец</option>
                        <option value="INDIA">Индус</option>
                        <option value="JAPAN">Японец</option>
                        <option value="ITALY">Итальянец</option>
                    </select>
                </div>
            </div>

            <div class="wide-button noselect center-text submit">Изменить</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-delete-group" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Удалить группу
        </h1>
        <hr/>
        <form method="post" action="/delete">
            <div class="field" style="margin-bottom: 10px;">
                <label>ID</label><input type="text" name="id" />
            </div>


            <div class="wide-button noselect center-text submit">Удалить</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-get-group" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Найти группу по ID
        </h1>
        <hr/>
        <form method="post" action="/get">
            <div class="field" style="margin-bottom: 10px;">
                <label>ID</label><input type="text" name="id" />
            </div>


            <div class="wide-button noselect center-text submit">Найти</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-get-all-groups" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Все группы
        </h1>
        <hr/>
        <form name="getAll" method="post" action="/getAll">
            <div class="field">
                <label>Номер страницы (необязательно)</label><input type="text" name="pageNumber" />
            </div>
            <div class="field">
                <label>Размер страницы (необязательно)</label><input type="text" name="pageSize" />
            </div>
            <div class="field">
                <label>Порядок сортировки</label>
                <select name="sortType">
                    <option value="asc">По возрастанию</option>
                    <option value="desc">По убыванию</option>
                </select>
            </div>
            <div class="header">
                <span class="label">Сортировать по</span>
            </div>
            <div class="field with-button">
                <select id="combo-order-by">
                    <option value="id" disabled>ID</option>
                    <option value="name">Имя</option>
                    <option value="coordinates.x">Координата X</option>
                    <option value="coordinates.y">Координата Y</option>
                    <option value="creationDate">Дата создания</option>
                    <option value="studentsCount">Количество студентов</option>
                    <option value="formOfEducation">Форма обучения</option>
                    <option value="semesterEnum">Семестр</option>
                    <option value="groupAdmin.name">Имя старосты</option>
                    <option value="groupAdmin.passportID">ID паспорта старосты</option>
                    <option value="groupAdmin.eyeColor">Цвет глаз старосты</option>
                    <option value="groupAdmin.hairColor">Цвет волос старосты</option>
                    <option value="groupAdmin.nationality">Национальность старосты</option>
                </select>
                <input type="button" id="btn-order-by-plus" value="+" />
            </div>
            <div class="field with-button">
                <select multiple id="list-order-by">
                    <option value="id">ID</option>
                </select>
                <div class="vertical-toolbar">
                    <input type="button" id="btn-order-by-higher" value="&#9652;" disabled />
                    <input type="button" id="btn-order-by-lower" value="&#9662;" disabled />
                    <div class="gap"></div>
                    <input type="button" id="btn-order-by-minus" value="–" disabled />
                </div>
            </div>
            <input type="hidden" name="orderBy" id="hidden-order-by" value="id" />

            <div class="header">
                <span class="label">Фильтры</span>
            </div>
            <div class="field with-button">
                <select id="combo-filter-by">
                    <option value="id">ID</option>
                    <option value="name">Имя</option>
                    <option value="coordinates.x">Координата X</option>
                    <option value="coordinates.y">Координата Y</option>
                    <option value="creationDate">Дата создания</option>
                    <option value="studentsCount">Количество студентов</option>
                    <option value="formOfEducation">Форма обучения</option>
                    <option value="semesterEnum">Семестр</option>
                    <option value="groupAdmin.name">Имя старосты</option>
                    <option value="groupAdmin.passportID">ID паспорта старосты</option>
                    <option value="groupAdmin.eyeColor">Цвет глаз старосты</option>
                    <option value="groupAdmin.hairColor">Цвет волос старосты</option>
                    <option value="groupAdmin.nationality">Национальность старосты</option>
                </select>
                <span class="label">&nbsp;=&nbsp;</span>
                <div id="block-filter-by" class="input-wrapper">
                    <input type="text" id="input-filter-by" />
                </div>
                <input type="button" id="btn-filter-by-plus" value="+" />
            </div>
            <div class="field with-button">
                <select multiple id="list-filter-by">
                </select>
                <input type="button" id="btn-filter-by-minus" value="–" disabled />
            </div>
            <input type="hidden" name="filterBy" id="hidden-filter-by" />

            <div class="wide-button noselect center-text submit">Показать</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-count-higher-semester" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Группы выше семестра
        </h1>
        <hr/>
        <form method="post" action="/count_higher_semester">
            <div class="field" style="margin-bottom: 10px;">
                <label>Семестр</label>
                <select name="semesterEnum">
                    <option value="SECOND">Второй</option>
                    <option value="THIRD">Третий</option>
                    <option value="FOURTH">Четвёртый</option>
                    <option value="FIFTH">Пятый</option>
                    <option value="SEVENTH">Седьмой</option>
                </select>
            </div>


            <div class="wide-button noselect center-text submit">Посчитать</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-prefix" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Найти группы по префиксу
        </h1>
        <hr/>
        <form method="post" action="/get_starts_from">
            <div class="field" style="margin-bottom: 10px;">
                <label>Префикс</label>
                <input type="text" name="prefix" />
            </div>


            <div class="wide-button noselect center-text submit">Найти</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-change-edu-form" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Сменить форму обучения группы
        </h1>
        <hr/>
        <form method="post" action="/change_edu_form">
            <div class="field" style="margin-bottom: 10px;">
                <label><b>ID</b></label><input type="text" name="id" />
            </div>

            <div class="field">
                <label>Форма обучения</label>
                <select name="formOfEducation">
                    <option value="DISTANCE_EDUCATION">Дистанционное обучение</option>
                    <option value="FULL_TIME_EDUCATION">Полный день</option>
                    <option value="EVENING_CLASSES">Вечернее обучение</option>
                </select>
            </div>

            <div class="wide-button noselect center-text submit">Сменить</div>
            <div class="centered hidden spinner">
                <div>
                    <object data="/spinner.svg" width="40px" height="40px"></object>
                </div>
            </div>
        </form>
    </div>

    <div id="page-show" class="page wide-page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Список групп
        </h1>
        <hr/>
        <table>
            <thead>
                <tr>
                    <td>ID</td>
                    <td>Имя</td>
                    <td colspan="2">Координаты</td>
                    <td>Ч. студентов</td>
                    <td>Форма обучения</td>
                    <td>Семестр</td>
                    <td colspan="5">Староста</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>X</td>
                    <td>Y</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>Имя</td>
                    <td>Паспорт</td>
                    <td>Цвет глаз</td>
                    <td>Цвет волос</td>
                    <td>Нац-сть</td>
                </tr>
            </thead>
            <tbody id="grouplist">

            </tbody>
        </table>
    </div>

    <div id="page-list" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Список
        </h1>
        <hr/>
        <table>
            <tbody id="list">

            </tbody>
        </table>
    </div>

    <div id="page-count" class="page hidden">
        <h1>
            <div class="back-button noselect">&#129136;</div>
            Количество
        </h1>
        <hr/>
        <div class="center-text">
            <span id="huge-number">0</span>
        </div>
    </div>
</div>

<div id="balloon-layer">
</div>

<script type="text/javascript" src="/index.js"></script>
</body>
</html>