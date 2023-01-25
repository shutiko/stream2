package Sstream2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {


        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        Stream<Person> minorsStream = persons.stream();
        System.out.println("Количество людей моложе 18 лет - " + minorsStream.filter(person -> person.getAge() < 18).count());

        Stream<Person> conscriptsStream = persons.stream();
        // Получаем список призывников
        List<String> conscriptsList = conscriptsStream.filter(person -> person.getSex() == Sex.MAN && person.getAge() > 18 && person.getAge() < 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());

        Stream<Person> workableWomanStream = persons.stream();
        List<Person> workableListWoman = workableWomanStream
                .filter(person -> person.getAge() > 18 && person.getAge() < 60 && person.getSex() == Sex.WOMAN && person.getEducation() == Education.HIGHER)
                .collect(Collectors.toList());
        Stream<Person> workableManStream = persons.stream();
        List<Person> workableListMan = workableManStream
                .filter(person -> person.getAge() > 18 && person.getAge() < 65 && person.getSex() == Sex.MAN && person.getEducation() == Education.HIGHER)
                .collect(Collectors.toList());
        List<Person> workableListUnsorted = new ArrayList<>(workableListWoman);
        workableListUnsorted.addAll(workableListMan);
        // Получаем список потенциально работоспособных людей отсортированный по алфавиту
        List<Person> workableList = workableListUnsorted.stream()
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}
