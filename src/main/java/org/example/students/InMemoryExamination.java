package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryExamination implements Examination{
    public static final int INITIAL_CAPACITY = 256;
    // LinkedHashMap - потому что в одном из заданий требуются именно последние студенты,
    // а последовательность заполнения для именно "последних" сохранится только в LinkedHashMap
    private final Map<KeyStudentSubject, Score> items = new LinkedHashMap<>(INITIAL_CAPACITY);

    @Override
    // добавить сдачу студента (в зачет идет только последняя сдача, хранить все сдачи студента по одному и тому же предмету не нужно)
    public void addScore(Score score) {
        KeyStudentSubject keyStudentSubject = new KeyStudentSubject(score.student(), score.subject());
        items.remove(keyStudentSubject); // чтобы всегда добавлялась именно в конец последняя оценка
        items.put(keyStudentSubject,score);
    }

    @Override
    // получить сдачу студента по имени и фамилии и предмету
    public Score getScore(Student student, String subject) throws ItemNotFoundException {
        Score score = items.get(new KeyStudentSubject(student, subject));
        if (score == null) {
            throw new ItemNotFoundException( (new KeyStudentSubject(student, subject)).toString());
        }
        return score;
    }

    @Override
    // вывод средней оценки по предмету
    public Double getAverageForSubject(String subject) {
        Map<KeyStudentSubject, Score> copiedItems = getAllItems();
        return copiedItems.values().stream().filter(item -> item.subject().equals(subject)).mapToDouble(item -> item.score()).average().getAsDouble();
    }

    @Override
    // вывод студентов кто сдавал более одного раза
    public Set<Student> multipleSubmissionsStudentNames() {
        Set<Student> elements = new HashSet<>();
        Set<Student> studentHashSet = new HashSet<>();
        for (Score item : items.values()) {
            Student e = item.student();
            if (!elements.add(e)) {
                studentHashSet.add(e);
            }
        }
        return studentHashSet;
    }

    @Override
    // вывод последних пяти студентов сдавших на отлично по любому предмету
    public Set<Student> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        // Для возврата упорядоченного списка студентов начиная с последнего
        Set<Student> retSet = new LinkedHashSet<>();

        // создание итератора для просмотра с конца нашего списка (в условии пять "последних")
        LinkedList<KeyStudentSubject> listKeys = new LinkedList<KeyStudentSubject>(items.keySet());
        Iterator<KeyStudentSubject> iterator = listKeys.descendingIterator();

        while(iterator.hasNext()){
            Score score = items.get(iterator.next());
            if (score.score() == 5) {
                retSet.add(score.student());
                // Выход если последние пять студентов уже нашлись
                if (retSet.size() >= 5) break;
            }
        }

        return retSet;
    }

    @Override
    // вывод всех сданных предметов
    public Set<String> getAllSubject() {
        return items.values().stream().map(item -> item.subject()).collect(Collectors.toSet());
    }

    // Возврат копии рабочего хранилища
    @Override
    public Map<KeyStudentSubject, Score> getAllItems() {
        return new LinkedHashMap(items);
    }

    @Override
    // На всякий случай - выдача всех результатов экзаменов в List
    public Collection<Score> getAllScores() {
        return items.values().stream().collect(Collectors.toList());
    }


}
