package com.is1di.userservicegradle.service.direction;

import com.is1di.userservicegradle.entity.direction.Direction;
import com.is1di.userservicegradle.entity.direction.Discipline;
import com.is1di.userservicegradle.entity.users.Teacher;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.exception.NotUniqueException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.users.TeacherService;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class DisciplineService {
    private final MongoOperations mongoOperations;
    private final TeacherService teacherService;
    private final MessageService messageService;

    public Discipline create(String dirTitle, Discipline discipline) {
        //TODO add course and predmet (grpc)
        if (teacherService.existsById(discipline.getTeacherId())) {
            if (!exists(dirTitle, discipline.getTitle())) {
                return Optional.ofNullable(mongoOperations.update(Direction.class)
                                .matching(Query.query(Criteria.where("title").is(dirTitle)))
                                .apply(new Update().addToSet("disciplines").value(discipline))
                                .withOptions(FindAndModifyOptions.options().returnNew(true))
                                .findAndModifyValue())
                        .map(Direction::getDisciplines)
                        .map(di -> di.stream().filter(d -> d.getTitle().equals(discipline.getTitle()))
                                .findFirst()
                                .orElseThrow(() -> new NotFoundException("discipline %s not found".formatted(discipline.getTitle()))))
                        .orElseThrow(() -> new NotFoundException("direction not found"));
            }
            throw new NotUniqueException(
                    messageService.getMessage(MessageMethod.DISC_ALREADY_EXISTS, discipline.getTitle())
            );

        }
        throw new NotFoundException(
                messageService.getMessage(MessageMethod.TEACHER_NOT_FOUND, discipline.getTeacherId().toString())
        );
    }

    public Discipline getDiscipline(String dirTitle, String disciplineTitle) {
        AtomicReference<Discipline> discipline = new AtomicReference<>();
        Optional.ofNullable(mongoOperations.findOne(Query.query(Criteria.where("title").is(dirTitle)), Direction.class))
                .map(Direction::getDisciplines).ifPresentOrElse(d -> discipline.set(
                                d.stream()
                                        .filter(dis -> dis.getTitle().equals(disciplineTitle))
                                        .findFirst()
                                        .orElseThrow(() -> new NotFoundException(
                                                        messageService.getMessage(MessageMethod.DISC_NOT_FOUND, disciplineTitle)
                                                )
                                        )),
                        () -> {
                            throw new NotFoundException(
                                    messageService.getMessage(MessageMethod.DISC_NOT_FOUND, disciplineTitle)
                            );
                        });
        return discipline.get();
    }

    public boolean exists(String dirTitle, String disTitle) {
        return mongoOperations.exists(Query.query(Criteria.where("title").is(dirTitle)
                .and("disciplines").elemMatch(Criteria.where("title").is(disTitle))), Direction.class);
    }

    public Set<Discipline> getAllByDir(String dirTitle) {
        return Optional.ofNullable(mongoOperations.findOne(Query.query(Criteria.where("title").is(dirTitle)), Direction.class))
                .map(Direction::getDisciplines)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, dirTitle)
                ));
    }

    public Teacher getByDisc(String dirTitle, String discTitle) {
        ObjectId teacherId;
        if ((teacherId = getDiscipline(dirTitle, discTitle).getTeacherId()) != null)
            return teacherService.getById(teacherId);
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.TEACHER_NOT_FOUND, "lol")
        );
    }

    public Discipline updateCourseAndSubject(String dirTitle, Discipline created) {
        return Objects.requireNonNull(mongoOperations.update(Direction.class)
                        .matching(Query.query(Criteria.where("title").is(dirTitle).and("disciplines").is(created.getTitle())))
                        .apply(new Update().setOnInsert("disciplines.$[disc].courseId", created.getCourseId()).set("disciplines.$[disc].academicSubjectId", created.getAcademicSubjectId())
                                .filterArray(Criteria.where("disc.title").is(created.getTitle())))
                        .withOptions(FindAndModifyOptions.options().returnNew(true))
                        .findAndModifyValue())
                .getDisciplines().stream().filter(dis -> dis.getTitle().equals(created.getTitle())).findFirst()
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.DISC_NOT_FOUND, created.getTitle())
                ));
    }
}
