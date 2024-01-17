package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryExaminationTest {

    private Examination examination;
    @BeforeEach
    void setUp(){
        examination = new InMemoryExamination();
    }

    @Test
    void addScoreAndGetScore() throws ItemNotFoundException {
        Score score = new Score(new Student("Иван","Иванов"),"физика", 5);
        examination.addScore(score);

        Score actual = examination.getScore(new Student("Иван","Иванов"),"физика") ;
        Assertions.assertEquals(score, actual);
    }

    @Test
    void getScore() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> examination.getScore(new Student("Пётр","Иванов"),"физика"));
    }

    @Test
    void getAverageForSubject() {
        Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
        Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
        Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
        Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
        Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
        Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        examination.addScore(score01);
        examination.addScore(score02);
        examination.addScore(score03);
        examination.addScore(score04);
        examination.addScore(score05);
        examination.addScore(score06);
        examination.addScore(score07);
        Assertions.assertEquals(3.5f, examination.getAverageForSubject("физика"));
        Assertions.assertEquals(5.0f, examination.getAverageForSubject("математика"));
    }

    @Test
    void multipleSubmissionsStudentNames() {
        Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
        Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
        Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
        Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
        Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
        Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        Score score08 = new Score(new Student("Семён","Семёнов"),"математика", 5);
        Score score09 = new Score(new Student("Семён","Семёнов"),"химия", 3);
        examination.addScore(score01);
        examination.addScore(score02);
        examination.addScore(score03);
        examination.addScore(score04);
        examination.addScore(score05);
        examination.addScore(score06);
        examination.addScore(score07);
        examination.addScore(score08);
        examination.addScore(score09);
        Set<Student> forCompare = new HashSet<>(List.of(new Student("Илья","Кузнецов"), new Student("Семён","Семёнов")));
        Set<Student> multipleSubmissionsStudentNames = examination.multipleSubmissionsStudentNames();
        Assertions.assertEquals(2, multipleSubmissionsStudentNames.size());
        Assertions.assertEquals(forCompare,multipleSubmissionsStudentNames); // Исходя из того что multipleSubmissionsStudentNames() возвращает HashSet
    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
        Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
        Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
        Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
        Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
        Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        Score score08 = new Score(new Student("Семён","Семёнов"),"математика", 5);
        Score score09 = new Score(new Student("Семён","Семёнов"),"химия", 3);
        examination.addScore(score01);
        examination.addScore(score02);
        examination.addScore(score03);
        examination.addScore(score04);
        examination.addScore(score05);
        examination.addScore(score06);
        examination.addScore(score07);
        examination.addScore(score08);
        examination.addScore(score09);
        Set<Student> students = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();
        Assertions.assertEquals(3, students.size());
        Score score11 = new Score(new Student("Иван","Иванов"),"физика", 5);
        Score score12 = new Score(new Student("Пётр","Петров"),"физика", 5);
        Score score13 = new Score(new Student("Семён","Семёнов"),"физика", 5);
        Score score14 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score15 = new Score(new Student("Игорь","Ковалёв"),"химия", 5);
        Score score16 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        Score score17 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        Score score18 = new Score(new Student("Семён","Семенов"),"математика", 5);
        Score score19 = new Score(new Student("Дмитрий","Дмитриев"),"химия", 5);
        examination.addScore(score11);
        examination.addScore(score12);
        examination.addScore(score13);
        examination.addScore(score14);
        examination.addScore(score15);
        examination.addScore(score16);
        examination.addScore(score17);
        examination.addScore(score18);
        examination.addScore(score19);
        students = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();
        Assertions.assertEquals(5, students.size());
    }

    @Test
    void getAllSubject() {
        Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
        Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
        Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
        Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
        Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
        Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        Score score08 = new Score(new Student("Семён","Семёнов"),"математика", 5);
        Score score09 = new Score(new Student("Семён","Семёнов"),"химия", 3);
        examination.addScore(score01);
        examination.addScore(score02);
        examination.addScore(score03);
        examination.addScore(score04);
        examination.addScore(score05);
        examination.addScore(score06);
        examination.addScore(score07);
        examination.addScore(score08);
        examination.addScore(score09);
        Set<String> subjects = examination.getAllSubject();
        Assertions.assertEquals(3, subjects.size());
        Assertions.assertTrue(subjects.contains("химия"));
        Assertions.assertFalse(subjects.contains(5));
    }

    @Test
    void getAllScores() {
        Score score01 = new Score(new Student("Иван","Иванов"),"физика", 2);
        Score score02 = new Score(new Student("Пётр","Петров"),"физика", 3);
        Score score03 = new Score(new Student("Семён","Семёнов"),"физика", 4);
        Score score04 = new Score(new Student("Илья","Кузнецов"),"физика", 5);
        Score score05 = new Score(new Student("Илья","Кузнецов"),"химия", 5);
        Score score06 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 3);
        Score score07 = new Score(new Student("Дмитрий","Кузнецов"),"математика", 5);
        examination.addScore(score01);
        examination.addScore(score02);
        examination.addScore(score03);
        examination.addScore(score04);
        examination.addScore(score05);
        examination.addScore(score06);
        examination.addScore(score07);
        Assertions.assertEquals(6, examination.getAllScores().size());
    }
}