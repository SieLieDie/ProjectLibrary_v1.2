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

public class AddAuthor implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regex = "\\s";
        ArrayList<Attribute> authorList = getAuthorAttributes(request, regex);
        addAuthorToDb(request, response, regex, authorList);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }

    private ArrayList<Attribute> getAuthorAttributes(HttpServletRequest request, String regex) {
        ArrayList<Attribute> authorList = new ArrayList<Attribute>();
        ArrayList<UserLocale> langList = new ArrayList<>();
        InterfaceDao interfaceDAO = new InterfaceDao();
        interfaceDAO.getLangs(langList);
        for (UserLocale lang : langList) {
            String authorName = request.getParameter(lang.getName());
            String[] nameArray = authorName.split(regex);
            authorList.add(new Attribute(nameArray[0], nameArray[1], lang.getId()));
        }
        return authorList;
    }

    private void addAuthorToDb(HttpServletRequest request, HttpServletResponse response, String regex, ArrayList<Attribute> authorList) throws ServletException, IOException {
        BookDao bookDAO = new BookDao();
        Attribute authorCore = new Attribute();
        String authorCoreName = request.getParameter("authorCoreName");
        String[] coreArray = authorCoreName.split(regex);
        authorCore.setCoreName(coreArray[0]);
        authorCore.setSecondCoreName(coreArray[1]);
        try {
            bookDAO.addAuthor(authorList, authorCore);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error adding author to database");
            request.setAttribute(EXCEPTION, "addAuthorException");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
        }
    }
}