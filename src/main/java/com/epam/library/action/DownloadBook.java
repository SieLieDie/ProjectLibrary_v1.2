package com.epam.library.action;

import com.epam.library.Dao.BookDao;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.library.constants.ActionConstants.BOOK_ID;
import static com.epam.library.constants.ActionConstants.BOOK_NAME;

public class DownloadBook implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        BookDao bookDao = new BookDao();
        Translate translate = new Translate();
        ServletOutputStream out = response.getOutputStream();
        ServletInputStream in = request.getInputStream();
        try {
            Blob bookFile = bookDao.downloadBook(bookID);
            InputStream inputStream = bookFile.getBinaryStream();
            String bookName = bookDao.getBookName(bookID);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + translate.toTranslit(bookName) +".txt" + "\"");
            int i;
            while ((i = inputStream.read()) != -1) {
                out.write(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.close();
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }


    private class Translate{
        private Map<String, String> letters = new HashMap<String, String>();
        {
            letters.put("А", "A");
            letters.put("Б", "B");
            letters.put("В", "V");
            letters.put("Г", "G");
            letters.put("Д", "D");
            letters.put("Е", "E");
            letters.put("Ё", "E");
            letters.put("Ж", "Zh");
            letters.put("З", "Z");
            letters.put("И", "I");
            letters.put("Й", "I");
            letters.put("К", "K");
            letters.put("Л", "L");
            letters.put("М", "M");
            letters.put("Н", "N");
            letters.put("О", "O");
            letters.put("П", "P");
            letters.put("Р", "R");
            letters.put("С", "S");
            letters.put("Т", "T");
            letters.put("У", "U");
            letters.put("Ф", "F");
            letters.put("Х", "Kh");
            letters.put("Ц", "C");
            letters.put("Ч", "Ch");
            letters.put("Ш", "Sh");
            letters.put("Щ", "Sch");
            letters.put("Ъ", "'");
            letters.put("Ы", "Y");
            letters.put("Ъ", "'");
            letters.put("Э", "E");
            letters.put("Ю", "Yu");
            letters.put("Я", "Ya");
            letters.put("а", "a");
            letters.put("б", "b");
            letters.put("в", "v");
            letters.put("г", "g");
            letters.put("д", "d");
            letters.put("е", "e");
            letters.put("ё", "e");
            letters.put("ж", "zh");
            letters.put("з", "z");
            letters.put("и", "i");
            letters.put("й", "i");
            letters.put("к", "k");
            letters.put("л", "l");
            letters.put("м", "m");
            letters.put("н", "n");
            letters.put("о", "o");
            letters.put("п", "p");
            letters.put("р", "r");
            letters.put("с", "s");
            letters.put("т", "t");
            letters.put("у", "u");
            letters.put("ф", "f");
            letters.put("х", "h");
            letters.put("ц", "c");
            letters.put("ч", "ch");
            letters.put("ш", "sh");
            letters.put("щ", "sch");
            letters.put("ъ", "'");
            letters.put("ы", "y");
            letters.put("ъ", "'");
            letters.put("э", "e");
            letters.put("ю", "yu");
            letters.put("я", "ya");
        }

        public String toTranslit(String text) {
            StringBuilder sb = new StringBuilder(text.length());
            for (int i = 0; i<text.length(); i++) {
                String l = text.substring(i, i+1);
                if (letters.containsKey(l)) {
                    sb.append(letters.get(l));
                }
                else {
                    sb.append(l);
                }
            }
            return sb.toString();
        }
    }
}
