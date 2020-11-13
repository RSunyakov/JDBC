package ru.kpfu.itis.hateoasservice.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.hateoasservice.controllers.ExamController;
import ru.kpfu.itis.hateoasservice.models.Exam;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ExamRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Exam>> {
    @Override
    public EntityModel<Exam> process(EntityModel<Exam> model) {
        Exam exam = model.getContent();
        if (exam != null && (exam.getState().equals("Scheduled") || exam.getState().equals("Failure"))) {
            model.add(linkTo(methodOn(ExamController.class).pass(exam.getId())).withRel("pass"));
        }
        if (exam != null && (exam.getState().equals("Scheduled"))) {
            model.add(linkTo(methodOn(ExamController.class).failure(exam.getId())).withRel("failure"));
        }
        return model;
    }
}
