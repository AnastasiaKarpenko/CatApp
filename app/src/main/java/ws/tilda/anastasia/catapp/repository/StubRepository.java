package ws.tilda.anastasia.catapp.repository;

import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.catapp.model.Cat;

public class StubRepository {
    private  List<Cat> mCats = new ArrayList<>();

    public StubRepository() {
        Cat cat1 = new Cat();
        cat1.setId("jLtSJss72");
        cat1.setUrl("https://cdn2.thecatapi.com/images/jLtSJss72.jpg");

        Cat cat2 = new Cat();
        cat2.setId("JYAXLe0I-");
        cat2.setUrl("https://cdn2.thecatapi.com/images/JYAXLe0I-.jpg");

        Cat cat3 = new Cat();
        cat3.setId("BGB-X-OGh");
        cat3.setUrl("https://cdn2.thecatapi.com/images/BGB-X-OGh.jpg");

        Cat cat4 = new Cat();
        cat4.setId("jLtSJss72");
        cat4.setUrl("https://cdn2.thecatapi.com/images/jLtSJss72.jpg");

        Cat cat5 = new Cat();
        cat5.setId("JYAXLe0I-");
        cat5.setUrl("https://cdn2.thecatapi.com/images/JYAXLe0I-.jpg");

        Cat cat6 = new Cat();
        cat6.setId("BGB-X-OGh");
        cat6.setUrl("https://cdn2.thecatapi.com/images/BGB-X-OGh.jpg");

        mCats.add(cat1);
        mCats.add(cat2);
        mCats.add(cat3);
        mCats.add(cat4);
        mCats.add(cat5);
        mCats.add(cat6);
    }

    public List<Cat> getCats() {
        return mCats;
    }
}
