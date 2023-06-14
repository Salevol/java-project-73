package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

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

    private List<Long> labelIds;
}
