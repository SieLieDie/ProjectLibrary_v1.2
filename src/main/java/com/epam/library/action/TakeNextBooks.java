package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import com.epam.library.action.service.GetAuthors;
import com.epam.library.action.service.GetGenres;
import com.epam.library.action.service.GetLang;
import com.epam.library.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.*;

public class TakeNextBooks implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetLang getLang = new GetLang();
        getLang.execute(request, response);
        GetGenres getGenres = new GetGenres();
        getGenres.execute(request, response);
        GetAuthors getAuthors = new GetAuthors();
        getAuthors.execute(request, response);
        Integer idFirstBook = Integer.valueOf(request.getParameter(ID_LAST_BOOK));
        if (idFirstBook<0){
            idFirstBook = 0;
        }
        List<Book> bookList = new ArrayList<Book>();
        Integer langID = (Integer) request.getSession().getAttribute(LANG_ID);
        Integer attributeID = (Integer) request.getSession().getAttribute("attributeID");
        String attributeType = String.valueOf(request.getSession().getAttribute("attributeType"));
        BookDao bookDAO = new BookDao();
        try {
            if (attributeID == null) {
                bookDAO.returnNextBooks(bookList, langID, idFirstBook);
            } else {
                bookDAO.returnNextBooks(bookList, langID, idFirstBook, attributeID, attributeType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute(BOOK_LIST, bookList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/MainPage.jsp");
        dispatcher.forward(request, response);
    }
}
