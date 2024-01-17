package org.example.students;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;


class CachedInMemoryExaminationTest {

    @Test
    void getAverageForSubject() {
        MockInMemoryExamination mockInMemoryExamination = new MockInMemoryExamination();
        CachedInMemoryExamination cachedInMemoryExamination = new CachedInMemoryExamination(mockInMemoryExamination);
        String request1 = "математика";
        String request2 = "физика";
        String request3 = "химия";

        Double avg1 = cachedInMemoryExamination.getAverageForSubject(request1);
        Double avg2 = cachedInMemoryExamination.getAverageForSubject(request2);
        cachedInMemoryExamination.getAverageForSubject(request1);
        Assertions.assertEquals(2, mockInMemoryExamination.calls);

        Double avg3 =cachedInMemoryExamination.getAverageForSubject(request3);
        Assertions.assertEquals(3,  mockInMemoryExamination.calls);

        cachedInMemoryExamination.getAverageForSubject(request1);
        Assertions.assertEquals(3,  mockInMemoryExamination.calls);

        cachedInMemoryExamination.getAverageForSubject(request2);
        Assertions.assertEquals(4,  mockInMemoryExamination.calls);

    }

    // Для теста делаем унаследованный класс со своим набором данных и счётчиком обращений к набору данных
    private class MockInMemoryExamination extends InMemoryExamination {

        public int calls = 0;
        @Override
        public Map<KeyStudentSubject, Score> getAllItems() {
            Map<KeyStudentSubject, Score> mockItems = new LinkedHashMap<>();
            calls++;
            Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
            Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
            Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
            Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
            Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
            Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
            Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);


            KeyStudentSubject keyStudentSubject01 = new KeyStudentSubject(score01.student(), score01.subject());
            KeyStudentSubject keyStudentSubject02 = new KeyStudentSubject(score02.student(), score02.subject());
            KeyStudentSubject keyStudentSubject03 = new KeyStudentSubject(score03.student(), score03.subject());
            KeyStudentSubject keyStudentSubject04 = new KeyStudentSubject(score04.student(), score04.subject());
            KeyStudentSubject keyStudentSubject05 = new KeyStudentSubject(score05.student(), score05.subject());
            KeyStudentSubject keyStudentSubject06 = new KeyStudentSubject(score06.student(), score06.subject());
            KeyStudentSubject keyStudentSubject07 = new KeyStudentSubject(score07.student(), score07.subject());

            mockItems.put(keyStudentSubject01,score01);
            mockItems.put(keyStudentSubject02,score02);
            mockItems.put(keyStudentSubject03,score03);
            mockItems.put(keyStudentSubject04,score04);
            mockItems.put(keyStudentSubject05,score05);
            mockItems.put(keyStudentSubject06,score06);
            mockItems.put(keyStudentSubject07,score07);

            return mockItems;
        }
    }
}

