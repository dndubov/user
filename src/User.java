import java.util.*;                // Подключаем коллекции (List, Set, Arrays и т. д.)
import java.util.stream.Collectors; // Подключаем Stream API для обработки коллекций

// Класс User с полями name и age
class User {
    private String name;
    private int age;

    // Конструктор для удобного создания пользователя
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Геттеры для доступа к полям извне
    public String getName() { return name; }
    public int getAge() { return age; }

    // Переопределяем equals для корректного сравнения пользователей
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;               // Если сравниваем объект с самим собой → они равны
        if (o == null || getClass() != o.getClass()) return false; // Проверка на null и совпадение классов
        User user = (User) o;                     // Приведение к типу User
        return age == user.age && Objects.equals(name, user.name); // Сравнение по имени и возрасту
    }

    // Переопределяем hashCode, чтобы HashSet и distinct() работали корректно
    @Override
    public int hashCode() {
        return Objects.hash(name, age);           // Генерируем хэш на основе имени и возраста
    }

    // Переопределяем toString для удобного вывода
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

// Класс с методом для решения задачи
class Demo { // убрал public

    // Метод, который принимает коллекцию пользователей и возвращает 5 самых возрастных
    public static List<User> top5OldestUsers(Collection<User> users) {

        // Используем Stream API — это компактнее и читаемее, чем ручные циклы
        return users.stream()
                .distinct() // Убираем дубликаты (работает благодаря equals и hashCode)
                .sorted(
                        Comparator.comparingInt(User::getAge).reversed() // Сортировка по возрасту (от большего к меньшему)
                                .thenComparing(User::getName)         // Если возраст одинаковый → сортируем по имени
                )
                .limit(5) // Берём только 5 элементов из начала списка
                .collect(Collectors.toList()); //Собираем обратно в список
    }

    // Метод main для проверки работы
    public static void main(String[] args) {

        // Создаём список пользователей с дубликатами
        List<User> users = Arrays.asList(
                new User("Вася", 25),
                new User("Петя", 30),
                new User("Маша", 30),
                new User("Вася", 25), // дубликат
                new User("Аня", 35),
                new User("Саша", 40),
                new User("Ира", 28)
        );

        // Вызываем метод для получения 5 самых возрастных пользователей
        List<User> top5 = top5OldestUsers(users);

        // Выводим результат
        System.out.println(top5);
    }
}
