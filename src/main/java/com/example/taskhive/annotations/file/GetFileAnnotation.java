package com.example.taskhive.annotations.file;

import com.example.taskhive.domain.file.FileDB;
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
@Operation(summary = "Get file", description = "Get file from application", method = "GET")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Got file", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDB.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponseExceptionModel.class)))
})
public @interface GetFileAnnotation {
}
