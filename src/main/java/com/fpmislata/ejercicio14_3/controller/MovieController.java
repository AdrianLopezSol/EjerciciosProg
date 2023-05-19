package com.fpmislata.ejercicio14_3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fpmislata.ejercicio14_3.domain.entity.Actor;
import com.fpmislata.ejercicio14_3.domain.entity.Director;
import com.fpmislata.ejercicio14_3.domain.entity.Movie;
import com.fpmislata.ejercicio14_3.domain.service.ActorService;
import com.fpmislata.ejercicio14_3.domain.service.DirectorService;
import com.fpmislata.ejercicio14_3.domain.service.MovieService;
import com.fpmislata.ejercicio14_3.domain.service.impl.ActorServiceImpl;
import com.fpmislata.ejercicio14_3.domain.service.impl.DirectorServiceImpl;
import com.fpmislata.ejercicio14_3.domain.service.impl.MovieServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService = new MovieServiceImpl();
    private DirectorService directorService = new DirectorServiceImpl();
    private ActorService actorService = new ActorServiceImpl();
    
    @GetMapping("")
    public String ListMovies(Model model, @RequestParam (defaultValue = "1") int page) {
        try {
            List<Movie> movies = movieService.getAll(page);
            model.addAttribute("page", page);
            model.addAttribute("movies", movies);
            return "movies";                
        } catch (Exception e) {
            System.out.println("Error al procesar la petición");
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{movie_imdb_id}")
    public String DetailMovie(Model model, @PathVariable("movie_imdb_id") String imdb_id) {
        try {
            Movie movie = movieService.findByImdbId(imdb_id);
            model.addAttribute("movie", movie);
            return "movie";            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        List<Director> directors = directorService.getAll();
        List<Actor> actors = actorService.getAll();
        model.addAttribute("directors", directors);
        model.addAttribute("actors", actors);
        return "insertForm";
    }

    @PostMapping("")
    public String insert(HttpServletRequest httpServletRequest){
        try {
            String imdb_id = httpServletRequest.getParameter("imdb_id");
            String title = httpServletRequest.getParameter("title");
            int year = Integer.parseInt(httpServletRequest.getParameter("year"));
            int runtime = Integer.parseInt(httpServletRequest.getParameter("runtime"));
            Movie movie = new Movie(imdb_id, title, year, null, runtime, null);
            Director director = directorService.findByImdbId(httpServletRequest.getParameter("director_id"));
            movie.setDirector(director);
            String[] actors_id = httpServletRequest.getParameterValues("actors_id");
            List<Actor> actors = new ArrayList<>();
            for (String actor_id : actors_id) {
                Actor actor = actorService.findByImdbId(actor_id);
                actors.add(actor);
            }
            movie.setActors(actors);
            movieService.insert(movie);
            return "redirect:/movies";               
        } catch (Exception e) {
            System.out.println("Error al insertar la película");
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{imdb_id}")
    public String delete(@PathVariable("imdb_id") String imdb_id){
        try {
            movieService.delete(imdb_id);
            return "redirect:/movies";
        } catch (Exception e) {
            System.out.println("Error al borrar la película");
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/update/{imdb_id}")
    public String showUpdateForm(Model model, @PathVariable("imdb_id") String imdb_id) {
        Movie movie = movieService.findByImdbId(imdb_id);
        List<Director> directors = directorService.getAll();
        List<Actor> actors = actorService.getAll();
        model.addAttribute("movie", movie);
        model.addAttribute("directors", directors);
        model.addAttribute("actors", actors);
        return "updateForm";
    }

    @PutMapping ("/{imdb_id}")
    public String update(HttpServletRequest httpServletRequest, @PathVariable("imdb_id") String imdb_id){
        try {
            String title = httpServletRequest.getParameter("title");
            int year = Integer.parseInt(httpServletRequest.getParameter("year"));
            int runtime = Integer.parseInt(httpServletRequest.getParameter("runtime"));
            Movie movie = new Movie(imdb_id, title, year, null, runtime, null);
            Director director = directorService.findByImdbId(httpServletRequest.getParameter("director_id"));
            movie.setDirector(director);
            String[] actors_id = httpServletRequest.getParameterValues("actors_id");
            List<Actor> actors = new ArrayList<>();
            for (String actor_id : actors_id) {
                Actor actor = actorService.findByImdbId(actor_id);
                actors.add(actor);
            }
            movie.setActors(actors);
            System.out.println(movie);
            movieService.update(movie);
            return "redirect:/movies";
        } catch (Exception e) {
            System.out.println("Error al actualizar la película");
            System.out.println(e.getMessage());
            return "error";
        }
    }

}
