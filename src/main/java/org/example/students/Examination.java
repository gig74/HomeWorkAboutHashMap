package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

public interface Examination {
    void addScore(Score score);

    Score getScore(Student student, String subject) throws ItemNotFoundException;

    Double getAverageForSubject(String subject);

    Set<Student> multipleSubmissionsStudentNames();

    Set<Student> lastFiveStudentsWithExcellentMarkOnAnySubject();

    Set<String> getAllSubject();

    Collection<Score> getAllScores();

    public Map<KeyStudentSubject, Score> getAllItems();
}
