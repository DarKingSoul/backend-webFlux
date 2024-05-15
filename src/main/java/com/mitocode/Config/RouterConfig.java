package com.mitocode.Config;

import com.mitocode.Handler.CourseHandler;
import com.mitocode.Handler.EnRollHandler;
import com.mitocode.Handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesStudents(StudentHandler studentHandler) {
        return route(GET("/v2/students/findAll"), studentHandler::findAll)
                .andRoute(GET("/v2/students/findAllByAgeDesc"), studentHandler::findAllByAgeDesc)
                .andRoute(GET("/v2/students/findAllByAgeAsc"), studentHandler::findAllByAgeAsc)
                .andRoute(GET("/v2/students/findById/{id}"), studentHandler::findById)
                .andRoute(POST("/v2/students/save"), studentHandler::save)
                .andRoute(PUT("/v2/students/update/{id}"), studentHandler::update)
                .andRoute(DELETE("/v2/students/delete/{id}"), studentHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesCourses(CourseHandler courseHandler) {
        return route(GET("/v2/courses/findAll"), courseHandler::findAll)
                .andRoute(GET("/v2/courses/findById/{id}"), courseHandler::findById)
                .andRoute(POST("/v2/courses/save"), courseHandler::save)
                .andRoute(PUT("/v2/courses/update/{id}"), courseHandler::update)
                .andRoute(DELETE("/v2/courses/delete/{id}"), courseHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesEnRoll(EnRollHandler enRollHandler) {
        return route(GET("/v2/enRoll/findAll"), enRollHandler::findAll)
                .andRoute(GET("/v2/enRoll/findById/{id}"), enRollHandler::findById)
                .andRoute(POST("/v2/enRoll/save"), enRollHandler::save)
                .andRoute(PUT("/v2/enRoll/update/{id}"), enRollHandler::update)
                .andRoute(DELETE("/v2/enRoll/delete/{id}"), enRollHandler::delete);
    }

}
