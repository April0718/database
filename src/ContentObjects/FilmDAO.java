package ContentObjects;
import java.util.*;
public interface FilmDAO {
	
	public void insertFilm(Film fi);
	public void updateFilm();
	public void deleteFilm(int fid);
	public Film getFilm(int fid);
	public ArrayList<Film> getFilmByName(String fiName);

}
