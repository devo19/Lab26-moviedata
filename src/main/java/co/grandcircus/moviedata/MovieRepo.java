package co.grandcircus.moviedata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Long>{
	
	List<Movie> findByTitleContainsIgnoreCase(String titleMatch);
	
	List<Movie> findByCategoryContainsIgnoreCase(String category);
	
	List<Movie> findByCategory(String category);

}
