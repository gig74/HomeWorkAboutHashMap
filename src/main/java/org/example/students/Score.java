package org.example.students;

// Фамиилия и имя студента объединены в объект Student (фамилия добавлена по соглпсоаванию с ментором)
public record Score(Student student, String subject, int score) {
}
