package co.grandcircus.moviedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MovieApiController {

	@Autowired
	private MovieRepo dao;

	@GetMapping("/")
	public ModelAndView redirect() {
		return new ModelAndView("redirect:/movies");
	}

	// TODO GET /movies/ID
	@GetMapping("/movies/{id}")
	public Movie getMovie(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}

	// TODO GET /movies
	// TODO GET /movies?category=...
	@GetMapping("/movies") // this method will work with url with param and without param
	public List<Movie> listMovies(@RequestParam(value = "category", required = false) String category) {
		if (category == null || category.isEmpty()) {
			return dao.findAll();
		} else {
			return dao.findByCategoryContainsIgnoreCase(category);
		}
	}

	// random movie generator by id
	@GetMapping("/movies/random")
	public Movie randomMovie(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title) {
		if (category == null || category.isEmpty()) {
			Long id = (long) ((Math.random() * 10) + 1);
			return dao.findById(id).get();
		} else {
			List<Movie> moviesInCategory = dao.findByCategory(category);
			int length = moviesInCategory.size();
			int id = (int) ((Math.random() * length));
			return moviesInCategory.get(id);
		}
	}

	// all movies by categories
	@GetMapping("/categories")
	public List<String> getAllCategories() {
		List<Movie> movieList = dao.findAll();
		List<String> movieCategories = new ArrayList<String>();
		for (Movie movie : movieList) {
			if (!movieCategories.contains(movie.getCategory())) {
				movieCategories.add(movie.getCategory());
			}
		}
		return movieCategories;
	}

	// all movies by titles
	@GetMapping("/titles")
	public List<String> getAllTitles() {
		List<Movie> movieList = dao.findAll();
		List<String> movieTitles = new ArrayList<String>();
		for (Movie movie : movieList) {
			if (!movieTitles.contains(movie.getTitle())) {
				movieTitles.add(movie.getTitle());
			}
		}
		return movieTitles;
	}

}
