package ru.kpfu.itis.hateoasservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.hateoasservice.controllers.CoursesController;
import ru.kpfu.itis.hateoasservice.models.Course;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CoursesRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Course>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        Course course = model.getContent();
        if (course != null && course.getState().equals("Draft")) {
            model.add(linkTo(methodOn(CoursesController.class).publish(course.getId())).withRel("publish"));
        }
        return model;
    }
}
