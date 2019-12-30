package com.dongfang.advanced.annotation;

@MyAnnotation
@TableMapping(value = "tb_student")
public class Student {
    @FieldMapping(columnName = "ID", type = "int", length = 10)
    private int id;

    @FieldMapping(columnName = "STUDENT_NAME", type = "varchar", length = 10)
    private String studentName;

    @FieldMapping(columnName = "AGE", type = "int", length = 3)
    private int age;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
