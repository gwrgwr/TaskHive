package com.example.taskhive.annotations.list;

import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.user.UserResponseDTO;
import com.example.taskhive.exceptions.HttpResponseExceptionModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.client.HttpClientErrorException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Create List", description = "Create List to application", method = "POST")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "List Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListEntity.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponseExceptionModel.class)))
})
public @interface CreateListEntityAnnotation {
}