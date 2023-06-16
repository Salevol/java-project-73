package hexlet.code.service.impl;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus getTaskStatusById(long id) {
        return taskStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task status not found"));
    }

    @Override
    public List<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll();
    }

    @Override
    public TaskStatus createTaskStatus(TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = TaskStatus.builder()
                .name(taskStatusDto.getName())
                .build();
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatus updateTaskStatus(long id, TaskStatusDto taskStatusDto) {
        return taskStatusRepository.findById(id)
                .map(taskStatus -> {
                    taskStatus.setName(taskStatusDto.getName());
                    return taskStatusRepository.save(taskStatus);
                })
                .orElseThrow(() -> new EntityNotFoundException("Task status not found"));
    }

    @Override
    public void deleteTaskStatus(long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task status not found"));
        taskStatusRepository.delete(taskStatus);
    }
}
