package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CachedInMemoryExamination implements Examination{

    InMemoryExamination inMemoryExamination;

    private final Map<String, Double> cache = new LRUCache<>(2);

    public CachedInMemoryExamination(InMemoryExamination inMemoryExamination) {
        this.inMemoryExamination = inMemoryExamination;
    }

    @Override
    public void addScore(Score score) {
        inMemoryExamination.addScore(score);
    }

    @Override
    public Score getScore(Student student, String subject) throws ItemNotFoundException {
        return inMemoryExamination.getScore(student,subject);
    }

    @Override
    public Double getAverageForSubject(String subject) {
//        Double averageForSubject = cache.get(subject);
//        if ( averageForSubject == null ) {
//            averageForSubject = inMemoryExamination.getAverageForSubject(subject); // basicAnalytics.getAggregationByCategoryAndPlace(categoryAndPlace);
//            cache.put(subject,averageForSubject);
//        }
//        return averageForSubject;
        return  cache.computeIfAbsent(subject, inMemoryExamination::getAverageForSubject);
    }

    @Override
    public Set<Student> multipleSubmissionsStudentNames() {
        return inMemoryExamination.multipleSubmissionsStudentNames();
    }

    @Override
    public Set<Student> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        return inMemoryExamination.lastFiveStudentsWithExcellentMarkOnAnySubject();
    }

    @Override
    public Set<String> getAllSubject() {
        return inMemoryExamination.getAllSubject();
    }

    @Override
    public Collection<Score> getAllScores() {
        return inMemoryExamination.getAllScores();
    }

    @Override
    public Map<KeyStudentSubject, Score> getAllItems() {
        return inMemoryExamination.getAllItems();
    }
}
