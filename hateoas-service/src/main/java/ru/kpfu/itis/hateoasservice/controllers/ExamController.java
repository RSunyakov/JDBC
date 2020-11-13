package ru.kpfu.itis.hateoasservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.hateoasservice.service.ExamService;

@RepositoryRestController
public class ExamController {

    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/exams/{exam-id}/pass", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> pass(@PathVariable("exam-id") Long examId) {
        return ResponseEntity.ok(EntityModel.of(examService.pass(examId)));
    }

    @RequestMapping(value ="/exams/{exam-id}/failure", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> failure(@PathVariable("exam-id") Long examId) {
        return ResponseEntity.ok(EntityModel.of(examService.failure(examId)));
    }
}
