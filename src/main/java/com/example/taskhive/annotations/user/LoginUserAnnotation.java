package com.example.taskhive.annotations.user;

import com.example.taskhive.domain.user.LoginRequestDTO;
import com.example.taskhive.exceptions.HttpResponseExceptionModel;
import com.example.taskhive.exceptions.global.InternalServerErrorException;
import com.example.taskhive.exceptions.user.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Login user", description = "Login user to application", method = "POST", requestBody = @RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class))))
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class))),
        @ApiResponse(responseCode = "404", description = "User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponseExceptionModel.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponseExceptionModel.class)))
})
public @interface LoginUserAnnotation {
}
