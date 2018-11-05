package wolox.training.services;

import org.json.JSONArray;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import wolox.training.models.Book;

public class OpenLibraryService {

    private String urlBase = "https://openlibrary.org/api";

    public OpenLibraryService() {}

    public StringBuffer callOpenLibray(String isbn) {
        try {
            URL obj = new URL(urlBase + "/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response;
        } catch (IOException e){
            return null;
        }
    }

    public Book parseData(StringBuffer data, String isbn) {

        if (data == null) {
            return null;
        } else {
            JSONObject myResponse = new JSONObject(data.toString());
            if(myResponse.isNull("ISBN:" + isbn)) {
                return null;
            } else {
                Object bookInfoStr = myResponse.get("ISBN:" + isbn);
                JSONObject bookInfoObj = new JSONObject(bookInfoStr.toString());

                Book eBook = new Book();
                eBook.setIsbn(isbn);
                JSONArray authorsJson = bookInfoObj.getJSONArray("authors");
                JSONArray publishersJson = bookInfoObj.getJSONArray("publishers");
                String authors = "";
                String publishers = "";
                for (int i = 0; i < authorsJson.length(); ++i) {
                    JSONObject author = authorsJson.getJSONObject(i);
                    String name = author.getString("name");
                    authors += name + ".";
                }
                eBook.setAuthor(authors);
                for (int i = 0; i < publishersJson.length(); ++i) {
                    JSONObject publisher = publishersJson.getJSONObject(i);
                    String name = publisher.getString("name");
                    publishers += name + ".";
                }
                eBook.setPublisher(publishers);
                eBook.setPages(bookInfoObj.getInt("number_of_pages"));
                eBook.setYear(bookInfoObj.getString("publish_date"));
                eBook.setTitle(bookInfoObj.getString("title"));
                eBook.setSubtitle(bookInfoObj.getString("subtitle"));

                return eBook;
            }
        }
    }

    public Book find(String isbn) {
        //TODO
        // refactor callOpenLibray to call with other parameters besides ISBN
        StringBuffer response = callOpenLibray(isbn);
        Book book = parseData(response, isbn);
        return book;
    }

}
