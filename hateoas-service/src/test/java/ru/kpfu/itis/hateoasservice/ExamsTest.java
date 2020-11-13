package ru.kpfu.itis.hateoasservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.hateoasservice.models.Auditorium;
import ru.kpfu.itis.hateoasservice.models.Course;
import ru.kpfu.itis.hateoasservice.models.Exam;
import ru.kpfu.itis.hateoasservice.service.ExamService;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ExamsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        when(examService.pass(1L)).thenReturn(passedExam());
    }

    @Test
    public void examPassedTest() throws Exception {
        mockMvc.perform(put("/exams/1/pass")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(passedExam().getName()))
                .andExpect(jsonPath("$.state").value(passedExam().getState()))
                .andDo(document("pass_exam", responseFields(
                        fieldWithPath("name").description("Название экзамена"),
                        fieldWithPath("state").description("Состояние экзамена")
                )));
    }



    private Exam passedExam() {
        return Exam.builder()
                .name("Java Lab Exam")
                .state("Passed")
                .course(new Course())
                .students(new ArrayList<>())
                .auditorium(new Auditorium())
                .id(1L)
                .build();
    }
}
