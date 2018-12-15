import ClientHandler.MyCHandler;
import Model.Level;
import Model.ModelUtil;
import Server.MyServer;
import Server.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MainServer {

    public static void main(String[] args) {

//        SessionFactory sessionFactory = ModelUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Level dbLevel = new Level();
//        dbLevel.setBoard("SXG");
//        dbLevel.setLevel(1);
//
//        Level dbLevel2 = new Level();
//        dbLevel2.setBoard("SXXG");
//        dbLevel2.setLevel(2);
//
//        session.save(dbLevel);
//        session.save(dbLevel2);
//        session.getTransaction().commit();
//        session.close();


        Server s =  new MyServer(5555);
        s.start(new MyCHandler());
    }
}
