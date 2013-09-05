package wad.myalbums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        request.setAttribute("albums", generateAlbums());
        
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }

    public List<Album> generateAlbums() {
        List<Album> albums = new ArrayList<Album>();

        Album closerToBeingHere =
                new Album("rendezvous park", "closer to being here",
                Arrays.asList("soothe", "closer to being here", "ascension"));
        albums.add(closerToBeingHere);

        Album tdydn =
                new Album("rendezvous park", "the days you didn't notice",
                Arrays.asList("and grey was the morning...",
                "fell asleep on my lap (eternally)",
                "down - part I",
                "down - part II",
                "anything but hopeless",
                "unnoticed beginnings",
                "...leaves"));
        albums.add(tdydn);

        return albums;
    }
}
