package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import com.epam.library.Dao.InterfaceDao;
import com.epam.library.entity.Attribute;
import com.epam.library.entity.UserLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.epam.library.constants.ActionConstants.*;

public class AddGenre implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String core = request.getParameter("genreCore");
        Attribute genreCore = new Attribute();
        genreCore.setCoreName(core);
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
        getGenreAttributes(request, attributeList);
        addGenreToDb(request, response, genreCore, attributeList);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }

    private void getGenreAttributes(HttpServletRequest request, ArrayList<Attribute> attributeList) {
        String genreName;
        ArrayList<UserLocale> langList = new ArrayList<>();
        InterfaceDao interfaceDAO = new InterfaceDao();
        interfaceDAO.getLangs(langList);
        for (UserLocale lang : langList) {
            genreName = request.getParameter(lang.getName());
            attributeList.add(new Attribute(genreName, lang.getId()));
        }
    }

    private void addGenreToDb(HttpServletRequest request, HttpServletResponse response, Attribute genreCore, ArrayList<Attribute> attributeList) throws ServletException, IOException {
        BookDao bookDAO = new BookDao();
        try {
            bookDAO.addGenre(attributeList, genreCore);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error adding genre to database");
            request.setAttribute(EXCEPTION, "addGenreException");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
        }
    }
}