package hexlet.code.controllers;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "task-status-controller", description = "Task status CRUD controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TaskStatusController.TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {
    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";
    private final TaskStatusService taskStatusService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get task status by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status found"),
            @ApiResponse(responseCode = "404", description = "Task status with this id not found")
    })
    @GetMapping(ID)
    public TaskStatus getById(@PathVariable("id") long id) {
        return taskStatusService.getTaskStatusById(id);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get all task statuses")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public List<TaskStatus> getAll() {
        return taskStatusService.getAllTaskStatuses();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Create new task status")
    @ApiResponse(responseCode = "201", description = "Task status created")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskStatus create(@RequestBody @Valid TaskStatusDto taskStatusDto) {
        return taskStatusService.createTaskStatus(taskStatusDto);
    }


    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Update task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status updated"),
            @ApiResponse(responseCode = "404", description = "Task status with this id not found")
    })
    @PutMapping(ID)
    public TaskStatus update(@PathVariable("id") long id,
                             @RequestBody @Valid TaskStatusDto taskStatusDto) {
        return taskStatusService.updateTaskStatus(id, taskStatusDto);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status deleted"),
            @ApiResponse(responseCode = "404", description = "Task status with this id not found")
    })
    @DeleteMapping(ID)
    public void delete(@PathVariable("id") long id) {
        taskStatusService.deleteTaskStatus(id);
    }
}
