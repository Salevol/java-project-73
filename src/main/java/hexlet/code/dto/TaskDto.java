package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    @NotBlank(message = "Name should not be empty")
    private String name;

    private String description;

    @NotNull(message = "Task status should not be empty")
    private Long taskStatusId;

    private Long executorId;
}
